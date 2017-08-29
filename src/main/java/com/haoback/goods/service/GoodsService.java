package com.haoback.goods.service;

import com.haoback.common.service.BaseService;
import com.haoback.common.utils.ImageUtil;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.entity.GoodsRes;
import com.haoback.goods.entity.GoodsType;
import com.haoback.goods.repository.GoodsRepository;
import com.haoback.goods.vo.GoodsVo;
import com.haoback.sys.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by nong on 2017/6/5.
 */
@Service
public class GoodsService extends BaseService<Goods, Long> {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GoodsResService goodsResService;
    @Autowired
    private GoodsTypeService goodsTypeService;

    /**
     * 分页查询页面端
     * @param params
     * @return
     */
    public Page<GoodsVo> findByPageWeb(Map<String, Object> params){

        Integer pageNo = (Integer) params.get("pageNo");
        Integer pageSize = (Integer) params.get("pageSize");
        String goodsType = (String) params.get("goodsType");
        String key = (String) params.get("key");

        Pageable pageable = new PageRequest(pageNo, pageSize);

        StringBuilder hql = new StringBuilder();
        List<Object> paramters = new ArrayList<>();

        hql.append("select t FROM Goods t where t.deleted = false and t.status = '1' ");
        if(StringUtils.isNotBlank(goodsType)){
            if("hot".equals(goodsType)){
                hql.append("and exists (select 1 from GoodsType g where t.goodsType = g.id and g.deleted = 0) ");
            }else{
                hql.append("and exists (select 1 from GoodsType g where t.goodsType = g.id and g.code = ? and g.deleted = 0) ");
                paramters.add(goodsType);
            }
        }

        // 关键字搜索
        if(StringUtils.isNotBlank(key)){
            hql.append("and (t.name like ? or exists (select 1 from GoodsType g where t.goodsType = g.id and g.name like ?) ) ");
            paramters.add("%"+key+"%");
            paramters.add("%"+key+"%");

            hql.append("and exists (select 1 from GoodsType g where t.goodsType = g.id and g.deleted = 0) ");
        }

        if("hot".equals(goodsType)){
            hql.append("order by t.salesNum desc ");
        }else{
            hql.append("order by t.sort desc ");
        }

        Page<Goods> page = this.findByPage(hql.toString(), paramters, pageable);

        List<Goods> content = page.getContent();
        List<GoodsVo> result = new ArrayList<>();
        GoodsVo goodsVo = null;
        for(Goods goods : content){
            goodsVo = new GoodsVo();
            BeanUtils.copyProperties(goods, goodsVo);
            goodsVo.setId(goods.getId());

            String fileId = goodsResService.findFileId(goods.getId());
            goodsVo.setFileId(fileId);

            result.add(goodsVo);
        }

        Page<GoodsVo> resultPage = new PageImpl(result, pageable, page.getTotalElements());

        return resultPage;
    }

    /**
     * 分页查询管理平台
     * @param params
     * @return
     */
    public Page<GoodsVo> findByPageService(Map<String, Object> params){
        Integer pageNo = (Integer) params.get("pageNo");
        Integer pageSize = (Integer) params.get("pageSize");
        String goodsType = (String) params.get("goodsType");
        String name = (String) params.get("name");
        String beginDate = (String) params.get("beginDate");
        String endDate = (String) params.get("endDate");
        String validind = (String) params.get("validind");

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // 查询条件
        Specification<Goods> specification = (Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> where = new ArrayList<>();

            // 商品类型
            if(StringUtils.isNotBlank(goodsType)){
                Subquery<GoodsType> subquery = query.subquery(GoodsType.class);
                Root<GoodsType> goodsTypeRoot = subquery.from(GoodsType.class);
                subquery.select(goodsTypeRoot);

                // exists条件
                List<Predicate> existsPredicate = new ArrayList<>();
                existsPredicate.add(cb.equal(root.get("goodsType"), goodsTypeRoot.get("id")));
                existsPredicate.add(cb.equal(goodsTypeRoot.get("code"), goodsType));
                subquery.where(existsPredicate.toArray(new Predicate[]{}));

                Predicate exists = cb.exists(subquery);
                where.add(exists);
            }

            // 商品名称
            if(StringUtils.isNotBlank(name)){
                Predicate namePre = cb.like(root.get("name").as(String.class), "%"+name+"%");
                where.add(namePre);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 开始添加时间
            if(StringUtils.isNotBlank(beginDate)){
                Date beginTime = null;
                try {
                    beginTime = dateFormat.parse(beginDate + " 00:00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Predicate beginTimePre = cb.greaterThanOrEqualTo(root.get("addTime").as(Date.class), beginTime);
                where.add(beginTimePre);
            }

            // 结束添加时间
            if(StringUtils.isNotBlank(endDate)){
                Date endTime = null;
                try {
                    endTime = dateFormat.parse(endDate + " 23:59:59");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Predicate endTimePre = cb.lessThan(root.get("addTime").as(Date.class), endTime);
                where.add(endTimePre);
            }

            // 有效
            if(StringUtils.isNotBlank(validind)){
                Boolean validindBool = "1".equals(validind);
                Predicate validindPre = cb.equal(root.get("deleted").as(Boolean.class), validindBool);
                where.add(validindPre);
            }

            return cb.and(where.toArray(new Predicate[]{}));
        };

        Page<Goods> page = goodsRepository.findAll(specification, pageable);

        List<Goods> content = page.getContent();
        List<GoodsVo> result = new ArrayList<>();
        GoodsVo goodsVo = null;
        for(Goods goods : content){
            goodsVo = new GoodsVo();
            BeanUtils.copyProperties(goods, goodsVo);
            goodsVo.setId(goods.getId());
            result.add(goodsVo);
        }

        Page<GoodsVo> resultPage = new PageImpl(result, pageable, page.getTotalElements());

        return resultPage;
    }

    /**
     * 保存商品-管理平台调用
     * @return
     */
    public Map<String, Object> saveGoods(GoodsVo goodsVo, SysUser operator, String realPath){
        Map<String, Object> result = null;
        if(goodsVo.getId() == null){// 新增
            result = this.addGoods(goodsVo, operator, realPath);
        }else{
            result = this.updateGoods(goodsVo, operator, realPath);
        }

        return result;
    }

    /**
     * 更新商品
     * @param goodsVo
     * @return
     */
    private Map<String, Object> updateGoods(GoodsVo goodsVo, SysUser operator, String realPath){
        Map<String, Object> result = new HashMap<>();

        Goods goods = this.findById(goodsVo.getId());
        goods.setName(goodsVo.getName());
        goods.setInfo(goodsVo.getInfo());
        goods.setBrand(goodsVo.getBrand());
        goods.setModels(goodsVo.getModels());
        goods.setPrice(goodsVo.getPrice());
        goods.setSalesNum(goodsVo.getSalesNum());
        goods.setStatus(goodsVo.getStatus());
        goods.setSort(goodsVo.getSort());
        goods.setUrlLink(goodsVo.getUrlLink());
        goods.setUrlLinkCoupon(goodsVo.getUrlLinkCoupon());
        goods.setIsTmall(goodsVo.getIsTmall());
        goods.setDeleted(goodsVo.getDeleted());
        goods.setUpdateTime(new Date());
        goods.setUpdateOperator(operator);
        goods.setUpdateOperatorName(operator.getName());

        // 商品类目
        GoodsType goodsType = goodsTypeService.findById(goodsVo.getGoodsTypeId());
        goods.setGoodsType(goodsType);

        // 商品图片
        // 当图片更改后是base64字符串，必然包含英文逗号，否则是url
        if(StringUtils.isNotBlank(goodsVo.getImage()) && goodsVo.getImage().contains(",")){
            GoodsRes goodsRes = goodsResService.findThumbnailGoodsRes(goods.getId());

            String image = goodsVo.getImage();
            image = image.substring(image.indexOf(",")+1);

            // 获取图片保存路径 放在Tomcat根目录下的images中
//            String path = System.getProperty("user.dir");
//            path = path.substring(0, path.lastIndexOf(File.separator)) + File.separator + "images" + File.separator;
//            String filePath = path + fileId + ".jpg";

            String fileId = goodsRes.getFileId() == null ? UUID.randomUUID().toString() : goodsRes.getFileId();
            String path = realPath + "upload" + File.separator + fileId + ".jpg";

            ImageUtil.imageBase64Save(image, path, ImageUtil.quality);
        }

        result.put("code", "1");

        return result;
    }

    /**
     * 新增商品
     * @param goodsVo
     * @return
     */
    private Map<String, Object> addGoods(GoodsVo goodsVo, SysUser operator, String realPath){
        Map<String, Object> result = new HashMap<>();

        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsVo, goods);

        GoodsType goodsType = goodsTypeService.findById(goodsVo.getGoodsTypeId());
        goods.setGoodsType(goodsType);

        goods.setAddTime(new Date());
        goods.setAddOperator(operator);
        goods.setAddOperatorName(operator.getName());

        this.save(goods);

        // 保存商品图片
        String image = goodsVo.getImage();
        image = image.substring(image.indexOf(",")+1);

        // 获取图片保存路径 放在Tomcat根目录下的images中
//        String path = System.getProperty("user.dir");
//        path = path.substring(0, path.lastIndexOf(File.separator)) + File.separator + "images" + File.separator;
//        String filePath = path + fileId + ".jpg";

        String fileId = UUID.randomUUID().toString();
        String path = realPath + "upload" + File.separator + fileId + ".jpg";

        ImageUtil.imageBase64Save(image, path, ImageUtil.quality);

        GoodsRes goodsRes = new GoodsRes();
        goodsRes.setGoods(goods);
        goodsRes.setType("thumbnail");
        goodsRes.setFileId(fileId);
        goodsRes.setSort(1);
        goodsResService.save(goodsRes);

        result.put("code", "1");
        return result;
    }

    /**
     * 查找商品详情
     * @param goodsId
     * @return
     */
    public GoodsVo findDetails(Long goodsId){
        Goods goods = this.findById(goodsId);
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goods, goodsVo);
        goodsVo.setId(goods.getId());

        String fileId = goodsResService.findFileId(goods.getId());
        goodsVo.setFileId(fileId);

        goodsVo.setGoodsTypeId(goods.getGoodsType() == null ? null : goods.getGoodsType().getId());

        return goodsVo;
    }

    /**
     * 逻辑删除商品
     * @param goodsId
     * @param sysUser
     */
    public void deleteLogic(Long goodsId, SysUser sysUser){
        Goods goods = this.findById(goodsId);
        goods.setDeleted(true);
        goods.setStatus("0");
        goods.setUpdateTime(new Date());
        goods.setUpdateOperator(sysUser);
        goods.setUpdateOperatorName(sysUser.getName());
        this.save(goods);
    }
}

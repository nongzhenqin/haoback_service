package com.haoback.goods.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haoback.common.service.BaseService;
import com.haoback.common.utils.ImageUtil;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.entity.GoodsRes;
import com.haoback.goods.entity.GoodsType;
import com.haoback.goods.repository.GoodsRepository;
import com.haoback.goods.vo.GoodsVo;
import com.haoback.sys.entity.SysUser;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.request.TbkUatmFavoritesItemGetRequest;
import com.taobao.api.response.TbkTpwdCreateResponse;
import com.taobao.api.response.TbkUatmFavoritesGetResponse;
import com.taobao.api.response.TbkUatmFavoritesItemGetResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by nong on 2017/6/5.
 */
@Service
public class GoodsService extends BaseService<Goods, Long> {

    @Value("${taobao.sdk.api.serverUrl}")
    private String serverUrl;// 淘宝API URL
    @Value("${taobao.sdk.api.appKey}")
    private String appKey;// 淘宝API appKey
    @Value("${taobao.sdk.api.appSecret}")
    private String appSecret;// 淘宝API appSecret
    @Value("${taobao.sdk.api.adzoneId}")
    private Long adzoneId;// 推广位id，需要在淘宝联盟后台创建

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
        // 排序条件 zonghe-综合 new-最新 sales-销量 price-价格
        String sortKey = (String) params.get("sortKey");

        Pageable pageable = new PageRequest(pageNo, pageSize);

        StringBuilder hql = new StringBuilder();
        List<Object> paramters = new ArrayList<>();

        hql.append("select t FROM Goods t where t.deleted = false and t.status = '1' ");
        if(StringUtils.isNotBlank(goodsType)){
            if("hot".equals(goodsType)){
                hql.append("and exists (select 1 from GoodsType g where t.goodsType = g.id and g.deleted = false) ");
            }else{
                hql.append("and exists (select 1 from GoodsType g where t.goodsType = g.id and g.code = ? and g.deleted = false) ");
                paramters.add(goodsType);
            }
        }

        // 关键字搜索
        if(StringUtils.isNotBlank(key)){
            hql.append("and (t.name like ? or exists (select 1 from GoodsType g where t.goodsType = g.id and g.name like ?) ) ");
            paramters.add("%"+key+"%");
            paramters.add("%"+key+"%");

            hql.append("and exists (select 1 from GoodsType g where t.goodsType = g.id and g.deleted = false) ");
        }

        if("hot".equals(goodsType)){
            hql.append("order by t.salesNum desc ");
        }else{
            if(StringUtils.isBlank(sortKey)){
                hql.append("order by t.sort desc,t.addTime desc ");
            }else{
                switch (sortKey){
                    case "zonghe":
                        hql.append("order by t.sort desc,t.addTime desc ");
                        break;
                    case "new":
                        hql.append("order by t.addTime desc ");
                        break;
                    case "sales":
                        hql.append("order by t.salesNum desc ");
                        break;
                    case "price":
                        hql.append("order by t.price asc ");
                        break;
                    default:
                        hql.append("order by t.sort desc,t.addTime desc ");
                        break;
                }
            }
        }

        Page<Goods> page = this.findByPage(hql.toString(), paramters, pageable);

        List<Goods> content = page.getContent();
        List<GoodsVo> result = new ArrayList<>();
        GoodsVo goodsVo = null;
        for(Goods goods : content){
            goodsVo = new GoodsVo();
            BeanUtils.copyProperties(goods, goodsVo);
            goodsVo.setId(goods.getId());

            GoodsRes thumbnailGoodsRes = goodsResService.findThumbnailGoodsRes(goods.getId());
            goodsVo.setFileId(thumbnailGoodsRes.getFileId());
            goodsVo.setPicUrl(thumbnailGoodsRes.getPicUrl());

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
        String status = (String) params.get("status");

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

            if("0".equals(validind) && StringUtils.isNotBlank(status)){
                Predicate statusPre = cb.equal(root.get("status").as(String.class), status);
                where.add(statusPre);
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
        goods.setCouponAmount(goodsVo.getCouponAmount());
        goods.setTaoCommand(goodsVo.getTaoCommand());
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

    /**
     * 从淘宝联盟选品库拉取商品
     */
    public Map<String, Object> saveGoodsFromTaobao(String type, String operate, SysUser operator, String realPath) throws ApiException {
        // 获取选品库列表
        Map<String, Object> resultMap = new HashMap<>();

        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        TbkUatmFavoritesGetRequest req = new TbkUatmFavoritesGetRequest();
        req.setPageNo(1L);
        req.setPageSize(100L);
        req.setFields("favorites_title,favorites_id,type");
        req.setType(-1L);
        TbkUatmFavoritesGetResponse rsp = client.execute(req);

        JSONObject selectGroup = JSONObject.parseObject(rsp.getBody());
        if(selectGroup == null || selectGroup.size() == 0){
            resultMap.put("code", "0");
            resultMap.put("msg", "获取选品库列表为空！");
            return resultMap;
        }

        // 异常
        if(selectGroup.containsKey("error_response")){
            resultMap.put("code", "0");
            resultMap.put("msg", selectGroup.getString("error_response"));
            return resultMap;
        }

        JSONObject favoritesGetResponse = selectGroup.getJSONObject("tbk_uatm_favorites_get_response");

        int totalResults = favoritesGetResponse.getIntValue("total_results");// 商品库中的商品组总数
        if(totalResults == 0){
            resultMap.put("code", "0");
            resultMap.put("msg", "选品库总数为0");
            return resultMap;
        }

        JSONArray jsonArray = favoritesGetResponse.getJSONObject("results").getJSONArray("tbk_favorites");
        if(jsonArray == null || jsonArray.size() == 0){
            resultMap.put("code", "0");
            resultMap.put("msg", "选品库总数为0");
            return resultMap;
        }

        // 遍历选品库
        for(int i=0,len=jsonArray.size(); i<len; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            jsonObject.getInteger("type");// 选品库类型，1：普通类型，2高佣金类型
            Long favoritesId = jsonObject.getLong("favorites_id");// 选品库id
            String favoritesTitle = jsonObject.getString("favorites_title");// 选品组名称

            // 选品库名称需以#分隔
            GoodsType goodsType = null;
            if("all".equals(type)){// 拉取所有选品库
                goodsType = goodsTypeService.findByName(favoritesTitle.split("#")[0]);
                if(goodsType == null){
                    continue;
                }
            }else{
                String gtype = null;
                if("equals".equals(operate)){
                    if(type.contains(",") ? !type.contains(favoritesTitle+",") : !type.equals(favoritesTitle)){
                        continue;
                    }else{
                        gtype = favoritesTitle.split("#")[0];
                    }
                }else if("like".equals(operate)){
                    if(!favoritesTitle.contains(type)){
                        continue;
                    }else{
                        gtype = favoritesTitle.split("#")[0];
                    }
                }

                goodsType = goodsTypeService.findByName(gtype);
                if(goodsType == null){
                    continue;
                }
            }

            // 每次取100条，分页取
            int total = 0;
            long pageSize = 100L;
            long pageNo = 1L;

            total = this.saveGoodsFromSelectGroup(pageSize, pageNo, favoritesId, goodsType, operator, realPath);

            if(total > pageSize){
                while (total > pageSize*pageNo){
                    pageNo++;
                    this.saveGoodsFromSelectGroup(pageSize, pageNo, favoritesId, goodsType, operator, realPath);
                }
            }
        }

        resultMap.put("code", "1");
        resultMap.put("msg", "拉取选品库成功！");
        return resultMap;
    }

    /**
     * 获取选品库中的商品详情
     * @param pageSize
     * @param pageNo
     * @param favoritesId 选品库id
     * @param goodsType 商品类目
     * @param operator 操作人
     */
    public int saveGoodsFromSelectGroup(long pageSize, long pageNo, Long favoritesId, GoodsType goodsType, SysUser operator, String realPath) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        TbkUatmFavoritesItemGetRequest req = new TbkUatmFavoritesItemGetRequest();
        req.setPlatform(1L);// 链接形式：1：PC，2：无线，默认：１
        req.setPageSize(pageSize);// 页大小，默认20，1~100
        req.setAdzoneId(adzoneId);// 网站推广位 沃惠挑的 adzoneId 推广位id，需要在淘宝联盟后台创建；且属于appkey备案的媒体id（siteid），如何获取adzoneid，请参考，http://club.alimama.com/read-htm-tid-6333967.html?spm=0.0.0.0.msZnx5
//        req.setUnid("3456");// 自定义输入串，英文和数字组成，长度不能大于12个字符，区分不同的推广渠道
        req.setFavoritesId(favoritesId);// 选品库的id
        req.setPageNo(pageNo);// 第几页，默认：1，从1开始计数
        // 需要输出则字段列表，逗号分隔 num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type
        req.setFields("num_iid,title,pict_url,click_url,category,coupon_total_count,coupon_remain_count,coupon_click_url,coupon_info,coupon_end_time,coupon_start_time,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type");
        TbkUatmFavoritesItemGetResponse rsp = client.execute(req);

        String rspBody = rsp.getBody();
        if(StringUtils.isBlank(rspBody)){
            return 0;
        }

        JSONObject rspBodyJSONObject = JSONObject.parseObject(rspBody);
        if(rspBodyJSONObject == null || rspBodyJSONObject.size() == 0){
            return 0;
        }

        JSONObject itemGetResponse = rspBodyJSONObject.getJSONObject("tbk_uatm_favorites_item_get_response");
        if(itemGetResponse == null || itemGetResponse.size() == 0){
            return 0;
        }

        int totalResults = itemGetResponse.getIntValue("total_results");// 选品库中的商品总条数

        JSONArray itemJsonArray = itemGetResponse.getJSONObject("results").getJSONArray("uatm_tbk_item");
        if(itemJsonArray == null || itemJsonArray.size() == 0){
            return 0;
        }

        // 遍历选品库中的商品
        for(int i=0,len=itemJsonArray.size(); i<len; i++){
            JSONObject jsonObject = itemJsonArray.getJSONObject(i);
            this.saveGoodsFromSelectGroup(jsonObject, goodsType, operator, realPath);
        }

        return totalResults;
    }

    /**
     * 保存淘宝联盟选品库的商品
     * @param jsonObject
     */
    private void saveGoodsFromSelectGroup(JSONObject jsonObject, GoodsType goodsType, SysUser operator, String realPath) throws ApiException {
        Long numIid = jsonObject.getLong("num_iid");// 商品ID
        String title = jsonObject.getString("title");// 商品标题
        String pictUrl = jsonObject.getString("pict_url");// 商品主图
        JSONObject smallImages = jsonObject.getJSONObject("small_images");// 商品小图列表
        jsonObject.getBigDecimal("reserve_price");// 商品一口价格
        BigDecimal finalPrice = jsonObject.getBigDecimal("zk_final_price");// 商品折扣价格
        int userType = jsonObject.getIntValue("user_type");// 卖家类型，0表示集市，1表示商城
        String provcity = jsonObject.getString("provcity");// 宝贝所在地
        String itemUrl = jsonObject.getString("item_url");// 商品地址
        String clickUrl = jsonObject.getString("click_url");// 淘客地址
        String nick = jsonObject.getString("nick");// 卖家昵称
        String sellerId = jsonObject.getString("seller_id");// 卖家id
        Integer volume = jsonObject.getInteger("volume");// 30天销量
        jsonObject.getBigDecimal("tk_rate");// 收入比例，举例，取值为20.00，表示比例20.00%
        jsonObject.getBigDecimal("zk_final_price_wap");// 无线折扣价，即宝贝在无线上的实际售卖价格。
        jsonObject.getString("shop_title");
        jsonObject.getDate("event_start_time");// 招商活动开始时间；如果该宝贝取自普通选品组，则取值为1970-01-01 00:00:00；
        jsonObject.getDate("event_end_time");// 招行活动的结束时间；如果该宝贝取自普通的选品组，则取值为1970-01-01 00:00:00
        jsonObject.getIntValue("type");// 宝贝类型：1 普通商品； 2 鹊桥高佣金商品；3 定向招商商品；4 营销计划商品;
        String status = jsonObject.getString("status");// 宝贝状态，0失效，1有效；注：失效可能是宝贝已经下线或者是被处罚不能在进行推广
        jsonObject.getLong("category");// 后台一级类目
        String couponClickUrl = jsonObject.getString("coupon_click_url");// 商品优惠券推广链接
        jsonObject.getString("coupon_end_time");// 优惠券结束时间
        String couponInfo = jsonObject.getString("coupon_info");// 优惠券面额
        jsonObject.getString("coupon_start_time");// 优惠券开始时间
        jsonObject.getIntValue("coupon_total_count");// 优惠券总量
        jsonObject.getIntValue("coupon_remain_count");// 优惠券剩余量

        Goods goods = this.findByGoodsId(numIid);
        boolean isInsert = false;
        if(goods == null){
            goods = new Goods();
            isInsert = true;
        }

        goods.setGoodsType(goodsType);

        goods.setName(title);
        goods.setPrice(finalPrice);
        goods.setStatus(status);
        goods.setSalesNum(volume);
        goods.setSort(4);
        goods.setProvcity(provcity);
        goods.setItemUrl(itemUrl);
        goods.setNick(nick);
        goods.setSellerId(sellerId);
        goods.setUrlLink(clickUrl);
        goods.setUrlLinkCoupon(couponClickUrl);
        if(StringUtils.isNotBlank(couponInfo)){
            String coupon = couponInfo.split("减")[1].split("元")[0];
            if(NumberUtils.isNumber(coupon)){
                goods.setCouponAmount(new BigDecimal(coupon));
            }
        }
        goods.setIsTmall(userType==1);

        if(isInsert){
            // 淘口令
            String taoKouLing = this.createTaoKouLing(title, couponClickUrl, pictUrl);
            goods.setTaoCommand(taoKouLing);
            goods.setGoodsId(numIid);
            goods.setAddTime(new Date());
            goods.setAddOperator(operator);
            goods.setAddOperatorName(operator.getName());
            this.save(goods);

            // 下载商品主图到服务器
//            String image = ImageUtil.downLoadImage(pictUrl, realPath);
            String image = null;

                    // 商品主图
            GoodsRes goodsRes = new GoodsRes();
            goodsRes.setGoods(goods);
            goodsRes.setType("thumbnail");
            goodsRes.setPicUrl(pictUrl);
            goodsRes.setFileId(image);
            goodsRes.setSort(1);
            goodsResService.save(goodsRes);

            // 商品小图
            JSONArray jsonArray = smallImages.getJSONArray("string");
            for(int i=0,len=jsonArray.size(); i<len; i++){
                GoodsRes goodsResDetail = new GoodsRes();
                goodsResDetail.setGoods(goods);
                goodsResDetail.setType("detail");
                goodsResDetail.setPicUrl(jsonArray.getString(i));
                goodsResDetail.setFileId(null);
                goodsResDetail.setSort(i);
                goodsResService.save(goodsResDetail);
            }
        }else{
            goods.setUpdateTime(new Date());
            goods.setUpdateOperator(operator);
            goods.setUpdateOperatorName(operator.getName());
            this.update(goods);

            // 商品主图
            GoodsRes goodsRes = goodsResService.findThumbnailGoodsRes(goods.getId());
            if(goodsRes == null){
                goodsRes = new GoodsRes();
                goodsRes.setGoods(goods);
                goodsRes.setType("thumbnail");
                goodsRes.setPicUrl(pictUrl);
                goodsRes.setSort(1);
                goodsResService.save(goodsRes);
            }else{
                goodsRes.setPicUrl(pictUrl);
                goodsResService.update(goodsRes);
            }

            // 商品小图
            JSONArray jsonArray = smallImages.getJSONArray("string");
            for(int i=0,len=jsonArray.size(); i<len; i++){
                GoodsRes goodsRes1 = goodsResService.findByTypeAndPic(goods.getId(), "detail", jsonArray.getString(i));
                if(goodsRes1 == null){
                    GoodsRes goodsResDetail = new GoodsRes();
                    goodsResDetail.setGoods(goods);
                    goodsResDetail.setType("detail");
                    goodsResDetail.setPicUrl(jsonArray.getString(i));
                    goodsResDetail.setFileId(null);
                    goodsResDetail.setSort(i);
                    goodsResService.save(goodsResDetail);
                }else{
                    goodsRes1.setPicUrl(jsonArray.getString(i));
                    goodsResService.save(goodsRes1);
                }
            }
        }
    }

    /**
     * 生成淘口令
     * @param text 口令弹框内容
     * @param url 口令跳转目标页
     * @param logoURL 口令弹框logoURL 用商品主图即可
     * @return
     */
    public String createTaoKouLing(String text, String url, String logoURL) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
//        req.setUserId("123");
        req.setText(text);
        req.setUrl(url);
        req.setLogo(logoURL);
//        req.setExt("{}");
        TbkTpwdCreateResponse rsp = client.execute(req);
        String rspBody = rsp.getBody();
        if(StringUtils.isBlank(rspBody)){
            return null;
        }

        JSONObject rspBodyJSONObject = JSONObject.parseObject(rspBody);
        if(rspBodyJSONObject.containsKey("error_response")){
            return null;
        }

        return rspBodyJSONObject.getJSONObject("tbk_tpwd_create_response").getJSONObject("data").getString("model");
    }

    /**
     * 通过淘宝商品ID查找
     * @param goodsId 淘宝商品ID
     * @return
     */
    public Goods findByGoodsId(Long goodsId){
        return goodsRepository.findByGoodsId(goodsId);
    }
}

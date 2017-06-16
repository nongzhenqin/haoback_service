package com.haoback.goods.service;

import com.haoback.common.service.BaseService;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.entity.GoodsType;
import com.haoback.goods.repository.GoodsRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.lang3.time.FastDateParser;
import org.apache.commons.lang3.time.FastDatePrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by nong on 2017/6/5.
 */
@Service
public class GoodsService extends BaseService<Goods, Long> {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GoodsResService goodsResService;

    /**
     * 分页查询页面端
     * @param params
     * @return
     */
    public Page<Goods> findByPageWeb(Map<String, Object> params){

        Integer pageNo = (Integer) params.get("pageNo");
        Integer pageSize = (Integer) params.get("pageSize");
        String goodsType = (String) params.get("goodsType");

        Sort sort = null;

        if("hot".equals(goodsType)){
            sort = new Sort(Sort.Direction.DESC, "salesNum");

        }else{
            sort = new Sort(Sort.Direction.DESC, "sort");
        }

        Pageable pageable = new PageRequest(pageNo, pageSize, sort);
        // 查询条件
        Specification<Goods> specification = (Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate deleted = cb.equal(root.get("deleted").as(Boolean.class), false);
            if("hot".equals(goodsType)){
                return cb.and(deleted);
            }

            Subquery<GoodsType> subquery = query.subquery(GoodsType.class);
            Root<GoodsType> goodsTypeRoot = subquery.from(GoodsType.class);
            subquery.select(goodsTypeRoot);

            // exists条件
            List<Predicate> existsPredicate = new ArrayList<>();
            existsPredicate.add(cb.equal(root.get("goodsType"), goodsTypeRoot.get("id")));
            existsPredicate.add(cb.equal(goodsTypeRoot.get("code"), goodsType));
            subquery.where(existsPredicate.toArray(new Predicate[]{}));

            Predicate exists = cb.exists(subquery);

            return cb.and(deleted, exists);
        };

        Page<Goods> page = goodsRepository.findAll(specification, pageable);
        if(page != null && page.hasContent()){
            List<Goods> content = page.getContent();
            for(Goods goods : content){
                String thumbnailUrl = goodsResService.findThumbnail(goods.getId());
                goods.setThumbnailUrl(thumbnailUrl);
            }
        }
        return page;
    }

    /**
     * 分页查询管理平台
     * @param params
     * @return
     */
    public Page<Goods> findByPageService(Map<String, Object> params){
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

        return page;
    }

}

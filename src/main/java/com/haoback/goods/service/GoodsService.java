package com.haoback.goods.service;

import com.haoback.common.service.BaseService;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.entity.GoodsType;
import com.haoback.goods.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
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
     * 分页查询
     * @param params
     * @return
     */
    public Page<Goods> findByPage(Map<String, Object> params){

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

}

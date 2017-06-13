package com.haoback.goods.service;

import com.haoback.common.service.BaseService;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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
     * @param specification
     * @param pageable
     * @return
     */
    @Override
    public Page<Goods> findByPage(Specification<Goods> specification, Pageable pageable){
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

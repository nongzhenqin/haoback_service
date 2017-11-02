package com.haoback.goods.repository;

import com.haoback.common.repository.BaseRepository;
import com.haoback.goods.entity.Goods;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by nong on 2017/6/5.
 */
public interface GoodsRepository extends BaseRepository<Goods, Long> {

    /**
     * 通过淘宝商品ID查找
     * @param goodsId
     * @return
     */
    @Query("select t from Goods t where t.goodsId = ?1")
    Goods findByGoodsId(Long goodsId);
}

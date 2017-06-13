package com.haoback.goods.repository;

import com.haoback.common.repository.BaseRepository;
import com.haoback.goods.entity.GoodsRes;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by nong on 2017/6/11.
 */
public interface GoodsResRepository extends BaseRepository<GoodsRes, Long> {

    /**
     * 通过类型查找商品图片
     * @param goodsId
     * @param type
     * @return
     */
    @Query("SELECT t FROM GoodsRes t WHERE t.id = ?1 AND t.type = ?2 AND t.deleted = false")
    List<GoodsRes> getByType(Long goodsId, String type);
}

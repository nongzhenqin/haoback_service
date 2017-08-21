package com.haoback.goods.repository;

import com.haoback.common.repository.BaseRepository;
import com.haoback.goods.entity.GoodsCarousel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsCarouselRepository extends BaseRepository<GoodsCarousel, Long> {

    /**
     * 查找有效轮播图
     * @return
     */
    @Query("SELECT t FROM GoodsCarousel t WHERE t.deleted = false")
    List<GoodsCarousel> findValidGoodsCarousel();
}

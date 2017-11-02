package com.haoback.goods.repository;

import com.haoback.common.repository.BaseRepository;
import com.haoback.goods.entity.GoodsType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by nong on 2017/6/5.
 */
public interface GoodsTypeRepository extends BaseRepository<GoodsType, Long> {

    /**
     * 查询有效分类
     * @return
     */
    @Query("select t from GoodsType t where t.deleted = false")
    List<GoodsType> findAll();

    /**
     * 通过类目名称查找
     * @param name
     * @return
     */
    @Query("select t from GoodsType t where t.name = ?1")
    GoodsType findByName(String name);
}

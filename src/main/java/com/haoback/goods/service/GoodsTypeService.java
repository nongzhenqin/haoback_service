package com.haoback.goods.service;

import com.haoback.common.service.BaseService;
import com.haoback.goods.entity.GoodsType;
import com.haoback.goods.repository.GoodsTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nong on 2017/6/5.
 */
@Service
public class GoodsTypeService extends BaseService<GoodsType, Long> {

    @Autowired
    private GoodsTypeRepository goodsTypeRepository;
}

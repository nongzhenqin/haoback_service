package com.haoback.goods.service;

import com.haoback.common.service.BaseService;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nong on 2017/6/5.
 */
@Service
public class GoodsService extends BaseService<Goods, Long> {

    @Autowired
    private GoodsRepository goodsRepository;

}

package com.haoback.goods.service;

import com.haoback.common.service.BaseService;
import com.haoback.goods.entity.GoodsType;
import com.haoback.goods.repository.GoodsTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nong on 2017/6/5.
 */
@Service
public class GoodsTypeService extends BaseService<GoodsType, Long> {

    @Autowired
    private GoodsTypeRepository goodsTypeRepository;

    /**
     * 查询所有有效分类
     * @return
     */
    public List<GoodsType> findList(){
        return goodsTypeRepository.findAll();
    }

    /**
     * 逻辑删除类目
     * @param id
     */
    public void logicDelete(Long id){
        if(id == null) return;

        GoodsType goodsType = this.findById(id);
        goodsType.setDeleted(true);
        this.update(goodsType);
    }

    /**
     * 通过类目名称查找
     * @param name
     * @return
     */
    public GoodsType findByName(String name){
        return goodsTypeRepository.findByName(name);
    }
}

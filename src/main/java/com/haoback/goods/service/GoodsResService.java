package com.haoback.goods.service;

import com.haoback.common.service.BaseService;
import com.haoback.goods.entity.GoodsRes;
import com.haoback.goods.repository.GoodsRepository;
import com.haoback.goods.repository.GoodsResRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nong on 2017/6/11.
 */
@Service
public class GoodsResService extends BaseService<GoodsRes, Long> {

    @Autowired
    private GoodsResRepository goodsResRepository;

    /**
     * 根据类型查找商品图片对象集合
     * @param goodsId
     * @param type
     * @return
     */
    public List<GoodsRes> findByType(Long goodsId, String type){
        return goodsResRepository.getByType(goodsId, type);
    }

    /**
     * 查找商品图片
     * @param goodsId
     * @param type
     * @param picPath 淘宝图片路径
     * @return
     */
    public GoodsRes findByTypeAndPic(Long goodsId, String type, String picPath){
        return goodsResRepository.getByTypeAndPic(goodsId, type, picPath);
    }

    /**
     * 查找商品缩略图URL
     * @param goodsId
     * @return
     */
    public String findFileId(Long goodsId){
        List<GoodsRes> goodsRes = goodsResRepository.getByType(goodsId, "thumbnail");
        return CollectionUtils.isEmpty(goodsRes) ? null : goodsRes.get(0).getFileId();
    }

    /**
     * 查询商品缩略图对象
     * @param goodsId
     * @return
     */
    public GoodsRes findThumbnailGoodsRes(Long goodsId){
        List<GoodsRes> goodsRes = goodsResRepository.getByType(goodsId, "thumbnail");
        return CollectionUtils.isEmpty(goodsRes) ? null : goodsRes.get(0);
    }
}

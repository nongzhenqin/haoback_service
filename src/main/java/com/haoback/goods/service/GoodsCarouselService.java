package com.haoback.goods.service;

import com.haoback.common.service.BaseService;
import com.haoback.common.utils.ImageUtil;
import com.haoback.goods.entity.GoodsCarousel;
import com.haoback.goods.repository.GoodsCarouselRepository;
import com.haoback.goods.vo.GoodsCarouselVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class GoodsCarouselService extends BaseService<GoodsCarousel, Long> {

    @Autowired
    private GoodsCarouselRepository goodsCarouselRepository;

    /**
     * 新增/编辑
     * @param goodsCarouselVo
     */
    public void saveOrUpdate(GoodsCarouselVo goodsCarouselVo, String realPath){
        if(goodsCarouselVo.getId() ==  null){// 新增
            GoodsCarousel goodsCarousel = new GoodsCarousel();
            // 保存商品图片
            String image = goodsCarouselVo.getImage();
            image = image.substring(image.indexOf(",")+1);

            String fileId = UUID.randomUUID().toString();
            String path = realPath + "upload" + File.separator + fileId + ".jpg";

            ImageUtil.imageBase64Save(image, path, ImageUtil.quality);

            BeanUtils.copyProperties(goodsCarouselVo, goodsCarousel);
            goodsCarousel.setFileId(fileId);
            this.save(goodsCarousel);
        }else{// 更新
            GoodsCarousel goodsCarousel1 = this.findById(goodsCarouselVo.getId());
            if(goodsCarousel1 == null){
                return;
            }

            goodsCarousel1.setName(goodsCarouselVo.getName());
            goodsCarousel1.setDeleted(goodsCarouselVo.getDeleted());
            goodsCarousel1.setUrlLink(goodsCarouselVo.getUrlLink());
            goodsCarousel1.setUrlLinkCoupon(goodsCarouselVo.getUrlLinkCoupon());
            goodsCarousel1.setTaoCommand(goodsCarouselVo.getTaoCommand());

            // 当图片更改后是base64字符串，必然包含英文逗号，否则是url
            if(StringUtils.isNotBlank(goodsCarouselVo.getImage()) && goodsCarouselVo.getImage().contains(",")){
                String image = goodsCarouselVo.getImage();
                image = image.substring(image.indexOf(",")+1);

                String fileId = goodsCarousel1.getFileId() == null ? UUID.randomUUID().toString() : goodsCarousel1.getFileId();
                String path = realPath + "upload" + File.separator + fileId + ".jpg";

                ImageUtil.imageBase64Save(image, path, ImageUtil.quality);
                goodsCarousel1.setFileId(fileId);
            }

            this.update(goodsCarousel1);
        }
    }

    /**
     * 查找有效轮播图
     * @return
     */
    public List<GoodsCarousel> findValidGoodsCarousel(){
        return goodsCarouselRepository.findValidGoodsCarousel();
    }
}

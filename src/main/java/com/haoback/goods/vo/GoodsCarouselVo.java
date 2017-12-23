package com.haoback.goods.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nong
 */
@Getter
@Setter
public class GoodsCarouselVo {

    private Long id;
    private String name;
    private String fileId;
    private String urlLink;
    private String urlLinkCoupon;
    private String taoCommand;
    private Boolean deleted;
    private String image;
    private Boolean isWxApp;
    private Long goodsId;
}

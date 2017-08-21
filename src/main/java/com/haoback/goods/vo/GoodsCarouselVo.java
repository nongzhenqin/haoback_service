package com.haoback.goods.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsCarouselVo {

    private Long id;
    private String name;
    private String fileId;
    private String urlLink;
    private String urlLinkCoupon;
    private Boolean deleted;
    private String image;
}

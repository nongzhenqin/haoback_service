package com.haoback.goods.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情VO
 * @author nong
 */
@Getter
@Setter
public class GoodsDetailsVo implements Serializable {
    private static final long serialVersionUID = 5501360233671403756L;

    private Long id;
    private Long goodsId;
    private String name;
    private String info;
    private BigDecimal price;
    private Long goodsTypeId;
    private String goodsTypeName;
    private Integer salesNum;
    private String status;
    private Integer sort;
    private String image;
    /**
     * 商品小图列表
     */
    private List<String> smallImgs;
    private Boolean deleted;
    private String urlLink;
    private String urlLinkCoupon;
    private BigDecimal couponAmount;
    private String taoCommand;
    private Boolean isTmall;
}

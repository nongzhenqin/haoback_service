package com.haoback.goods.vo;

import com.haoback.sys.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 超级搜VO
 * @author nong
 */
@Getter
@Setter
public class GoodsSuperSearchVo implements Serializable {

    private static final long serialVersionUID = 1120307716020026249L;
    private Long goodsId;
    private String name;
    private String info;
    private String brand;
    private String models;
    private BigDecimal price;
    private Long goodsTypeId;
    private String goodsTypeName;
    private Integer salesNum;
    private String status;
    private Integer sort;
    private String image;
    // 淘宝商品主图url
    private String picUrl;
    // 商品小图列表
    private List<String> smallUrls;
    private String urlLink;
    private String urlLinkCoupon;
    private BigDecimal couponAmount;
    private String taoCommand;
    private Boolean isTmall;
    private BigDecimal commissionRate;
    private BigDecimal commissionAmount;
}

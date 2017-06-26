package com.haoback.goods.vo;

import com.haoback.sys.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by nong on 2017/6/17.
 */
@Getter
@Setter
public class GoodsVo implements Serializable {
    private Long id;
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
    private Date addTime;
    private SysUser addOperatorId;
    private String addOperatorName;
    private Date updateTime;
    private SysUser updateOperatorId;
    private String updateOperatorName;
    private String fileId;// 商品缩略图名称
    private Boolean deleted;
    private String urlLink;
    private String urlLinkCoupon;
    private Boolean isTmall;
}

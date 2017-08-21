package com.haoback.goods.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 轮播图
 */
@Entity
@Table(name = "goods_carousel")
@Data
public class GoodsCarousel extends BaseEntity<Long> {

    private static final long serialVersionUID = 436916587627149395L;

    /**
     * 名称
     */
    @Column(name = "NAME", length=50)
    private String name;

    /**
     * 图片文件名
     */
    @Column(name = "FILE_ID", length=255)
    private String fileId;

    /**
     * 商品URL
     */
    @Column(name = "URL_LINK", length=1000)
    private String urlLink;

    /**
     * 优惠券URL，当同时存在商品URL和优惠券URL时，默认用优惠券URL打开
     */
    @Column(name = "URL_LINK_COUPON", length=1000)
    private String urlLinkCoupon;

    /**
     * 逻辑删除 false/0-未删除 true/1-删除
     */
    @Column(name = "IS_DELETED")
    private Boolean deleted = Boolean.FALSE;
}

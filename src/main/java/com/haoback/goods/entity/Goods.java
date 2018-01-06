package com.haoback.goods.entity;

import com.haoback.common.entity.BaseEntity;
import com.haoback.sys.entity.SysUser;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 * Created by nong on 2017/6/5.
 */
@Entity
@Table(name = "goods")
@Data
@ToString
public class Goods extends BaseEntity<Long> {

    private static final long serialVersionUID = 382622447525273329L;

    /**
     * 淘宝商品ID
     */
    @Column(name = "goods_id", length=20)
    private Long goodsId;

    /**
     * 商品名称
     */
    @Column(name = "NAME", length=255)
    private String name;

    /**
     * 一句话简介
     */
    @Column(name = "INFO", length=255)
    private String info;

    /**
     * 品牌
     */
    @Column(name = "BRAND", length=55)
    private String brand;

    /**
     * 型号
     */
    @Column(name = "MODELS", length=55)
    private String models;

    /**
     * 商品单位
     */
    @Column(name = "UNIT", length=20)
    private Long unit;

    /**
     * 商品价格
     */
    @Column(name = "PRICE", precision = 19, scale = 2)
    private BigDecimal price;

    /**
     * 所属商品类目
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="GOODS_TYPE_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    private GoodsType goodsType;

    /**
     * 商品状态（上下架状态） 1-上架 0-下架
     */
    @Column(name = "STATUS", length=3)
    private String status;

    /**
     * 销量
     */
    @Column(name = "SALES_NUM", length=11)
    private Integer salesNum;

    /**
     * 排序权重，从0开始，数值越大越靠前，如果权重值一致则按ID从小到大排序
     */
    @Column(name = "SORT", length=11)
    private Integer sort;

    /**
     * 商品URL
     */
    @Column(name = "url_link", length=1000)
    private String urlLink;

    /**
     * 优惠券URL，当同时存在商品URL和优惠券URL时，默认用优惠券URL打开
     */
    @Column(name = "URL_LINK_COUPON", length=1000)
    private String urlLinkCoupon;

    /**
     * 优惠券金额
     */
    @Column(name = "COUPON_AMOUNT", precision = 19, scale = 2)
    private BigDecimal couponAmount;

    /**
     * 淘口令
     */
    @Column(name = "TAO_COMMAND", length=100)
    private String taoCommand;

    /**
     * 是否天猫商家 true-是 false-否
     */
    @Column(name = "IS_TMALL")
    private Boolean isTmall = Boolean.FALSE;

    /**
     * 商品所在地
     */
    @Column(name = "provcity", length=100)
    private String provcity;

    /**
     * 商品地址
     */
    @Column(name = "item_url", length=255)
    private String itemUrl;

    /**
     * 卖家昵称
     */
    @Column(name = "nick", length=50)
    private String nick;

    /**
     * 卖家id
     */
    @Column(name = "seller_id", length=50)
    private String sellerId;

    /**
     * 淘宝后台一级类目
     */
    @Column(name = "category")
    private Long category;

    /**
     * 优惠券开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "coupon_start_time")
    private Date couponStartTime;

    /**
     * 优惠券结束时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "coupon_end_time")
    private Date couponEndTime;

    /**
     * 优惠券总量
     */
    @Column(name = "coupon_total_count")
    private Integer couponTotalCount;

    /**
     * 优惠券剩余量
     */
    @Column(name = "coupon_remain_count")
    private Integer couponRemainCount;

    /**
     * 新增时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADD_TIME")
    private Date addTime;

    /**
     * 新增人
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="ADD_OPERATOR_ID")
    @NotFound(action=NotFoundAction.IGNORE)
    private SysUser addOperator;

    /**
     * 新增人名称
     */
    @Column(name = "ADD_OPERATOR_NAME", length=60)
    private String addOperatorName;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="UPDATE_OPERATOR_ID")
    @NotFound(action=NotFoundAction.IGNORE)
    private SysUser updateOperator;

    /**
     * 更新人名称
     */
    @Column(name = "UPDATE_OPERATOR_NAME", length=60)
    private String updateOperatorName;

    /**
     * 逻辑删除 false/0-未删除 true/1-删除
     */
    @Column(name = "IS_DELETED")
    private Boolean deleted = Boolean.FALSE;

}

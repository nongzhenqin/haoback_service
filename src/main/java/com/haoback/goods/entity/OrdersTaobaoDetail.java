package com.haoback.goods.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 淘客订单表
 * @author nong
 */
@Entity
@Table(name = "orders_taobao_detail")
@Data
public class OrdersTaobaoDetail extends BaseEntity<Long> {

    private static final long serialVersionUID = 8131501016655569958L;
    /**
     * 淘宝订单号
     */
    @Column(name = "taobao_orders_no", length=50)
    private String taobaoOrdersNo;

    /**
     * 成交日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "due_date")
    private Date dueDate;

    /**
     * 订单创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_create_time")
    private Date orderCreateTime;

    /**
     * 订单点击时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_click_time")
    private Date orderClickTime;

    /**
     * 淘宝商品名
     */
    @Column(name = "goods_name", length=200)
    private String goodsName;

    /**
     * 淘宝商品ID
     */
    @Column(name = "goods_id", length=50)
    private String goodsId;

    /**
     * 掌柜旺旺
     */
    @Column(name = "wang_wang_no", length=50)
    private String wangWangNo;

    /**
     * 所属店铺
     */
    @Column(name = "store_name", length=50)
    private String storeName;

    /**
     * 商品数量
     */
    @Column(name = "goods_number", length=11)
    private Integer goodsNumber;

    /**
     * 商品单价
     */
    @Column(name = "goods_unit_price", precision = 14, scale = 2)
    private BigDecimal goodsUnitPrice;

    /**
     * 订单状态 订单结算
     */
    @Column(name = "order_status", length=20)
    private String orderStatus;

    /**
     * 订单类型 天猫 淘宝
     */
    @Column(name = "order_type", length=20)
    private String orderType;

    /**
     * 收入比例单位%
     */
    @Column(name = "income_rate", precision = 14, scale = 2)
    private BigDecimal incomeRate;

    /**
     * 分成比例 单位%
     */
    @Column(name = "div_rate", precision = 14, scale = 2)
    private BigDecimal divRate;

    /**
     * 付款金额
     */
    @Column(name = "pay_price", precision = 14, scale = 2)
    private BigDecimal payPrice;

    /**
     * 效果预估
     */
    @Column(name = "effect_estimation", precision = 14, scale = 2)
    private BigDecimal effectEstimation;

    /**
     * 结算金额
     */
    @Column(name = "due_amount", precision = 14, scale = 2)
    private BigDecimal dueAmount;

    /**
     * 预估收入
     */
    @Column(name = "estimate_income", precision = 14, scale = 2)
    private BigDecimal estimateIncome;

    /**
     * 佣金比例 单位%
     */
    @Column(name = "commission_rate", precision = 14, scale = 2)
    private BigDecimal commissionRate;

    /**
     * 佣金金额
     */
    @Column(name = "commission_amount", precision = 14, scale = 2)
    private BigDecimal commissionAmount;

    /**
     * 补贴比例 单位%
     */
    @Column(name = "subsidy_rate", precision = 14, scale = 2)
    private BigDecimal subsidyRate;

    /**
     * 补贴金额
     */
    @Column(name = "subsidy_amount", precision = 14, scale = 2)
    private BigDecimal subsidyAmount;

    /**
     * 补贴类型 天猫 淘宝
     */
    @Column(name = "subsidy_type", length=20)
    private String subsidyType;

    /**
     * 成交平台 无线 PC
     */
    @Column(name = "transaction_platform", length=20)
    private String transactionPlatform;

    /**
     * 第三方服务来源
     */
    @Column(name = "third_party_server_source", length=50)
    private String thirdPartyServerSource;

    /**
     * 类目名称
     */
    @Column(name = "class_name", length=50)
    private String className;

    /**
     * 来源媒体ID
     */
    @Column(name = "media_source_id", length=50)
    private String mediaSourceId;

    /**
     * 来源媒体名称
     */
    @Column(name = "media_source_name", length=50)
    private String mediaSourceName;

    /**
     * 广告位ID
     */
    @Column(name = "ad_id", length=50)
    private String adId;

    /**
     * 广告位名称
     */
    @Column(name = "ad_name", length=50)
    private String adName;

    /**
     * 创建日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_name", length=30)
    private String createName;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新人
     */
    @Column(name = "update_name", length=30)
    private String updateName;
}

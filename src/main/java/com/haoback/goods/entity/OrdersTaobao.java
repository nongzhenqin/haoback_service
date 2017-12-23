package com.haoback.goods.entity;

import com.haoback.common.entity.BaseEntity;
import com.haoback.sys.entity.SysUser;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 微信用户提交订单表
 * @author nong
 */
@Entity
@Table(name = "orders_taobao")
@Data
public class OrdersTaobao extends BaseEntity<Long> {
    private static final long serialVersionUID = 1463655899679780164L;

    /**
     * 所属用户
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="sys_user_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private SysUser sysUser;

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
     * 结算金额
     */
    @Column(name = "due_amount", precision = 14, scale = 2)
    private BigDecimal dueAmount;

    /**
     * 订单创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_create_time")
    private Date orderCreateTime;

    /**
     * 淘宝商品名
     */
    @Column(name = "goods_name", length=200)
    private String goodsName;

    /**
     * 淘宝商品ID
     */
    @Column(name = "goods_id", length=50)
    private Long goodsId;

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
     * 付款金额
     */
    @Column(name = "pay_price", precision = 14, scale = 2)
    private BigDecimal payPrice;

    /**
     * 佣金金额
     */
    @Column(name = "commission_amount", precision = 14, scale = 2)
    private BigDecimal commissionAmount;

    /**
     * 成交平台 无线 PC
     */
    @Column(name = "transaction_platform", length=20)
    private String transactionPlatform;

    /**
     * 类目名称
     */
    @Column(name = "class_name", length=50)
    private String className;

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

    /**
     * 状态 0-审核中 1-审核通过 2-审核不通过
     */
    @Column(name = "status", length=1)
    private String status;

    /**
     * 是否删除 false/0-未删除 true/1-删除
     */
    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;
}

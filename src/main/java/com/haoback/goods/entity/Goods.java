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
 * 商品表
 * Created by nong on 2017/6/5.
 */
@Entity
@Table(name = "goods")
@Data
public class Goods extends BaseEntity<Long> {

    private static final long serialVersionUID = 382622447525273329L;

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
    @OneToOne(fetch = FetchType.LAZY)
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
     * 新增时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADD_TIME")
    private Date addTime;

    /**
     * 新增人
     */
    @OneToOne(fetch = FetchType.LAZY)
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
    @OneToOne(fetch = FetchType.LAZY)
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

    /**
     * 商品缩略图URL
     */
    @Transient
    private String thumbnailUrl;
}

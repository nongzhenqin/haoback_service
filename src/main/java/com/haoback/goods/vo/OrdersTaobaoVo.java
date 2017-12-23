package com.haoback.goods.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author nong
 */
@Getter
@Setter
public class OrdersTaobaoVo implements Serializable {
    private static final long serialVersionUID = -6885279470920555073L;

    /**
     * 淘宝订单号
     */
    private String taobaoOrdersNo;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 成交日期
     */
    private Date dueDate;

    /**
     * 图片地址
     */
    private String picPath;

    /**
     * 此单获得的积分
     */
    private Integer integration;

    /**
     * 结算金额
     */
    private BigDecimal dueAmount;

    /**
     * 状态 0-审核中 1-审核通过 2-审核不通过
     */
    private String status;
}

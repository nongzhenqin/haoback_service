package com.haoback.platform.entity;

import com.haoback.common.entity.BaseEntity;
import com.haoback.goods.entity.Goods;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * PV UV详情表
 * Created by nong on 2017/6/24.
 */
@Data
@Entity
@Table(name = "pv_uv_detail")
public class PvUvDetail extends BaseEntity<Long> {
    private static final long serialVersionUID = -6162836545962010501L;

    /**
     * 新增时间/点击时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADD_TIME")
    private Date addTime;

    /**
     * 点击的商品
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="GOODS_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    private Goods goods;

    /**
     * 用户IP
     */
    @Column(name = "IP", length=100)
    private String ip;

    /**
     * 标题编码
     */
    @Column(name = "TITLE_CODE", length=20)
    private String titleCode;

    /**
     * 标题名称
     */
    @Column(name = "TITLE_NAME", length=30)
    private String titleName;

    /**
     * 域名
     */
    @Column(name = "DOMAIN", length=50)
    private String domain;

    /**
     * 用户编码
     */
    @Column(name = "USER_CODE", length=30)
    private String userCode;

    /**
     * 请求来源，为空表示用户直接从浏览器访问，百度蜘蛛访问会包含baidu.
     */
    @Column(name = "REFERER", length=500)
    private String referer;

    /**
     * PC端或者移动端
     */
    @Column(name = "FLAG", length=10)
    private String flag;

    /**
     * 来源渠道 normal-正常（通过地址访问） email-邮件广告
     */
    @Column(name = "source", length=20)
    private String source;

    /**
     * 微信用户openid
     */
    @Column(name = "wx_openid", length=50)
    private String wxOpenid;
}

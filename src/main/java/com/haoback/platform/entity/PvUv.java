package com.haoback.platform.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 日PVUV表，每天一条记录
 * Created by nong on 2017/6/24.
 */
@Data
@Entity
@Table(name = "pv_uv")
public class PvUv extends BaseEntity<Long> {
    private static final long serialVersionUID = 6096707996453138072L;

    /**
     * 日期
     */
    @Column(name = "DAY_TIME", length=10)
    private String dayTime;

    /**
     * 日PV 每天同一IP仅记录一次
     */
    @Column(name = "PV", length=20)
    private Long pv;

    /**
     * 日UV 30分钟内同一IP进来不累加
     */
    @Column(name = "UV", length=20)
    private Long uv;

    /**
     * 访客IP
     */
    @Column(name = "IP", length=20)
    private Long ip;
}

package com.haoback.mail.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 邮箱信息表
 */
@Entity
@Table(name = "mail_info")
@Data
public class MailInfo extends BaseEntity<Long> {

    private static final long serialVersionUID = 2182927166675027070L;

    /**
     * 收件人地址
     */
    @Column(name = "EMAIL", length=100)
    private String email;

    /**
     * 是否已发送 1-是 0-否
     */
    @Column(name = "IS_SEND")
    private Boolean isSend = Boolean.FALSE;
}

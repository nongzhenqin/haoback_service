package com.haoback.mail.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 发送邮箱配置
 */
@Entity
@Table(name = "mail_config")
@Data
public class MailConfig extends BaseEntity<Long> {
    private static final long serialVersionUID = 5574059079726812851L;

    /**
     * 邮箱host
     */
    @Column(name = "HOST", length=100)
    private String host;

    /**
     * 发送邮件的邮箱地址
     */
    @Column(name = "ADDRESS", length=100)
    private String address;

    /**
     * 邮件备注名
     */
    @Column(name = "NICK", length=50)
    private String nick;

    /**
     *发送邮箱的账户名
     */
    @Column(name = "USER_NAME", length=100)
    private String userName;

    /**
     * 发送邮箱的密码
     */
    @Column(name = "PASSWORD", length=100)
    private String password;

    /**
     * 是否以安全方式发送
     */
    @Column(name = "IS_SSL")
    private Boolean isSsl = Boolean.FALSE;

    /**
     * 发送端口
     */
    @Column(name = "PORT", length=10)
    private String port;

    /**
     * 是否有效 1-有效 0-无效
     */
    @Column(name = "VALIDIND")
    private Boolean validind = Boolean.TRUE;

    /**
     * 邮件标题
     */
    @Column(name = "SUBJECT", length=100)
    private String subject;

    /**
     * 发送内容
     */
    @Column(name = "CONTENT")
    private String content;
}

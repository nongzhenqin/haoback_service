package com.haoback.mail.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 邮箱列表
 */
@Entity
@Table(name = "mail_list")
@Data
public class MailList extends BaseEntity<Long> {

    /**
     * 邮箱账号
     */
    @Column(name = "mail_account", length=100)
    private String mailAccount;

    /**
     * 是否已发送 1-是 0-否
     */
    @Column(name = "is_send")
    private Boolean isSend = Boolean.FALSE;

    /**
     * 发送状态 0-发送失败 1-发送成功
     */
    @Column(name = "send_status")
    private Integer sendStatus;

    /**
     * 发送次数
     */
    @Column(name = "send_count")
    private Integer sendCount;

    /**
     * 发送时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 退订 0-否 1-是
     */
    @Column(name = "send_cancle")
    private Boolean sendCancle = Boolean.FALSE;
}

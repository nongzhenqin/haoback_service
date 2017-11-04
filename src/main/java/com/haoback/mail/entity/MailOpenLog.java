package com.haoback.mail.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 打开广告邮件记录表
 */
@Entity
@Table(name = "mail_open_log")
@Data
public class MailOpenLog extends BaseEntity<Long> {

    /**
     * 邮箱账号
     */
    @Column(name = "mail_account", length=100)
    private String mailAccount;

    /**
     * 打开时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "open_time")
    private Date openTime;
}

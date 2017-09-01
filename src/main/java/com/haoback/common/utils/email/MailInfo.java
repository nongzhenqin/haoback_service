package com.haoback.common.utils.email;

import lombok.Data;
import org.apache.commons.mail.EmailAttachment;

import java.util.List;

/**
 * 邮件相关信息
 */
@Data
public class MailInfo {
    // 收件人
    private List<String> toAddress;
    // 抄送人地址
    private List<String> ccAddress;
    // 密送人
    private List<String> bccAddress;
    // 附件信息
    private List<EmailAttachment> attachments;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;
}

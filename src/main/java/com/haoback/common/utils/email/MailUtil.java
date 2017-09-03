package com.haoback.common.utils.email;

import com.haoback.mail.entity.MailConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.List;

/**
 * 邮件发送工具类
 */
public class MailUtil {

    /**
     * 发送 邮件方法 (Html格式，支持附件)
     * @param mailInfo 收件人信息
     * @param mailConfig 发件人信息配置
     */
    public static boolean sendEmail(MailInfo mailInfo, MailConfig mailConfig) {

        try {
            HtmlEmail email = new HtmlEmail();
            // 配置信息
            email.setHostName(mailConfig.getHost());
            email.setFrom(mailConfig.getAddress(), mailConfig.getNick());
            email.setAuthentication(mailConfig.getUserName(), mailConfig.getPassword());
            email.setCharset("UTF-8");
            email.setSubject(mailInfo.getSubject());
            email.setHtmlMsg(mailInfo.getContent());
            if(mailConfig.getIsSsl()){
                email.setSSLOnConnect(mailConfig.getIsSsl());
                email.setSslSmtpPort(mailConfig.getPort());
            }

            // 添加附件
            List<EmailAttachment> attachments = mailInfo.getAttachments();
            if (CollectionUtils.isNotEmpty(attachments)) {
                for (int i = 0; i < attachments.size(); i++) {
                    email.attach(attachments.get(i));
                }
            }

            // 收件人
            List<String> toAddress = mailInfo.getToAddress();
            if (CollectionUtils.isNotEmpty(toAddress)) {
                for (int i = 0; i < toAddress.size(); i++) {
                    email.addTo(toAddress.get(i));
                }
            }
            // 抄送人
            List<String> ccAddress = mailInfo.getCcAddress();
            if (CollectionUtils.isNotEmpty(ccAddress)) {
                for (int i = 0; i < ccAddress.size(); i++) {
                    email.addCc(ccAddress.get(i));
                }
            }
            //邮件模板 密送人
            List<String> bccAddress = mailInfo.getBccAddress();
            if (CollectionUtils.isNotEmpty(bccAddress)) {
                for (int i = 0; i < bccAddress.size(); i++) {
                    email.addBcc(ccAddress.get(i));
                }
            }

            email.send();

            System.out.println("邮件发送成功！");
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }

    }
}

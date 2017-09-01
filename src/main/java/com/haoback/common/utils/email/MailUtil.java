package com.haoback.common.utils.email;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.List;

/**
 * 邮件发送工具类
 */
public class MailUtil {

    //邮箱
    private static String mailServerHost = "smtp.qq.com";
    private static String mailSenderAddress = "694159256@qq.com";
    private static String mailSenderNick = "我会挑优选生活";
    private static String mailSenderUsername = "694159256@qq.com";
    private static String mailSenderPassword = "lmnbnowltrpmbcec";

    /**
     * 发送 邮件方法 (Html格式，支持附件)
     *
     * @return void
     */
    public static void sendEmail(MailInfo mailInfo) {

        try {
            HtmlEmail email = new HtmlEmail();
            // 配置信息
            email.setHostName(mailServerHost);
            email.setFrom(mailSenderAddress,mailSenderNick);
            email.setAuthentication(mailSenderUsername,mailSenderPassword);
            email.setCharset("UTF-8");
            email.setSubject(mailInfo.getSubject());
            email.setHtmlMsg(mailInfo.getContent());

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
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }
}

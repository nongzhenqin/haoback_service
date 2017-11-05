package com.haoback.mail.service;

import com.haoback.common.service.BaseService;
import com.haoback.common.utils.email.MailUtil;
import com.haoback.mail.entity.MailConfig;
import com.haoback.mail.entity.MailInfo;
import com.haoback.mail.entity.MailList;
import com.haoback.mail.repository.MailListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MailListService extends BaseService<MailList, Long> {

    @Autowired
    private MailListRepository mailListRepository;
    @Autowired
    private MailConfigService mailConfigService;

    /**
     * 发送营销邮件
     * @param sendTotal
     * @return
     */
    public Map<String, Object> sendAd(int sendTotal) throws InterruptedException {
        Map<String, Object> resultMap = new HashMap<>();
        Pageable pageable = new PageRequest(0, sendTotal);

        Page<MailList> mailLists = mailListRepository.find(pageable);

        MailConfig mailConfig = mailConfigService.findOne();

        List<MailList> mailListsContent = mailLists.getContent();

        com.haoback.common.utils.email.MailInfo mailInfo = new com.haoback.common.utils.email.MailInfo();
        String content = mailConfig.getContent();
        mailInfo.setSubject(mailConfig.getSubject());

        List<Map> resultList = new ArrayList<>();
        Map map = null;

        int sendSuccess = 0;

        for(MailList m : mailListsContent){
            map = new HashMap();
            mailInfo.setToAddress(Arrays.asList(m.getMailAccount()));
            // 替换@tihuan_mail_account，写入收件人的邮箱地址
            String sendContent = content.replaceAll("@tihuan_mail_account", m.getMailAccount());
            mailInfo.setContent(sendContent);
            boolean result = MailUtil.sendEmail(mailInfo, mailConfig);
            if(result) {
                sendSuccess++;
                m.setSendStatus(1);
                m.setSendCount(m.getSendCount() == null ? 1 : m.getSendCount() + 1);
                m.setSendTime(new Date());
            }else {
                m.setSendStatus(0);
            }
            m.setIsSend(true);// 发送后设置已发送标志，无论是否成功
            this.update(m);
            map.put(m.getMailAccount(), result ? "发送成功" : "发送失败");
            resultList.add(map);

            Random random = new Random();
            int sleep = (random.nextInt(3) + 1) * 1000;
            Thread.sleep(sleep);
        }

        resultMap.put("resultList", resultList);
        resultMap.put("msg", "发送成功数："+sendSuccess+"，发送失败数："+(sendTotal - sendSuccess));

        return resultMap;
    }

    /**
     * 通过有效账号查找
     * @param mailAccount
     * @return
     */
    public MailList findByMailAccount(String mailAccount){
        return mailListRepository.findByMailAccount(mailAccount);
    }
}

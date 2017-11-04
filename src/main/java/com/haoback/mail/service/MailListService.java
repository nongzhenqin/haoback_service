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
    public Map<String, Object> sendAd(int sendTotal){
        Map<String, Object> resultMap = new HashMap<>();
        Pageable pageable = new PageRequest(0, sendTotal);

        Page<MailList> mailLists = mailListRepository.find(pageable);

        MailConfig mailConfig = mailConfigService.findOne();

        List<MailList> mailListsContent = mailLists.getContent();

        com.haoback.common.utils.email.MailInfo mailInfo = new com.haoback.common.utils.email.MailInfo();
        mailInfo.setSubject(mailConfig.getSubject());
        mailInfo.setContent(mailConfig.getContent());

        List<Map> resultList = new ArrayList<>();
        Map map = null;

        int sendSuccess = 0;

        for(MailList m : mailListsContent){
            map = new HashMap();
            mailInfo.setToAddress(Arrays.asList(m.getMailAccount()));
            boolean result = MailUtil.sendEmail(mailInfo, mailConfig);
            if(result) sendSuccess++;
            m.setIsSend(true);// 发送后设置已发送标志，无论是否成功
            this.update(m);
            map.put(m.getMailAccount(), result ? "发送成功" : "发送失败");
            resultList.add(map);
        }

        resultMap.put("resultList", resultList);
        resultMap.put("msg", "发送成功数："+sendSuccess+"，发送失败数："+(sendTotal - sendSuccess));

        return resultMap;
    }
}

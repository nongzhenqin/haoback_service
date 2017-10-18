package com.haoback.mail.service;

import com.haoback.common.service.BaseService;
import com.haoback.common.utils.email.MailUtil;
import com.haoback.mail.entity.MailConfig;
import com.haoback.mail.entity.MailInfo;
import com.haoback.mail.repository.MailInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MailInfoService extends BaseService<MailInfo, Long> {

    @Autowired
    private MailInfoRepository mailInfoRepository;
    @Autowired
    private MailConfigService mailConfigService;

    /**
     * 查找未发送过的收件人
     * @return
     */
    public Page<MailInfo> find(Pageable pageable){
        return mailInfoRepository.find(pageable);
    }

    /**
     * 发送广告邮件
     * @return
     */
    public List<Map> sendAd(){
        Pageable pageableMailInfo = new PageRequest(0, 100);

        Page<MailInfo> mailInfos = this.find(pageableMailInfo);
        MailConfig mailConfig = mailConfigService.findOne();

        List<MailInfo> mailInfoList = mailInfos.getContent();

        com.haoback.common.utils.email.MailInfo mailInfo = new com.haoback.common.utils.email.MailInfo();
        mailInfo.setSubject(mailConfig.getSubject());
        mailInfo.setContent(mailConfig.getContent());

        List<Map> resultList = new ArrayList<>();
        Map map = null;

        for(MailInfo m : mailInfoList){
            map = new HashMap();
            mailInfo.setToAddress(Arrays.asList(m.getEmail()));
            boolean result = MailUtil.sendEmail(mailInfo, mailConfig);
            m.setIsSend(true);// 发送后设置已发送标志，无论是否成功
            this.update(m);
            map.put(m.getEmail(), result ? "发送成功" : "发送失败");
            resultList.add(map);
        }

        return resultList;
    }

}

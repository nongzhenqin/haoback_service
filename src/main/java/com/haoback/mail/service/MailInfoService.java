package com.haoback.mail.service;

import com.haoback.common.service.BaseService;
import com.haoback.mail.entity.MailInfo;
import com.haoback.mail.repository.MailInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MailInfoService extends BaseService<MailInfo, Long> {

    @Autowired
    private MailInfoRepository mailInfoRepository;

    /**
     * 查找未发送过的收件人
     * @return
     */
    public Page<MailInfo> find(Pageable pageable){
        return mailInfoRepository.find(pageable);
    }

}

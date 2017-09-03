package com.haoback.mail.service;

import com.haoback.common.service.BaseService;
import com.haoback.mail.entity.MailConfig;
import com.haoback.mail.repository.MailConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MailConfigService extends BaseService<MailConfig, Long> {

    @Autowired
    private MailConfigRepository mailConfigRepository;

    /**
     * 查找发件人信息
     * @return
     */
    public MailConfig findOne(){
        return mailConfigRepository.findOne();
    }
}

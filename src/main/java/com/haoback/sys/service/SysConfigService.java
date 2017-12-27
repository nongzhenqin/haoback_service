package com.haoback.sys.service;

import com.haoback.common.service.BaseService;
import com.haoback.sys.entity.SysConfig;
import com.haoback.sys.repository.SysConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nong
 */
@Service
public class SysConfigService extends BaseService<SysConfig, Long> {
    @Autowired
    private SysConfigRepository sysConfigRepository;
}

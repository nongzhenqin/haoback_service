package com.haoback.sys.service;

import com.haoback.common.service.BaseService;
import com.haoback.sys.entity.SysPermission;
import com.haoback.sys.repository.SysPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysPermissionService extends BaseService<SysPermission, Long> {
	
	@Autowired
	private SysPermissionRepository sysPermissionRepository;
	
}

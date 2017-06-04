package com.haoback.sys.service;

import com.haoback.common.service.BaseService;
import com.haoback.sys.entity.SysUserRole;
import com.haoback.sys.repository.SysUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleService extends BaseService<SysUserRole, Long> {
	
	@Autowired
	private SysUserRoleRepository sysUserRoleRepository;
	
}

package com.haoback.sys.service;

import com.haoback.common.service.BaseService;
import com.haoback.sys.entity.SysRoleMenuPermission;
import com.haoback.sys.repository.SysRoleMenuPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleMenuPermissionService extends BaseService<SysRoleMenuPermission, Long> {
	
	@Autowired
	private SysRoleMenuPermissionRepository sysRoleMenuPermissionRepository;
	
}

package com.haoback.sys.service;

import com.haoback.common.service.BaseService;
import com.haoback.sys.entity.SysRole;
import com.haoback.sys.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService extends BaseService<SysRole, Long> {
	
	@Autowired
	private SysRoleRepository sysRoleRepository;

	/**
	 * 通过角色编码查找角色
	 * @param code
	 * @return
	 */
	public SysRole findByCode(String code){
		return sysRoleRepository.findByCode(code);
	}
	
}

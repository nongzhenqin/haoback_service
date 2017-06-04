package com.haoback.sys.repository;

import com.haoback.common.repository.BaseRepository;
import com.haoback.sys.entity.SysUser;

public interface SysUserRepository extends BaseRepository<SysUser, Long> {
	
	/**
	 * 根据用户名查找
	 * @param userName
	 * @return
	 */
	public SysUser findByUserName(String userName);
}

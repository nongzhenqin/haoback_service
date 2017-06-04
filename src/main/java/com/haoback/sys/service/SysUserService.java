package com.haoback.sys.service;

import com.haoback.common.service.BaseService;
import com.haoback.sys.entity.SysUser;
import com.haoback.sys.repository.SysUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysUserService extends BaseService<SysUser, Long> {
	
	@Autowired
	private SysUserRepository sysUserRepository;
	
	/**
	 * 根据用户名查找
	 * @param userName
	 * @return
	 */
	public SysUser findByUserName(String userName){
		return sysUserRepository.findByUserName(userName);
	}

	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @param sysUser
	 * @return
	 */
	public Map<String, Object> changePassword(String oldPassword, String newPassword, SysUser sysUser){
		Map<String, Object> result = new HashMap<>();

		if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)){
			result.put("code", "0");
			result.put("msg", "参数错误");
			return result;
		}

		SysUser sysUserDB = this.findById(sysUser.getId());

		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		String password = md5PasswordEncoder.encodePassword(oldPassword, sysUserDB.getSalt());
		if(!password.equals(sysUserDB.getPassword())){
			result.put("code", "2");
			result.put("msg", "旧密码错误");
			return result;
		}

		sysUserDB.randomSalt();
		sysUserDB.setPassword(md5PasswordEncoder.encodePassword(newPassword, sysUserDB.getSalt()));
		this.update(sysUserDB);

		result.put("code", "1");
		result.put("msg", "成功");

		return result;
	}
}

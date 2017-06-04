package com.haoback.sys.service;

import com.haoback.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
	
	@Autowired
	private SysUserService sysUserService;

//	@Secured("ROLE_ADMIN")
	public void registerUser(SysUser sysUser){
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//		sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));//加密

		sysUser.randomSalt();
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		sysUser.setPassword(md5PasswordEncoder.encodePassword(sysUser.getPassword(), sysUser.getSalt()));
		sysUserService.save(sysUser);
	}
}

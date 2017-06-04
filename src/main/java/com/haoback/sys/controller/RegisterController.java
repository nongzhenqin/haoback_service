package com.haoback.sys.controller;

import javax.annotation.Resource;

import com.haoback.sys.entity.SysUser;
import com.haoback.sys.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/register")
public class RegisterController {
	
	@Resource
	private RegisterService registerService;
	
	@RequestMapping("/registerUser")
	public @ResponseBody void registerUser(SysUser sysUser){
		registerService.registerUser(sysUser);
	}
	
	@RequestMapping("/registerPage")
	public String registerPage(){
		return "/page/register/register.jsp";
	}
	
}

package com.haoback.sys.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.common.utils.CommonUtils;
import com.haoback.sys.entity.SysUser;
import com.haoback.sys.service.SysUserService;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
// 操作权限控制 *、create、update、delete、view
@PreAuthorize("hasPermission('com.haoback.sys.controller.SysUserController', 'view')")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	// 也可单独控制方法
//	@PreAuthorize("hasPermission('com.haoback.sys.controller.SysUserController', 'view')")
	@RequestMapping("/test")
	@ResponseBody
	public String loginPage(){
		return "aaaa";
	}

	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@PreAuthorize("hasPermission('com.haoback.sys.controller.SysUserController', 'update')")
	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult changePassword(String oldPassword, String newPassword, HttpServletRequest request){
		AjaxResult ajaxresult = new AjaxResult();
		Map<String, Object> datas = new HashMap<String, Object>();

		SysUser sysUser = CommonUtils.getSysUser(request);

		Map<String, Object> map = sysUserService.changePassword(oldPassword, newPassword, sysUser);

		datas.put("code", map.get("code"));
		datas.put("msg", map.get("msg"));

		ajaxresult.setDatas(datas);
		ajaxresult.setStatus(HttpStatus.SC_OK);
		return ajaxresult;
	}
}

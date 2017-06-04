package com.haoback.common.utils.loginUtils.service;

import com.haoback.sys.entity.*;
import com.haoback.sys.service.SysMenuService;
import com.haoback.sys.service.SysPermissionService;
import com.haoback.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * spring security 登录处理类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysPermissionService sysPermissionService;
	@Autowired
	private SysMenuService sysMenuService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		SysUser sysUser = sysUserService.findByUserName(userName);
		// 用户-角色映射
		List<SysUserRole> sysUserRoles = sysUser.getSysUserRoles();
		// 将用户-角色对应菜单和操作权限放入以下对象，key为菜单的package_path，value为permission的集合  *、create、update、delete、view
		Map<String, Set<String>> permissions = new HashMap<>();

		for(SysUserRole sysUserRole : sysUserRoles){
			SysRole sysRole = sysUserRole.getSysRole();
			List<SysRoleMenuPermission> sysRoleMenuPermissionList = sysRole.getSysRoleMenuPermissionList();
			for(SysRoleMenuPermission sysRoleMenuPermission : sysRoleMenuPermissionList){
				SysMenu sysMenu = sysRoleMenuPermission.getSysMenu();

				String[] permissionIds = sysRoleMenuPermission.getPermissionIds().split(",");
				Set<String> permissionsSet = new HashSet<>();
				for(String permissionId : permissionIds){
					SysPermission sysPermission = sysPermissionService.findById(Long.parseLong(permissionId));
					permissionsSet.add(sysPermission.getPermission());
				}
				permissions.put(sysMenu.getPackagePath(), permissionsSet);
			}
		}
		sysUser.setPermissions(permissions);

		return sysUser;
	}
}

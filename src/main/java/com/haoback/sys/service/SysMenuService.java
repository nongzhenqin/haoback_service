package com.haoback.sys.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haoback.common.service.BaseService;
import com.haoback.sys.entity.*;
import com.haoback.sys.repository.SysMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysMenuService extends BaseService<SysMenu, Long> {
	
	@Autowired
	private SysMenuRepository sysMenuRepository;
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 获取菜单列表
	 * @param sysUserId
	 * @return
	 */
	public List<Object> getMenus(Long sysUserId){
		if(sysUserId == null) return null;

		SysUser sysUser = sysUserService.findById(sysUserId);
		if(sysUser == null) return null;

		// 用户-角色映射
		List<SysUserRole> sysUserRoles = sysUser.getSysUserRoles();
		// 存放菜单列表的对象
		Map<Long, SysMenu> menuMap = new HashMap<>();

		for(SysUserRole sysUserRole : sysUserRoles){
			SysRole sysRole = sysUserRole.getSysRole();
			List<SysRoleMenuPermission> sysRoleMenuPermissionList = sysRole.getSysRoleMenuPermissionList();
			for(SysRoleMenuPermission sysRoleMenuPermission : sysRoleMenuPermissionList){
				SysMenu sysMenu = sysRoleMenuPermission.getSysMenu();
				menuMap.put(sysMenu.getId(), sysMenu);
			}
		}

		// 处理菜单层级关系 暂时只支持两级菜单
		JSONArray menuJSONArray = new JSONArray();

		// 一级菜单对象
		Set<Long> mainMenuIdSet = new HashSet<>();

		for(Map.Entry<Long, SysMenu> map : menuMap.entrySet()){
			SysMenu sysMenu = map.getValue();
			if(sysMenu.getUpperId().longValue() != 0){
				mainMenuIdSet.add(sysMenu.getUpperId());
			}else{
				menuJSONArray.add((JSONObject) JSONObject.toJSON(sysMenu));
			}
		}

		for(Long menuId : mainMenuIdSet){
			SysMenu sysMenuMain = this.findById(menuId);
			JSONObject menuJSONObject = (JSONObject) JSONObject.toJSON(sysMenuMain);

			// 子菜单
			JSONArray childJSONArray = new JSONArray();

			for(Map.Entry<Long, SysMenu> map : menuMap.entrySet()){
				SysMenu sysMenu = map.getValue();
				if(sysMenu.getUpperId().longValue() == sysMenuMain.getId().longValue()){
					childJSONArray.add((JSONObject) JSONObject.toJSON(sysMenu));
				}
			}

			// 按菜单权重排序
			childJSONArray.sort((o1, o2) -> {
				JSONObject s1 = (JSONObject) o1;
				JSONObject s2 = (JSONObject) o2;
				return s1.getLong("sort").compareTo(s2.getLong("sort"));
			});

			menuJSONObject.put("children", childJSONArray);
			menuJSONArray.add(menuJSONObject);
		}

		// 按菜单权重排序
		menuJSONArray.sort((o1, o2) -> {
			JSONObject s1 = (JSONObject) o1;
			JSONObject s2 = (JSONObject) o2;
			return s1.getLong("sort").compareTo(s2.getLong("sort"));
		});

		menuJSONArray.toString();

		return menuJSONArray;
	}
	
}

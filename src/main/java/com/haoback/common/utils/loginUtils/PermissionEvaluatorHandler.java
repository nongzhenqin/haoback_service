package com.haoback.common.utils.loginUtils;

import com.haoback.sys.entity.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 操作权限控制
 * Created by nong on 2017/4/12.
 */
public class PermissionEvaluatorHandler implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        Map<String, Set<String>> permissions = sysUser.getPermissions();

        Set<String> permissionInDB = permissions.get(targetDomainObject);
        if(CollectionUtils.isEmpty(permissionInDB)){
            return false;
        }

        if(permissionInDB.contains("*")){// 拥有所有操作权限
            return true;
        }else if(permissionInDB.contains(permission)){// 拥有对应的操作权限
            return true;
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String targetType, Object permission) {
        System.out.println("session 授权2");
        return false;
    }
}

package com.haoback.sys.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 角色-菜单-操作权限关系映射表
 * Created by nong on 2017/4/12.
 */
@Entity
@Table(name = "sys_role_menu_permission")
@Getter
@Setter
public class SysRoleMenuPermission extends BaseEntity<Long> {

    private static final long serialVersionUID = -7491396012657295802L;

    /**
     * 角色
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SysRole sysRole;

    /**
     * 菜单
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SysMenu sysMenu;

    /**
     * 操作权限id 英文逗号分隔
     */
    @Column(name = "permission_ids", length = 100)
    private String permissionIds;

}

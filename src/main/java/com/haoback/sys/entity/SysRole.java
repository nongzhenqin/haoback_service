package com.haoback.sys.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 角色表
 * Created by nong on 2017/4/12.
 */
@Entity
@Table(name = "sys_role")
@Getter
@Setter
public class SysRole extends BaseEntity<Long> {
    private static final long serialVersionUID = 5140449977771604226L;

    /**
     * 角色编码
     */
    @Column(name = "code", length = 55)
    private String code;

    /**
     * 角色名称
     */
    @Column(name = "name", length = 55)
    private String name;

    /**
     * 描述
     */
    @Column(name = "desc", length = 255)
    private String desc;

    /**
     * 有效位 1-有效 0-无效
     */
    @Column(name = "validind")
    private Boolean validind = Boolean.TRUE;

    /**
     * 角色对应菜单
     */
    @OneToMany(mappedBy = "sysRole")
    private List<SysRoleMenuPermission> sysRoleMenuPermissionList;

}

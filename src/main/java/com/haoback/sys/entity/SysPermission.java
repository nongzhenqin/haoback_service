package com.haoback.sys.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 操作权限表
 * Created by nong on 2017/4/12.
 */
@Entity
@Table(name = "sys_permission")
@Getter
@Setter
public class SysPermission extends BaseEntity<Long> {

    private static final long serialVersionUID = -5965044123889857865L;

    /**
     * 操作权限名称
     */
    @Column(name = "name", length = 20)
    private String name;

    /**
     * 操作权限
     */
    @Column(name = "permission", length = 20)
    private String permission;

    /**
     * 描述
     */
    @Column(name = "desc", length = 50)
    private String desc;

}

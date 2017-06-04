package com.haoback.sys.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 用户角色关联表
 * Created by nong on 2017/4/12.
 */
@Entity
@Table(name = "sys_user_role")
@Getter
@Setter
public class SysUserRole extends BaseEntity<Long> {

    private static final long serialVersionUID = 1842229889535415687L;

    /**
     * 用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SysUser sysUser;

    /**
     * 角色
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SysRole sysRole;

}

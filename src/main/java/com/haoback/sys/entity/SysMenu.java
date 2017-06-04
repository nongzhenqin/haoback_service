package com.haoback.sys.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 菜单资源表
 * Created by nong on 2017/4/12.
 */
@Entity
@Table(name = "sys_menu")
@Getter
@Setter
public class SysMenu extends BaseEntity<Long> {

    private static final long serialVersionUID = -6231510755564305850L;

    /**
     * 父节点id
     */
    @Column(name = "upper_id", length = 20)
    private Long upperId;

    /**
     * 菜单编码
     */
    @Column(name = "code", length = 55)
    private String code;

    /**
     * 菜单名称
     */
    @Column(name = "name", length = 55)
    private String name;

    /**
     * 请求url
     */
    @Column(name = "url", length = 255)
    private String url;

    /**
     * icon菜单图标
     */
    @Column(name = "icon", length = 65)
    private String icon;

    /**
     * 菜单对应的controller类全路径名
     */
    @Column(name = "package_path", length = 200)
    private String packagePath;

    /**
     * 排序，从0开始越小越靠前
     */
    @Column(name = "sort", length = 11)
    private Integer sort;

    /**
     * 有效位 1-有效 0-无效
     */
    @Column(name = "validind")
    private Boolean validind = Boolean.TRUE;

}

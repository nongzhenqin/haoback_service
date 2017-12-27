package com.haoback.sys.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统配置
 * @author nong
 */
@Entity
@Table(name = "sys_config")
@Getter
@Setter
public class SysConfig extends BaseEntity<Long> {
    private static final long serialVersionUID = 3404299981270121915L;

    /**
     * 是否审核期间，在提交新版本审核时打开
     */
    @Column(name = "is_audit")
    private Boolean isAudit;
}

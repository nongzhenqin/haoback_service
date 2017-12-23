package com.haoback.sys.repository;

import com.haoback.common.repository.BaseRepository;
import com.haoback.sys.entity.SysRole;
import org.springframework.data.jpa.repository.Query;

public interface SysRoleRepository extends BaseRepository<SysRole, Long> {

    /**
     * 通过角色编码查找角色
     * @param code
     * @return
     */
    @Query("select t from SysRole t where t.code = ?1 and t.validind = true")
    SysRole findByCode(String code);
}

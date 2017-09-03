package com.haoback.mail.repository;

import com.haoback.common.repository.BaseRepository;
import com.haoback.mail.entity.MailConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MailConfigRepository extends BaseRepository<MailConfig, Long> {

    /**
     * 查找发件人信息
     * @return
     */
    @Query("select t from MailConfig t where t.validind = true")
    MailConfig findOne();
}

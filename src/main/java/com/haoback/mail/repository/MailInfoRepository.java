package com.haoback.mail.repository;

import com.haoback.common.repository.BaseRepository;
import com.haoback.mail.entity.MailInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface MailInfoRepository extends BaseRepository<MailInfo, Long> {

    /**
     * 查找未发送过的收件人
     * @return
     */
    @Query("select t from MailInfo t where t.email is not null and t.isSend = false")
    Page<MailInfo> find(Pageable pageable);
}

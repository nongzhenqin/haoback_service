package com.haoback.mail.repository;

import com.haoback.common.repository.BaseRepository;
import com.haoback.mail.entity.MailList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface MailListRepository extends BaseRepository<MailList, Long> {

    /**
     * 查找未发送过的收件人
     * @return
     */
    @Query("select t from MailList t where t.isSend = false")
    Page<MailList> find(Pageable pageable);

    /**
     * 通过有效账号查找
     * @param mailAccount
     * @return
     */
    @Query("select t from MailList t where t.mailAccount = ?1")
    MailList findByMailAccount(String mailAccount);
}

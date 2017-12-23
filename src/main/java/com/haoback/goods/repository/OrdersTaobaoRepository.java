package com.haoback.goods.repository;

import com.haoback.common.repository.BaseRepository;
import com.haoback.goods.entity.OrdersTaobao;
import com.haoback.sys.entity.SysUser;
import org.springframework.data.jpa.repository.Query;

public interface OrdersTaobaoRepository extends BaseRepository<OrdersTaobao, Long> {

    /**
     * 通过所属用户和订单号查询
     * @param sysUser
     * @param orderNo
     * @return
     */
    @Query("select t from OrdersTaobao t where t.sysUser = ?1 and t.taobaoOrdersNo = ?2")
    OrdersTaobao findBySysuserAndNo(SysUser sysUser, String orderNo);

    /**
     * 通过订单号查询
     * @param orderNo
     * @return
     */
    @Query("select t from OrdersTaobao t where t.taobaoOrdersNo = ?1")
    OrdersTaobao findByOrderNo(String orderNo);
}

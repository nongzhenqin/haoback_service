package com.haoback.goods.service;

import com.haoback.common.service.BaseService;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.entity.OrdersTaobao;
import com.haoback.goods.repository.OrdersTaobaoRepository;
import com.haoback.goods.vo.GoodsVo;
import com.haoback.goods.vo.OrdersTaobaoVo;
import com.haoback.sys.entity.SysUser;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrdersTaobaoService extends BaseService<OrdersTaobao, Long> {

    @Autowired
    private OrdersTaobaoRepository ordersTaobaoRepository;
    @Autowired
    private GoodsService goodsService;

    /**
     * 通过所属用户和订单号查询
     *
     * @param sysUser
     * @param orderNo
     * @return
     */
    public OrdersTaobao findBySysuserAndNo(SysUser sysUser, String orderNo) {
        return ordersTaobaoRepository.findBySysuserAndNo(sysUser, orderNo);
    }

    /**
     * 通过订单号查询
     *
     * @param orderNO
     * @return
     */
    public OrdersTaobao findByOrderNO(String orderNO) {
        return ordersTaobaoRepository.findByOrderNo(orderNO);
    }

    /**
     * 分页查找
     * @param params
     * @return
     */
    public Page<OrdersTaobaoVo> findByPage(Map<String, Object> params){
        Integer pageNo = (Integer) params.get("pageNo");
        Integer pageSize = (Integer) params.get("pageSize");
        SysUser sysUser = (SysUser) params.get("sysUser");

        Pageable pageable = new PageRequest(pageNo, pageSize);
        StringBuilder hql = new StringBuilder();
        List<Object> paramters = new ArrayList<>();

        hql.append("select t FROM OrdersTaobao t where t.sysUser = ?");
        paramters.add(sysUser);

        Page<OrdersTaobao> page = this.findByPage(hql.toString(), paramters, pageable);

        List<OrdersTaobao> content = page.getContent();
        List<OrdersTaobaoVo> result = new ArrayList<>();

        OrdersTaobaoVo ordersTaobaoVo = null;
        for(OrdersTaobao ordersTaobao : content){
            ordersTaobaoVo = new OrdersTaobaoVo();
            BeanUtils.copyProperties(ordersTaobao, ordersTaobaoVo);

            // 审核中时，商品名从本地库中拉取
            if("0".equals(ordersTaobao.getStatus())){
                Goods goods = goodsService.findByGoodsId(ordersTaobao.getGoodsId());
                if(goods != null){
                    ordersTaobaoVo.setGoodsName(goods.getName());
                }
            }
            result.add(ordersTaobaoVo);
        }

        Page<OrdersTaobaoVo> resultPage = new PageImpl(result, pageable, page.getTotalElements());

        return  resultPage;
    }

    /**
     * 更新或保存订单信息
     *
     * @param taobaoOrdersNo
     * @param dueDate
     * @param sysUser
     * @return
     */
    public Map<String, Object> saveOrUpdate(String taobaoOrdersNo, String dueDate, SysUser sysUser) {
        Map<String, Object> resultMap = new HashMap<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if(StringUtils.isBlank(taobaoOrdersNo) || StringUtils.isBlank(dueDate)){
            // 参数错误
            resultMap.put("code", "3");
            return resultMap;
        }

        // 防止其他人重复添加同一个订单号
        OrdersTaobao ordersTaobaoExist = this.findByOrderNO(taobaoOrdersNo);
        if (ordersTaobaoExist != null && !ordersTaobaoExist.getDeleted()) {
            if (!ordersTaobaoExist.getSysUser().getId().equals(sysUser.getId())) {
                // 订单已被其他人添加
                resultMap.put("code", "2");

                return resultMap;
            } else {// 更新成交日期
                try {
                    ordersTaobaoExist.setDueDate(df.parse(dueDate));
                    this.update(ordersTaobaoExist);

                    resultMap.put("code", "1");

                    return resultMap;
                } catch (ParseException e) {
                    e.printStackTrace();
                    // 参数错误
                    resultMap.put("code", "3");
                    return resultMap;
                }
            }
        }

        OrdersTaobao ordersTaobao = new OrdersTaobao();
        ordersTaobao.setSysUser(sysUser);
        ordersTaobao.setTaobaoOrdersNo(taobaoOrdersNo);
        try {
            ordersTaobao.setDueDate(df.parse(dueDate));
        } catch (ParseException e) {
            e.printStackTrace();
            // 参数错误
            resultMap.put("code", "3");
            return resultMap;
        }
        ordersTaobao.setCreateTime(new Date());
        ordersTaobao.setCreateName(sysUser.getName());
        // 审核中
        ordersTaobao.setStatus("0");
        this.save(ordersTaobao);

        resultMap.put("code", "1");

        return resultMap;
    }
}

package com.haoback.goods.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.common.utils.CommonUtils;
import com.haoback.goods.service.OrdersTaobaoService;
import com.haoback.goods.vo.OrdersTaobaoVo;
import com.haoback.sys.entity.SysUser;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序后台控制器-需登录授权
 */
@Controller
@RequestMapping("/goods_wechat_auth")
public class GoodsWechatMiniAppAuthController {

    @Autowired
    private OrdersTaobaoService ordersTaobaoService;

    /**
     * 保存微信用户提交的订单
     *
     * @param taobaoOrdersNo
     * @param dueDate
     * @return
     */
    @RequestMapping(value = "/orders_taobao/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveOrdersTaobao(String taobaoOrdersNo, String dueDate, HttpServletRequest request) {
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        SysUser sysUser = CommonUtils.getSysUser(request);

        Map<String, Object> map = ordersTaobaoService.saveOrUpdate(taobaoOrdersNo, dueDate, sysUser);

        datas.put("code", map.get("code"));

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 分页查找
     * @param pageSize
     * @param pageNo
     * @param request
     * @return
     */
    @RequestMapping(value = "/orders_taobao/search", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findOrdersTaobaoPage(Integer pageSize, Integer pageNo, HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        SysUser sysUser = CommonUtils.getSysUser(request);

        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", pageSize);
        params.put("pageNo", pageNo);
        params.put("sysUser", sysUser);

        Page<OrdersTaobaoVo> page = ordersTaobaoService.findByPage(params);

        datas.put("code", "1");
        datas.put("data", page);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }
}

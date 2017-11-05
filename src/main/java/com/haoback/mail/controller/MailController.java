package com.haoback.mail.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.mail.entity.MailList;
import com.haoback.mail.service.MailInfoService;
import com.haoback.mail.service.MailListService;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mail")
// 操作权限控制 *、create、update、delete、view
//@PreAuthorize("hasPermission('com.haoback.mail.controller.MailController', 'view')")
public class MailController {

    @Autowired
    private MailInfoService mailInfoService;
    @Autowired
    private MailListService mailListService;

    /**
     * 发送广告邮件
     * @return
     */
    @RequestMapping(value = "/sendAd", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult sendAd(int sendTotal){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        if(sendTotal <= 0){
            datas.put("msg", "请填写发送数量");
            ajaxresult.setDatas(datas);
            ajaxresult.setStatus(HttpStatus.SC_OK);
            return ajaxresult;
        }

//        List<Map> list = mailInfoService.sendAd();
        Map<String, Object> map = null;
        try {
            map = mailListService.sendAd(sendTotal);
        } catch (InterruptedException e) {
            e.printStackTrace();
            datas.put("msg", "异常!!"+e.getMessage());
            ajaxresult.setDatas(datas);
            ajaxresult.setStatus(HttpStatus.SC_OK);
            return ajaxresult;
        }

        datas.put("result", map.get("resultList"));
        datas.put("msg", map.get("msg"));
        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 退订邮件
     * @param mailAccount
     * @return
     */
    @RequestMapping(value = "/tuiding", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String tuiding(String mailAccount){
        String msg = "退订成功！！！";
        if(StringUtils.isBlank(mailAccount) || !mailAccount.contains("@")) return msg;
        MailList mailAccount1 = mailListService.findByMailAccount(mailAccount);
        if(mailAccount1 == null) return msg;
        mailAccount1.setSendCancle(true);
        mailListService.update(mailAccount1);
        return msg;
    }
}

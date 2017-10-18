package com.haoback.mail.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.mail.service.MailInfoService;
import org.apache.commons.httpclient.HttpStatus;
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

    /**
     * 发送广告邮件
     * @return
     */
    @RequestMapping(value = "/sendAd", method = RequestMethod.POST)
    @ResponseBody
//    @PreAuthorize("hasPermission('com.haoback.mail.controller.MailController', 'update')")
    public AjaxResult sendAd(){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        List<Map> list = mailInfoService.sendAd();

        datas.put("result", list);
        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }
}

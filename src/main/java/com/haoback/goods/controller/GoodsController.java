package com.haoback.goods.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.service.GoodsService;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nong on 2017/6/5.
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查找商品
     * @param request
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findByPage(HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<String, Object>();

        int pageNo = 0;
        int pageSize = 10;
        Sort sort = new Sort(Sort.Direction.DESC, "sort");

        Pageable pageable = new PageRequest(pageNo, pageSize, sort);

        Page<Goods> page = goodsService.findByPage(pageable);

        datas.put("code", "1");
        datas.put("data", page);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }
}

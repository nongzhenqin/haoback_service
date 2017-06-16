package com.haoback.goods.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.service.GoodsService;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品服务端控制器
 * Created by nong on 2017/6/15.
 */
@Controller
@RequestMapping("/goods_service")
// 操作权限控制 *、create、update、delete、view
@PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsServiceController', 'view')")
public class GoodsServiceController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查找商品
     * @param pageNo
     * @param pageSize
     * @param goodsType 商品类型
     * @param request
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findByPage(Integer pageNo, Integer pageSize, String goodsType,
                                 String name, String beginDate, String endDate, String validind,
                                 HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", pageNo - 1);
        params.put("pageSize", pageSize);
        params.put("goodsType", goodsType);
        params.put("name", name);
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        params.put("validind", validind);

        Page<Goods> page = goodsService.findByPageService(params);

        datas.put("code", "1");
        datas.put("data", page);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }
}

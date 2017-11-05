package com.haoback.goods.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.common.service.autotask.AutoTaskService;
import com.haoback.common.utils.CommonUtils;
import com.haoback.common.utils.ImageUtil;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.entity.GoodsRes;
import com.haoback.goods.service.GoodsResService;
import com.haoback.goods.service.GoodsService;
import com.haoback.goods.vo.GoodsVo;
import com.haoback.sys.entity.SysUser;
import com.taobao.api.ApiException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理平台端控制器
 * Created by nong on 2017/6/15.
 */
@Controller
@RequestMapping("/goods_service")
// 操作权限控制 *、create、update、delete、view
@PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsServiceController', 'view')")
public class GoodsServiceController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private AutoTaskService autoTaskService;
    @Autowired
    private GoodsResService goodsResService;

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
                                 String status, HttpServletRequest request){

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
        params.put("status", status);

        Page<GoodsVo> page = goodsService.findByPageService(params);

        datas.put("code", "1");
        datas.put("data", page);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 保存商品
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsServiceController', 'create,update')")
    public AjaxResult saveGoods(GoodsVo goodsVo, HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        SysUser sysUser = CommonUtils.getSysUser(request);

        String realPath = request.getSession().getServletContext().getRealPath("/");

        Map<String, Object> map = goodsService.saveGoods(goodsVo, sysUser, realPath);

        datas.put("code", map.get("code"));// 1-成功 0-失败

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 通过id查询商品信息
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/details/{goodsId}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findDetailsById(@PathVariable("goodsId") Long goodsId){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        GoodsVo goodsVo = goodsService.findDetails(goodsId);
        datas.put("code", "1");
        datas.put("data", goodsVo);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 逻辑删除商品
     * @param goodsId
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsServiceController', 'delete')")
    public AjaxResult delete(Long goodsId, HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        SysUser sysUser = CommonUtils.getSysUser(request);

        goodsService.deleteLogic(goodsId, sysUser);

        datas.put("code", "1");
        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 手动触发更新商品信息
     * @return
     */
    @RequestMapping(value = "/updateGoodsInfo", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsServiceController', 'update')")
    public AjaxResult updateGoodsInfo(){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        autoTaskService.autoGetTaoBaoInfo();

        datas.put("code", "1");
        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 从淘宝联盟选品库拉取商品
     * @param type 商品类目，对应选品库的名称 如果为all则拉取所有选品库
     * @param operate 操作类型 equals=等于 like=模糊查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/getGoods", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsServiceController', 'update')")
    public AjaxResult getGoodsFromTaobao(String type, String operate, HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        if(StringUtils.isBlank(type) || StringUtils.isBlank(operate)){
            datas.put("code", "0");
            datas.put("msg", "参数错误！");
            ajaxresult.setDatas(datas);
            ajaxresult.setStatus(HttpStatus.SC_OK);
            return ajaxresult;
        }

        SysUser sysUser = CommonUtils.getSysUser(request);
        String realPath = request.getSession().getServletContext().getRealPath("/");
        try {
            datas = goodsService.saveGoodsFromTaobao(type, operate, sysUser, realPath);
        } catch (ApiException e) {
            e.printStackTrace();
            datas.put("code", "0");
            datas.put("msg", e.getErrCode()+" | "+e.getErrMsg());
        }

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 下载淘宝商品图片
     * @param request
     * @return
     */
    @RequestMapping(value = "/downloadImage", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsServiceController', 'update')")
    public AjaxResult downloadImage(HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/");

        List<Goods> goodsList = goodsService.findAll();
        int i = 0;
        for(Goods goods : goodsList){
            i++;
            if(goods.getGoodsId() == null){
                continue;
            }

            GoodsRes goodsRes = goodsResService.findThumbnailGoodsRes(goods.getId());
            if(goodsRes == null || StringUtils.isBlank(goodsRes.getPicUrl()) ||
                    StringUtils.isNotBlank(goodsRes.getFileId())) continue;

            String image = ImageUtil.downLoadImage(goodsRes.getPicUrl(), realPath);
            goodsRes.setFileId(image);
            System.out.println("第"+i+"个商品，图片FileId="+image);
            goodsResService.update(goodsRes);
        }

        datas.put("msg", "success!!!");
        datas.put("code", "1");

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }
}

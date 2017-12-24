package com.haoback.goods.controller;

import com.alibaba.fastjson.JSONObject;
import com.haoback.common.entity.AjaxResult;
import com.haoback.common.utils.HttpUtil;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.entity.GoodsCarousel;
import com.haoback.goods.entity.GoodsRes;
import com.haoback.goods.entity.GoodsType;
import com.haoback.goods.service.GoodsCarouselService;
import com.haoback.goods.service.GoodsResService;
import com.haoback.goods.service.GoodsService;
import com.haoback.goods.service.GoodsTypeService;
import com.haoback.goods.vo.GoodsDetailsVo;
import com.haoback.goods.vo.GoodsVo;
import com.haoback.sys.entity.SysUser;
import com.haoback.sys.service.RegisterService;
import com.haoback.sys.service.SysUserService;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序后台控制器
 */
@Controller
@RequestMapping("/goods_wechat")
public class GoodsWechatMiniAppController {

    @Autowired
    private GoodsCarouselService goodsCarouselService;
    @Autowired
    private GoodsTypeService goodsTypeService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private GoodsResService goodsResService;

    @Value("${weixin.mini.app.AppID}")
    private String appId;

    @Value("${weixin.mini.app.AppSecret}")
    private String appSecret;

    /**
     * 查询微信小程序轮播图
     * @return
     */
    @RequestMapping(value = "/carousel/search", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findCarousel(){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        List<GoodsCarousel> wxValidGoodsCarousel = goodsCarouselService.findWxValidGoodsCarousel();

        datas.put("code", "1");
        datas.put("data", wxValidGoodsCarousel);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 查找商品分类
     * @param flag 数字-分类数量 ALL-全部
     * @return
     */
    @RequestMapping(value = "/goods/type/search", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findGoodsTypes(String flag){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        List<GoodsType> list = goodsTypeService.findList();

        datas.put("code", "1");
        datas.put("data", list);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 分页查找商品
     * @param pageSize
     * @param pageNo
     * @param searchKey 查询条件
     * @param goodsType 商品类目
     * @return
     */
    @RequestMapping(value = "/goods/search", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findGoodsPage(Integer pageSize, Integer pageNo, String searchKey,
                                    String goodsType, String sortKey){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        params.put("key", searchKey);
        params.put("goodsType", goodsType);
        params.put("sortKey", sortKey);

        Page<GoodsVo> page = goodsService.findByPageWeb(params);

        datas.put("code", "1");
        datas.put("data", page);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 查询商品详情
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/goods/detail/search", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findGoodsDetails(Long goodsId){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        Goods goods = goodsService.findById(goodsId);
        if(goods == null){
            datas.put("code", "0");

            ajaxresult.setDatas(datas);
            ajaxresult.setStatus(HttpStatus.SC_OK);
            return ajaxresult;
        }

        GoodsDetailsVo goodsDetailsVo = new GoodsDetailsVo();
        BeanUtils.copyProperties(goods, goodsDetailsVo);
        goodsDetailsVo.setId(goods.getId());

        List<String> smallImgs = new ArrayList<>();
        // 商品主图
        List<GoodsRes> goodsResMainList = goodsResService.findByType(goods.getId(), "thumbnail");
        for(GoodsRes goodsRes : goodsResMainList){
            smallImgs.add(goodsRes.getPicUrl());
        }

        // 商品小图
        List<GoodsRes> goodsResList = goodsResService.findByType(goods.getId(), "detail");
        for(GoodsRes goodsRes : goodsResList){
            smallImgs.add(goodsRes.getPicUrl());
        }

        goodsDetailsVo.setSmallImgs(smallImgs);

        datas.put("code", "1");
        datas.put("data", goodsDetailsVo);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 注册微信用户
     * @param code
     * @param nickName
     * @param city
     * @param country
     * @param gender
     * @param language
     * @param province
     * @return
     */
    @RequestMapping(value = "/registerWxUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult registerWxUser(String code, String nickName, String city,
                                     String country, String gender, String language,
                                     String province){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        if(StringUtils.isBlank(code)){
            datas.put("code", "0");

            ajaxresult.setDatas(datas);
            ajaxresult.setStatus(HttpStatus.SC_OK);
            return ajaxresult;
        }

        String res = HttpUtil.sendHttpsGET("https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code");
        JSONObject resJsonObject = JSONObject.parseObject(res);
        if(!resJsonObject.containsKey("openid")){
            datas.put("code", "0");

            ajaxresult.setDatas(datas);
            ajaxresult.setStatus(HttpStatus.SC_OK);
            return ajaxresult;
        }

        String openid = resJsonObject.getString("openid");

        SysUser user = sysUserService.findByUserName(openid);
        if(user != null){
            datas.put("code", "1");
            datas.put("openid", openid);
            ajaxresult.setDatas(datas);
            ajaxresult.setStatus(HttpStatus.SC_OK);
            return ajaxresult;
        }

        SysUser sysUser = new SysUser();
        sysUser.setUserName(openid);
        sysUser.setPassword(openid);
        sysUser.setName(nickName);
        sysUser.setNickName(nickName);
        sysUser.setCity(city);
        sysUser.setCountry(country);
        sysUser.setGender(gender);
        sysUser.setLanguage(language);
        sysUser.setProvince(province);
        sysUser.setWxUser(true);

        registerService.registerWxUser(sysUser);

        datas.put("code", "1");
        datas.put("openid", openid);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }
}

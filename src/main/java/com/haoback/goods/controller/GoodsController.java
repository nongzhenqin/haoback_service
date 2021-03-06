package com.haoback.goods.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.common.utils.IpUtils;
import com.haoback.goods.entity.GoodsType;
import com.haoback.goods.service.GoodsService;
import com.haoback.goods.service.GoodsTypeService;
import com.haoback.goods.vo.GoodsVo;
import com.haoback.platform.entity.PvUvDetail;
import com.haoback.platform.service.PvUvDetailService;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品用户端控制器
 * Created by nong on 2017/6/5.
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsTypeService goodsTypeService;
    @Autowired
    private PvUvDetailService pvUvDetailService;

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
    public AjaxResult findByPage(Integer pageNo, Integer pageSize, String goodsType, String key, HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        params.put("goodsType", goodsType);
        params.put("key", key);

        Page<GoodsVo> page = goodsService.findByPageWeb(params);

        datas.put("code", "1");
        datas.put("goods", page);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 获取所有商品分类
     * @return
     */
    @RequestMapping(value = "/goods_type", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findGoodsType(){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        List<GoodsType> goodsTypes = goodsTypeService.findList();

        datas.put("code", "1");
        datas.put("data", goodsTypes);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 获取图片 弃用
     * @param fileId
     * @param request
     * @param response
     */
//    @RequestMapping(value = "/image/{fileId}", method = RequestMethod.GET)
//    public void findGoodsPic(@PathVariable(value = "fileId") String fileId, HttpServletRequest request, HttpServletResponse response){
//        String path = System.getProperty("user.dir");
//        if(path.lastIndexOf("/bin") > -1){
//            path = path.substring(0, path.lastIndexOf("/")) + "/images/";
//        }else{
//            path = path + "/images/";
//        }
//
//        File file = new File(path + fileId + ".jpg");
//
//        try {
//            FileInputStream fileInputStream = new FileInputStream(file);
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//            byte[] b = new byte[1024];
//            int n;
//            while((n = fileInputStream.read(b)) != -1){
//                byteArrayOutputStream.write(b, 0, n);
//            }
//
//            fileInputStream.close();
//            byteArrayOutputStream.close();
//
//            response.getOutputStream().write(byteArrayOutputStream.toByteArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 保存pv uv
     * @param goodsId
     * @param referer
     * @param titileName
     * @param flag
     * @param opid 微信openid
     * @param source
     * @param request
     * @return
     */
    @RequestMapping(value = "/pv_uv", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult savePvUv(Long goodsId, String referer, String titileName,
                               String opid, String flag, String source, HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        PvUvDetail pvUvDetail = new PvUvDetail();
        pvUvDetail.setAddTime(new Date());
        pvUvDetail.setIp(IpUtils.getIpAddr(request));
        pvUvDetail.setReferer(referer);
        pvUvDetail.setWxOpenid(opid);
        pvUvDetail.setTitleName(titileName);
        pvUvDetail.setFlag(flag);
        pvUvDetail.setGoods(goodsService.findById(goodsId));
        pvUvDetail.setSource(source);
        pvUvDetailService.save(pvUvDetail);

        datas.put("code", "1");

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

}

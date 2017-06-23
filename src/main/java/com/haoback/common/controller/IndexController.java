package com.haoback.common.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.common.utils.CommonUtils;
import com.haoback.goods.entity.GoodsType;
import com.haoback.goods.service.GoodsService;
import com.haoback.goods.service.GoodsTypeService;
import com.haoback.goods.vo.GoodsTypeVo;
import com.haoback.goods.vo.GoodsVo;
import com.haoback.sys.entity.SysUser;
import com.haoback.sys.service.SysMenuService;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 程序入口控制器
 * Created by nong on 2017/3/30.
 */
@Controller
public class IndexController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private GoodsTypeService goodsTypeService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 登录页面跳转
     * @param request
     * @return
     */
    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String loginPage(HttpServletRequest request, HttpServletResponse response){
        // 如果已登录，访问登录页面时直接跳转到登录成功页
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContextImpl != null && securityContextImpl.getAuthentication() != null &&
                securityContextImpl.getAuthentication().isAuthenticated()){
            return "page/index.html";
        }

        return "login.jsp";
    }

    @RequestMapping(value="/index.html",method=RequestMethod.GET)
    public ModelAndView success(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("page/index.jsp");

        // 查询商品分类
        List<GoodsType> goodsTypes = goodsTypeService.findAll();
        List<GoodsTypeVo> goodsTypesVo = new ArrayList<>();
        GoodsTypeVo goodsTypeVo = null;
        for(GoodsType goodsType : goodsTypes){
            goodsTypeVo = new GoodsTypeVo();
            BeanUtils.copyProperties(goodsType, goodsTypeVo);

            // 查找商品
            Map<String, Object> params = new HashMap<>();
            params.put("pageNo", 0);
            params.put("pageSize", 10);
            params.put("goodsType", goodsType.getCode());
            Page<GoodsVo> page = goodsService.findByPageWeb(params);
            List<GoodsVo> content = page.getContent();

            // 商品列表
            goodsTypeVo.setGoodsList(content);

            goodsTypesVo.add(goodsTypeVo);
        }

        // 查找热门推荐商品
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", 0);
        params.put("pageSize", 5);
        params.put("goodsType", "hot");
        Page<GoodsVo> page = goodsService.findByPageWeb(params);

        mav.addObject("goodsTypesHot", page.getContent());

        mav.addObject("goodsTypes", goodsTypesVo);
        return mav;
    }

    @RequestMapping(value="/login_fail",method=RequestMethod.GET)
    public String fail(){
        return "login.jsp";
    }

    /**
     * 判断是否已登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/is_login", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult loginPage(HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<String, Object>();
        /**
         * 实际运用中发现获得的Authentication为null。仔细看了下源代码发现，如果想用上面的代码获得当前用户，必须在spring
         * security过滤器执行中执行，否则在过滤链执行完时org.springframework.security.web.context.SecurityContextPersistenceFilter类会
         * 调用SecurityContextHolder.clearContext();而把SecurityContextHolder清空，所以会得到null。    经过spring security认证后，
         * security会把一个SecurityContextImpl对象存储到session中，此对象中有当前用户的各种资料
         * */
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = session.getAttribute("SPRING_SECURITY_CONTEXT") == null ? null : (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");

        if(securityContext == null){
            datas.put("code", "0");//1：成功，0：失败
            datas.put("msg", "未登录");
            ajaxresult.setDatas(datas);
            ajaxresult.setStatus(HttpStatus.SC_OK);
            return ajaxresult;
        }

        Authentication authentication = securityContext.getAuthentication();

//        // 登录名
//        System.out.println("Username:" + securityContext.getAuthentication().getName());
//        // 登录密码，未加密的
//        System.out.println("Credentials:" + securityContext.getAuthentication().getCredentials());
//        WebAuthenticationDetails details = (WebAuthenticationDetails) securityContext.getAuthentication().getDetails();
//        // 获得访问地址
//        System.out.println("RemoteAddress" + details.getRemoteAddress());
//        // 获得sessionid
//        System.out.println("SessionId" + details.getSessionId());
//        // 获得当前用户所拥有的权限
//        List<GrantedAuthority> authorities = (List<GrantedAuthority>) securityContext.getAuthentication().getAuthorities();
//        for (GrantedAuthority grantedAuthority : authorities) {
//            System.out.println("Authority" + grantedAuthority.getAuthority());
//        }

        if(authentication != null && authentication.isAuthenticated()){
            datas.put("code", "1");//1：成功，0：失败
            datas.put("msg", "已登录");
        }else{
            datas.put("code", "0");//1：成功，0：失败
            datas.put("msg", "未登录");
        }

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 获取登录用户的菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getMenus(HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<String, Object>();

        SysUser sysUser = CommonUtils.getSysUser(request);

        List<Object> menus = sysMenuService.getMenus(sysUser.getId());

        datas.put("code", "1");
        datas.put("data", menus);
        datas.put("userName", sysUser.getUsername());
        datas.put("id", sysUser.getId());

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }
}

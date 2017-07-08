package com.haoback.common.utils;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.haoback.sys.entity.SysUser;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具类
 * Created by nong on 2017/4/21.
 */
public class CommonUtils {

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    public static SysUser getSysUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return (SysUser) securityContext.getAuthentication().getPrincipal();
    }

    /**
     * 通过url把url后带的参数转换成map
     * @param url
     * @return
     */
    public static Map<String, String> getUrlParams(String url){
        Map<String, String> result = new HashMap<>();
        String[] params = url.split("\\?")[1].split("&");

        for(String p : params){
            String[] cell = p.split("=");
            if(cell.length > 1){
                result.put(cell[0], cell[1]);
            }else{
                result.put(cell[0], "");
            }
        }

        return result;
    }

    /**
     * 把map中值转换为&拼接的url参数
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, String> map){
        if(MapUtils.isEmpty(map)) return "";

        StringBuffer result = new StringBuffer();

        for(Map.Entry<String, String> m : map.entrySet()){
            if(StringUtils.isBlank(m.getValue())) continue;
            result.append(m.getKey()).append("=").append(m.getValue()).append("&");
        }

        return result.substring(0, result.length() - 1);
    }

    /**
     * 获取淘宝商品id
     * @param url
     * @return
     */
    public static Map<String, String> getGoodsItemIdOrTmallUrl(String url){
        Map<String, String> result = new HashMap<>();

        WebClient wc = new WebClient();
        wc.getOptions().setUseInsecureSSL(true); // 允许使用不安全的SSL连接。如果不打开，站点证书过期的https将无法访问
        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器
        wc.getOptions().setCssEnabled(false); //禁用css支持
        // 禁用一些异常抛出
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);

        wc.getOptions().setDoNotTrackEnabled(false); // 随请求发送DoNotTrack
        wc.setJavaScriptTimeout(10000);      // 设置JS超时，这里是1s
        wc.getOptions().setTimeout(5000); //设置连接超时时间 ，这里是5s。如果为0，则无限期等待
        wc.setAjaxController(new NicelyResynchronizingAjaxController()); // 设置ajax控制器

        WebRequest request = null;
        try {
            request = new WebRequest(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            Page page = wc.getPage(request);
            if(page.isHtmlPage()){
                HtmlPage htmlPage = (HtmlPage) page;

                String html = htmlPage.asXml();
                System.out.println(html);
                DomNodeList<HtmlElement> script = htmlPage.getHead().getElementsByTagName("script");

                for(HtmlElement elm : script) {
                    String textContent = elm.getTextContent();
                    String attribute = elm.getAttribute("src");
                    if(attribute != null && attribute.contains("tmallBuySupport") && !result.containsKey("src")){// 天猫
                        String src = elm.getAttribute("src");
                        result.put("src", src);
                    }else{// 淘宝
                        if (textContent.contains("var g_config = {")) {
                            String[] split = textContent.split(",");
                            for(String s : split){
                                if(s.contains("itemId")){
                                    String itemId = s.split(":")[1].trim().replaceAll("'", "");
                                    result.put("itemId", itemId);
                                    break;
                                }
                            }
                            for(String s : split){
                                if(s.contains("online")){
                                    String online = s.split(":")[1].trim();
                                    result.put("online", online);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}

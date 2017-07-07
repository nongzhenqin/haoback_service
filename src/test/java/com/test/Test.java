package com.test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.UrlUtils;
import com.haoback.common.utils.SSLClient;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nong on 2017/6/6.
 */
public class Test {

    public static void main(String[] args){
//        HttpClient httpClient = null;
//        try {
//            httpClient = new SSLClient();
//            long currentTimeMillis = System.currentTimeMillis();
//            HttpGet get = new HttpGet("https://mdskip.taobao.com/core/initItemDetail.htm?isUseInventoryCenter=false&sellerPreview=false&service3C=true&isPurchaseMallPage=false&cachedTimestamp=1499326709981&addressLevel=2&queryMemberRight=true&isForbidBuyItem=false&isAreaSell=false&isApparel=false&tmallBuySupport=true&household=false&offlineShop=false&isSecKill=false&isRegionLevel=false&showShopProm=false&tryBeforeBuy=false&cartEnable=true&itemId=542679911886&callback=setMdskip&timestamp=1499352070262&isg=null&isg2=ApycK3z8KfE-r90tYQxuSCbIbbyOvUFaWUeSm3admgdzwTxLniUQzxJxVx7D");
//            get.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
//            get.setHeader("Referer", "https://s.click.taobao.com/t?e=m%3D2%26s%3DLrCgALW3n1EcQipKwQzePOeEDrYVVa64LKpWJ%2Bin0XLjf2vlNIV67kHDoRknYxidDOz%2BQ0Bmwbw4TDOFLMq84x0HboYUFcE7WLvr7c%2ByinCyB1uQURnJOTB9474Z8faHXEki0A8JiayzN6VX9%2FTL1LHhGvcSi2Bvxg5p7bh%2BFbQ%3D&pvid=12_218.17.254.194_376_1497842139427");
//
//            HttpResponse execute = httpClient.execute(get);
//            String s = EntityUtils.toString(execute.getEntity(), "utf-8");
//            System.out.println(s);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



//        String s = HttpUtil.sendHttpsGET("https://s.click.taobao.com/t?e=m%3D2%26s%3DLrCgALW3n1EcQipKwQzePOeEDrYVVa64LKpWJ%2Bin0XLjf2vlNIV67kHDoRknYxidDOz%2BQ0Bmwbw4TDOFLMq84x0HboYUFcE7WLvr7c%2ByinCyB1uQURnJOTB9474Z8faHXEki0A8JiayzN6VX9%2FTL1LHhGvcSi2Bvxg5p7bh%2BFbQ%3D&pvid=12_218.17.254.194_376_1497842139427");

//        Map<String, String> heads = new HashMap<>();
//        heads.put("Referer", "");
//        heads.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
//        String s = HttpUtil.doGet("https://s.click.taobao.com/t?e=m%3D2%26s%3DLrCgALW3n1EcQipKwQzePOeEDrYVVa64LKpWJ%2Bin0XLjf2vlNIV67kHDoRknYxidDOz%2BQ0Bmwbw4TDOFLMq84x0HboYUFcE7WLvr7c%2ByinCyB1uQURnJOTB9474Z8faHXEki0A8JiayzN6VX9%2FTL1LHhGvcSi2Bvxg5p7bh%2BFbQ%3D&pvid=12_218.17.254.194_376_1497842139427", null, heads);
//        System.out.println(s);

//        BrowserVersion bv = BrowserVersion.BEST_SUPPORTED.clone();
//        // 设置语言，否则不知道传过来是什么编码
//        bv.setUserLanguage("zh_cn");
//        bv.setSystemLanguage("zh_cn");
//        bv.setBrowserLanguage("zh_cn");
//
//        // 源码里是写死Win32的，不知道到生产环境（linux）会不会变，稳妥起见还是硬设
//        bv.setPlatform("Win32");

        WebClient wc = new WebClient();
        wc.getOptions().setUseInsecureSSL(true); // 允许使用不安全的SSL连接。如果不打开，站点证书过期的https将无法访问
        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器
        wc.getOptions().setCssEnabled(false); //禁用css支持
        // 禁用一些异常抛出
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);

        wc.getOptions().setDoNotTrackEnabled(false); // 随请求发送DoNotTrack
        wc.setJavaScriptTimeout(20000);      // 设置JS超时，这里是1s
        wc.getOptions().setTimeout(20000); //设置连接超时时间 ，这里是5s。如果为0，则无限期等待
        wc.setAjaxController(new NicelyResynchronizingAjaxController()); // 设置ajax控制器

        WebRequest request = null;
        try {
            request = new WebRequest(new URL("https://s.click.taobao.com/t?e=m%3D2%26s%3DLrCgALW3n1EcQipKwQzePOeEDrYVVa64LKpWJ%2Bin0XLjf2vlNIV67kHDoRknYxidDOz%2BQ0Bmwbw4TDOFLMq84x0HboYUFcE7WLvr7c%2ByinCyB1uQURnJOTB9474Z8faHXEki0A8JiayzN6VX9%2FTL1LHhGvcSi2Bvxg5p7bh%2BFbQ%3D&pvid=12_218.17.254.194_376_1497842139427"));
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
                    if (textContent.contains("var g_config = {")) {
                        String[] split = textContent.split(",");
                        for(String s : split){
                            if(s.contains("itemId")){
                                String itemid = s.split(":")[1].trim().replaceAll("'", "");
                                System.out.println(itemid);
                            }
                            if(s.contains("online")){
                                String online = s.split(":")[1].trim();
                                System.out.println(online);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFirstMatch(String str,String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        String ret = null;
        if(matcher.find()) {
            ret = matcher.group();
        }
        return ret;
    }

}

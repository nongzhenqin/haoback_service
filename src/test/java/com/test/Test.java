package com.test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.UrlUtils;
import com.haoback.common.utils.CommonUtils;
import com.haoback.common.utils.SSLClient;
import com.haoback.common.utils.email.MailInfo;
import com.haoback.common.utils.email.MailUtil;
import com.haoback.mail.entity.MailConfig;
import com.haoback.sys.entity.SysUser;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nong on 2017/6/6.
 */
public class Test {

//    public static void main(String[] args){
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

//        WebClient wc = new WebClient();
//
//        try {
//            WebRequest request = new WebRequest(new URL("https://s.click.taobao.com/t?e=m%3D2%26s%3DmWvfPd%2BHzvgcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67kt%2BynDjopnclg6AtVBcXjw4TDOFLMq84x0HboYUFcE7WLvr7c%2ByinCyB1uQURnJORMUkXW4%2FAZqbyX%2FYA14wSXrH9qPUYCKxAKV1b%2FDKVqV%2BG7i9suu4R2iZ%2BQMlGz6FQ%3D%3D&pvid=12_219.72.202.0_6762_1498292012986"));
//
////            request.setAdditionalHeaders(searchRequestHeader);
//
//            wc.getOptions().setUseInsecureSSL(true); // 允许使用不安全的SSL连接。如果不打开，站点证书过期的https将无法访问
//            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器
//            wc.getOptions().setCssEnabled(false); //禁用css支持
//            // 禁用一些异常抛出
//            wc.getOptions().setThrowExceptionOnScriptError(false);
//            wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
//
//            wc.getCurrentWindow().getTopWindow().setOuterHeight(Integer.MAX_VALUE);
//            wc.getCurrentWindow().getTopWindow().setInnerHeight(Integer.MAX_VALUE);
//
//            Page page = wc.getPage(request);
//            page.getEnclosingWindow().setOuterHeight(Integer.MAX_VALUE);
//            page.getEnclosingWindow().setInnerHeight(Integer.MAX_VALUE);
//
//            if(page.isHtmlPage()) {
//                HtmlPage htmlPage = (HtmlPage) page;
//
//                String html = htmlPage.asXml();
//                System.out.println(html);
//                DomNodeList<HtmlElement> script = htmlPage.getHead().getElementsByTagName("script");
//
//                String detailUrl = "";
//                for(HtmlElement elm : script) {
//                    String textContent = elm.getTextContent();
//                    if(textContent.contains("var g_config = {")) {
//                        for(String line : textContent.split("\n")) {
//                            if(line.startsWith("        descUrl")) {
//                                detailUrl = "http:" + getFirstMatch(line,
//                                        "'//dsc.taobaocdn.com/i[0-9]+/[0-9]+/[0-9]+/[0-9]+/.+[0-9]+'\\s+:"
//                                ).replaceAll("\\s+:","").replace("'","");
//                                break;
//                            }
//
//                        }
//                        break;
//                    }
//                }
//
//                if(StringUtils.isNotBlank(detailUrl)) {
//                    String detail = wc.getPage(detailUrl).getWebResponse().getContentAsString().replace("var desc='", "").replace("';", "");
//                    System.out.println(detail);
//                }
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }


//        WebClient wc = new WebClient();
//        wc.getOptions().setUseInsecureSSL(true); // 允许使用不安全的SSL连接。如果不打开，站点证书过期的https将无法访问
//        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器
//        wc.getOptions().setCssEnabled(false); //禁用css支持
//        // 禁用一些异常抛出
//        wc.getOptions().setThrowExceptionOnScriptError(false);
//        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
//
//        wc.getOptions().setDoNotTrackEnabled(false); // 随请求发送DoNotTrack
//        wc.setJavaScriptTimeout(20000);      // 设置JS超时，这里是1s
//        wc.getOptions().setTimeout(20000); //设置连接超时时间 ，这里是5s。如果为0，则无限期等待
//        wc.setAjaxController(new NicelyResynchronizingAjaxController()); // 设置ajax控制器
//
//        WebRequest request = null;
//        try {
//            request = new WebRequest(new URL("https://s.click.taobao.com/t?e=m%3D2%26s%3D4FvSzOXi8aAcQipKwQzePOeEDrYVVa64LKpWJ%2Bin0XLjf2vlNIV67tyorF7K3xkCn7yqOUL3SI04TDOFLMq84x0HboYUFcE7WLvr7c%2ByinCyB1uQURnJOUCQnSgylte0f8bLL3SQY1Fb2nN1iGs%2F5PEmWtovubA0IYULNg46oBA%3D&pvid=12_218.17.254.194_376_1497842139427"));
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Page page = wc.getPage(request);
//            if(page.isHtmlPage()){
//                HtmlPage htmlPage = (HtmlPage) page;
//
//                String html = htmlPage.asXml();
//                System.out.println(html);
//
//                DomNodeList<HtmlElement> script = htmlPage.getHead().getElementsByTagName("script");
//
//                for(HtmlElement elm : script) {
//                    String textContent = elm.getTextContent();
//                    if (textContent.contains("var g_config = {")) {
//                        String[] split = textContent.split(",");
//                        for(String s : split){
//                            if(s.contains("itemId")){
//                                String itemid = s.split(":")[1].trim().replaceAll("'", "");
//                                System.out.println(itemid);
//                            }
//                            if(s.contains("online")){
//                                String online = s.split(":")[1].trim();
//                                System.out.println(online);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Map<Object, Object> map = new HashMap<>();
//        map.put("1", 1);
//        map.put(1, 2);
//
//        System.out.println(map.get("1"));

//        String a = "SELECT * from table t";
//
//        String s = CommonUtils.upperCaseSqlFrom(a);
//
//        System.out.println(s);
//
//        System.out.println("select count(1) " + a.substring(a.indexOf("from")));


//    }


//    public static String getFirstMatch(String str,String regex) {
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(str);
//        String ret = null;
//        if(matcher.find()) {
//            ret = matcher.group();
//        }
//        return ret;
//    }

    /**
     * 生成账号密码
     */
//    @org.junit.Test
    public void password(){
        SysUser sysUser = new SysUser();
        sysUser.setUserName("admin");
        sysUser.setPassword("nong135790");
//        sysUser.setPermissions();
        sysUser.randomSalt();
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        sysUser.setPassword(md5PasswordEncoder.encodePassword(sysUser.getPassword(), sysUser.getSalt()));
        System.out.println(sysUser.getPassword());
        System.out.println(sysUser.getSalt());
    }

    /**
     * 测试邮件发送
     */
    @org.junit.Test
    public void testMail(){
        MailInfo mailInfo = new MailInfo();
        List<String> toList = new ArrayList<String>();
        toList.add("nongzhenqin@outlook.com");

//        List<String> ccList = new ArrayList<String>();
//        ccList.add("my@163.com");
//
//        List<String> bccList = new ArrayList<String>();
//        bccList.add("my@163.com");

        //添加附件
//        EmailAttachment att = new EmailAttachment();
//        att.setPath("g:\\测试.txt");
//        att.setName("测试.txt");
//        List<EmailAttachment> atts = new ArrayList<EmailAttachment>();
//        atts.add(att);
//        mailInfo.setAttachments(atts);

        mailInfo.setToAddress(toList);//收件人
//        mailInfo.setCcAddress(ccList);//抄送人
//        mailInfo.setBccAddress(bccList);//密送人

        mailInfo.setSubject("测试主题");
        mailInfo.setContent("内容：<h1>test,测试</h1>");

        MailConfig mailConfig = new MailConfig();


        MailUtil.sendEmail(mailInfo, mailConfig);
    }

}

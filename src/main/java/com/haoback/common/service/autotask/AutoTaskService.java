package com.haoback.common.service.autotask;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haoback.common.utils.CommonUtils;
import com.haoback.common.utils.SSLClient;
import com.haoback.common.utils.email.MailUtil;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.service.GoodsService;
import com.haoback.mail.entity.MailConfig;
import com.haoback.mail.entity.MailInfo;
import com.haoback.mail.service.MailConfigService;
import com.haoback.mail.service.MailInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 自动任务
 * Created by nong on 2017/7/7.
 */
@Component
public class AutoTaskService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private MailInfoService mailInfoService;
    @Autowired
    private MailConfigService mailConfigService;

    /**
     * 自动捉取淘宝商品最新价格
     */
//    @Scheduled(cron="0 25 0 * * ?")// 每天凌晨3点执行
    public void autoGetTaoBaoInfo(){
        List<Goods> goodsList = goodsService.findAll();
        String targetUrl = "https://mdskip.taobao.com/core/initItemDetail.htm?isUseInventoryCenter=false&sellerPreview=false&service3C=true&isPurchaseMallPage=false&cachedTimestamp=1499326709981&addressLevel=2&queryMemberRight=true&isForbidBuyItem=false&isAreaSell=false&isApparel=false&tmallBuySupport=true&household=false&offlineShop=false&isSecKill=false&isRegionLevel=false&showShopProm=false&tryBeforeBuy=false&cartEnable=true&itemId=542679911886&callback=setMdskip&timestamp=1499352070262&isg=null&isg2=ApycK3z8KfE-r90tYQxuSCbIbbyOvUFaWUeSm3admgdzwTxLniUQzxJxVx7D&ref=https%3A%2F%2Fs.click.taobao.com%2Ft_js%3Ftu%3Dhttps%253A%252F%252Fs.click.taobao.com%252Ft%253Fe%253Dm%25253D2%252526s%25253DPqUtWiWY62gcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67sdnvqqQ72iF2yqscP0DbOI4TDOFLMq84x0HboYUFcE7WLvr7c%25252ByinCyB1uQURnJOQNUfesiEEBWFeJIblRvDFAhLvzk%25252B4PKMFplD%25252FwDQ0UaIYULNg46oBA%25253D%2526pvid%253D12_218.17.254.194_488_1497856124560%2526ref%253Dhttp%25253A%25252F%25252Flocalhost%25253A8090%25252Fhaoback_service%25252Findex.html%2526et%253DFwoyxiNZPvKb%25252BLa50vGTi9ow%25252F%25252FiZa9eU";
        for(Goods goods : goodsList){
            String url = goods.getUrlLink();
            HttpClient httpClient = null;
            try {
                httpClient = new SSLClient();

                Map<String, String> goodsMap = CommonUtils.getGoodsItemIdOrTmallUrl(url);
                String getUrl = "";
                boolean isTmall = false;

                // 请求页面失败，无法解析出商品ID
                if(!goodsMap.containsKey("src") && !goodsMap.containsKey("itemId")){
                    continue;
                }

                // 天猫
                if(goodsMap.containsKey("src")){
                    getUrl = "https:" + goodsMap.get("src").replaceAll("&amp;", "&");

                    isTmall = true;
                }else{// 淘宝
                    Map<String, String> urlParamsMap = CommonUtils.getUrlParams(targetUrl);

                    // 宝贝已下架
                    if("false".equals(goodsMap.get("online"))){
                        goods.setStatus("0");// 下架
                        goodsService.update(goods);
                        continue;
                    }

                    long currentTimeMillis = System.currentTimeMillis();
                    urlParamsMap.put("cachedTimestamp", Long.toString(currentTimeMillis));
                    urlParamsMap.put("timestamp", Long.toString(currentTimeMillis));
                    urlParamsMap.put("itemId", goodsMap.get("itemId"));
                    urlParamsMap.put("ref", "");
//                    getUrl = "https://mdskip.taobao.com/core/initItemDetail.htm?" + CommonUtils.getUrlParamsByMap(urlParamsMap);
                    getUrl = "https://detailskip.taobao.com/service/getData/1/p1/item/detail/sib.htm?itemId="+goodsMap.get("itemId")+"&sellerId="+goodsMap.get("sellerId")+"&modules=dynStock,qrcode,viewer,price,duty,xmpPromotion,delivery,upp,activity,fqg,zjys,couponActivity,soldQuantity,originalPrice,tradeContract&callback=onSibRequestSuccess";

                    isTmall = false;
                }

                HttpGet get = new HttpGet(getUrl);
                get.setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
                get.setHeader("referer", "https://item.taobao.com/item.htm?spm=a230r.1.14.145.76bf523fne7p5&id="+goodsMap.get("itemId")+"&ns=1&abbucket=18");
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//设置请求和传输超时时间
                get.setConfig(requestConfig);

                HttpResponse execute = httpClient.execute(get);
                String s = EntityUtils.toString(execute.getEntity(), "utf-8");
                System.out.println(s);

                // 商品过期，无信息返回
                if(StringUtils.isBlank(s) || !s.contains("{")){
//                    goods.setStatus("0");// 下架
//                    goodsService.update(goods);
                    System.out.println("商品ID="+goods.getId()+"无法拉取最新价格");
                    continue;
                }

                s = s.substring(s.indexOf("{"), s.lastIndexOf("}")+1);

                JSONObject jsonObject = JSONObject.parseObject(s);

                if(goodsMap.containsKey("src")) {// 天猫
                    JSONObject priceInfo = jsonObject.getJSONObject("defaultModel").getJSONObject("itemPriceResultDO").getJSONObject("priceInfo");

                    double price = Double.MAX_VALUE;

                    for (Map.Entry<String, Object> set : priceInfo.entrySet()) {
                        JSONObject skuJSONObject = JSONObject.parseObject(set.getValue().toString());
                        JSONArray promotionList = skuJSONObject.getJSONArray("promotionList");
                        if (promotionList == null || promotionList.size() == 0) {
                            double priceTemp = skuJSONObject.getDoubleValue("price");
                            if (priceTemp < price) {
                                price = priceTemp;
                            }
                            continue;
                        }
                        for (int i = 0, len = promotionList.size(); i < len; i++) {
                            JSONObject promotion = promotionList.getJSONObject(i);
                            double priceTemp = promotion.getDoubleValue("price");
                            if (priceTemp < price) {
                                price = priceTemp;
                            }
                        }
                    }

                    Integer sellCount = jsonObject.getJSONObject("defaultModel").getJSONObject("sellCountDO").getInteger("sellCount");

                    System.out.println("price=" + price + "&sellCount=" + sellCount);
                    goods.setPrice(new BigDecimal(Double.toString(price)).setScale(2, RoundingMode.HALF_UP));// 价格
                    goods.setSalesNum(sellCount);
                    goods.setIsTmall(isTmall);
                    goodsService.update(goods);
                }else{// 淘宝
                    JSONObject data = jsonObject.getJSONObject("data");
                    String price = data.getString("price").split("-")[0].trim();
                    Integer sellCount = data.getJSONObject("soldQuantity").getInteger("confirmGoodsCount");
                    System.out.println("price="+price+"&sellCount="+sellCount);
                    goods.setPrice(new BigDecimal(price).setScale(2, RoundingMode.HALF_UP));// 价格
                    goods.setSalesNum(sellCount);
                    goods.setIsTmall(isTmall);
                    goodsService.update(goods);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 自动发营销邮件
     */
//    @Scheduled(cron="0 0 7 * * ?")// 每天早上7点执行
    public void autoSendADByMail(){

        Pageable pageableMailInfo = new PageRequest(0, 100);

        Page<MailInfo> mailInfos = mailInfoService.find(pageableMailInfo);
        MailConfig mailConfig = mailConfigService.findOne();

        List<MailInfo> mailInfoList = mailInfos.getContent();

        com.haoback.common.utils.email.MailInfo mailInfo = new com.haoback.common.utils.email.MailInfo();
        mailInfo.setSubject(mailConfig.getSubject());
        mailInfo.setContent(mailConfig.getContent());

        for(MailInfo m : mailInfoList){
            mailInfo.setToAddress(Arrays.asList(m.getEmail()));
            boolean result = MailUtil.sendEmail(mailInfo, mailConfig);
            m.setIsSend(true);// 发送成功后设置已发送标志，无论是否成功
            mailInfoService.update(m);
        }
    }
}

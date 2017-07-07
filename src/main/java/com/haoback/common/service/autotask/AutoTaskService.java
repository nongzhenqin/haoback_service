package com.haoback.common.service.autotask;

import com.alibaba.fastjson.JSONObject;
import com.haoback.common.utils.CommonUtils;
import com.haoback.common.utils.SSLClient;
import com.haoback.goods.entity.Goods;
import com.haoback.goods.service.GoodsService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自动任务
 * Created by nong on 2017/7/7.
 */
@Component
public class AutoTaskService {

    @Autowired
    private GoodsService goodsService;

    /**
     * 自动捉取淘宝商品最新价格
     */
    @Scheduled(cron="0 15 03 * * ?")// 每天凌晨3点执行
    public void autoGetTaoBaoInfo(){
        List<Goods> goodsList = goodsService.findAll();
        String targetUrl = "https://mdskip.taobao.com/core/initItemDetail.htm?isUseInventoryCenter=false&sellerPreview=false&service3C=true&isPurchaseMallPage=false&cachedTimestamp=1499326709981&addressLevel=2&queryMemberRight=true&isForbidBuyItem=false&isAreaSell=false&isApparel=false&tmallBuySupport=true&household=false&offlineShop=false&isSecKill=false&isRegionLevel=false&showShopProm=false&tryBeforeBuy=false&cartEnable=true&itemId=542679911886&callback=setMdskip&timestamp=1499352070262&isg=null&isg2=ApycK3z8KfE-r90tYQxuSCbIbbyOvUFaWUeSm3admgdzwTxLniUQzxJxVx7D&ref=https%3A%2F%2Fs.click.taobao.com%2Ft_js%3Ftu%3Dhttps%253A%252F%252Fs.click.taobao.com%252Ft%253Fe%253Dm%25253D2%252526s%25253DPqUtWiWY62gcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67sdnvqqQ72iF2yqscP0DbOI4TDOFLMq84x0HboYUFcE7WLvr7c%25252ByinCyB1uQURnJOQNUfesiEEBWFeJIblRvDFAhLvzk%25252B4PKMFplD%25252FwDQ0UaIYULNg46oBA%25253D%2526pvid%253D12_218.17.254.194_488_1497856124560%2526ref%253Dhttp%25253A%25252F%25252Flocalhost%25253A8090%25252Fhaoback_service%25252Findex.html%2526et%253DFwoyxiNZPvKb%25252BLa50vGTi9ow%25252F%25252FiZa9eU";
        for(Goods goods : goodsList){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String url = goods.getUrlLink();
            HttpClient httpClient = null;
            try {
                httpClient = new SSLClient();

                Map<String, String> urlParamsMap = CommonUtils.getUrlParams(targetUrl);
                Map<String, String> goodsMap = CommonUtils.getGoodsItemId(url);

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
                String getUrl = "https://mdskip.taobao.com/core/initItemDetail.htm?" + CommonUtils.getUrlParamsByMap(urlParamsMap);

                HttpGet get = new HttpGet(getUrl);
                get.setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
                get.setHeader("referer", url);
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//设置请求和传输超时时间
                get.setConfig(requestConfig);

                HttpResponse execute = httpClient.execute(get);
                String s = EntityUtils.toString(execute.getEntity(), "utf-8");
                System.out.println(s);

                s = s.substring(s.indexOf("{"), s.lastIndexOf("}")+1);

                JSONObject jsonObject = JSONObject.parseObject(s);

                JSONObject priceInfo = jsonObject.getJSONObject("defaultModel").getJSONObject("itemPriceResultDO").getJSONObject("priceInfo");

                double price = Double.MAX_VALUE;

                for(Map.Entry<String, Object> set : priceInfo.entrySet()){
                    double priceTemp = JSONObject.parseObject(set.getValue().toString()).getDoubleValue("price");
                    if(priceTemp < price){
                        price = priceTemp;
                    }
                }

                Integer sellCount = jsonObject.getJSONObject("defaultModel").getJSONObject("sellCountDO").getInteger("sellCount");

                goods.setPrice(new BigDecimal(Double.toString(price)));// 价格
                goods.setSalesNum(sellCount);
                goodsService.update(goods);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

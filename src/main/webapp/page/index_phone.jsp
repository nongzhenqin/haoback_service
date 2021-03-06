<%@ page import="java.util.UUID" %><%--
  Created by IntelliJ IDEA.
  User: nong
  Date: 2017/7/9
  Time: 下午11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>沃惠挑-优惠券免费领</title>
    <meta name="Description" content="沃惠挑免费领取优惠券、vip券、内部券、优选-优惠券折扣、人工筛选、每天更新，折扣多多，为你省钱！">
    <meta name="Keywords" content="优惠券,优惠券网,vip内部优惠券,vip券,内部券,沃惠挑,优选,生活,9块9,热销推荐,特卖,优选折扣,优品">

    <link href="img/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <link href="img/favicon.ico" rel="icon" type="image/x-icon">
    <link rel="stylesheet" href="static/frozen/css/frozen.css">
    <link rel="stylesheet" href="css/index_phone.css?t=<%=UUID.randomUUID().toString()%>">

    <%--<script src="static/jquery-2.2.4.min.js"></script>--%>
    <script src="static/frozen/lib/zepto.min.js"></script>
    <script src="static/frozen/js/frozen.js"></script>
    <script src="static/clipboard/clipboard.min.js"></script>
</head>

<body ontouchstart>
<%--微信复制到剪贴板后的提示框--%>
<div class="ui-dialog">
    <div class="ui-dialog-cnt">
        <header class="ui-dialog-hd ui-border-b">
            <h3>提示</h3>
            <i class="ui-dialog-close" data-role="button"></i>
        </header>
        <div class="ui-dialog-bd">
            <div>淘口令已复制到剪贴板，请打开淘宝APP查看</div>
        </div>
        <%--<div class="ui-dialog-ft">--%>
            <%--<button type="button" data-role="button">关闭</button>--%>
        <%--</div>--%>
    </div>
</div>

<div class="ui-row-flex ui-whitespace">
    <%--<c:forEach var="type" items="${goodsTypes}">--%>
        <%--<div class="ui-col ui-col">--%>
            <%--<a class="a-type" href="#${type.code}">${type.name}</a>--%>
        <%--</div>--%>
    <%--</c:forEach>--%>
</div>

<%--搜索框--%>
<div class="jd-header-home-wrapper">
    <div class="jd-search-container on-blur" id="index_search_head">
        <div class="jd-search-box-cover" style="opacity: 0;" id="search_cover"></div>
        <div class="jd-search-box">
            <div class="jd-search-tb">
                <%--<div class="jd-search-icon">--%>
                <%--<span id="index_search_bar_cancel" class="jd-search-icon-cancel"><i class="jd-sprite-icon"></i></span>--%>
                <%--</div>--%>
                <form action="/search" onsubmit="return false;">
                    <div class="jd-search-form-box cf">
                        <span class="jd-search-icon-logo J_ping" report-eventid="MHome_Blogo" report-eventparam="" report-eventlevel="1" page_name="index"></span>
                        <span class="jd-search-form-icon"></span>
                        <div class="jd-search-form-input">
                            <input type="search" maxlength="20" autocomplete="off" id="index_newkeyword" name="keyword" <c:if test="${search == true}">value="${key}"</c:if> placeholder="搜索宝贝" class="hilight1">
                            <%--<input id="index_category" name="catelogyList" type="text" style="display:none">--%>
                        </div>

                        <a href="javascript:void(0);" class="jd-search-icon-close jd-sprite-icon" id="index_clear_keyword"></a>
                        <a href="javascript:void(0)" id="index_search_submit" class="jd-search-form-action"><span class="jd-sprite-icon"></span></a>

                    </div>
                </form>
                <%--<div class="jd-search-login login-ing">--%>
                <%--<a id="index_searchLogin" href="https://passport.m.jd.com/user/login.action?returnurl=https://m.jd.com?indexloc=1" class="J_ping" page_name="index" report-eventlevel="1" report-eventparam="" report-eventid="MHome_BLogin">--%>
                <%--<span class="jd-search-icon-login">登录</span></a>--%>
                <%--</div>--%>
            </div>
        </div>
    </div>
</div>

<c:if test="${search == false}">
<%--轮播图--%>
<div class="ui-slider" style="height: 60px;">
    <ul class="ui-slider-content" style="width: 300%;">
        <%--<li><span style="background-image:url(./upload/22fa46cb-bef4-44f2-82d3-39984fadaa1b.jpg)"></span></li>--%>
        <%--<li><span style="background-image:url(./upload/34fd55b4-7c99-4260-a23b-919fc44c76e6.jpg)"></span></li>--%>
        <c:forEach var="goodsCarousel" items="${goodsCarouselList}" varStatus="status">
            <li><img class="product-pvuv" data-clipboard-text="${goodsCarousel.taoCommand}" is-lunbo="1" style="width: 100%;height: 100%;" src="${ctx}upload/${goodsCarousel.fileId}.jpg" id-data="${goodsCarousel.id}" url-data="${goodsCarousel.urlLinkCoupon != null && goodsCarousel.urlLinkCoupon.length() > 0 ? goodsCarousel.urlLinkCoupon : goodsCarousel.urlLink}" alt="优质生活，每日更新" /></li>
        </c:forEach>
    </ul>
</div>

<section class="ui-container">
    <section id="layout" style="background-color: #F9F9F9;">
        <ul class="ui-row">
            <li class="ui-col ui-col-100">
                <h4 class="h4-title">热销推荐</h4>
            </li>
            <c:forEach var="goods" items="${goodsTypesHot}" varStatus="status">
                <li class="ui-col ui-col-50" style="position: relative;text-align: left;height: 290px;background-color: rgb(255,255,255);<c:if test="${status.index > 1}">margin-top:10px;</c:if>">
                    <img class="product-img product-pvuv" onclick="clickGoods(this)" data-clipboard-text="${goods.taoCommand}" id-data="${goods.id}" url-data="${goods.urlLink}" style="width: 100%; height: auto;" src="${goods.picUrl == null ? (ctx==null?'':ctx).concat('upload/').concat(goods.fileId).concat('.jpg') : goods.picUrl}">
                    <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                    <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                    <span class="ui-nowrap-multi ui-whitespace" style="font-size: 14px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">
                        <c:if test="${goods.isTmall == true}">
                            <img src="${ctx}img/tmall.png" style="height: 13px;width: 13px;">
                        </c:if>
                            ${goods.name}
                    </span>
                    <%--<c:if test="${goods.info != null && goods.info.length() > 0}">--%>
                        <%--<span class="ui-nowrap ui-whitespace" style="color: #888;font-size: 12px;float: left;padding-top: 5px;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.info}</span>--%>
                    <%--</c:if>--%>
                    <a class="a-coupon" href="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0 ? goods.urlLinkCoupon : goods.urlLink}" target="_blank">
                        <c:if test="${goods.couponAmount != null}">
                            立即领取<span style="font-weight: bold;"><fmt:formatNumber type="number" value="${goods.couponAmount } " maxFractionDigits="0"/></span>元劵
                        </c:if>
                        <c:if test="${goods.couponAmount == null}">
                            无劵
                        </c:if>
                    </a>
                </li>
            </c:forEach>
        </ul>

        <c:forEach var="type" items="${goodsTypes}">
            <ul class="ui-row" style="padding-top:15px;">
                <li class="ui-col ui-col-100">
                    <h4 class="h4-title">${type.name}</h4>
                </li>
                <c:forEach var="goods" items="${type.goodsList}" varStatus="status">
                    <li class="ui-col ui-col-50" style="position: relative;text-align: left;height: 290px;background-color: rgb(255,255,255);<c:if test="${status.index > 1}">margin-top:10px;</c:if>">
                        <img class="product-img product-pvuv" onclick="clickGoods(this)" data-clipboard-text="${goods.taoCommand}" id-data="${goods.id}" url-data="${goods.urlLink}" style="width: 100%; height: auto;" src="${goods.picUrl == null ? (ctx==null?'':ctx).concat('upload/').concat(goods.fileId).concat('.jpg') : goods.picUrl}">
                        <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                        <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                        <span class="ui-nowrap-multi ui-whitespace" style="font-size: 14px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">
                            <c:if test="${goods.isTmall == true}">
                                <img src="${ctx}img/tmall.png" style="height: 13px;width: 13px;">
                            </c:if>
                                ${goods.name}
                        </span>
                        <%--<c:if test="${goods.info != null && goods.info.length() > 0}">--%>
                            <%--<span class="ui-nowrap ui-whitespace" style="color: #888;font-size: 12px;float: left;padding-top: 5px;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.info}</span>--%>
                        <%--</c:if>--%>
                        <a class="a-coupon" href="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0 ? goods.urlLinkCoupon : goods.urlLink}" target="_blank">
                            <c:if test="${goods.couponAmount != null}">
                                立即领取<span style="font-weight: bold;"><fmt:formatNumber type="number" value="${goods.couponAmount } " maxFractionDigits="0"/></span>元劵
                            </c:if>
                            <c:if test="${goods.couponAmount == null}">
                                无劵
                            </c:if>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:forEach>
    </section>


</section>
</c:if>

<c:if test="${search == true}">
    <section id="layout" style="background-color: #F9F9F9;padding-top: 40px;">
        <ul id="search-ul" class="ui-row">
            <%--<li class="ui-col ui-col-100">--%>
                <%--<h4 class="h4-title">${type.name}</h4>--%>
            <%--</li>--%>
            <c:forEach var="goods" items="${goodsList}" varStatus="status">
                <li class="ui-col ui-col-50" style="position: relative;text-align: left;height: 290px;background-color: rgb(255,255,255);<c:if test="${status.index > 1}">margin-top:10px;</c:if>">
                    <img class="product-img product-pvuv" onclick="clickGoods(this)" data-clipboard-text="${goods.taoCommand}" id-data="${goods.id}" url-data="${goods.urlLink}" style="width: 100%; height: auto;" src="${goods.picUrl == null ? (ctx==null?'':ctx).concat('upload/').concat(goods.fileId).concat('.jpg') : goods.picUrl}">
                    <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                    <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                    <span class="ui-nowrap-multi ui-whitespace" style="font-size: 14px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">
                        <c:if test="${goods.isTmall == true}">
                            <img src="${ctx}img/tmall.png" style="height: 13px;width: 13px;">
                        </c:if>
                            ${goods.name}
                    </span>
                    <c:if test="${goods.info != null && goods.info.length() > 0}">
                        <span class="ui-nowrap ui-whitespace" style="color: #888;font-size: 12px;float: left;padding-top: 5px;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.info}</span>
                    </c:if>
                    <a class="a-coupon" href="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0 ? goods.urlLinkCoupon : goods.urlLink}" target="_blank">
                        <c:if test="${goods.couponAmount != null}">
                            立即领取<span style="font-weight: bold;"><fmt:formatNumber type="number" value="${goods.couponAmount } " maxFractionDigits="0"/></span>元劵
                        </c:if>
                        <c:if test="${goods.couponAmount == null}">
                            无劵
                        </c:if>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </section>
    <c:if test="${goodsList.size() == 0}">
        <div style="text-align: center;padding-top: 50px;">
            抱歉，暂时没有相关商品信息，搜索别的试试~
        </div>
    </c:if>
</c:if>
</body>
</html>

<script type="text/javascript">
    // 是微信浏览器打开则直接复制淘口令到剪贴板
    var isWx = isWeiXin();
    var isSpider = ${isSpider};

    // 点击商品
    function clickGoods(_this){
        if(isSpider || isWx) return;
        _this = $(_this);
        window.open(_this.attr('url-data'), '_bank');

        var titileName = "商品";
        if(_this.attr('is-lunbo') == '1'){
            titileName = "轮播图";
        }
        // 保存pv
        $.post('./goods/pv_uv', {goodsId: _this.attr('id-data'), referer: document.referrer.toLowerCase(), titileName: titileName, flag: "mobile"});
    }

    $(function(){
        if(isWx){
            var clipboard = new Clipboard('.product-pvuv');
            clipboard.on('success',function(e){
//                    e.clearSelection();
//                    console.info('Action:',e.action);
//                    console.info('Text:',e.text);
//                    console.info('Trigger:',e.trigger);
                    $(".ui-dialog").dialog("show");

                    var titileName = "商品";
                    if($(e.trigger).attr('is-lunbo') == '1'){
                        titileName = "轮播图";
                    }
                    // 保存pv
                    $.post('./goods/pv_uv', {goodsId: $(e.trigger).attr('id-data'), referer: document.referrer.toLowerCase(), titileName: titileName, flag: "mobile"});
                });
            clipboard.on('error', function(e){
                alert(e);
            });
        }

        // 点击事件
        <%--if(!${isSpider}){--%>
            <%--!isWx && $('.product-pvuv').on('click', function(){--%>
                <%--window.open($(this).attr('url-data'), '_bank');--%>
                <%--var titileName = "商品";--%>
                <%--if($(this).attr('is-lunbo') == '1'){--%>
                    <%--titileName = "轮播图";--%>
                <%--}--%>
                <%--// 保存pv--%>
                <%--$.post('./goods/pv_uv', {goodsId: $(this).attr('id-data'), referer: document.referrer.toLowerCase(), titileName: titileName, flag: "mobile"});--%>
            <%--});--%>
        <%--}--%>

        // 保存pv
        var source = 'normal';
        if(getUrlParms('_s') == 'e'){
            source = 'email';
        }
        $.post('./goods/pv_uv', {goodsId: 0, referer: document.referrer.toLowerCase(), titileName: "首页", flag: "mobile", source: source});

        // 屏幕滚动事件
        $(window).scroll(function () {
            var top = $(window).scrollTop();
            if(top < 185){
                $('#search_cover').css('opacity', top/185);
            }
        });

        <c:if test="${search == true}">
            // 滚动到底部后加装商品
            var pageNo = 1;
            var totalPage = 0;
            var isAllowLoad = true;// 控制调用频率，必须在ajax加载完后才能再次加载
            $(window).scroll(function () {
                if(!isAllowLoad) return;
                if(pageNo > 2 && pageNo >= totalPage){
                    return;
                }
                var $this =$(this),
                    viewH = $(document).height(),//可见高度
                    contentH = window.innerHeight,//内容高度
                    scrollTop = $this.scrollTop();//滚动高度
                //if(contentH - viewH - scrollTop <= 100) { //到达底部100px时,加载新内容
                if((scrollTop + contentH) / viewH >= 0.90){ //到达底部100px时,加载新内容
                    // 这里加载数据..
                    isAllowLoad = false;
                    pageNo++;
                    searchAjax($('#index_newkeyword').val(), pageNo, function(res){
                        var goodsList = res.datas.goods.content;
                        if(goodsList.length > 0){
                            var html = '';
                            var ctx = '${ctx}';
                            for(var i=0,len=goodsList.length; i<len; i++){
                                var goods = goodsList[i];
                                html += '<li class="ui-col ui-col-50" style="position: relative;text-align: left;height: 290px;background-color: rgb(255,255,255);margin-top:10px;">';
                                html += '<img class="product-img product-pvuv" onclick="clickGoods(this)" data-clipboard-text="'+goods.taoCommand+'" id-data="'+goods.id+'" url-data="'+goods.urlLink+'" style="width: 100%; height: auto;" src="'+(goods.picUrl == null ? (!ctx||ctx==null?'':ctx)+'upload/'+goods.fileId+'.jpg' : goods.picUrl)+'">';
                                html += '<strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥'+goods.price+'</strong>';
                                html += '<span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;'+goods.salesNum+'</span>';
                                html += '<span class="ui-nowrap-multi ui-whitespace" style="font-size: 14px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,\'Hiragino Sans GB\', 宋体,sans-serif;">';
                                if(goods.isTmall){
                                    html += '<img src="'+ctx+'img/tmall.png" style="height: 13px;width: 13px;">';
                                }
                                html += goods.name;
                                html += '</span>';
//                                if(goods.info != null && goods.info.length > 0){
//                                    html += '<span class="ui-nowrap ui-whitespace" style="color: #888;font-size: 12px;float: left;padding-top: 5px;padding-left: 8px;padding-right: 8px;font-family: arial,\'Hiragino Sans GB\', 宋体,sans-serif;">'+goods.info+'</span>';
//                                }
                                html += '<a class="a-coupon" '+(goods.urlLinkCoupon != null && goods.urlLinkCoupon.length > 0 ? 'href="'+goods.urlLinkCoupon+'"' : 'href="'+goods.urlLink+'"')+' target="_blank">';
                                if(goods.couponAmount != null && goods.couponAmount != ''){
                                    html += '立即领取<span style="font-weight: bold;">'+goods.couponAmount+'</span>元劵';
                                }else{
                                    html += '无劵';
                                }
                                html += '</a>';
                                html += '</li>';
                            }
                            $('#search-ul').append(html);
                        }

                        totalPage = res.datas.goods.totalPages;
                        isAllowLoad = true;
                    });
                }
            });
        </c:if>

        // 搜索事件
        $('#index_newkeyword').bind('search', function(){
            search($(this).val(), 1);
        });
    });

    // ajax滚动加载搜索方法
    function searchAjax(key, pageNo, callBack){
        $.ajax({
            url: './goods/page',
            type: 'GET',
            dataType: 'json',
            data: {pageNo: pageNo, pageSize: 25, key: key},
            success: function(res){
                !!callBack && callBack(res);
            }
        });
    }

    // 搜索方法
    function search(key, pageNo){
        var key = key || $('#index_newkeyword').val();
        var url = window.location.href.split('?')[0].split('#')[0];
        window.location.href = !!key ? url + '?key=' + key + '&pageNo=' + pageNo: url;
    }

    function isWeiXin() {
        var ua = window.navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == 'micromessenger') {
            return true;
        } else {
            return false;
        }
    }

    //获取地址栏参数，name:参数名称
    function getUrlParms(name){
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r != null)
            return unescape(r[2]);
        return null;
    }
</script>

<c:if test="${search == false}">
<script type="text/javascript">
//    轮播图
    (function (){
        var slider = new fz.Scroll('.ui-slider', {
            role: 'slider',
            indicator: true,
            autoplay: true,
            interval: 3000
        });

        slider.on('beforeScrollStart', function(fromIndex, toIndex) {
//            console.log(fromIndex,toIndex)
        });

        slider.on('scrollEnd', function(cruPage) {
//            console.log(cruPage)
        });
    })();
</script>
</c:if>
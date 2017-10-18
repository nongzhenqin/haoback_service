<%@ page import="java.util.UUID" %>
<%--
  Created by IntelliJ IDEA.
  User: nong
  Date: 2017/6/20
  Time: 下午4:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>沃惠挑-精挑细选优质商品</title>
    <meta name="Keywords" content="沃惠挑,优选,生活,9块9,热销推荐,特卖,优选折扣,优品">
    <meta name="Description" content="沃惠挑,优选-优惠券折扣，人工筛选，每天更新，折扣多多">

    <link href="img/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <link href="img/favicon.ico" rel="icon" type="image/x-icon">

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <%--<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">--%>
    <link rel="stylesheet" href="static/bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/index.css?t=<%=UUID.randomUUID().toString()%>">
    <link rel="stylesheet" href="static/frozen/css/frozen.css">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <%--<script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>--%>

    <script src="static/jquery-3.2.1.min.js"></script>
    <script src="static/bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script src="static/scrollTop.js"></script>
    <script src="static/page.js"></script>

</head>

<body>
<div class="row head-top">
    <div class="col-md-2"></div>
    <div class="col-md-10">你好，欢迎来到沃惠挑</div>
    <div class="col-md-10" style="text-align: right;padding-right: 0px;">
        <a id="set-home">设为首页</a>
        <span style="color: #d3d3d3;">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
        <a id="add-favorite">收藏本站</a>
    </div>
    <div class="col-md-2"></div>
</div>

<div class="row search-row">
    <div class="col-md-2"></div>
    <div class="col-md-3">
        <a href="./">
            <img style="width: 175px;height: 75px;margin-top: -20px;margin-left: -10px;" src="${ctx}img/logo.png">
        </a>
    </div>
    <div class="col-md-8">
        <input type="text" class="header-search-input" id="header-search-input" placeholder="输入关键字搜索全网优惠" <c:if test="${search == true}">value="${key}"</c:if>>
        <input type="submit" class="header-search-btn" id="header-search-btn" value="">
        <div class="search-key">
            热门搜索：
            <a class="search-a">包包</a>
            <a class="search-a">女装</a>
            <a class="search-a">零食</a>
            <a class="search-a">化妆品</a>
            <a class="search-a">9块9</a>
        </div>
    </div>
    <div class="col-md-9" style="float: right;">
        <div class="header-feature">
            <i class="i1"></i>全网好货
        </div>
        <div class="header-feature">
            <i class="i2"></i>人工推荐
        </div>
        <div class="header-feature">
            <i class="i3"></i>每日更新
        </div>
    </div>
    <div class="col-md-2"></div>
</div>

<c:if test="${search == false}">
    <div class="row head-row">
        <div class="row top-div" style="margin: 0 auto;background-color: #3a3838">
            <div class="col-md-2"></div>
            <div class="col-md-20">
                <ul class="nav navbar-nav" style="margin-left: -15px;">
                    <c:forEach var="type" items="${goodsTypes}">
                        <li>
                            <a class="a-type" href="#${type.code}">${type.name}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-md-2"></div>
        </div>
    </div>
    <%--轮播图--%>
    <div class="row">
        <div id="carousel-example-generic" class="carousel slide top-div" style="margin: 0 auto;" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <c:forEach var="goodsCarousel" items="${goodsCarouselList}" varStatus="status">
                    <li data-target="#carousel-example-generic" data-slide-to="${status.index}" <c:if test="${status.index == 0}">class="active"</c:if>></li>
                </c:forEach>
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">
                <c:forEach var="goodsCarousel" items="${goodsCarouselList}" varStatus="status">
                    <div class="item<c:if test="${status.index == 0}"> active</c:if>">
                        <img class="product-pvuv" src="${ctx}upload/${goodsCarousel.fileId}.jpg" id-data="${goodsCarousel.id}" is-lunbo="1" onclick="window.open('${goodsCarousel.urlLinkCoupon != null && goodsCarousel.urlLinkCoupon.length() > 0 ? goodsCarousel.urlLinkCoupon : goodsCarousel.urlLink}', '_bank')" style="width: 100%;height: 400px;" alt="优质生活，每日更新">
                        <div class="carousel-caption"></div>
                    </div>
                </c:forEach>
            </div>

            <!-- Controls -->
            <a style="background-image: none;" class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a style="background-image: none;" class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
</c:if>

<c:if test="${search == true}">
    <div class="row head-row">
        <div class="row top-div" style="margin: 0 auto;background-color: #3a3838">
            <div class="col-md-2"></div>
            <div class="col-md-20" style="color: white;height: 50px;">
                <div style="padding-top: 15px;">为您找到的搜索结果，共${goodsListTotal}条</div>
            </div>
            <div class="col-md-2"></div>
        </div>
    </div>
</c:if>

<%--首页--%>
<c:if test="${search == false}">
    <div class="container-fluid top-div">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-5">
                <br>
                <h2 class="h2-title">热销推荐</h2>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2"></div>
            <c:forEach var="goods" items="${goodsTypesHot}" varStatus="status">
                <c:if test="${status.index != 0 && status.index % 5 == 0}">
                    <div class="col-md-2"></div>
                </c:if>
                <div class="col-md-4 col-style">
                    <div class="row product-pvuv" style="cursor:pointer;position: relative;height: 100%;" id-data="${goods.id}"
                         url-data="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0 ? goods.urlLinkCoupon : goods.urlLink}">
                        <div class="col-md-24">
                            <img class="product-img" src="${ctx}upload/${goods.fileId}.jpg">
                        </div>
                        <div class="col-md-24" style="padding-top:8px;">
                            <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                            <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                        </div>
                        <div class="col-md-24" style="padding-top: 8px;text-align: left;">
                            <span class="ui-nowrap-multi ui-whitespace"
                                  style="font-size: 12px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">
                                <c:if test="${goods.isTmall == true}">
                                    <img style="width: 13px;height: 13px;" src="${ctx}img/tmall.png">
                                </c:if>
                                    ${goods.name}
                            </span>
                        </div>
                        <c:if test="${goods.info != null && goods.info.length() > 0}">
                            <div class="col-md-24" style="padding-top:8px;text-align: left;">
                                <span class="ui-nowrap ui-whitespace"
                                      style="color: #888;font-size: 10px;float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.info}</span>
                            </div>
                        </c:if>
                    </div>
                    <a class="a-coupon" <c:if test="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0}">href="${goods.urlLinkCoupon}" target="_blank" </c:if>>
                        <c:if test="${goods.couponAmount != null}">
                            立即领取<span style="font-weight: bold;"><fmt:formatNumber type="number" value="${goods.couponAmount } " maxFractionDigits="0"/></span>元劵
                        </c:if>
                        <c:if test="${goods.couponAmount == null}">
                            无劵
                        </c:if>
                    </a>
                </div>
            </c:forEach>
            <div class="col-md-2"></div>
        </div>

        <c:forEach var="type" items="${goodsTypes}">
            <div class="row" style="margin-top: 55px;">
                <div class="col-md-2"></div>
                <div class="col-md-5">
                    <br>
                    <h2 class="h2-title" id="${type.code}">${type.name}</h2>
                </div>
            </div>

            <div class="row">
                <div class="col-md-2"></div>
                <c:forEach var="goods" items="${type.goodsList}" varStatus="status">
                    <c:if test="${status.index != 0 && status.index % 5 == 0}">
                        <div class="col-md-2"></div>
                    </c:if>
                    <div class="col-md-4 col-style" style="<c:if test="${status.index > 4}">margin-top: 60px;</c:if>">
                        <div class="row product-pvuv" style="cursor:pointer;position: relative;height: 100%;" id-data="${goods.id}"
                             url-data="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0 ? goods.urlLinkCoupon : goods.urlLink}">
                            <div class="col-md-24">
                                <img class="product-img" src="${ctx}upload/${goods.fileId}.jpg">
                            </div>
                            <div class="col-md-24" style="padding-top:8px;">
                                <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                                <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                            </div>
                            <div class="col-md-24" style="padding-top: 8px;text-align: left;">
                                <span class="ui-nowrap-multi ui-whitespace"
                                      style="font-size: 12px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">
                                    <c:if test="${goods.isTmall == true}">
                                        <img style="width: 13px;height: 13px;" src="${ctx}img/tmall.png">
                                    </c:if>
                                        ${goods.name}
                                </span>
                            </div>
                            <c:if test="${goods.info != null && goods.info.length() > 0}">
                                <div class="col-md-24" style="padding-top:8px;text-align: left;">
                                    <span class="ui-nowrap ui-whitespace"
                                          style="color: #888;font-size: 10px;float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.info}</span>
                                </div>
                            </c:if>
                        </div>
                        <a class="a-coupon" <c:if test="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0}">href="${goods.urlLinkCoupon}" target="_blank" </c:if>>
                            <c:if test="${goods.couponAmount != null}">
                                立即领取<span style="font-weight: bold;"><fmt:formatNumber type="number" value="${goods.couponAmount } " maxFractionDigits="0"/></span>元劵
                            </c:if>
                            <c:if test="${goods.couponAmount == null}">
                                无劵
                            </c:if>
                        </a>
                    </div>
                </c:forEach>
                <div class="col-md-2"></div>
            </div>
        </c:forEach>
        <br>
    </div>
</c:if>

<%--搜索页面--%>
<c:if test="${search == true}">
    <div class="container-fluid top-div">
        <div class="row">
            <div class="col-md-2"></div>
            <c:forEach var="goods" items="${goodsList}" varStatus="status">
                <c:if test="${status.index != 0 && status.index % 5 == 0}">
                    <div class="col-md-2"></div>
                </c:if>
                <div class="col-md-4 col-style" style="<c:if test="${status.index > 4}">margin-top: 60px;</c:if>">
                    <div class="row product-pvuv" style="cursor:pointer;position: relative;height: 100%;" id-data="${goods.id}"
                         url-data="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0 ? goods.urlLinkCoupon : goods.urlLink}">
                        <div class="col-md-24">
                            <img class="product-img" src="${ctx}upload/${goods.fileId}.jpg">
                        </div>
                        <div class="col-md-24" style="padding-top:8px;">
                            <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                            <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                        </div>
                        <div class="col-md-24" style="padding-top: 8px;text-align: left;">
                                <span class="ui-nowrap-multi ui-whitespace"
                                      style="font-size: 12px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">
                                    <c:if test="${goods.isTmall == true}">
                                        <img style="width: 13px;height: 13px;" src="${ctx}img/tmall.png">
                                    </c:if>
                                        ${goods.name}
                                </span>
                        </div>
                        <c:if test="${goods.info != null && goods.info.length() > 0}">
                            <div class="col-md-24" style="padding-top:8px;text-align: left;">
                                    <span class="ui-nowrap ui-whitespace"
                                          style="color: #888;font-size: 10px;float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.info}</span>
                            </div>
                        </c:if>
                    </div>
                    <a class="a-coupon" <c:if test="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0}">href="${goods.urlLinkCoupon}" target="_blank" </c:if>>
                        <c:if test="${goods.couponAmount != null}">
                            立即领取<span style="font-weight: bold;"><fmt:formatNumber type="number" value="${goods.couponAmount } " maxFractionDigits="0"/></span>元劵
                        </c:if>
                        <c:if test="${goods.couponAmount == null}">
                            无劵
                        </c:if>
                    </a>
                </div>
            </c:forEach>
            <div class="col-md-2"></div>
        </div>
        <c:if test="${goodsList.size() == 0}">
            <div class="row" style="width: 100%;">
                <div class="col-md-24" style="height: 600px;padding-top:20px;text-align:center;background-color:white;">
                    抱歉，暂时没有相关商品信息
                </div>
            </div>
        </c:if>
    </div>
</c:if>

<c:if test="${goodsListTotal > 10}">
    <div class="pagec" style="text-align: center;margin-top: 60px;">
        <ul class="pagination" id="page-ul"></ul>
    </div>

</c:if>

<div class="row" style="width: 100%;<c:if test="${search == true}">margin-top: 60px;</c:if><c:if test="${search == false}">margin-top: 30px;</c:if>">
    <div class="col-md-24" style="height: 60px;padding-top:20px;text-align:center;background-color:white;">
        我是有底线的~ © CopyRight 2017 沃惠挑
        <div class="toTop" style="display: block;background: url('img/top.png'); height: 56px; width: 56px;"
             onclick=" $('body,html').animate({scrollTop:0},1000);">
        </div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    $(function () {
        // 点击事件
        if (!${isSpider}) {
            $('.product-pvuv').on('click', function () {
                window.open($(this).attr('url-data'), '_bank');
                var titileName = "商品";
                if($(this).attr('is-lunbo') == '1'){
                    titileName = "轮播图";
                }
                // 保存pv
                $.post('./goods/pv_uv', {
                    goodsId: $(this).attr('id-data'),
                    referer: document.referrer.toLowerCase(),
                    titileName: titileName,
                    flag: "pc"
                });
            });
        }

        // 保存pv
        $.post('./goods/pv_uv', {goodsId: 0, referer: document.referrer.toLowerCase(), titileName: "首页", flag: "pc"});

        // 回到顶部按钮
        $(window).scroll(function () {
            if ($(window).scrollTop() > 500) {
                $(".toTop").fadeIn(1000);
            }
            else {
                $(".toTop").fadeOut(1000);
            }
        });

        // 设为首页
        $('#set-home').on('click', function(){
            var _this = $(this);
            var url = 'http://wohuitiao.com';
            try{
                _this.style.behavior='url(#default#homepage)';
                _this.setHomePage(url);
            }catch(e){
                if(window.netscape){
                    try{
                        netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                    }catch(e){
                        alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
                    }
                }else{
                    alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【"+url+"】设置为首页。");
                }
            }
        });

        // 收藏本站
        $('#add-favorite').on('click', function(){
            var title = '优选-精挑细选优质商品';
            var url = window.location.href;
            try {
                window.external.addFavorite(url, title);
            }
            catch (e) {
                try {
                    window.sidebar.addPanel(title, url, "");
                }
                catch (e) {
                    alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加");
                }
            }
        });

        // 搜索
        $('#header-search-btn').on('click', function(){
            search(null, 1);
        });

        // 搜索回车
        $('#header-search-input').on('keyup', function(e){
            if(e.keyCode == 13){
                $('#header-search-btn').click();
            }
        });

        // 分页
        $("#page-ul").length > 0 && $("#page-ul").createPage({
            totalPage: ${goodsListTotalPage},
            currPage: ${goodsListPageNo},
            backFn:function(p){
                search(null, p);
            }
        });

        // 热门关键字搜索
        $('.search-a').on('click', function(){
            search($(this).text(), 1);
        });

    });

    // 搜索方法
    function search(key, pageNo){
        var key = key || $('#header-search-input').val();
        var url = window.location.href.split('?')[0].split('#')[0];
        window.location.href = !!key ? url + '?key=' + key + '&pageNo=' + pageNo: url;
    }
</script>
<%@ page import="java.util.UUID" %>
<%--
  Created by IntelliJ IDEA.
  User: nong
  Date: 2017/6/20
  Time: 下午4:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <title>优选-精挑细选优质商品</title>
    <meta name="Keywords" content="优选,生活,9块9,热销推荐,特卖,优选折扣,优品">
    <meta name="Description" content="优选-优惠券折扣，人工筛选，每天更新，折扣多多">

    <link href="img/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <link href="img/favicon.ico" rel="icon" type="image/x-icon">

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <%--<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">--%>
    <link rel="stylesheet" href="static/bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/index.css?t=<%=UUID.randomUUID().toString()%>">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <%--<script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>--%>

    <script src="static/jquery-3.2.1.min.js"></script>
    <script src="static/scrollTop.js"></script>

</head>

<body>
<div class="container-fluid">
    <div class="row head-row">
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

    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-5">
            <h2 class="h2-title">热销推荐</h2>
        </div>
    </div>

    <div class="row">
        <div class="col-md-2"></div>
        <c:forEach var="goods" items="${goodsTypesHot}" varStatus="status">
            <c:if test="${status.index != 0 && status.index % 5 == 0}">
                <div class="col-md-2"></div>
            </c:if>
            <div class="col-md-4 col-style" id-data="${goods.id}" url-data="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0 ? goods.urlLinkCoupon : goods.urlLink}">
                <div class="row" style="position: relative;height: 100%;">
                    <div class="col-md-24">
                        <img class="product-img" src="./upload/${goods.fileId}.jpg">
                    </div>
                    <div class="col-md-24" style="padding-top:8px;">
                        <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                        <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                    </div>
                    <div class="col-md-24" style="padding-top:8px;">
                        <span style="font-size: 12px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.name}</span>
                    </div>
                    <c:if test="${goods.info != null && goods.info.length() > 0}">
                        <div class="col-md-24" style="padding-top:8px;">
                            <span style="color: #888;font-size: 10px;">${goods.info}</span>
                        </div>
                    </c:if>
                    <c:if test="${goods.isTmall == true}">
                        <div class="col-md-24" style="position: absolute;bottom: 0;">
                            <img src="img/tmall.png" style="height: 16px;width: 16px;float: right;">
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
        <div class="col-md-2"></div>
    </div>

    <c:forEach var="type" items="${goodsTypes}">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-5">
                <h2 class="h2-title" id="${type.code}">${type.name}</h2>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2"></div>
            <c:forEach var="goods" items="${type.goodsList}" varStatus="status">
                <c:if test="${status.index != 0 && status.index % 5 == 0}">
                    <div class="col-md-2"></div>
                </c:if>
                <div class="col-md-4 col-style" id-data="${goods.id}" url-data="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0 ? goods.urlLinkCoupon : goods.urlLink}">
                    <div class="row" style="position: relative;height: 100%;">
                        <div class="col-md-24">
                            <img class="product-img" src="./upload/${goods.fileId}.jpg">
                        </div>
                        <div class="col-md-24" style="padding-top:8px;">
                            <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                            <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                        </div>
                        <div class="col-md-24" style="padding-top:8px;">
                            <span style="font-size: 12px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.name}</span>
                        </div>
                        <c:if test="${goods.info != null && goods.info.length() > 0}">
                            <div class="col-md-24" style="padding-top:8px;">
                                <span style="color: #888;font-size: 10px;">${goods.info}</span>
                            </div>
                        </c:if>
                        <c:if test="${goods.isTmall == true}">
                            <div class="col-md-24" style="position: absolute;bottom: 0;">
                                <img src="img/tmall.png" style="height: 16px;width: 16px;float: right;">
                            </div>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
            <div class="col-md-2"></div>
        </div>
    </c:forEach>

    <br>

    <div class="row">
        <div class="col-md-24" style="height: 60px;padding-top:20px;text-align:center;background-color:white;">
            我是有底线的~ © CopyRight 2017 优选
            <div class="toTop" style="display: block;background: url('img/top.png'); height: 56px; width: 56px;" onclick=" $('body,html').animate({scrollTop:0},1000);">
            </div>
            <%--<span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>--%>
        </div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    $(function(){
        // 点击事件
        if(!${isSpider}){
            $('.col-style').on('click', function(){
                window.open($(this).attr('url-data'), '_bank');
                // 保存pv
                $.post('./goods/pv_uv', {goodsId: $(this).attr('id-data'), referer: document.referrer.toLowerCase(), titileName: "商品"});
            });
        }

        // 保存pv
        $.post('./goods/pv_uv', {goodsId: 0, referer: document.referrer.toLowerCase(), titileName: "首页"});

        // 回到顶部按钮
        $(window).scroll(function () {
            if ($(window).scrollTop() > 500) {
                $(".toTop").fadeIn(1000);
            }
            else {
                $(".toTop").fadeOut(1000);
            }
        });
    });
</script>
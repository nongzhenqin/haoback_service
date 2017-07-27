<%@ page import="java.util.UUID" %><%--
  Created by IntelliJ IDEA.
  User: nong
  Date: 2017/7/9
  Time: 下午11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>优选-精挑细选优质商品</title>
    <meta name="Keywords" content="优选,生活,9块9,热销推荐,特卖,优选折扣,优品">
    <meta name="Description" content="优选-优惠券折扣，人工筛选，每天更新，折扣多多">

    <link href="img/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <link href="img/favicon.ico" rel="icon" type="image/x-icon">
    <link rel="stylesheet" href="static/frozen/css/frozen.css">
    <link rel="stylesheet" href="css/index.css?t=<%=UUID.randomUUID().toString()%>">

    <script src="static/jquery-3.2.1.min.js"></script>
</head>

<body ontouchstart>
<div class="ui-row-flex ui-whitespace">
    <%--<c:forEach var="type" items="${goodsTypes}">--%>
        <%--<div class="ui-col ui-col">--%>
            <%--<a class="a-type" href="#${type.code}">${type.name}</a>--%>
        <%--</div>--%>
    <%--</c:forEach>--%>
</div>
<section class="ui-container">
    <section id="layout" style="background-color: #F9F9F9;">
        <ul class="ui-row">
            <li class="ui-col ui-col-100">
                <h4 class="h4-title">热销推荐</h4>
            </li>
            <c:forEach var="goods" items="${goodsTypesHot}" varStatus="status">
                <li class="ui-col ui-col-50 click" style="text-align: left;height: 310px;background-color: rgb(255,255,255);" id-data="${goods.id}" url-data="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0 ? goods.urlLinkCoupon : goods.urlLink}">
                    <img class="product-img" style="width: 100%; height: auto;" src="./upload/${goods.fileId}.jpg">
                    <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                    <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                    <span class="ui-nowrap-multi ui-whitespace" style="font-size: 14px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.name}</span>
                    <c:if test="${goods.info != null && goods.info.length() > 0}">
                        <span class="ui-nowrap ui-whitespace" style="color: #888;font-size: 12px;float: left;padding-top: 5px;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.info}</span>
                    </c:if>
                    <c:if test="${goods.isTmall == true}">
                        <img src="img/tmall.png" style="height: 16px;width: 16px;float: right;padding-top: 8px;margin-right: 8px;margin-bottom: 8px;">
                    </c:if>
                </li>
            </c:forEach>
        </ul>

        <c:forEach var="type" items="${goodsTypes}">
            <ul class="ui-row">
                <li class="ui-col ui-col-100">
                    <h4 class="h4-title">${type.name}</h4>
                </li>
                <c:forEach var="goods" items="${type.goodsList}" varStatus="status">
                    <li class="ui-col ui-col-50 click" style="text-align: left;height: 310px;background-color: rgb(255,255,255);" id-data="${goods.id}" url-data="${goods.urlLinkCoupon != null && goods.urlLinkCoupon.length() > 0 ? goods.urlLinkCoupon : goods.urlLink}">
                        <img class="product-img" style="width: 100%; height: auto;" src="./upload/${goods.fileId}.jpg">
                        <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                        <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                        <span class="ui-nowrap-multi ui-whitespace" style="font-size: 14px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.name}</span>
                        <c:if test="${goods.info != null && goods.info.length() > 0}">
                            <span class="ui-nowrap ui-whitespace" style="color: #888;font-size: 12px;float: left;padding-top: 5px;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.info}</span>
                        </c:if>
                        <c:if test="${goods.isTmall == true}">
                            <img src="img/tmall.png" style="height: 16px;width: 16px;float: right;padding-top: 8px;margin-right: 8px;margin-bottom: 8px;">
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </c:forEach>
    </section>


</section><!-- /.ui-container-->
</body>
</html>

<script type="text/javascript">
    $(function(){
        // 点击事件
        if(!${isSpider}){
            $('.click').on('click', function(){
                window.open($(this).attr('url-data'), '_bank');
                // 保存pv
                $.post('./goods/pv_uv', {goodsId: $(this).attr('id-data'), referer: document.referrer.toLowerCase(), titileName: "商品", flag: "mobile"});
            });
        }

        // 保存pv
        $.post('./goods/pv_uv', {goodsId: 0, referer: document.referrer.toLowerCase(), titileName: "首页", flag: "mobile"});
    });
</script>
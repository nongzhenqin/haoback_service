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
    <section id="layout">
        <ul class="ui-row">
            <li class="ui-col ui-col-100">
                <h4 class="h4-title">热销推荐</h4>
            </li>
            <c:forEach var="goods" items="${goodsTypesHot}" varStatus="status">
                <li class="ui-col ui-col-50" style="text-align: left;">
                    <img class="product-img" style="width: 100%; height: inherit;" src="./upload/${goods.fileId}.jpg">
                    <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                    <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                    <span style="font-size: 12px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.name}</span>
                    <c:if test="${goods.info != null && goods.info.length() > 0}">
                        <span style="color: #888;font-size: 10px;">${goods.info}</span>
                    </c:if>
                    <c:if test="${goods.isTmall == true}">
                        <img src="img/tmall.png" style="height: 16px;width: 16px;float: right;margin-right: 8px;margin-bottom: 8px;">
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
                    <li class="ui-col ui-col-50" style="text-align: left;">
                        <img class="product-img" style="width: 100%; height: inherit;" src="./upload/${goods.fileId}.jpg">
                        <strong style="padding-left: 8px;float: left;font-size: 20px;font-family: arial; color: #F40;">￥${goods.price}</strong>
                        <span style="padding-right: 8px;padding-top:6px;float: right;color: #888;font-size: 10px;">销量&nbsp;${goods.salesNum}</span>
                        <span style="font-size: 12px;color: rgb(61,61,61);float: left;padding-left: 8px;padding-right: 8px;font-family: arial,'Hiragino Sans GB', 宋体,sans-serif;">${goods.name}</span>
                        <c:if test="${goods.info != null && goods.info.length() > 0}">
                            <span style="color: #888;font-size: 10px;">${goods.info}</span>
                        </c:if>
                        <c:if test="${goods.isTmall == true}">
                            <img src="img/tmall.png" style="height: 16px;width: 16px;float: right;margin-right: 8px;margin-bottom: 8px;">
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </c:forEach>
    </section>


</section><!-- /.ui-container-->
</body>
</html>

<style>
    body{
        -webkit-user-select:initial;
        background-color: #fff;
    }
    body>a{display: none;}
    .demo-desc{
        padding: 10px;
        font-size: 16px;
        color: #7CAE23;
    }
    .demo-block{
        position: relative;
    }
    .demo-block .ui-header,
    .demo-block .ui-footer{
        position: absolute;
    }
    .ui-tab-nav{
        top: 45px;
    }
    .ui-tab-content{
        margin-top: 45px;
    }
    .demo-block > .ui-list,.demo-block >.ui-form,.demo-block >.ui-tooltips{margin-bottom: 20px;}
    .content h3{
        padding: 0 15px;
        line-height: 44px;
        font-size: 15px;
    }
    .border-list{
        background-color: #fff;
    }
    .border-list li{
        width: 100px;
        margin: 10px auto;
        -webkit-box-pack: center;
        text-align: center;
    }
    .ui-btn-group-bottom{bottom: 56px;}
    .ui-table{
        line-height: 40px;
        text-align: center;
        background-color: #fff;
    }
    .ui-scroller {width:auto;height:300px;margin:20px;padding:10px;overflow:hidden;}
    .ui-scroller li {margin-bottom:10px;}
    .ui-notice{
        top: 0;
        z-index: -1;
        height: 400px;
    }
    .icon-lists li{
        float:left;
        height: 100px;
        width: 103px;
        text-align: center;

    }
    .icon-lists li i{
        font-size: 32px;
        line-height: 44px;
    }
    .icon-lists .ui-icon,.icon-lists [class^="ui-icon-"]{
        margin: 10px 0;
        color: #000;
    }
    .code,.fontclass{
        font-size: 12px;
    }
    .ui-col{
        padding: 5px;
        /*background: #777;*/
        text-align: center;
        box-sizing: border-box;
        border: 0px solid #ddd;
    }
    .ui-flex{
        border: 1px solid #ddd;
        min-height: 80px;
        margin-bottom: 10px;
    }
    .ui-flex-ver{
        border: 1px solid #ddd;
        min-height: 80px;
        margin-bottom: 10px;
    }
    .ui-row-flex-ver{
        border: 1px solid #ddd;
        min-height: 100px;
    }
    .test-img {
        width: 50px;
        height: 50px;
        background: #777;
    }

</style>

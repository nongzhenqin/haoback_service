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
    <title>优选</title>
    <link href="img/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <link href="img/favicon.ico" rel="icon" type="image/x-icon">

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <%--<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">--%>
    <link rel="stylesheet" href="static/bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/index.css?t=<%=UUID.randomUUID().toString()%>">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <%--<script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>--%>
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
            <div class="col-md-4 col-style" onclick="window.open('${goods.urlLink}', '_bank')">
                <img class="product-img" src="./goods/image/${goods.fileId}">
                <h6 style="padding-left: 5px;padding-right: 5px;">${goods.name}</h6>
                <c:if test="goods.info != null && goods.info.length > 0">
                    <h6>${goods.info}</h6>
                </c:if>
                <span style="color: red;width: 100%;height: 100%;font-size: 20px;font-family: arial; color: #F40; font-weight: 700;">￥${goods.price}</span>
                &nbsp;&nbsp;&nbsp;&nbsp;月销&nbsp;${goods.salesNum}
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
                <div class="col-md-4 col-style" onclick="window.open('${goods.urlLink}', '_bank')">
                    <img class="product-img" src="./goods/image/${goods.fileId}">
                    <h6 style="padding-left: 5px;padding-right: 5px;">${goods.name}</h6>
                    <c:if test="goods.info != null && goods.info.length > 0">
                        <h6>${goods.info}</h6>
                    </c:if>
                    <span style="color: red;width: 100%;height: 100%;font-size: 20px;font-family: arial; color: #F40; font-weight: 700;">￥${goods.price}</span>
                    &nbsp;&nbsp;&nbsp;&nbsp;月销&nbsp;${goods.salesNum}
                </div>
            </c:forEach>
            <div class="col-md-2"></div>
        </div>
    </c:forEach>

    <br>

    <div class="row">
        <div class="col-md-24" style="height: 60px;padding-top:20px;text-align:center;background-color:white;">
            <%--优选--%>
        </div>
    </div>
</div>
</body>
</html>

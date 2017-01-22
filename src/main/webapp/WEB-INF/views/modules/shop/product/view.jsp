<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="pageId" value="product-view-page" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <meta name="decorator" content="shop_default"/>
    <meta name="description" content="月光茶人"/>
    <meta name="keywords" content="月光茶人"/>
</head>
<body>

<div class="breadcrumb">
    <strong><a href="<c:url value='/'/>">首页</a></strong>
    <span>
        &nbsp;&gt;&nbsp;<a href="">目录</a>
        &nbsp;&gt;&nbsp;<a href="<c:url value='/product/${product.id}.html'/>">${product.name}</a>
    </span>
</div>

<!-- product-intro -->
<div id="product-intro" class="clearfix">
    <div id="product-intro-left">
        <div class="jqzoom">
            <img height="400" width="400" alt="${product.name}" src="<c:url value='${product.image}'/>">
        </div>
    </div>
    <div id="product-intro-right">
        <div class="name">
            <h1>${product.name}</h1>
            <strong>【月光茶人专营 支持货到付款】</strong>
        </div>
        <div class="price-box">
            <c:choose>
                <c:when test="${not empty product.featuredPrice}">
                    <span class="price">￥${product.featuredPrice}</span>
                    <span class="original-word">原价&nbsp;</span><span class="original-price">￥${product.price}</span>
                </c:when>
                <c:otherwise>
                    <span class="price">￥${product.price}</span>
                </c:otherwise>
            </c:choose>
        </div>
        <%--
        <ul class="summary">
            <li class="summary-li stock">
                <div class="dt">配&nbsp;送&nbsp;至：</div>
                <div class="dd">
                    <jsp:include page="../common/area-selector.jsp"/>
                    <div id="store-prompt">下单后立即配送</div>
                </div>
                <span class="clr"></span>
            </li>
        </ul>
        --%>
        <ul class="choose clearfix">
            <%--
			<c:forEach items="${product.attributes}" var="attribute">
			<li class="choose-color clearfix" id="choose-color">
				<div class="dt">${attribute.item.name}：</div>
				<div class="dd">
					<c:forEach items="${attribute.item.values}" var="value">
					<div class="item selected">
						<b></b><a href="#none"><i>${value.name}</i></a>
					</div>
					</c:forEach>
				</div>
			</li>
			</c:forEach>
			 --%>
            <li class="choose-amount clearfix">
                <div class="dt">购买数量：</div>
                <div class="dd">
                    <a class="btn-operate" href="javascript:;">-</a>
                    <input class="amount" value="1">
                    <a class="btn-operate" href="javascript:;">+</a>
                </div>
            </li>
            <li class="choose-cart clearfix">
                <div class="dt">&nbsp;</div>
                <div class="dd">
                    <%--<a class="add-to-cart" rel="nofollow" href="<c:url value='/cart/add/${product.id}/1.html'/>"--%>
                    <a class="add-to-cart" rel="nofollow" href="<c:url value='/cart/success/${product.id}'/>"
                       title="">加入购物车<b></b></a>
                </div>
            </li>
        </ul>
    </div>
</div>
<!-- /product-intro -->


<!-- left -->
<div class="main-left">
    <div>${product.description}</div>
</div>

<!-- content -->
<div id="" class="main-content clearfix">

</div>

</body>
</html>
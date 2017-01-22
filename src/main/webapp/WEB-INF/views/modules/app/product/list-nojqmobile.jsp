<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="app-product-list" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <meta name="decorator" content="app_default"/>
    <meta name="description" content="月光茶人" />
    <meta name="keywords" content="月光茶人" />
</head>
<body>

<div class="floor activity-floor">
    <h2 class="activity-floor-title">分类: ${category.name}</h2>
    <ul class="activity-list product-list clearfix">
        <c:forEach items="${productList}" var="product">
            <li class="activity-item">
                <a class="activity-link" href="<c:url value='/app/product/${product.id}.html'/>">
                    <div class="left-b"><img src="<c:url value="${product.imageSmall}"/>"></div>
                    <div class="right-b">
                        <div class="name">${product.name}</div>
                        <div class="desc">${product.shortDescription}</div>
                    </div>
                </a>
            </li>
        </c:forEach>
    </ul>
    <div class="activity-clear"></div>
</div>

</body>
</html>
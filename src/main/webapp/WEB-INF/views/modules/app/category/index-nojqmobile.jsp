<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="app-category-index" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>分类列表</title>
    <meta name="decorator" content="app_default"/>
    <meta name="description" content="月光茶人" />
    <meta name="keywords" content="月光茶人" />
</head>
<body>

<div class="floor activity-floor" id="theme9">
    <h2 class="activity-floor-title">所有分类</h2>
    <ul class="activity-list">
        <c:forEach items="${categoryList}" var="category">
            <li class="activity-item">
                <a class="activity-link" href="<c:url value='/app/product/list/${category.id}.html'/>">
                    <div class="left-b"><img src="<c:url value="${category.imageSmall}"/>"></div>
                    <div class="right-b">
                        <div class="name">${category.name}</div>
                        <div class="desc">${category.shortDescription}</div>
                    </div>
                </a>
            </li>
        </c:forEach>
    </ul>
    <div class="activity-clear"></div>
</div>

</body>
</html>
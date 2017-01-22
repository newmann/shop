<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<ul data-role="listview" data-split-icon="gear" data-inset="true">
    <c:forEach items="${categoryList}" var="category">
        <li>
            <a tapmode="active" onclick="openProductList('${category.id}', '${category.name} 下的产品')">
                <img src="${url}${category.imageSmall}">
                <h2>${category.name}</h2>
                <p>${category.shortDescription}</p>
            </a>
        </li>
    </c:forEach>
</ul>
<script type="text/javascript">
</script>
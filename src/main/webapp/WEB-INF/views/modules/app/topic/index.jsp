<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<ul data-role="listview" data-split-icon="gear" data-inset="true" class="product-list">
    <c:forEach items="${productList}" var="product">
        <li>
            <a tapmode="active" onclick="openWin('product_view', '${url}/app/product/${product.id}.html')">
                <img src="<c:url value="${product.imageSmall}"/>">
                <div class="name">${product.name}</div>
                <div class="price-box">
                    <c:choose>
                        <c:when test="${not empty product.featuredPrice}">
                            <span class="price">￥${product.featuredPrice}</span>&nbsp;
                            <span class="original-price">原价￥${product.price}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="price">￥${product.price}</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <p>${product.shortDescription}</p>
            </a>
        </li>
    </c:forEach>
</ul>
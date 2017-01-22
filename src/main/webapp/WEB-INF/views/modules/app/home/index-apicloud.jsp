<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
    h2{color:red;}
</style>
<h2>热卖品类</h2>
<ul data-role="listview" data-split-icon="gear" data-inset="true">
    <c:forEach items="${featuredCategoryList}" var="category">
        <li>
            <a tapmode="active" onclick="openProductList('${category.id}', '${category.name} 下的产品')">
                <img src="${url}${category.imageSmall}">
                <h2>${category.name}</h2>
            </a>
        </li>
    </c:forEach>
</ul>
<h2>热卖单品</h2>
<ul data-role="listview" data-split-icon="gear" data-inset="true" class="product-list">
    <c:forEach items="${featuredProductList}" var="product">
        <li>
            <a tapmode="active" onclick="openProduct('${product.id}')">
                <img src="${url}${product.imageSmall}">
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
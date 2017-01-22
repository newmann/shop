<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
    #main {padding: 10px 8px 80px;}
    #nav .collect{
        background: url(${url}/static/app/images/collect.png) no-repeat center 4px;
    }
    #nav .collect.active, #nav li.active .collect{
        background: url(${url}/static/app/images/collect-l.png) no-repeat center 4px;
    }
    #nav .w-cart i {
         background-color: #f24b48;
         border-radius: 100%;
         bottom: 34px;
         color: #fff;
         font-size: 10px;
         font-style: normal;
         height: 15px;
         line-height: 15px;
         position: absolute;
         width: 15px;
         margin-left: 18px;
     }
    #nav {
        border: none;
    }
    #nav .w-add-cart {
        background-color: #fd6161;
    }
    #nav .w-add-cart a {
        font-size: 20px;
        padding-top: 12px;
        color: #fff;
    }
    #nav .w-cart{
        width: 75px;
    }
    #nav .w-cart{
        background: url(${url}/static/app/images/cart.png) no-repeat center 4px;
    }
    #nav .w-cart.active, #nav li.active .w-cart{
        background: url(${url}/static/app/images/cart-l.png) no-repeat center 4px;
    }
    .detail-img{text-align: center;}
    .detail-img img {margin:54px 0 8px;}
</style>

<form id="productForm" method="post" action="${url}/app/cart/add.html">
    <input type="hidden" name="productId" value="${product.id}">
    <input type="hidden" name="count" value="1">
    <div id="main">
        <div class="detail-img">
            <img height="200" src="${url}${product.imageMedium}"/>
        </div>
        <h2 class="detail-title">
            ${product.name}
        </h2>
        <h3>${product.shortDescription}</h3>
        <div class="price-box">
            <c:choose>
                <c:when test="${not empty product.featuredPrice}">
                    <span class="price">￥${product.featuredPrice}</span>&nbsp;&nbsp;
                    <span class="original-price">原价￥${product.price}</span>
                </c:when>
                <c:otherwise>
                    <span class="price">￥${product.price}</span>
                </c:otherwise>
            </c:choose>
        </div>
        <h3>选择我的:</h3>
        <ul data-role="listview" data-inset="true">
            <c:forEach items="${product.attributeList}" var="attribute">
                <li class="ui-field-contain">
                    <label>${attribute.item.name}:</label>
                    <select name="attributes">
                        <c:forEach items="${attribute.item.valueList}" var="value">
                            <option value="${attribute.item.id}_${value.id}">
                                    ${value.name}<c:if test="${!empty value.price && value.price > 0}">&nbsp;&nbsp;+￥${value.price}</c:if>
                            </option>
                        </c:forEach>
                    </select>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div id="nav" class="w-add-cart-nav">
        <ul>
            <li class="active"><a class="collect" tapmode="active" onclick="">收藏</a></li>
            <li><a class="w-cart" data-url="${url}/app/cart.html" data-title="购物车" tapmode="active" onclick="openCartView(this)"><i id="cart-num">${cartNum}</i>购物车</a></li>
            <li class="w-add-cart"><a tapmode="active" onclick="submitProductForm()">加入购物车</a></li>
        </ul>
    </div>
</form>
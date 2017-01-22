<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
    .clearfix:after{content: ''; display: block; clear: both;}

    #nav {border: none;}
    #nav a {
        padding: 12px 0;
        color: #fff;
    }
    #nav a span {
        color: #fff;
    }
    .nav-left {
        font-size: 16px;
    }
    .nav-right {
        background-color: #fd6161;
    }
    .nav-right a {
        font-size: 20px;
    }

    .check-wrapper {
        height: 20px;
        width: 20px;
        margin-left: -36px;
        margin-top: 19px;
        display: block;
        position: absolute;
    }
    .check-wrapper .w-cart-checkbox {
        width: 20px;
        height: 20px;
    }
    .w-cart-checkbox {
        display: block;
        width: 20px;
        height: 20px;
        margin: 0 auto;
        background: url(${url}/static/app/images/shp-cart-icon-sprites.png?v=1) no-repeat 0 0;
        background-size: 50px 100px;
    }
    .w-cart-checkbox.w-checked {
        background-position: -25px 0;
    }

    <%-- 加减工具条 --%>
    .quantity-wrapper {
        display: inline-block;
    }
    .quantity-decrease, .quantity, .quantity-increase {
        float: left;
        font-size: 14px;
        text-align: center;
    }
    .quantity-decrease, .quantity-increase {
        background: #fff;
        border: 1px solid #ccc;
        color: #666;
        display: block;
        width:30px;
        overflow: hidden;
        text-indent: -200px;
        background: url(${url}/static/app/images/shp-cart-icon-sprites.png?v=1) no-repeat -15px -18px;
        background-size: 50px 100px;
        height: 28px;
    }
    .quantity-decrease {
        -webkit-border-radius: 2px 0 0 2px;
        background-position: 10px -18px;
    }
    .quantity {
        color: #333;
        border: solid #ccc;
        border-width: 1px 0 1px 0;
        height: 28px;
        width: 32px;
        border-radius: 0;
    }
    .quantity-increase {
        -webkit-border-radius: 0 2px 2px 0;
    }
    .quantity-decrease.disabled, .quantity-increase.disabled {
        background-color: #e8e8e8;
        color: #999;
        background-position: 10px -45px;
    }
    .quantity-increase.disabled {
        background-position: -15px -45px;
    }

    .top-content {
        background-color: #ececec;
        position: relative;
    }
    .top-content .item-list-wrap.current {
        display: block;
    }
    .top-content .item-list-wrap {
        display: none;
        min-height: 300px;
        position: relative;
    }
    .top-content .item-list {
        margin-top: -4px;
        width: 100% !important;
        z-index: 2;
        min-height: 82px;
    }
    .top-content ul {
        list-style: outside none none;
        margin: 0;
        padding: 0;
    }
    .top-content .item-list li {
        background-color: #fff;
        margin-top: 4px;
        padding-top: 1px;
        position: relative;
        padding-left: 40px;
    }
    .top-content img.item-pic {
        display: block;
        float: left;
        height: 80px;
        margin: 7px 20px 0 0;
        height: 70px;
        width: 70px;
    }
    .top-content .item-name {
        font-size: 16px;
        line-height: 30px;
        margin: 0 5px 0 0;
        overflow: hidden;
        color: #252525;
    }
    .top-content .item-attr{
        padding: 5px 0 10px;
        color: #bdbdbd;
        font-size: 14px;
    }
    .top-content .item-remove {
        display: inline-block;
        background: url('${url}/static/app/images/icon-shp-cart-remove.png') no-repeat 0 0;
        background-size: 50px 50px;
        width: 23px;
        height: 23px;
        position: absolute;
        right: 0;
        margin-right: 15px;
        text-indent: -1000px;
        overflow: hidden;
        cursor: pointer;
    }

    <%-- price-box --%>
    .price-box { margin-bottom:2px; }
    .price-box .price {font-size:18px; }
    .price-box .original-price {color:#bdbdbd; font-size:16px; text-decoration:line-through;}
</style>

<%@ include file="../common/nologin-box.jsp"%>

<c:if test="${not empty cartItemList}">
<form id="cart-form" method="post" action="${url}/app/preorder/add.html">
    <div id="main">
        <div class="top-wrap top-wrap-module">
            <div class="top-content">
                <div class="item-list-wrap j-item-list-wrap current">
                    <ul class="item-list j-item-list">
                        <c:forEach items="${cartItemList}" var="item" varStatus="status">
                            <li id="cart-item-${status.count}">
                                <div class="check-wrapper">
                                    <span class="<c:if test='${item.isSelected eq 1}'>w-checked</c:if> w-cart-checkbox" id="checkIcon_${status.count}"
                                          data-num="${status.count}" data-itemId="${item.id}" data-url="${url}/app/cart/setIsSelected/${item.id}.html"
                                          tapmode="" onclick="setIsSelected(this)"></span>
                                </div>
                                <img tapmode="active" onclick="openProduct('${item.product.id}')" class="item-pic" src="${url}${item.product.imageSmall}">
                                <div class="item-name">${item.product.name}</div>

                                <%-- price加上attributes的价格 --%>
                                <c:set var="_featuredPrice" value="${item.product.featuredPrice}" />
                                <c:set var="_price" value="${item.product.price}" />
                                <c:if test="${not empty item.attributeList}">
                                    </c:if>
                                    <c:forEach items="${item.attributeList}" var="attribute" varStatus="status2">
                                        <c:set var="_featuredPrice" value="${_featuredPrice + attribute.attributeItemValue.price}" />
                                        <c:set var="_price" value="${_price + attribute.attributeItemValue.price}" />
                                    </c:forEach>
                                    <c:if test="${not empty item.attributeList}">
                                </c:if>

                                <div class="price-box">
                                    <c:choose>
                                        <c:when test="${not empty item.product.featuredPrice}">
                                            <span class="price">￥${_featuredPrice}</span>&nbsp;
                                            <span class="original-price">原价￥${_price}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="price">￥${_price}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                    <%-- 加减工具条 --%>
                                <div class="quantity-wrapper">
                                    <a class="quantity-decrease" tapmode="" onclick="setCartItemNum(this)" data-cart-item-num-id="cart-item-num-${status.count}"
                                       data-action="decrease" data-itemId="${item.id}" data-url="${url}/app/cart/setCartItemNum/${item.id}.html">-</a>
                                    <input id="cart-item-num-${status.count}" type="text" class="quantity" name="count" value="${item.count}" maxlength="3" readonly="readonly">
                                    <a class="quantity-increase" tapmode="" onclick="setCartItemNum(this)" data-cart-item-num-id="cart-item-num-${status.count}"
                                       data-action="increase" data-itemId="${item.id}" data-url="${url}/app/cart/setCartItemNum/${item.id}.html">+</a>
                                    <a tapmode="" onclick="removeCartItem(this)" data-url="${url}/app/cart/delete-item/${item.id}.html" class="item-remove" data-cart-item-id="cart-item-${status.count}"></a>
                                </div>

                                    <%-- product attributes and price --%>
                                <c:if test="${not empty item.attributeList}">
                                    <div class="item-attr">
                                        <c:forEach items="${item.attributeList}" var="attribute" varStatus="status3">
                                            ${attribute.attributeItem.name}:${attribute.attributeItemValue.name}<c:if test="${not status3.last}">, </c:if>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div id="nav" class="add-preorder-nav">
        <ul>
            <li class="nav-left"><a onclick="void(0)">总计: ￥<span id="w-total-price" class="w-total-price">${totalPrice}</span></a></li>
            <li class="nav-right"><a tapmode="active" onclick="submitCartForm()">去结算(<span id="w-cart-num">${cartNum}</span>)</a></li>
        </ul>
    </div>
</form>
</c:if>
<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="app-product-view" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>商品详情</title>
    <meta name="decorator" content="app_goods"/>
    <meta name="description" content="月光茶人" />
    <meta name="keywords" content="月光茶人" />
</head>
<body>

<div id="mainLayout">
    <div id="mainStay" class="new-wt">
    <form id="productForm" method="post" action="/cart/add">
        <input type="hidden" name="productId" value="${product.id}">
        <div id="goods-img-box" class="miblebox">
            <div class="new-p-re">
                <div class="detail-img">
                    <img height="200" src="<c:url value='${product.image}'/>"/>
                </div>
            </div>
            <div class="goodsinfo">
                <h1 class="detail-title" id="title">
                    ${product.name}
                </h1>
                <h3>${product.shortDescription}</h3>
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
            </div>
        </div>

        <div class="saleinfo miblebox">
            <c:forEach items="${product.attributeList}" var="attribute">
                <dl class="list-entry">
                    <dt class="row01">
                        <span class="col01">${attribute.item.name}:</span>
                        <span class="col02">
                            <select name="attribute" onChange="window.androidJs.onChange()">
                                <c:forEach items="${attribute.item.valueList}" var="value">
                                    <option value="${value.id}">
                                        ${value.name}<c:if test="${!empty value.price && value.price > 0}">&nbsp;&nbsp;+￥${value.price}</c:if>
                                    </option>
                                </c:forEach>
                            </select>
                        </span>
                    </dt>
                </dl>
            </c:forEach>
            <dl class="list-entry">
                <dt class="row01">
                    <span class="col01">数量:</span>
                    <span class="col02 choose-amount">
                        <a href="javascript:;" class="btn-operate" id="btn-minus" onClick="window.androidJs.minusCount()">-</a>
                        <input value="1" class="amount" id="count" name="count">
                        <a href="javascript:;" class="btn-operate" id="btn-plus" onClick="window.androidJs.plusCount()">+</a>
                    </span>
                </dt>
            </dl>
            <dl class="list-entry">
                <dt class="row01">
                    <span class="col01">
                        商品详情:
                        <p>${product.description}</p>
                    </span>
                </dt>
            </dl>
        </div>
        <br />

        <div class="cart-btns-fixed" id="cart1" style="display: table;">
            <div class="cart-btns-fixed-box">
                <a class="btn cart-num" id="toCart" href="javascript:void(0)"></a>
                <a id="add_cart" class="btn btn-cart" onClick="window.androidJs.submitForm()">加入购物车</a>
            </div>
        </div>
    </form>
    </div>
</div>

<script language="javascript">
    /* This function is invoked by the activity */
    function plusCount() {
        var count = parseInt(document.getElementById("count").value);
        document.getElementById("count").value = count + 1;
    }

    function minusCount() {
        var count = parseInt(document.getElementById("count").value);
        if (count > 1)
            document.getElementById("count").value = count - 1;
    }

    function onChange() {
        alert(this);
        alert(this.value);
        alert(this.text);
    }

    function submitForm() {
        document.getElementById("productForm").submit();
    }
</script>

</body>
</html>
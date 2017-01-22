<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
    .clearfix:after{content: ''; display: block; clear: both;}

    .list-w{width: 100%;}
    .list{
        background-color: #FFF;
        margin-top: 10px;
        float: left;
        width: 47%;
        overflow: hidden;
        margin-left:2%;
    }
    .list-mr {margin-right: 2%;}
    .list-left{
        float: left;
        width: 60%;
    }
    .list-right{
        float: left;
        width: 40%;
    }
    .list-right img{
        width: 100%;
        display: block;
    }
    .list-title {
        padding: 4px;
        font-size: 16px;
        color: #58616d;
        display: block;
    }
    .list-brief {
        padding: 0 4px 0;
        font-size: 12px;
        color: #bdbdbd;
        display: block;
    }

    <%--热卖单品--%>
    .top-title {
        border-bottom: 1px solid #d1cdce;
        height: 50px;
        position: relative;
    }
    .top-title h2 {
        color: #262626;
        font-size: 22px;
        line-height: 50px;
        margin: 10px 0 0 0;
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
    }
    .top-content ul {
        list-style: outside none none;
        margin: 0;
        padding: 0;
    }
    .top-content .item-list li {
        background-color: #fff;
        height: 95px;
        margin-top: 4px;
        padding-top: 1px;
        position: relative;
    }
    .top-content .item-list img.item-pic {
        display: block;
        float: left;
        height: 80px;
        margin: 7px 20px 0 12px;
        height: 70px;
        width: 70px;
    }
    .top-content .item-list h2 {
        font-size: 16px;
        line-height: 30px;
        margin: 0 5px 0 0;
        overflow: hidden;
        color: #252525;
    }
    .top-content .item-desc{
        height: 32px;
        overflow: hidden;
    }

    <%-- price-box --%>
    .price-box {
        margin-bottom: 11px;
    }
    .price-box .price {font-size:20px; }
    .price-box .original-price {color:#bdbdbd; font-size:16px; text-decoration:line-through;}
</style>

<div class="list-w clearfix">
    <c:forEach items="${featuredCategoryList}" var="category" varStatus="status">
        <div class="list clearfix <c:if test="${status.count%2 eq 0}">list-mr</c:if>" tapmode="active" onclick="openProductList('${category.id}', '${category.name} 下的产品')">
            <div class="list-left">
                <span class="list-title">${category.name}</span>
                <span class="list-brief">好茶事业圆</span>
            </div>
            <div class="list-right">
                <img src="${url}${category.imageSmall}">
            </div>
        </div>
    </c:forEach>
</div>

<%--热卖单品--%>
<div class="top-wrap top-wrap-module">
    <div class="top-title">
        <h2>大家都在喝</h2>
    </div>
    <div class="top-content">
        <div class="item-list-wrap j-item-list-wrap current">
            <ul class="item-list j-item-list">
                <c:forEach items="${featuredProductList}" var="product">
                    <li>
                        <a tapmode="active" onclick="openProduct('${product.id}')">
                            <img class="item-pic" src="${url}${product.imageSmall}">
                            <h2>${product.name}</h2>
                            <div class="item-desc">${product.shortDescription}</div>
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
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>


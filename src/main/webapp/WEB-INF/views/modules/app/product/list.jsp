<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
    #main {padding-top:80px;}

    .top-title {
        border-bottom: 1px solid #d1cdce;
        position: relative;
        color: #262626;
        font-size: 20px;
        padding: 10px;
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
        margin: 7px 20px 0 12px;
        height: 70px;
        width: 70px;
    }
    .top-content .item-list .item-name {
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
</style>

<div id="main">
    <div class="top-wrap top-wrap-module">
        <div class="top-content">
            <div class="item-list-wrap j-item-list-wrap current">
                <ul class="item-list j-item-list">
                    <c:forEach items="${productList}" var="product">
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
</div>
<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
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
        <div class="top-title">亲, 这是所有饮品和小吃哦！</div>
        <div class="top-content">
            <div class="item-list-wrap current">
                <ul class="item-list">
                    <c:forEach items="${categoryList}" var="category">
                        <li>
                            <a tapmode="active" onclick="openProductList('${category.id}', '${category.name} 下的产品')">
                                <img class="item-pic" src="${url}${category.imageSmall}">
                                <div class="item-name">${category.name}</div>
                                <div class="item-desc">${category.shortDescription}</div>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
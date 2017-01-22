<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
    ul, li {
        list-style-type: none;
        margin: 0;
        padding: 0;
    }

    .head-img {text-align: center;}
    .head-img .my-name {margin-top:0;}
    .head-img .my-img {
        background-image: url("${url}/app/images/my-head-default.png");
        background-size: cover;
        border: 0.125em solid #fff;
        border-radius: 4em;
        box-shadow: 0 1px 8px rgba(0, 0, 0, 0.2) inset;
        display: inline-block;
        height: 3.8125em;
        overflow: hidden;
        width: 3.8125em;
    }

    .menu-list {
        background-color: #fff;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        margin-top: 10px;
        overflow: hidden;
        padding: 0.46875em;
        width: 100%;
    }
    .menu-list li {
        float: left;
        padding: 0.46875em;
        text-align: center;
        width: 25%;
    }
    .menu-list li a {
        display: block;
        text-decoration: none;
        width: 100%;
    }
    .menu-list li a img {
        display: block;
        height: 2.5em;
        margin: 0 auto 0.75em;
        width: 2.5em;
    }
    .menu-list li p {
        color: #6a6a6a;
        font-size: 0.75em;
        line-height: 1.35em;
        text-align: center;
    }
</style>

<div id="main">
	<c:set var="isUserIndexPage" value="true" scope="request"/>
	<%@ include file="../common/nologin-box.jsp"%>

    <div class="head-img">
        <a onclick="openUserInfo(this)" data-url="${url}/app/user/info.html" tapmode=""><span class="my-img"></span></a>
        <p class="my-name">${fns:getUser().loginName}</p>
    </div>

    <ul class="menu-list">
        <li>
            <a tapmode="active" onclick="openOrderList(this)" data-url="${url}/app/order/list.html">
                <img alt="" src="${url}/static/app/images/icon-order.png">
                <p>我的订单</p>
            </a>
        </li>
        <li>
            <a tapmode="active" onclick="openCollectList(this)" data-url="${url}/app/collect/list.html">
                <img alt="" src="${url}/static/app/images/icon-collect.png">
                <p>我的收藏</p>
            </a>
        </li>
        <li>
            <a tapmode="active" onclick="openHistoryList(this)" data-url="${url}/app/history/list.html">
                <img alt="" src="${url}/static/app/images/icon-history.png">
                <p>浏览记录</p>
            </a>
        </li>
        <li>
            <a tapmode="active" onclick="openInfoSupport(this)" data-url="${url}/app/info/support.html">
                <img alt="" src="${url}/static/app/images/icon-app.png">
                <p>技术支持</p>
            </a>
        </li>
    </ul>
</div>
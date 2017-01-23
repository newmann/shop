<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="user-index" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <meta name="decorator" content="shop_default"/>
    <meta name="description" content="月光茶人" />
    <meta name="keywords" content="月光茶人" />
</head>
<body>




<!-- content -->
<div id="" class="main-content clearfix">
    <div class="head-img">
        <a onclick="openUserInfo(this)" data-url="${url}/shop/user/info.html" tapmode=""><span class="my-img"></span></a>
        <p class="my-name">${fns:getUser().loginName}</p>
    </div>
</div>
<!-- /content -->

<!-- right -->
<div class="main-right clearfix">
    <div class="side-block">
        <div class="title">
            <h2><i></i>个人中心</h2>
        </div>

        <ul class="menu-list">
            <li>
                <a tapmode="active" onclick="openOrderList(this)" data-url="${url}/shop/order/list.html">
                    <img alt="" src="${url}/static/app/images/icon-order.png">
                    <p>我的订单</p>
                </a>
            </li>
            <li>
                <a tapmode="active" onclick="openCollectList(this)" data-url="${url}/shop/collect/list.html">
                    <img alt="" src="${url}/static/app/images/icon-collect.png">
                    <p>我的收藏</p>
                </a>
            </li>
            <li>
                <a tapmode="active" onclick="openHistoryList(this)" data-url="${url}/shop/history/list.html">
                    <img alt="" src="${url}/static/app/images/icon-history.png">
                    <p>浏览记录</p>
                </a>
            </li>
            <li>
                <a tapmode="active" onclick="openInfoSupport(this)" data-url="${url}/shop/info/support.html">
                    <img alt="" src="${url}/static/app/images/icon-app.png">
                    <p>技术支持</p>
                </a>
            </li>
        </ul>
    </div>
    <br />
    <div class="side-block">
        <div class="title">
            <h2><i></i>每日特卖</h2>
        </div>
    </div>
</div>
<!-- /right -->


</body>
</html>


<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<!--[if lt IE 7]><html class="lt-ie10 lt-ie9 lt-ie8 lt-ie7" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if IE 7]><html class="lt-ie10 lt-ie9 lt-ie8" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if IE 8]><html class="lt-ie10 lt-ie9" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if IE 9]><html class="lt-ie10" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if gt IE 9]><!--><html xmlns="http://www.w3.org/1999/xhtml"> <!--<![endif]-->
<head>
	<title><sitemesh:title default="欢迎您"/> - 月光茶人</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta name="author" content="http://www.ygcr8.com"/>
	<meta http-equiv="X-UA-Compatible" content="IE=6,IE=7,IE=9,IE=10" />
	<link href="<c:url value='/static/css/shop.css'/>" type="text/css" rel="stylesheet" />
	<script src="<c:url value='/static/jquery/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/static/jquery.icommon/jquery.icommon.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/static/jquery.cookie/jquery.cookie.js'/>" type="text/javascript"></script>
	<sitemesh:head/>
</head>
<body class="page-body" id="${pageId}">
<!-- header -->
<div class="header">
	<div class="top-nav-w"><div class="top-nav">
		<ul class="nav-left">
			<li class="favor">
				<a href="javascript:void(0);" rel="nofollow">
					<i class="F-glob-star-border"></i>
					收藏月光茶人
				</a>
			</li>
			<li class="user-info">
				<a href="javascript:void(0);" class="login">登录</a>
				<a href="javascript:void(0);" class="signup">注册</a>
			</li>
		</ul>
		<ul class="nav-right">
			<li><a target="_blank" href="/fapp.html">手机客户端</a></li>
			<%-- <li class="user-order"><a target="" href="/orders/">我的订单</a></li> --%>
			<li class="mini-cart">
				<a href="<c:url value='/cart.html'/>" class="dropdown-toggle" rel="nofollow">
					<i class="icon icon-cart"></i>
					<span>我的购物车<em data-newindex="true" class="badge"><strong class="cart-count">${cartItemsCount}</strong>件</em></span>
					<i class="tri tri--dropdown"></i>
					<i class="vertical-bar"></i>
				</a>
			</li>
		</ul>
	</div></div>

	<%--
	<div class="top-slider">
		<div class="banner"><img width="1200" height="58" alt="" src="<c:url value='/image/banner1.jpg'/>"></div>
	</div>
	 --%>

	<div class="brand-bar-w"><div class="brand-bar">
		<a class="logo" href="<c:url value='/'/>"><img height="90" alt="月光茶人" src="<c:url value='/static/images/logo.jpg'/>"></a>
		<%-- <a class="logo-gift"><img src="<c:url value='/static/images/logo-gift.gif"'/>"/></a> --%>
		<div class="search-bar">
			<div class="search-box">
				<form action="#" method="get">
					<div class="s-form">
						<input class="s-text" type="text">
						<input class="s-button" type="submit" value="搜索">
					</div>
				</form>
			</div>
			<div class="hotwords">
				<strong>热门搜索：</strong>
				<a target="_blank" href="#">手抓饼</a>
				<a target="_blank" href="#">榴莲酥</a>
				<a target="_blank" href="#">月光招牌奶茶</a>
				<a target="_blank" href="#">每天优惠</a>
				<a target="_blank" href="#">微信红包</a>
			</div>
		</div>
		<a class="promise"><img src="<c:url value='/static/images/demo_promise.png'/>"/></a>
	</div></div>
</div>
<!-- header -->

<!-- main -->
<div class="main-w clearfix">
	<div class="main clearfix">

			<sitemesh:body/>

	</div>
</div>
<!-- /main -->

<!-- footer -->
<div class="footer-w"><div class="footer">
	<div class="links">
		<a rel="nofollow" target="_blank" href="">关于我们</a>|
		<a rel="nofollow" target="_blank" href="">联系我们</a>|
		<a rel="nofollow" target="_blank" href="">商家加盟</a>|
		<a rel="nofollow" target="_blank" href="">营销中心</a>|
		<a rel="nofollow" target="_blank" href="">营销中心</a>|
		<a rel="nofollow" target="_blank" href="">使用帮助</a>|
		<a target="_blank" href="">友情链接</a>
	</div>
	<div class="copyright">xxx备案编号11010501123456&nbsp;&nbsp;|&nbsp;&nbsp;京ICP证xxx号&nbsp;&nbsp;|&nbsp;&nbsp;</div>
</div></div>
<!-- /footer -->
</body>
</html>
<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.clearfix:after{content: ''; display: block; clear: both;}

	#main {padding-bottom:60px;}
</style>
<div id="main">
	<!--
	<div id="nologin-tip">
		<i></i>您还没有登录！登录后订单将保存到您账号中，方便以后查看！ <a href="#none" class="btn-red-1">立即登录</a>
	</div>
	-->
	<div id="cart-success">
        <h2>已成功下单！</h2>
		<h2>货到付款，立即配送。</h2>
        <h2>请耐心等待。</h2>
	</div>

	<div class="footer-btn-w">
		<div data-role="footer" data-position="fixed">
			<div class="footer-buttons">
				<a style="font-weight: normal" data-role="button" data-icon="home" data-transition="none" href="<c:url value='/app.html'/>">首页</a>&nbsp;&nbsp;&nbsp;
				<a style="font-weight: normal" data-role="button" data-icon="gear" data-transition="none" href="<c:url value='/app/category.html'/>">继续购物</a>&nbsp;&nbsp;&nbsp;
				<a style="font-weight: normal" data-role="button" data-icon="gear" data-transition="none" href="<c:url value='/app/order/${order.id}.html'/>">查看订单</a>
			</div>
		</div>
	</div>
</div>

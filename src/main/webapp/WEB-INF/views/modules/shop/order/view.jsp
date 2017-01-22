<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="pageId" value="order-view-page" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>查看订单</title>
    <meta name="decorator" content="shop_default"/>
    <meta name="description" content="月光茶人"/>
    <meta name="keywords" content="月光茶人"/>
</head>
<body>

<div class="main-content clearfix">
	<div id="nologin-tip">
		<i></i>您还没有登录！登录查看我的积分 <a href="#none" class="btn-red-1">立即登录</a>
	</div>
	<div class="preorder" id="preorder">
		<h1>查看订单详情</h1>
		
		<div class="step step-complete" id="step-1">
			<div class="title">
				<strong>收货人信息</strong>
			</div>
			<div class="content">
				<div id="address-t-title">
					<div id="address-t-title-1">${order.addressFullname}&nbsp;&nbsp;&nbsp;${order.addressTelephone}</div>
					<div id="address-t-title-2">${fn:replace(order.areaPathNames, "中国/", "")}&nbsp;&nbsp;${order.addressDetail}</div>
				</div>
			</div>
		</div>
		
		<div class="step" id="step-2">
			<div class="title">
				<strong>支付及配送方式</strong>
			</div>
			<div class="content">货到付款，立即送餐。</div>
		</div>
		
		<div class="step" id="step-3">
			<div class="title">
				<strong>商品清单</strong>
			</div>
			<div class="content">
				<div class="preorder-main t-div">
					<div class="t-header">
						<div class="t-column t-name">商品</div>
						<div class="t-column t-price">单价(元)</div>
						<div class="t-column t-qty">数量</div>
						<div class="t-column t-subtotal">小计(元)</div>
					</div>
			
					<div class="t-body clearfix">
						<c:forEach items="${orderItemList}" var="item" varStatus="status">
							<div class="t-row clearfix">
								<div class="t-column t-name">${item.name}</div>
								<div class="t-column t-price"><strong>${item.price}</strong></div>
								<div class="t-column t-qty"><div>${item.count}</div></div>
								<div class="t-column t-subtotal"><strong>${item.price * item.count}</strong></div>
							</div>
						</c:forEach>
					</div>
					
					<div class="t-footer clearfix">
						<div class="t-column t-total-count">共<em>${order.totalCount}</em>件商品：</div>
						<div class="t-column t-total-price"><em>¥${order.totalPrice}</em></div>
						<div class="t-column t-submit"><a id="preorder-form-submit" class="btn-submit" href="<c:url value='/'/>">继续购物</a></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>

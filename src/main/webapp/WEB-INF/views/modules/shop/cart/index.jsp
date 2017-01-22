<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="cart-success-page" scope="request"/>
<!DOCTYPE html>
<html>
<head>
	<title>购物车</title>
	<meta name="decorator" content="shop_default"/>
	<meta name="description" content="月光茶人" />
	<meta name="keywords" content="月光茶人" />
</head>
<body>

<!-- content -->
<div class="main-content clearfix">
	<div id="nologin-tip">
		<i></i>您还没有登录！登录后购物车的商品将保存到您账号中 <a href="#none" class="btn-red-1">立即登录</a>
	</div>
	<div id="cart">
		<h1>我的购物车</h1>		
		<div class="cart-w">
			<form id="cart-form" method="post" action="<c:url value='/preorder/add.html'/>">
			<div class="cart-main t-div">
				<div class="t-header">
					<div class="t-column t-checkbox"><input type="checkbox" name="toggle-checkboxes" id="toggle-checkboxes" checked="checked"></div>
					<div class="t-column t-cell">全选</div>
					<div class="t-column t-name">商品</div>
					<div class="t-column t-price">单价(元)</div>
					<div class="t-column t-qty">数量</div>
					<div class="t-column t-subtotal">小计(元)</div>
					<div class="t-column t-action">操作</div>
				</div>
		
				<div class="t-body clearfix">
					<c:set var="totalCount" value="0" />
					<c:set var="totalPrice" value="0" />
					<c:forEach items="${cartItemList}" var="item" varStatus="status">
						<div class="t-row clearfix">
							<div class="t-column t-checkbox"><input type="checkbox" checked="checked" value="${item.id}_${item.product.id}_${item.count}" name="cartItemProps"></div>
							<div class="t-column t-cell"><a target="_blank" href="<c:url value='/product/${item.product.id}.html'/>"><img src="<c:url value='${item.product.imageSmall}'/>" alt="${item.product.name}"></a></div>
							<div class="t-column t-name"><a target="_blank" href="<c:url value='/product/${item.product.id}.html'/>">${item.product.name}</a></div>
							<div class="t-column t-price"><strong>${item.product.price}</strong></div>
							<div class="t-column t-qty">
								<div class="choose-qty">
									<a class="decrement " href="javascript:void(0);">-</a>
									<input type="text" value="${item.count}">
									<a class="increment" href="javascript:void(0);">+</a>
								</div>
								<div>立即配送</div>
							</div>
							<div class="t-column t-subtotal"><strong>${item.product.price * item.count}</strong></div>
							<div class="t-column t-action"><a href="javascript:void(0);" class="cart-remove">删除</a></div>
						</div>
						<c:set var="_totalCount" value="${item.count + _totalCount}" />
			    		<c:set var="_totalPrice" value="${(item.count * item.product.price) + _totalPrice}" />
					</c:forEach>
				</div>
				
				<div class="t-footer clearfix">
					<div class="t-column t-checkbox"><input type="checkbox" name="toggle-checkboxes" checked="checked"></div>
					<div class="t-column t-cell">全选</div>
					<div class="t-column t-total-count">已选择<em>${_totalCount}</em>件商品：</div>
					<div class="t-column t-total-price"><em>¥${_totalPrice}</em></div>
					<div class="t-column t-submit"><a id="submit-cart-form" class="btn-submit" href="#">去结算</a></div>
				</div>
			</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$("#submit-cart-form").click(function(){
		$("#cart-form").submit();
	});
</script>

</body>
</html>

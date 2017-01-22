<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="pageId" value="product-view-page" scope="request"/>
<!DOCTYPE html>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="shop_default"/>
	<meta name="description" content="月光茶人"/>
	<meta name="keywords" content="月光茶人"/>

	<!-- 禁用浏览器后退按钮 -->
	<script type="text/javascript">
		window.history.forward(1);
	</script>
</head>
<body>

<!-- main -->
<div class="main-w clearfix">
	<div class="main clearfix">
		<!-- content -->
		<div class="main-content clearfix">
			<div id="nologin-tip">
				<i></i>您还没有登录！登录后获取我的地址 <a href="#none" class="btn-red-1">立即登录</a>
			</div>
			<div class="preorder" id="preorder">
				<h1>填写并核对订单信息</h1>
				
				<div class="step step-complete" id="step-1">
				<form id="address-form" method="post" action="<c:url value='/address/ajax-save-last-area.html'/>">
					<div class="title">
						<strong>收货人信息</strong>
						<a href="javascript:void(0);" id="btn-address-edit" <c:if test="${empty address}">style="display:none"</c:if>>[修改]</a>
					</div>
					<div class="content">
						<div id="address-t-title" <c:if test="${empty address}">style="display:none"</c:if>>
							<div id="address-t-title-1">${address.fullname}&nbsp;&nbsp;&nbsp;${address.telephone}</div>
							<div id="address-t-title-2">${fn:replace(address.area.pathNames, "中国/", "")}&nbsp;&nbsp;${address.detail}</div>
						</div>
						<div id="address-t-content" <c:if test="${not empty address}">style="display:none"</c:if>>
						   <div id="name_div" class="list message">
							   <span class="label"><em>*</em>收货人：</span>
							   <div class="field"><input type="text" class="textbox" name="fullname" id="fullname" value="${address.fullname}" maxlength="20"></div>
						   </div>
						   <div id="call_div" class="list">
							   <span class="label"><em>*</em>手机号码：</span>
							   <div class="field"><input type="text" class="textbox" id="telephone" name="telephone" value="${address.telephone}" maxlength="11"></div>
						   </div>
						   <div id="area_div" class="list">
							   <span class="label"><em>*</em>所在地区：</span>
							   <div class="field">
							   		<c:set var="areaSelectorAction" value="preorderView" scope="request"/>
							   		<jsp:include page="../area/area-selector.jsp" />
							   </div>
						   </div>
					       <div class="list address">
							   <span class="label"><em>*</em>详细地址：</span>
							   <div class="field"><input type="text" class="textbox" name="detail" maxlength="50" value="${address.detail}"></div>
						   </div>
						   <div class="list submit">
							   <span class="label">&nbsp;</span>
							   <div class="field"><input type="submit" class="btn-red" value="保存收货人信息"></div>
						   </div>
						</div>
					</div>
				</form>
				</div>
				
				<div class="step" id="step-2">
					<div class="title">
						<strong>支付及配送方式</strong>
					</div>
					<div class="content">货到付款，立即送餐。</div>
				</div>
				
				<div class="step" id="step-3">
				<form id="preorder-form" method="post" action="<c:url value='/order/add/${preorder.id}.html'/>">
					<input type="hidden" id="addressId" name="addressId" value="${address.id}"/>
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
								<c:forEach items="${preorderItemList}" var="item" varStatus="status">
									<div class="t-row clearfix">
										<div class="t-column t-name">${item.name}</div>
										<div class="t-column t-price"><strong>${item.price}</strong></div>
										<div class="t-column t-qty"><div>${item.count}</div></div>
										<div class="t-column t-subtotal"><strong>${item.price * item.count}</strong></div>
									</div>
								</c:forEach>
							</div>
							
							<div class="t-footer clearfix">
								<div class="t-column t-total-count">共<em>${preorder.totalCount}</em>件商品：</div>
								<div class="t-column t-total-price"><em>¥${preorder.totalPrice}</em></div>
								<div class="t-column t-submit"><a id="preorder-form-submit" class="btn-submit" href="#">提交</a></div>
							</div>
						</div>
					</div>
				</form>
				</div>
			</div>
		</div>
		<!-- /content -->
	</div>
</div>
<!-- /main -->

<!-- footer -->
<div class="footer-w"><div class="footer">
	<div class="copyright">xxx备案编号11010501123456&nbsp;&nbsp;|&nbsp;&nbsp;京ICP证xxx号&nbsp;&nbsp;|&nbsp;&nbsp;</div>
</div></div>
<!-- /footer -->

<script type="text/javascript">
$(function(){
	// edit/submit address
	$("#btn-address-edit").click(function(){
		$("#address-t-title").toggle();
		$("#address-t-content").toggle();
		$(this).toggle();
	});
	$("#address-form").submit(function(event){
		event.preventDefault();
		$.ajax({
			url: $(this).attr("action"),
			type: $(this).attr("method"),
			data: $(this).serialize(),
			dataType: "json",
			success: function(response){
				if(response.result){
					var data = response.data;
					$("#addressId").val(data.id);
					$("#address-t-title-1").html(data.fullname + "&nbsp;&nbsp;&nbsp;" + data.telephone);
					$("#address-t-title-2").html(data.area.pathNames.replace(/^中国\//i, "") + "&nbsp;&nbsp;" + data.detail);
					$("#address-t-title").toggle();
					$("#address-t-content").toggle();
					$("#btn-address-edit").toggle();
				}else{
					alert(response.message);
				}
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){
				alert("服务器出错：" + textStatus);
			}
		});
	});
	
	$("#preorder-form-submit").click(function(){
		$("#preorder-form").submit();
	});
});
</script>

</body>
</html>

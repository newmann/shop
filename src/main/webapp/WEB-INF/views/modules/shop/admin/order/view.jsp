<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查看订单</title>
	<meta name="decorator" content="default"/>
    <style type="text/css">
    </style>
</head>
<body>
<div id="order-view">
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order">订单列表</a></li>
		<li class="active"><a href="${ctx}/order/view?id=${order.id}">查看订单</a></li>
	</ul>

	<div class="form-horizontal">
        <!-- 订单状态 -->
        <div class="control-group">
            <label class="control-label">订单状态:</label>
            <div class="controls">${order.orderStatus.pendingLabel}</div>
        </div>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <div class="order-status clearfix">
                    <c:forEach items="${order.orderStatus.statusProcessList}" var="statusProcess" varStatus="status">
                        <div class="node" data-statusProcess="${statusProcess.id}" data-statusUnion="${statusProcess.statusUnion}" data-isFinished="${statusProcess.isFinished}">
                            <i class="status-img ${statusProcess.cssClass}"></i>
                            <div class="node-txt">${statusProcess.label}</div>
                        </div>
                    </c:forEach>
                </div>

                <!-- 设置订单状态: com.iwc.shop.modules.shop.entity.OrderStatus-->
                <c:if test="${order.statusUnion eq '1-100'}"><button class="setOrderStatus" data-url="${ctx}/order/setCashStatusDelivering?id=${order.id}">设置现金支付-配送中</button></c:if>
                <c:if test="${order.statusUnion eq '1-200'}"><button class="setOrderStatus" data-url="${ctx}/order/setCashStatusReceived?id=${order.id}">设置现金支付-已收货</button></c:if>
                <c:if test="${order.statusUnion eq '2-100'}"><button class="setOrderStatus" data-url="${ctx}/order/setOPStatusPaid?id=${order.id}">设置在线支付-已付款</button></c:if>
                <c:if test="${order.statusUnion eq '2-200'}"><button class="setOrderStatus" data-url="${ctx}/order/setOPStatusDelivering?id=${order.id}">设置在线支付-配送中</button></c:if>
                <c:if test="${order.statusUnion eq '2-300'}"><button class="setOrderStatus" data-url="${ctx}/order/setOPStatusReceived?id=${order.id}">设置在线支付-已收货</button></c:if>
            </div>
        </div>

		<div class="control-group">
			<label class="control-label">订单ID:</label>
			<div class="controls">${order.id}</div>
		</div>
        <div class="control-group">
            <label class="control-label">订单号:</label>
            <div class="controls">${order.serialNo}</div>
        </div>
		<div class="control-group">
			<label class="control-label">下单时间:</label>
			<div class="controls"><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人:</label>
			<div class="controls">${order.addressFullname}</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码:</label>
			<div class="controls">${order.addressTelephone}</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货地址:</label>
			<c:set var="areaTitle" value='${fn:replace(order.areaPathNames, "中国/广东省/", "")}'/>
			<c:set var="areaTitle" value='${fn:replace(areaTitle, "/", " ")}'/>
			<div class="controls">${areaTitle} ${order.addressDetail}</div>
		</div>
		<div class="control-group">
			<label class="control-label">IP:</label>
			<div class="controls">${order.ip}</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码:</label>
			<div class="controls">${order.areaZipCode}</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间:</label>
			<div class="controls"><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间:</label>
			<div class="controls"><fmt:formatDate value="${order.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
		</div>
		<div class="control-group">
			<label class="control-label">创建者:</label>
			<div class="controls">${order.createBy.id}</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新者:</label>
			<div class="controls">${order.createBy.id}</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品个数:</label>
			<div class="controls">${order.totalCount}</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品列表:</label>
			<div class="controls"><ul class="product-list">
				<c:forEach items="${orderItemList}" var="item" varStatus="status">
					<li>
						<div class="product-img">
							<a href="<c:url value='${pageContext.request.contextPath}/app/product/${item.product.id}.html'/>" target="_blank"><img src="<c:url value="${item.imageSmall}"/>"></a>
						</div>
						<div class="product-text">
							<div><a href="<c:url value='${pageContext.request.contextPath}/app/product/${item.product.id}.html'/>" target="_blank">${item.name}</a></div>

								<%-- product attributes and price --%>
							<c:set var="_price" value="${item.price}" />
							<c:if test="${not empty item.attributeList}">
							<div>
								</c:if>
								<c:forEach items="${item.attributeList}" var="attribute" varStatus="status">
									${attribute.attributeItemName}: ${attribute.attributeItemValueName}
                                    <c:if test="${attribute.attributeItemValuePrice > 0}">+￥${attribute.attributeItemValuePrice}</c:if>
                                    <c:if test="${not status.last}">, &nbsp;&nbsp;</c:if>
								</c:forEach>
								<c:if test="${not empty item.attributeList}">
							</div>
							</c:if>
							<div>￥${item.price} x ${item.count}</div>
						</div>
					</li>
				</c:forEach>
			</ul></div>
            <div class="control-group">
                <label class="control-label">是否付款:</label>
                <div class="controls">
                    <c:if test="${order.hasPaid == '1'}">已付款</c:if>
                    <c:if test="${order.hasPaid != '1'}">未付款</c:if>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">付款方式:</label>
                <div class="controls">
                    <c:choose>
                        <c:when test="${order.payType == '1'}">现金支付</c:when>
                        <c:when test="${order.payType == '2'}">微信支付</c:when>
                        <c:when test="${order.payType == '3'}">支付宝</c:when>
                        <c:otherwise>未知方式</c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">优惠红包:</label>
                <div class="controls">
                    <c:choose>
                        <c:when test="${not empty order.couponUser}">${order.couponUser.name}</c:when>
                        <c:otherwise>无</c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">优惠总额(元):</label>
                <div class="controls">-￥${order.couponUserTotalPrice}</div>
            </div>
			<div class="control-group">
				<label class="control-label">订单总额(元):</label>
				<div class="controls">￥${order.totalPrice}</div>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="javascript:location.href='${ctx}/order'"/>
		</div>
	</div>
</div>
<script type="text/javascript">
    $(function(){
        $('.setOrderStatus').click(function(){
            var msg = '是否' + $(this).html() + '?';
            var href = $(this).attr('data-url');
            confirmx(msg, href);
        });
    });
</script>
</body>
</html>
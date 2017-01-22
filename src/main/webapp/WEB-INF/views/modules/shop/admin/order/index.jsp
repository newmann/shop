<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/order">订单列表</a></li>
</ul>

<form:form id="searchForm" modelAttribute="order" action="${ctx}/order" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>订单ID:</label><form:input path="id" htmlEscape="false" maxlength="128" class="input-small"/>&nbsp;
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
</form:form>

<sys:message content="${message}"/>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
        <th>订单ID</th>
        <th>订单号</th>
		<th>下单时间</th>
		<th>收货人</th>
		<th>手机号码</th>
		<th>收货地址</th>
		<th>IP</th>
        <th>订单状态</th>
        <th>是否付款</th>
        <th>付款方式</th>
		<th>产品个数</th>
        <th>优惠总额(元)</th>
        <th>订单总额(元)</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="order">
		<c:set var="areaTitle" value='${fn:replace(order.areaPathNames, "中国/广东省/", "")}'/>
		<c:set var="areaTitle" value='${fn:replace(areaTitle, "/", " ")}'/>
		<tr>
            <td><a href="${ctx}/order/view?id=${order.id}">${order.id}</a></td>
            <td><a href="${ctx}/order/view?id=${order.id}">${order.serialNo}</a></td>
			<td><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${order.addressFullname}</td>
			<td>${order.addressTelephone}</td>
			<td>${areaTitle} ${order.addressDetail}</td>
			<td>${order.ip}</td>
			<td>${order.orderStatus.pendingLabel}</td>
            <td><c:if test="${order.hasPaid == '1'}">已付款</c:if><c:if test="${order.hasPaid != '1'}">未付款</c:if></td>
            <td>
                <c:choose>
                    <c:when test="${order.payType == '1'}">现金支付</c:when>
                    <c:when test="${order.payType == '2'}">微信支付</c:when>
                    <c:when test="${order.payType == '3'}">支付宝</c:when>
                    <c:otherwise>未知方式</c:otherwise>
                </c:choose>
            </td>
            <td>${order.totalCount}</td>
            <td>￥${order.couponUserTotalPrice}</td>
			<td>￥${order.totalPrice}</td>
			<td>
				<a href="${ctx}/order/view?id=${order.id}">查看&nbsp;&nbsp;</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
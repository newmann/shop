<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分类管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3});
		});
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/shop/category/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/shop/category/">分类列表</a></li>
		<shiro:hasPermission name="shop:category:edit">
			<li><a href="${ctx}/shop/category/form">分类添加</a></li>
		</shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<tr>
				<th>分类名称</th>
				<th>简要描述</th>
				<th>APP首页推荐</th>
				<th>APP首页推荐排序</th>
				<th style="text-align:center;">排序</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${list}" var="category">
				<tr id="categoryId_${category.id}">
					<td><a href="${ctx}/shop/category/form?id=${category.id}">${category.name}</a></td>
					<td>${category.shortDescription}</td>
					<td>
						<c:if test="${category.appFeaturedHome eq '1'}">是</c:if>
						<c:if test="${category.appFeaturedHome ne '1'}">&nbsp;</c:if>
					</td>
					<td>${category.appFeaturedHomeSort}</td>
					<td style="text-align:center;">
						<shiro:hasPermission name="shop:category:edit">
							<input type="hidden" name="ids" value="${category.id}"/>
							<input name="sorts" type="text" value="${category.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						</shiro:hasPermission>
						<shiro:lacksPermission name="shop:category:edit">
							${category.sort}
						</shiro:lacksPermission>
					</td>
					<td>
						<a href="${pageContext.request.contextPath}/product/list/${category.id}${fns:getUrlSuffix()}" target="_blank">访问&nbsp;</a>
						<shiro:hasPermission name="shop:category:edit">
							<a href="${ctx}/shop/category/form?id=${category.id}">修改&nbsp;</a>
							<a href="${ctx}/shop/category/delete?id=${category.id}" onclick="return confirmx('要删除该分类及所有子分类吗？', this.href)">删除&nbsp;</a>
							<%--<a href="${ctx}/shop/category/form?parent.id=${category.id}">添加下级分类</a>--%>
						</shiro:hasPermission>
					</td>
				</tr>
			</c:forEach>
		</table>
		<shiro:hasPermission name="shop:category:edit">
			<div class="form-actions pagination-left">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
			</div>
		</shiro:hasPermission>
	</form>
</body>
</html>
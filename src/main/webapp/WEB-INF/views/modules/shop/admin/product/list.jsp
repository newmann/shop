<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function viewComment(href){
			top.$.jBox.open('iframe:'+href,'查看评论',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
					$("body", h.find("iframe").contents()).css("margin","10px");
				}
			});
			return false;
		}
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
		<li class="active"><a href="${ctx}/shop/product/list?category.id=${product.category.id}">产品列表</a></li>
		<shiro:hasPermission name="shop:product:edit">
			<li><a href="<c:url value='${fns:getAdminPath()}/shop/product/form?id=${product.id}&category.id=${product.category.id}&category.name=${product.category.name}'/>">产品添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/shop/product/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>分类：</label><sys:treeselect id="category" name="category.id" value="${product.category.id}" labelName="category.name" labelValue="${product.category.name}"
					title="分类" url="/shop/category/treeData" module="product" notAllowSelectRoot="false" cssClass="input-small"/>
		<label>产品名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品名称</th>
				<th>分类</th>
				<th>简要描述</th>
                <th>商品价格(元)</th>
                <th>折后价格(元)</th>
				<th>APP首页推荐</th>
				<th>APP首页推荐排序</th>
				<th>APP乐呵呵推荐</th>
				<th>APP乐呵呵推荐排序</th>
				<th>排序</th>
				<th>更新时间</th>
				<td>状态</td>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<tr>
				<td><a href="${ctx}/shop/product/form?id=${product.id}" title="${product.name}">${fns:abbr(product.name,40)}</a></td>
				<td><a href="javascript:" onclick="$('#categoryId').val('${product.category.id}');$('#categoryName').val('${product.category.name}');$('#searchForm').submit();return false;">${product.category.name}</a></td>
				<td>${product.shortDescription}</td>
                <td><fmt:formatNumber value="${product.price}" pattern="0.00"/></td>
                <td><fmt:formatNumber value="${product.featuredPrice}" pattern="0.00"/></td>
				<td>
					<c:if test="${product.appFeaturedHome eq '1'}">是</c:if>
					<c:if test="${product.appFeaturedHome ne '1'}">&nbsp;</c:if>
				</td>
				<td>${product.appFeaturedHomeSort}</td>
				<td>
					<c:if test="${product.appFeaturedTopic eq '1'}">是</c:if>
					<c:if test="${product.appFeaturedTopic ne '1'}">&nbsp;</c:if>
				</td>
				<td>${product.appFeaturedTopicSort}</td>
				<td>${product.sort}</td>
				<td><fmt:formatDate value="${product.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
                    <c:forEach items="${fns:getDictList('status')}" var="status">
                        <c:if test="${product.status eq status.value}">${status.label}</c:if>
                    </c:forEach>
                </td>
				<td>
					<a href="${pageContext.request.contextPath}/product/${product.id}${fns:getUrlSuffix()}" target="_blank">访问&nbsp;&nbsp;</a>
					<shiro:hasPermission name="shop:product:edit">
	    				<a href="${ctx}/shop/product/form?id=${product.id}">修改&nbsp;&nbsp;</a>
	    				<shiro:hasPermission name="shop:product:audit">
							<a href="${ctx}/shop/product/delete?id=${product.id}" onclick="return confirmx('确认要删除该产品吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
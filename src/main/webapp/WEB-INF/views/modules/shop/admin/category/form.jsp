<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/shop/category/">分类列表</a></li>
	<shiro:hasPermission name="shop:category:edit">
	<li class="active">
		<a href="${ctx}/shop/category/form?id=${category.id}&parent.id=${category.parent.id}">栏目${not empty category.id?'修改':'添加'}</a>
	</li>
	</shiro:hasPermission>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="category" action="${ctx}/shop/category/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">分类名称:</label>
		<div class="controls">
			<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
		</div>
	</div>
	<div class="control-group" style="display: none">
		<label class="control-label">上级分类:</label>
		<div class="controls">
			<sys:treeselect id="category" name="parent.id" value="${category.parent.id}" labelName="parent.name" labelValue="${category.parent.name}"
							title="分类" url="/shop/category/treeData" extId="${category.id}" cssClass="required"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">原图:</label>
		<div class="controls">
			<form:hidden path="image" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<sys:ckfinder input="image" type="images" uploadPath="" maxHeight="80"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">大图:</label>
		<div class="controls">
			<form:hidden path="imageLarge" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<sys:ckfinder input="imageLarge" type="images" uploadPath="" maxHeight="70"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">中图:</label>
		<div class="controls">
			<form:hidden path="imageMedium" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<sys:ckfinder input="imageMedium" type="images" uploadPath="" maxHeight="60"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">小图:</label>
		<div class="controls">
			<form:hidden path="imageSmall" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<sys:ckfinder input="imageSmall" type="images" uploadPath="" maxHeight="50"/>
		</div>
	</div>
	<%--
	<div class="control-group">
		<label class="control-label">链接:</label>
		<div class="controls">
			<form:input path="href" htmlEscape="false" maxlength="200"/>
			<span class="help-inline">分类超链接地址，优先级“高”</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">目标窗口:</label>
		<div class="controls">
			<form:input path="hrefTarget" htmlEscape="false" maxlength="200"/>
			<span class="help-inline">分类超链接打开的目标窗口，新窗口打开，请填写：“_blank”</span>
		</div>
	</div>
	--%>
	<div class="control-group">
		<label class="control-label">简短描述:</label>
		<div class="controls">
			<form:textarea path="shortDescription" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">APP首页推荐:</label>
		<div class="controls">
			<form:checkboxes path="appFeaturedHome" items="${fns:getDictList('yes')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">APP首页推荐排序:</label>
		<div class="controls">
			<form:input path="appFeaturedHomeSort" htmlEscape="false" maxlength="11" class="required digits"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">排序:</label>
		<div class="controls">
			<form:input path="sort" htmlEscape="false" maxlength="11" class="required digits"/>
			<span class="help-inline">栏目的排列次序</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">关键字:</label>
		<div class="controls">
			<form:input path="metaKeywords" htmlEscape="false" maxlength="600" cssClass="meta-input"/>
			<span class="help-inline">多个关键字，用英文逗号,分隔，有助于搜索引擎优化</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">关键描述:</label>
		<div class="controls">
			<form:input path="metaDescription" htmlEscape="false" maxlength="600" cssClass="meta-input"/>
			<span class="help-inline">关键描述，用英文逗号,分隔，有助于搜索引擎优化</span>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="shop:category:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>
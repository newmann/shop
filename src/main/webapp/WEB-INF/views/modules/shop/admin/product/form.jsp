<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            if($("#link").val()){
                $('#linkBody').show();
                $('#url').attr("checked", true);
            }
			$("#title").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    if ($("#categoryId").val()==""){
                        $("#categoryName").focus();
                        top.$.jBox.tip('请选择商品分类','warning');
                    }else if (CKEDITOR.instances.content.getData()=="" && $("#link").val().trim()==""){
                        top.$.jBox.tip('请填写正文','warning');
                    }else{
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
		<li><a href="${ctx}/shop/product/?category.id=${product.category.id}">商品列表</a></li>
		<shiro:hasPermission name="shop:product:edit">
			<li class="active">
				<a href="<c:url value='${fns:getAdminPath()}/shop/product/form?id=${product.id}&category.id=${product.category.id}&category.name=${product.category.name}'/>">商品${not empty product.id?'修改':'添加'}</a>
			</li>
		</shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/shop/product/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">产品名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品分类:</label>
			<div class="controls">
                <sys:treeselect id="category" name="category.id" value="${product.category.id}" labelName="category.name" labelValue="${product.category.name}"
					title="分类" url="/shop/category/treeData" notAllowSelectRoot="false" notAllowSelectParent="false" cssClass="required"/>&nbsp;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原图:</label>
			<div class="controls">
                <input type="hidden" id="image" name="image" value="${product.image}" />
				<sys:ckfinder input="image" type="images" uploadPath="" maxHeight="70" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">大图:</label>
			<div class="controls">
				<form:hidden path="imageLarge" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="imageLarge" type="images" uploadPath="" maxHeight="70" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">中图:</label>
			<div class="controls">
				<form:hidden path="imageMedium" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="imageMedium" type="images" uploadPath="" maxHeight="60" selectMultiple="false"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">小图:</label>
            <div class="controls">
                <form:hidden path="imageSmall" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <sys:ckfinder input="imageSmall" type="images" uploadPath="" maxHeight="50" selectMultiple="false"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">APP长图1:</label>
            <div class="controls">
                <form:hidden path="appLongImage1" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
                <sys:ckfinder input="appLongImage1" type="images" uploadPath="" maxHeight="50" selectMultiple="false"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">APP长图2:</label>
            <div class="controls">
                <form:hidden path="appLongImage2" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <sys:ckfinder input="appLongImage2" type="images" uploadPath="" maxHeight="50" selectMultiple="false"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">APP长图3:</label>
            <div class="controls">
                <form:hidden path="appLongImage3" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <sys:ckfinder input="appLongImage3" type="images" uploadPath="" maxHeight="50" selectMultiple="false"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">APP长图4:</label>
            <div class="controls">
                <form:hidden path="appLongImage4" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <sys:ckfinder input="appLongImage4" type="images" uploadPath="" maxHeight="50" selectMultiple="false"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">APP长图5:</label>
            <div class="controls">
                <form:hidden path="appLongImage5" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <sys:ckfinder input="appLongImage5" type="images" uploadPath="" maxHeight="50" selectMultiple="false"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商品类型:</label>
            <div class="controls">
                <form:select path="type" class="input-medium required">
                    <form:option value="" label="请选择..."/>
                    <form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                </form:select>
            </div>
        </div>

		<div class="control-group">
			<label class="control-label">商品价格（元）:</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" maxlength="20" class="input-large required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">折后价格（元）:</label>
			<div class="controls">
				<form:input path="featuredPrice" htmlEscape="false" maxlength="20" class="input-large"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品属性:</label>
			<div class="controls">
				<form:checkboxes path="attrItemIdList" items="${fns:getAttrItemList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
			<label class="control-label">APP乐呵呵推荐:</label>
			<div class="controls">
				<form:checkboxes path="appFeaturedTopic" items="${fns:getDictList('yes')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">APP乐呵呵推荐排序:</label>
			<div class="controls">
				<form:input path="appFeaturedTopicSort" htmlEscape="false" maxlength="11" class="required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐位:</label>
			<div class="controls">
				<form:checkboxes path="featuredPositionList" items="${fns:getDictList('product_featured_position')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐位排序:</label>
			<div class="controls">
				<form:input path="featuredPositionSort" htmlEscape="false" maxlength="20" class="input-large required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="20" class="input-large required digits"/>
			</div>
		</div>
		<%--
		<div class="control-group">
			<label class="control-label">APP首页推荐:</label>
			<div class="controls">
				<form:checkboxes path="appFeaturedHomeList" items="${fns:getDictList('product_app_featured_home')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">APP首页推荐排序:</label>
			<div class="controls">
				<form:input path="appFeaturedHomeSort" htmlEscape="false" maxlength="20" class="input-large"/>
			</div>
		</div>
		--%>
		<div class="control-group">
			<label class="control-label">简短描述:</label>
			<div class="controls">
				<form:input path="shortDescription" htmlEscape="false" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品详情:</label>
			<div class="controls">
				<form:textarea id="description" htmlEscape="true" path="description" rows="4" maxlength="200" class="input-xxlarge"/>
				<sys:ckeditor replace="description" uploadPath="/product/description"/>
			</div>
		</div>
		<%--
		<div class="control-group">
			<label class="control-label">来源:</label>
			<div class="controls">
				<form:input path="productData.copyfrom" htmlEscape="false" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关商品:</label>
			<div class="controls">
				<form:hidden id="productDataRelation" path="productData.relation" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<ol id="productSelectList"></ol>
				<a id="relationButton" href="javascript:" class="btn">添加相关</a>
				<script type="text/javascript">
					var productSelect = [];
					function productSelectAddOrDel(id,title){
						var isExtents = false, index = 0;
						for (var i=0; i<productSelect.length; i++){
							if (productSelect[i][0]==id){
								isExtents = true;
								index = i;
							}
						}
						if(isExtents){
							productSelect.splice(index,1);
						}else{
							productSelect.push([id,title]);
						}
						productSelectRefresh();
					}
					function productSelectRefresh(){
						$("#productDataRelation").val("");
						$("#productSelectList").children().remove();
						for (var i=0; i<productSelect.length; i++){
							$("#productSelectList").append("<li>"+productSelect[i][1]+"&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"productSelectAddOrDel('"+productSelect[i][0]+"','"+productSelect[i][1]+"');\">×</a></li>");
							$("#productDataRelation").val($("#productDataRelation").val()+productSelect[i][0]+",");
						}
					}
					$.getJSON("${ctx}/shop/product/findByIds",{ids:$("#productDataRelation").val()},function(data){
						for (var i=0; i<data.length; i++){
							productSelect.push([data[i][1],data[i][2]]);
						}
						productSelectRefresh();
					});
					$("#relationButton").click(function(){
						top.$.jBox.open("iframe:${ctx}/shop/product/selectList?pageSize=8", "添加相关",$(top.document).width()-220,$(top.document).height()-180,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
				</script>
			</div>
		</div>
		--%>
		<div class="control-group">
			<label class="control-label">关键字:</label>
			<div class="controls">
				<form:input path="metaKeywords" htmlEscape="false" maxlength="200" class="input-xxlarge"/>
				<span class="help-inline">多个关键字，用英文逗号,分隔，有助于搜索引擎优化</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关键描述:</label>
			<div class="controls">
				<form:input path="metaDescription" htmlEscape="false" maxlength="200" class="input-xxlarge"/>
				<span class="help-inline">关键描述，用英文逗号,分隔，有助于搜索引擎优化</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间:</label>
			<div class="controls">
				<input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium"
					   value="<fmt:formatDate value="${product.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间:</label>
			<div class="controls">
				<input id="updateDate" name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium"
					   value="<fmt:formatDate value="${product.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">状态:</label>
            <div class="controls">
                <form:radiobuttons path="status" items="${fns:getDictList('status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
            </div>
        </div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:product:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="javascript:location.href='${ctx}/shop/product/?category.id=${product.category.id}'"/>
		</div>
	</form:form>
</body>
</html>
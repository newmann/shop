<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%-- require jquery --%>
<%--jsp 动态引入 <jsp:include page="/WEB-INF/views/modules/app/area/area-selector-cascading.jsp" />--%>

<script src="<c:url value='/static/app/js/jquery.chained/jquery.chained.remote.min.js'/>"></script>

<div class="area-chained">
	<input type="hidden" id="areaId" name="areaId" value="${area.id}"/>
	<select id="chained-province" name="province">
		<%--<option value="">请选择</option>--%>
		<c:forEach items="${areaChainedList[0].children}" var="province">
			<option value="${province.id}">${province.name}</option>
		</c:forEach>
	</select>
	<select id="chained-city" name="city">
		<%--<option value="">请选择</option>--%>
		<c:forEach items="${areaChainedList[1].children}" var="province">
			<option value="${province.id}">${province.name}</option>
		</c:forEach>
	</select>
	<select id="chained-district" name="district">
		<%--<option value="">请选择</option>--%>
		<c:forEach items="${areaChainedList[2].children}" var="province">
			<option value="${province.id}">${province.name}</option>
		</c:forEach>
	</select>
	<select id="chained-village" name="village">
		<%--<option value="">请选择</option>--%>
		<c:forEach items="${areaChainedList[3].children}" var="province">
			<option value="${province.id}">${province.name}</option>
		</c:forEach>
	</select>
	<select id="chained-street" name="street">
		<option value="">请选择</option>
		<c:forEach items="${areaChainedList[4].children}" var="province">
			<option value="${province.id}">${province.name}</option>
		</c:forEach>
	</select>
</div>
<%--
<div class="selectors">
	<c:forEach items="${areaChainedList}" var="item">
		<c:if test="${not empty item.children}">
			<select class="selector">
				<option value="">请选择</option>
				<c:forEach items="${item.children}" var="child">
					<option value="${child.id}">${child.name}</option>
				</c:forEach>
			</select>
		</c:if>
	</c:forEach>
</div>
--%>

<script type="text/javascript">
$(function(){
	$("#chained-city").remoteChained({
		parents : "#chained-province",
		url : "/area/ajax-find-city.html",
		loading : "..."
	});
	$("#chained-district").remoteChained({
		parents : "#chained-city",
		url : "/area/ajax-find-district.html",
		loading : "..."
	});
	$("#chained-village").remoteChained({
		parents : "#chained-district",
		url : "/area/ajax-find-village.html"
	});
	$("#chained-street").remoteChained({
		parents : "#chained-village",
		url : "/area/ajax-find-street.html",
		clear : true
	});

	//地区级联选择器, 以后可做成插件
	<%--
	$(document).on("change", ".selector", function(){
		$(this).next().remove();
		$('<span>add</span>').insertAfter($(this));
	});
	//get children of selector
	$(document).on("click", ".area-selector .tab-content ul li a", function(){
		var areaId = $(this).attr("data-area-id");
		var url = "<c:url value='/area/ajax-list-html/'/>" + areaId + ".html";
		var index = $(this).parent().parent().parent().attr("data-index");
		var title = $(this).html();
		$.ajax({
			type : "post",
			url : url,
			dataType : "json",
			success : function(response) {
				if (response.result) {
					// has children
					if (!$.empty(response.data.list)) {	
						$(".area-selector .tab-title ul li").each(function(){
							if ($(this).attr("data-index") > index) {
								$(this).remove();
							}
							$(this).removeClass("current");
							$(this).removeClass("select");
						});
						var li = '<li class="select current" data-index="' + (parseInt(index) + 1) + '"><a href="#none" rel="nofollow"><em>请选择</em><i></i></a></li>';
						$(".area-selector .tab-title ul").append(li);
						
						$(".area-selector .tabs .tab-content").each(function(){
							if ($(this).attr("data-index") > index) {
								$(this).remove();
							}
							$(this).addClass("display-none");
						});						
						$(".area-selector .tabs").append(response.data.list);
					}
					// click the last area
					else {
						var text = response.data.area.pathNames;
						text = text.replace(/^中国\//i, "");
						$(".area-selector .area-text").html(text);
						$("#areaId").val(areaId);
						$(".area-selector .area-content").hide();
						$(".area-selector .btn-close").hide();
						// set cookie "areaId"
						$.cookie("areaId", areaId, {path:'/'});
					}
					$(".area-selector .tab-title ul li[data-index="+index+"] em").html(title);
				}
			}
		});
	});
	--%>
});
</script>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%-- require jquery --%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div class="area-selector">
	<input type="hidden" id="areaId" name="areaId" value="${area.id}"/>
	<div class="area-text">
		<div>${fn:replace(area.pathNames, "中国/", "")}</div>
		<b></b>
	</div>
	<div class="area-content" style="display:none">
		<div class="tabs">
			<div class="tab-title">
				<ul>
					<c:forEach items="${areaSelector.titles}" var="title" varStatus="status">
					<li data-index="${status.count}" class="<c:if test='${status.last}'>current</c:if>"><a rel="nofollow" href="#none"><em>${title}</em><i></i></a></li>
					</c:forEach>
				</ul>
				<div class="stock-line"></div>
			</div>
			<c:forEach items="${areaSelector.items}" var="items" varStatus="status">
			<div data-index="${status.count}" class="tab-content <c:if test='${!status.last}'>display-none</c:if>">
				<ul>
					<c:forEach items="${items}" var="item" varStatus="status">
					<li><a rel="nofollow" data-area-id="${item.id}" href="#none">${item.name}</a></li>
					</c:forEach>
				</ul>
			</div>
			</c:forEach>
		</div>
	</div>
	<div class="btn-close" style="display:none"></div>
</div>

<script type="text/javascript">
$(function(){
	//地区级联选择器, 以后可做成插件
	<c:choose>
		<c:when test="${areaSelectorAction eq 'preorderView'}">
			$(".area-selector .area-text").click(function(){		//preorder view页面调用：不能用hover()，可能导致连续的隐藏和显示
				$(".area-selector .area-content").toggle();
				$(".area-selector .btn-close").toggle();
			});
		</c:when>
		<c:otherwise>
			$(".area-selector .area-text").hover(function(){	//产品详情页面调用
				$(".area-selector .area-content").show();
				$(".area-selector .btn-close").show();
			});
		</c:otherwise>
	</c:choose>
	$(".area-selector .btn-close").click(function(){
		$(".area-selector .area-content").hide();
		$(this).hide();
	});
	$(document).on("click", ".area-selector .tab-title li", function(){
		var index = $(this).attr("data-index");
		$(this).parent().children().removeClass("current");
		$(this).addClass("current");
		$(".area-selector .tab-content").each(function(){
			$(this).addClass("display-none");
		});
		$(".area-selector .tab-content[data-index=" + index + "]").removeClass("display-none");
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
});
</script>

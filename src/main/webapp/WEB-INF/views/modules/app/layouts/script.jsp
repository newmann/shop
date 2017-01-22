<%@page language="java" pageEncoding="UTF-8"%>

<%--jsp 静态态引入 <%@ include file="script.jsp"%> --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="cordova.js"></script>
<script src="<c:url value='/static/app/js/iscroll/iscroll.js'/>"></script>
<script type="text/javascript" src="<c:url value='/static/app/js/fastclick/fastclick.js?v=${jsVersion}'/>"></script>
<script src="<c:url value='/static/app/js/jquery.icommon/jquery.icommon.js?v=${jsVersion}'/>"></script>
<script src="<c:url value='/static/app/js/jquery.cookie/jquery.cookie.js?v=${jsVersion}'/>"></script>
<script src="<c:url value='/static/app/js/main.js?v=${jsVersion}'/>"></script>
<script>
	app.initialize();

	//		$(document).on("pageinit", "#page", function(){
	//			$(".footer-ajax").click(function(event){
	//				event.preventDefault();
	//				app.showLoader();
	//				var url = $(this).attr("href");
	//				$.ajax({
	//					type: "get",
	//					url: url,
	//					success: function(response){
	//						$("#content").html(response);
	//						app.hideLoader();
	//					}
	//				});
	//			});
	//		});
</script>

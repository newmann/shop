<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="fapp-index" scope="request"/>
<!DOCTYPE html>
<html>
<head>
	<title>手机客户端</title>
	<meta name="decorator" content="shop_default"/>
	<meta name="description" content="手机客户端" />
	<meta name="keywords" content="手机客户端" />
</head>
<body>

		<h1>月光茶人客户端下载</h1>
		<br />

		<div class="nav-wrap">
			<div class="sub-nav">
				<div class="w">
					<div>
						<%--<a class="fore3 curr" href="/static/file/EcActivity-EcActivity.apk"><b class="b3"></b></a>--%>
						<a class="fore3" href="/static/file/android.apk"><b class="b3"></b></a>
						<a class="fore1" href="javascript:;"><b class="b1"></b></a>
						<%--<a class="fore2" href="ipad.html"><b class="b2"></b></a>
						<a class="fore4" href="androidpad.html"><b class="b4"></b></a>
						<a class="fore5" href="androidtv.html"><b class="b5"></b></a>
						<a class="fore6" href="winphone.html"><b class="b6"></b></a>
						<a class="fore7" href="win8.html"><b class="b7"></b></a>--%>
					</div>
				</div>
			</div>
			<div class="float-nav-wrap">
				<div class="fixed-line" style="height: 30px;">
					<div class="w">
						<%--<a class="fore3 curr" href="android.html">Android phone</a>--%>
						<a class="fore3" href="/static/file/EcActivity-EcActivity.apk">Android phone</a>
						<a class="fore1" href="javascript:;">iPhone</a>
						<%--<a class="fore2" href="ipad.html">iPad</a>
						<a class="fore4" href="androidpad.html">Android pad</a>
						<a class="fore5" href="androidtv.html">Android TV</a>
						<a class="fore6" href="winphone.html">Windows Phone</a>
						<a class="fore7" href="win8.html">Windows 8</a>--%>
					</div>
				</div>
			</div>
		</div>

</body>
</html>
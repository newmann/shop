<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>订单打印页</title>
    <script src="<c:url value='/static/jquery/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            //浓度为10
            // clears user preferences always silent print value
            // to enable using 'printSilent' option
            jsPrintSetup.clearSilentPrint();

            // set top margins in millimeters
            jsPrintSetup.setOption('marginTop', -2);
            jsPrintSetup.setOption('marginBottom', 0);
            jsPrintSetup.setOption('marginLeft', -1);
            jsPrintSetup.setOption('marginRight', 1);

            // set page header
            jsPrintSetup.setOption('headerStrLeft', '');
            jsPrintSetup.setOption('headerStrCenter', '');
            jsPrintSetup.setOption('headerStrRight', '');

            // set empty page footer
            jsPrintSetup.setOption('footerStrLeft', '');
            jsPrintSetup.setOption('footerStrCenter', '');
            jsPrintSetup.setOption('footerStrRight', '');

            // Suppress print dialog (for this context only)
            jsPrintSetup.setOption('printSilent', 1);
            jsPrintSetup.setOption('printRange', jsPrintSetup.kRangeSpecifiedPageRange);
            jsPrintSetup.setOption('startPageRange', 1);
            jsPrintSetup.setOption('endPageRange', 1);

            jsPrintSetup.setOption('shrinkToFit', 1);
            jsPrintSetup.setOption('numCopies', 1);

            // Funciona no linux

            jsPrintSetup.setPaperSizeUnit(jsPrintSetup.kPaperSizeMillimeters);
            //jsPrintSetup.setOption('paperHeight', 15800);//一张小纸等于3950高
            var count = Number('${order.totalCount}');
            var height = 3150 * (2 + count);
            jsPrintSetup.setOption('paperHeight', height);
            jsPrintSetup.setOption('paperWidth', 4100);
//
//            jsPrintSetup.setOption('printBGImages', 1);

            // Do Print
            // When print is submitted it is executed asynchronous and
            // script flow continues after print independently of
            //completetion of print process!
            //jsPrintSetup.print(); //print at main window
            jsPrintSetup.printWindow(window); //print at frame

            //设置已经打印的订单, 设置订单状态为配送中
            var token = "${token}";
            var orderId = "${order.id}";
            var url = "/print-order/set-print/?token=" + token + "&orderId=" + orderId +  "&flush=" + new Date().getTime();
            $.ajax({
                type : "post",
                url : url,
                dataType : "json",
                success : function(response) {
                    if (response.result) {
                        //
                    } else {
                        if (response.data.printLog) {
                            console.log(response.message);
                        }
                    }
                }
            });
            return ;

            //设置横向方向
//            jsPrintSetup.setOption('orientation', jsPrintSetup.kPortraitOrientation);
//
//            // 打印背景颜色
//            //jsPrintSetup.setOption('printBGColors',0);
//            // 打印背景图片
//            //jsPrintSetup.setOption('printBGImages',0);
//
//            //设置的顶缘毫米
//            jsPrintSetup.setOption('marginTop', '');
//            jsPrintSetup.setOption('marginBottom', '');
//            //jsPrintSetup.setOption('marginLeft', '');
//            //jsPrintSetup.setOption('marginRight', '');
//
//            //设置页面标题
//            jsPrintSetup.setOption('headerStrLeft', '');
//            jsPrintSetup.setOption('headerStrCenter', '');
//            jsPrintSetup.setOption('headerStrRight', '');
//
//            // 页面外围设置
//            jsPrintSetup.setOption('marginTop', '');
//            jsPrintSetup.setOption('marginBottom', '');
//            //jsPrintSetup.setOption('marginLeft', '');
//
//            //设置空页脚
//            jsPrintSetup.setOption('footerStrLeft', '');
//            jsPrintSetup.setOption('footerStrCenter', '');
//            jsPrintSetup.setOption('footerStrRight', '');
//
//            //抑制打印对话框
//            jsPrintSetup.setSilentPrint(true);
//            //jsPrintSetup.printWindow(window);
//            jsPrintSetup.print();
//            //恢复打印对话框
//            //jsPrintSetup.setSilentPrint(false);
        });
    </script>
    <style type="text/css">
        body {font-size:12px; maring:0; padding:0;}
        .item-name {font-size: 14px;}
        .print-num-1, .print-num-2 {font-size:14px;}
        .print-num-2 {float:right;}
        .print-set-top {height: 122px;}
        .print-set {height: 122px;}
        .total-price {}
        .price-set {font-size:10px; padding-left:85px;}
        .price {font-size:10px; padding-left:100px;}
        .date {font-size:10px; padding-top:5px;}
        .item-attr {font-size:10px;}
        .tel {padding-top:8px; padding-bottom:4px;}
        .serial-no {font-size:10px;}
        .notice {}
        .notice .end-line {padding-top:5px; border-bottom:dashed 2px #000;}
        .notice .serial-no {padding-top:5px;}
    </style>
</head>
<body id="body">
    <!-- 商品分列表 -->
    <c:set var="countNum" value="0"/>
    <c:forEach items="${orderItemList}" var="item" varStatus="status">
        <c:forEach begin="1" end="${item.count}" var="item3" varStatus="status3">
            <c:set var="countNum" value="${countNum + 1}"/>
            <div class="print-set">
                <div><span class="print-num-1">APP-${printNum}</span><span class="print-num-2">${countNum}/${order.totalCount}</span></div>
                <div class="item-name"><strong>${item.name}</strong></div>
                <c:if test="${not empty item.attributeList}">
                    <div class="item-attr">
                        <c:forEach items="${item.attributeList}" var="attribute" varStatus="status4">
                            <c:if test="${not empty attribute.attributeItemValuePrintName}">(${attribute.attributeItemValuePrintName})</c:if>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="price">￥<fmt:formatNumber value="${item.price}" pattern="0.00"/></div>
                <div class="tel">TEL: 0755-29350032</div>
                <div class="serial-no">订单号: ${order.serialNo}</div>
            </div>
        </c:forEach>
    </c:forEach>

    <!-- 地址信息 -->
    <c:set var="areaTitle" value='${fn:replace(order.areaPathNames, "中国/广东省/深圳市/宝安区/", "")}'/>
    <c:set var="areaTitle" value='${fn:replace(areaTitle, "/", " ")}'/>
    <div class="print-set-top">
        <div><span class="print-num-1">APP-${printNum}</span><span class="print-num-2">(${order.totalCount})</span></div>
        <div>${order.addressTelephone}&nbsp;${order.addressFullname}</div>
        <div>${areaTitle}&nbsp;${order.addressDetail}</div>
        <div>
            <c:choose>
                <c:when test="${order.hasPaid == '1'}">已付款</c:when>
                <c:otherwise>未付款</c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${order.payType == '1'}">,现金支付</c:when>
                <c:when test="${order.payType == '2'}">,微信支付</c:when>
                <c:when test="${order.payType == '3'}">,支付宝</c:when>
                <c:otherwise>,未知方式</c:otherwise>
            </c:choose>
        </div>
        <div class="total-price">&nbsp;&nbsp;￥<fmt:formatNumber value="${order.totalPrice}" pattern="0.00"/></div>
        <div class="serial-no">订单号: ${order.serialNo}</div>
    </div>
    <div class="notice">
        <div>备注:
            <c:choose>
                <c:when test="${not empty order.notice}">${order.notice}</c:when>
                <c:otherwise>无</c:otherwise>
            </c:choose>
        </div>

        <!-- 是否有优惠红包 -->
        <c:if test="${not empty order.couponUser}">
            <div>优惠红包: ${order.couponUser.name} ￥-<fmt:formatNumber value="${order.couponUserTotalPrice}" pattern="0.00"/></div>
        </c:if>

        <div class="date"><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
        <div class="serial-no">订单号: ${order.serialNo}</div>
        <div class="end-line"></div>
    </div>

    <!-- 商品总列表 -->
    <%--
    <div class="print-set">
        <c:forEach items="${orderItemList}" var="item" varStatus="status">
                <div class="item-name"><strong>${item.name}</strong></div>
                <c:if test="${not empty item.attributeList}">
                    <div class="item-attr">
                        <c:forEach items="${item.attributeList}" var="attribute" varStatus="status2">
                            <c:if test="${not empty attribute.attributeItemValuePrintName}">(${attribute.attributeItemValuePrintName})</c:if>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="price-set">￥${item.price} x ${item.count}</div>
        </c:forEach>
    </div>
    --%>
</body>
</html>
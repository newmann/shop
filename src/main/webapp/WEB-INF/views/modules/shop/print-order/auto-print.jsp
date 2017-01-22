<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>订单自动打印</title>
    <script src="<c:url value='/static/jquery/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
</head>
<body>

<img height="100px" src="/static/images/logo.jpg">
<h1>订单自动打印页面，不能关闭！</h1>
<h3 id="printStatus"></h3>
<iframe id="print-frame" width="600" height="600" name="test" src=""></iframe>
<script type="text/javascript">
    function setFrameSrc(){
        var token = "${token}";
        var url = "/print-order/get-oldest-for-auto-print/?token=" + token + "&flush=" + new Date().getTime();
        $.ajax({
            type : "post",
            url : url,
            dataType : "json",
            success : function(response) {
                if (response.result) {
                    var order = response.data.order;
                    var frameSrc = "/print-order/get-frame-html/?token=" + token + "&orderId=" + order.id + "&flush=" + new Date().getTime();
                    $("#print-frame").attr("src", frameSrc);
                    $('#printStatus').html('正在打印订单，订单号：' + order.serialNo);
                } else {
                    if (response.data.printLog) {
                        console.log(response.message);
                    }
                    $('#printStatus').html('没有订单需要打印');
                }
            }
        });
    }

    $(function(){
        //10秒打印一次
        setFrameSrc();
        setInterval(setFrameSrc, 10000);
    });
</script>
</body>
</html>
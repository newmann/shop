<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.clearfix:after{content: ''; display: block; clear: both;}

    #main {padding-bottom:60px;}

    a.btn, button.btn{
        border:1px solid #4390af; border-radius: 6px; background-color: #55abce;
        padding: 10px 0; display: block; margin: 0 10px;
        font-size: 16px; color: #fff; text-align: center;
    }
    a.btn{
        background-color: #3394bb;
        margin-top:20px;
    }
    input{
        border-bottom:1px solid #4390af;
        margin-left:10px;
    }

    #nav {border: none;}
    #nav a {
        padding: 12px 0;
        color: #fff;
    }
    #nav a span {
        color: #fff;
    }
    .nav-left {
        font-size: 16px;
    }
    .nav-right {
        background-color: #fd6161;
    }
    .nav-right a {
        font-size: 20px;
    }

    .step {background: #fff; margin-bottom: 4px; padding:4px;}
    .step .title {height: 45px; line-height: 45px;}
    .step .content {padding:0 22px 22px;}
    #step-1 {border-top: none; }
    #step-1 .content, #step-2 .content {font-size: 1.5em; }
    #step-1 .title, #step-2 .title {font-size: 1.5em; font-weight: 700; color: #38c;}
    #address-t-content input{width: 150px;}
    #address-t-content .list {height:22px; padding:8px 0;}
    #address-t-content .label {float: left; height: 26px; line-height: 26px; margin-right: 5px; text-align: right; width: 85px;}
    #address-t-content .label em {color: #f60; font-family: sans-serif; margin-right: 5px;}
    #address-t-content .field {float: left; height: 26px; line-height: 26px;}
    #address-t-content .address .textbox {width:300px;}

    .top-content {
        background-color: #ececec;
        position: relative;
    }
    .top-content .item-list-wrap.current {
        display: block;
    }
    .top-content .item-list-wrap {
        display: none;
        min-height: 300px;
        position: relative;
    }
    .top-content .item-list {
        margin-top: -4px;
        width: 100% !important;
        z-index: 2;
    }
    .top-content ul {
        list-style: outside none none;
        margin: 0;
        padding: 0;
    }
    .top-content .item-list li {
        background-color: #fff;
        margin-top: 4px;
        padding-top: 1px;
        position: relative;
        padding-left: 40px;
        min-height: 82px;
    }
    .top-content img.item-pic {
        display: block;
        float: left;
        height: 80px;
        margin: 7px 20px 0 0;
        height: 70px;
        width: 70px;
    }
    .top-content .item-name {
        font-size: 16px;
        line-height: 30px;
        margin: 0 5px 0 0;
        overflow: hidden;
        color: #252525;
    }
    .top-content .item-attr{
        padding: 5px 0 10px;
        color: #bdbdbd;
        font-size: 14px;
    }
    .top-content .item-remove {
        display: inline-block;
        background: url('${url}/static/app/images/icon-shp-cart-remove.png') no-repeat 0 0;
        background-size: 50px 50px;
        width: 23px;
        height: 23px;
        position: absolute;
        right: 0;
        margin-right: 15px;
        text-indent: -1000px;
        overflow: hidden;
        cursor: pointer;
    }
</style>
<div id="main">
	<div class="step step-complete" id="step-1">
	<form id="address-form" method="post" action="${url}/app/address/save-last-area.html">
		<input type="hidden" id="address-form-areaId" name="areaId" value="${area.id}"/>
		<div class="title">
			<strong>收货人信息</strong>
			<a tapmode="active" onclick="editAddress()" id="btn-address-edit" <c:if test="${empty address}">style="display:none"</c:if>>[修改]</a>
		</div>
		<div class="content">
			<div id="address-t-title" <c:if test="${empty address}">style="display:none"</c:if>>
				<div id="address-t-title-1">
                    <span id="address-title-fullname">${address.fullname}</span>&nbsp;&nbsp;&nbsp;
                    <span id="address-title-telephone">${address.telephone}</span>
                </div>
				<c:set var="areaTitle" value='${fn:replace(address.area.pathNames, "中国/广东省/", "")}'/>
				<c:set var="areaTitle" value='${fn:replace(areaTitle, "/", " ")}'/>
				<div id="address-t-title-2">
				    <span id="address-title-pathNames">${areaTitle}</span>&nbsp;
				    <span id="address-title-detail">${address.detail}</span>
                </div>
			</div>
			<ul id="address-t-content" <c:if test="${not empty address}">style="display:none"</c:if>>
				<li>
					<label><em>*</em>收货人:</label>
					<input type="text" name="fullname" id="fullname" value="${address.fullname}" maxlength="20">
				</li>
				<li>
					<label><em>*</em>手机:</label>
					<input type="text" name="telephone" id="telephone" value="${address.telephone}" maxlength="11">
				</li>
				<li>
					<label><em>*</em>城市:</label>
					<select id="chained-city" name="city">
						<c:forEach items="${areaChainedList[1].children}" var="province">
							<option value="${province.id}">${province.name}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label><em>*</em>区域:</label>
					<select id="chained-district" name="district">
						<c:forEach items="${areaChainedList[2].children}" var="province">
							<option value="${province.id}">${province.name}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label><em>*</em>村庄:</label>
					<select id="chained-village" name="village">
						<c:forEach items="${areaChainedList[3].children}" var="province">
							<option value="${province.id}">${province.name}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label><em>*</em>街道:</label>
					<select id="chained-street" name="street">
						<option value="">请选择...</option>
						<c:forEach items="${areaChainedList[4].children}" var="province">
							<option value="${province.id}" <c:if test="${province.id eq area.id}">selected="selected"</c:if>>${province.name}</option>
						</c:forEach>
					</select>
				</li>
				<%-- 京东的地址选择器
				<li class="ui-field-contain" style="overflow: visible">
					<label><em>*</em>所在地区:</label>
					<c:set var="areaSelectorAction" value="preorderView" scope="request"/>
					<jsp:include page="/WEB-INF/views/modules/shop/area/area-selector.jsp" />
				</li>
				--%>
				<li>
					<label><em>*</em>门牌号:</label>
					<input type="text" name="detail" id="detail" maxlength="50" value="${address.detail}" placeholder="如: 33栋602">
				</li>
			</ul>
            <a tapmode="active" onclick="submitAddressForm()" class="btn" id="submit-address-btn" <c:if test="${not empty address}">style="display:none"</c:if>>保存收货人信息</a>
		</div>
	</form>
	</div>

	<div class="step" id="step-2">
		<div class="title">
			<strong>支付及配送方式</strong>
		</div>
		<div class="content">货到付款，立即送餐。</div>
	</div>

    <form id="preorder-form" method="post" action="${url}/app/order/add/${preorder.id}.html" data-ajax="false">
        <input type="hidden" id="preorder-form-addressId" name="addressId" value="${address.id}"/>
        <div class="top-wrap top-wrap-module">
            <div class="top-title">
                <h2>商品清单</h2>
            </div>
            <div class="top-content">
                <div class="item-list-wrap current">
                    <ul class="item-list">
                        <c:forEach items="${preorderItemList}" var="item" varStatus="status">
                            <li>
                                <img class="item-pic" src="${url}${item.imageSmall}">
                                <div class="item-name">${item.name}</div>

                                <div class="price-box">
                                    <span class="price">￥${item.price}</span> x <span>${item.count}</span>
                                </div>

                                <%-- product attributes --%>
                                <c:if test="${not empty item.attributeList}">
                                    <div class="item-attr">
                                        <c:forEach items="${item.attributeList}" var="attribute" varStatus="status2">
                                            ${attribute.attributeItem.name}:${attribute.attributeItemValue.name}<c:if test="${not status2.last}">, </c:if>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</div>

<div id="nav" class="add-preorder-nav">
    <ul>
        <li class="nav-left">
            <a onclick="void(0)">总计: ￥<span id="w-total-price" class="w-total-price">${preorder.totalPrice}</span></a>
            <%--<span class="sale-off">
            商品总额:￥<span id="cart_oriPrice" class="bottom-bar-price">1113.50</span> 优惠:￥<span id="cart_rePrice" class="bottom-bar-price">100.00</span>
            </span>--%>
        </li>
        <li class="nav-right">
            <a tapmode="active" onclick="submitPreorderForm()">提交订单</a>
        </li>
    </ul>
</div>

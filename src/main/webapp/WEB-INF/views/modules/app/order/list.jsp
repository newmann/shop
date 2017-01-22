<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.top-title {
		border-bottom: 1px solid #d1cdce;
		position: relative;
		color: #262626;
		font-size: 20px;
		padding: 10px;
	}
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
	}
	.top-content ul {
		list-style: outside none none;
		margin: 0;
		padding: 0;
	}
	.top-content .item-list li {
		background-color: #fff;
		height: 95px;
		margin-top: 4px;
		padding-top: 1px;
		position: relative;
	}
	.top-content .item-list img.item-pic {
		display: block;
		float: left;
		margin: 7px 20px 0 12px;
		height: 50px;
		width: 50px;
	}
	.top-content .item-list .item-name {
		font-size: 16px;
		line-height: 30px;
		margin: 0 5px 0 0;
		overflow: hidden;
		color: #252525;
	}
	.top-content .item-desc{
		height: 32px;
		overflow: hidden;
	}
</style>

<div id="main">
	<div class="top-wrap top-wrap-module">
		<div class="top-content">
			<div class="item-list-wrap current">
				<ul class="item-list">
					<c:forEach items="${page.list}" var="order">
						<li>
							<div class="title"><span>下单时间: </span><span><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
							<a tapmode="active" onclick="openOrder(this)" data-url="${url}/app/order/${order.id}.html">
                                <c:forEach items="${order.itemList}" var="item" varStatus="status">
                                    <c:if test="${status.count <= 5}"><img class="item-pic" src="${url}${item.imageSmall}" /></c:if>
                                    <c:if test="${status.end and status.count > 5}">...</c:if>
                                </c:forEach>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>
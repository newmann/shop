<%@page pageEncoding="UTF-8"%>

<%--jsp 静态态引入 <%@ include file="*.jsp"%> --%>

<div id="message-box" class="tip ${empty message ? 'hide' : ''}">
    <i></i><span id="message">${message}</span>
    <a data-transition="none" id="close-message-btn" class="close-btn">×</a>
</div>

<script>
    $(function() {
        //close message box
        $("#close-message-btn").click(function (event) {
            event.preventDefault();
            $("#message-box").addClass("hide");
        });
    });
</script>
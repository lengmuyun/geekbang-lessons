<head>
    <jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
    <title>ERROR PAGE</title>
</head>
<body>
    <div class="container" style="margin-top: 30px">
        <img alt="" src="https://pay.chinalife.com.cn/portal/images/500.jpg" style="margin: 0 auto; display: block" />
        <h3 style="margin-top: 30px">错误信息</h3>
        <p class="text-justify" style="margin-top: 30px">
            <%=request.getAttribute("error-message")%>
        </p>
    </div>
</body>

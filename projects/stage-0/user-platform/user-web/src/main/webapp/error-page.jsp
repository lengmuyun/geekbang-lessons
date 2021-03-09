<head>
    <jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
    <title>ERROR PAGE</title>
</head>
<body>
    <div class="container">
        <img src="https://pay.chinalife.com.cn/portal/images/500.jpg">
        <p>错误信息</p>
        <%=request.getAttribute("error-message")%>
    </div>
</body>

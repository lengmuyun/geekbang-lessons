<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>My Home Page</title>
</head>
<body>
	<div class="container-lg">
		<!-- Content here -->
		Hello,World 2021<br>
		欢迎您, 新用户 <%=request.getAttribute("username")%>
	</div>
</body>
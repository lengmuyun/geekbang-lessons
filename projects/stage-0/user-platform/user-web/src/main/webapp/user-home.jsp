<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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

		<c:forEach items="${userList}" var="user">
			<p>${user.id}</p><br/>
			<p>${user.name}</p><br/>
			<p>${user.password}</p><br/>
			<p>${user.email}</p><br/>
			<p>${user.phoneNumber}</p><br/>
		</c:forEach>
	</div>
</body>
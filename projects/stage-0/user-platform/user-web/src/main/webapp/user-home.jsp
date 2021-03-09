<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>My Home Page</title>
	<style>
		.font-large {
			font-size: large;
		}
	</style>
</head>
<body>
	<div class="container-lg">
		<div>
			<img src="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1047710162,3312189091&fm=26&gp=0.jpg"
				 style="margin: 0 auto; display: block; width: 128px; height: 128px">
			<center class="font-large">${user.name}</center>
			<center class="font-large">${user.email}</center>
			<center class="font-large">${user.phoneNumber}</center>
		</div>

		<h4 style="margin-top: 30px">系统用户列表</h4>
		<table class="table table-striped" style="margin-top: 30px">
			<thead>
			<tr>
				<th>用户ID</th>
				<th>用户名</th>
				<th>邮箱</th>
				<th>手机号</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${userList}" var="user">
				<tr>
					<td>${user.id}</td>
					<td>${user.name} </td>
					<td>${user.email}</td>
					<td>${user.phoneNumber}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>

	</div>
</body>
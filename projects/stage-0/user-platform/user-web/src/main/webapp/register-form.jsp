<head>
<jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
	<title>Register Page</title>
    <style>
    </style>
</head>
<body>
	<div class="container" style="margin-top: 30px">
		<div class="panel-heading">
			<h3 class="panel-title" align="center">注册页面</h3>
		</div>
		<div class="panel-body" style="margin-top: 30px">
			<form method="post" action="/register-action">
				<div class="form-group row">
					<label for="inputUserName" class="col-sm-2 col-form-label">用户名</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputUserName" name="name">
					</div>
				</div>
				<div class="form-group row">
					<label for="inputPassword" class="col-sm-2 col-form-label">密码</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" id="inputPassword" name="password">
					</div>
				</div>
				<div class="form-group row">
					<label for="inputEmail" class="col-sm-2 col-form-label">邮箱</label>
					<div class="col-sm-10">
						<input type="email" class="form-control" id="inputEmail" name="email">
					</div>
				</div>
				<div class="form-group row">
					<label for="inputPhoneNumber" class="col-sm-2 col-form-label">手机号</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputPhoneNumber" name="phoneNumber">
					</div>
				</div>
				<div class="form-group">
					<button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>
				</div>
			</form>
		</div>
	</div>
</body>
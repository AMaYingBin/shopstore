<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>添加页面</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="./css/x-admin.css" media="all">
</head>
<body>
	<div class="x-body">
		<form action="adminadd.do" method="get">
			<div class="layui-form-item">
				<label for="L_email" class="layui-form-label"> <span class="x-red">*</span>姓名
				</label>
				<div class="layui-input-inline">
					<input type="text" id="L_email" name="aname" required=""
						lay-verify="aname" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>密码
				</label>
				<div class="layui-input-inline">
					<input type="password" id="L_pass" name="apwd" required="" lay-verify="pass" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div>
				<input class="layui-btn"  type="submit" value="提交" >
			</div>
		</form>
	</div>
</body>
</html>
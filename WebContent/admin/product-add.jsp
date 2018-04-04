<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script type="text/javascript">
function calMemberPrice() {
	var normalprice=document.getElementById("normalprice").value;
	var memberprice=normalprice*0.8;
	document.getElementById("memberprice").value=memberprice;
}
</script>
	<div class="x-body">
		<form action="productadd.do" method="post">
			<div class="layui-form-item">
				<label for="L_email" class="layui-form-label"> <span
					class="x-red">*</span>产品名称
				</label>
				<div class="layui-input-inline">
					<input type="text" id="L_email" name="productname" required=""
						lay-verify="aname" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>产品描述
				</label>
				<div class="layui-input-inline">
					<input type="text" id="L_pass" name="producrdescr" required=""
						lay-verify="pass" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>普通价格
				</label>
				<div class="layui-input-inline">
					<input type="text" id="normalprice" name="normalprice" required=""
						lay-verify="pass" autocomplete="off" class="layui-input" onblur="calMemberPrice()">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>会员价格
				</label>
				<div class="layui-input-inline">
					<input type="text" id="memberprice" name="memberprice" required=""
						lay-verify="pass" autocomplete="off" class="layui-input" disabled="disabled">
				</div>
			</div>
			<div class="layui-input-inline">
			<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>产品类别
				</label>
				<select name="fid">
					<c:forEach var="category" items="${categories}">
						<option value="${category.id}">${category.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="layui-form-item">
                    <label for="L_repass" class="layui-form-label">
                    </label>
                    <button  class="layui-btn" lay-filter="add" lay-submit="">  增加
                    </button>
                </div>

		</form>
	</div>
</body>
</html>
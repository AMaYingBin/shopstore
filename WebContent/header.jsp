<%@page import="com.neuedu.pojo.Category"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.neuedu.dao.impl.CategoryDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!--抬头开始-->

	<div class="top">

		<div class="top1">
			<div class="top1_main">
				<span class="dl">您好，欢迎光临本亲生活网！<a href="login.jsp"> [请登录]</a>
					<a href="register.jsp">[免费注册]</a></span><span class="yh_zx"><a
					href="hyzx.html">用户中心</a> | <a href="#">我的订单</a> | <a href="#">帮助中心</a>
					| <a href="#">联系方式</a></span>
			</div>
		</div>


		<div class="top_logo">

			<div class="logo">
				<a href="index.html"><img src="images/logo.jpg" width="338"
					height="113" / alt="本亲生活的logo标志"></a>
			</div>
			<div class="top_you">

				<div class="ss_1">
					<input name="key" type="text" id="key" value="请输入您要搜索的产品" size="30"
						onclick="if(value==defaultValue){value='';this.style.color='#898b8c'}"
						onBlur="if(!value){value=defaultValue;this.style.color='#999'}"
						style="color: #999; line-height: 26px;" / class="ssk1"><input
						name="" value="搜 索" type="button" / class="button1">
				</div>

			</div>

		</div>

	</div>

	<!--抬头结束-->


	<!--导航开始-->


	<div class="headNav">
		<div class="navCon w1020">
			<div class="navCon-cate fl navCon_on">
				<div class="navCon-cate-title">
					<a href="#">全部商品分类</a>
				</div>
				<div class="cateMenu hide">
					<ul>
						<% 
						CategoryDAOImpl categoryDAOImpl=new CategoryDAOImpl();
						List<Category> cs1=categoryDAOImpl .findByPid(0);
						for(Category c1:cs1){
							
						%>
						<li style="border-top: none;">
							<div class="cate-tag">
								<strong><a href="#"><%=c1.getName() %></a></strong>
								<div class="listModel">
								<% 
						List<Category> cs2=categoryDAOImpl .findByPid(c1.getId());
						for(Category c2:cs2){
							
						%>
								
									<p>
										<a href="#"><%=c2.getName() %></a>
									</p>

								
								<%} %>
								</div>
							</div>
							
							<div class="list-item hide">
								<ul class="itemleft">
										<% 
								List<Category> cs3=categoryDAOImpl .findByPid(c1.getId());
								for(Category c3:cs3){ %>
									<dl>
										<dd>
											<a href="#"><%=c3.getName() %></a>
										</dd>
									</dl>
									<%} %>
								</ul>

							</div>
						</li>
					</ul>
					<%} %>
				</div>
			</div>
			<div class="navCon-menu fl">
				<ul>
					<li><a class="curMenu" href="index.action">商城首页</a></li>
					<li><a href="tplist.html">特色产品</a></li>
					<li><a href="tplist.html">VIP专区</a></li>
					<li><a href="#">服务支持</a></li>
					<li><a href="newslist.html">相关资讯</a></li>
					<li><a href="about.html">关于本亲</a></li>
				</ul>
			</div>
		</div>
	</div>

	<!--导航结束-->

</body>
</html>
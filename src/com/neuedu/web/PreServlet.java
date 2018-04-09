package com.neuedu.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.neuedu.dao.impl.CartItemDAOImpl;
import com.neuedu.dao.impl.CategoryDAOImpl;
import com.neuedu.dao.impl.ProductDAOImpl;
import com.neuedu.dao.impl.UserDAOImpl;
import com.neuedu.pojo.Admin;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.CartItem;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.User;
import com.neuedu.util.ServletUtil;

@WebServlet({ "*.action" })
public class PreServlet extends HttpServlet {
	private static final long serialVersionUID = 8296668174970787910L;

	public PreServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDAOImpl userdao=new UserDAOImpl();
		ProductDAOImpl productdao=new ProductDAOImpl();
		ServletUtil.setEcording(request, response);
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
		System.out.println(path);
		if ("/register".equals(path)) {
			String name = request.getParameter("username");
			String password = request.getParameter("pwd");
			String phone=request.getParameter("phone");
			String addr=request.getParameter("addr");
			User user=new User(null, name, password, phone, addr, null);
			userdao.add(user);
			response.sendRedirect("login.jsp");
		}else if("/login".equals(path)) {
			String name=request.getParameter("username");
			String password=request.getParameter("password");
			String code=request.getParameter("code");
			HttpSession session=request.getSession();
			String sessioncode=(String) session.getAttribute("code");
			User u=userdao.findByUsername(name);
			if(code.equals(sessioncode)) {
				if(u!=null) {
					if(ServletUtil.MD5(password).equalsIgnoreCase(u.getPassword())) {
						response.sendRedirect("index.jsp");
					}else {
						request.setAttribute("error_message", "用户名或者密码错误");
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
			}else {
				request.setAttribute("error_message", "用户名或者密码错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			}else {
				request.setAttribute("code_error_msg", "验证码错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}else if("/index".equals(path)) {
			List<Product> ps1 =productdao.lastProduct(1);
			List<Product> ps2 =productdao.priceProduct(1);
			request.setAttribute("ps1", ps1);
			request.setAttribute("ps2", ps2);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}else if("/productload".equals(path)) {
			Integer id=Integer.parseInt(request.getParameter("id"));
			Product p=productdao.findById(id);
			request.setAttribute("product", p);
			request.getRequestDispatcher("product.jsp").forward(request, response);
		}else if("/productaddtocart".equals(path)) {
			Integer id=Integer.parseInt(request.getParameter("id"));
			Integer pcount=Integer.parseInt(request.getParameter("pcount"));
			System.out.println(id+"----"+pcount);
			System.out.println("aa");
			Product p=productdao.findById(id);
			CartItem ci=new CartItem();
			ci.setId(1);
			ci.setOrderid(0);
			ci.setPcount(pcount);
			ci.setProduct(p);
			HttpSession session=request.getSession();
			Cart cart=(Cart) session.getAttribute("cart");
			if(cart==null) {
				 cart=new Cart();
				session.setAttribute("cart", cart);
			}
			cart.getItems().add(ci);
			PrintWriter out=response.getWriter();
			out.println(cart);
			CartItemDAOImpl cartItemDAOImpl=new CartItemDAOImpl();
			cartItemDAOImpl.add(ci);
		}
	}

}

package com.neuedu.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.dao.impl.AdminDAOImpl;
import com.neuedu.dao.impl.CategoryDAOImpl;
import com.neuedu.dao.impl.ProductDAOImpl;
import com.neuedu.dao.impl.UserDAOImpl;
import com.neuedu.pojo.Admin;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.User;
import com.neuedu.util.ServletUtil;

@WebServlet(urlPatterns = "*.do")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = -5518093809897078911L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtil.setEcording(req, resp);
		AdminDAOImpl admindao = new AdminDAOImpl();
		CategoryDAOImpl categorydao = new CategoryDAOImpl();
		UserDAOImpl userdao = new UserDAOImpl();
		ProductDAOImpl productdao=new ProductDAOImpl();
		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
System.out.println(path);		
		if ("/userlist".equals(path)) {
			List<User> users = userdao.findAll();
			req.setAttribute("users", users);
			req.getRequestDispatcher("member-list.jsp").forward(req, resp);
		} else if ("/userdelete".equals(path)) {
			Integer id = Integer.parseInt(req.getParameter("id"));
			userdao.delete(id);
			resp.sendRedirect("userlist.do");
		} else if ("/categorylist".equals(path)) {
			List<Category> categories = categorydao.findToTree();
			for (Category category : categories) {
				String str = "";
				for (int i = 0; i < category.getGrade() - 1; i++) {
					str += "----";
					category.setName(str + category.getName());
				}
			}
			req.setAttribute("categories", categories);
			req.getRequestDispatcher("categories.jsp").forward(req, resp);
		} else if ("/categoryadd".equals(path)) {

			Integer id = Integer.parseInt(req.getParameter("fid"));
			String name = req.getParameter("categoryname");
			if (id == 0) {
				categorydao.add(name, name);
			} else {
				categorydao.add(name, name, id);
			}
			List<Category> categories = categorydao.FindAll();
			req.getRequestDispatcher("categorylist.do").forward(req, resp);

		} else if ("/categorydelete".equals(path)) {
			Integer id = Integer.parseInt(req.getParameter("id"));
			Integer pid = Integer.parseInt(req.getParameter("pid"));
			categorydao.delete(id, pid);
			resp.sendRedirect("categorylist.do");

		} else if ("/adminlist".equals(path)) {
			admindao = new AdminDAOImpl();
			List<Admin> admins = admindao.findAll();
			req.setAttribute("admins", admins);
			req.getRequestDispatcher("adminlist.jsp").forward(req, resp);
		} else if ("/adminadd".equals(path)) {
			String name = req.getParameter("aname");
			String password = req.getParameter("apwd");
			Admin admin = new Admin(name, password);
			admindao.add(admin);
			resp.sendRedirect("adminlist.do");
		} else if ("/admindelete".equals(path)) {
			Integer id = Integer.parseInt(req.getParameter("id"));
			admindao.delete(id);
			resp.sendRedirect("adminlist.do");
		} else if ("/adminload".equals(path)) {
			// 1.获取要修改的管理员ID
			Integer id = Integer.parseInt(req.getParameter("id"));
			// 2.进行查询管理员ID的信息
			Admin admin = admindao.findById(id);
			req.setAttribute("admin", admin);
			req.getRequestDispatcher("member-password.jsp").forward(req, resp);

		} else if ("/adminupdate".equals(path)) {
			// 1.获取要修改的管理员ID
			Integer id = Integer.parseInt(req.getParameter("id"));
			// 2.进行查询管理员ID的信息
			Admin admin = admindao.findById(id);
			String oldpwd = req.getParameter("oldpwd");
			String newpwd = req.getParameter("newpwd");
			String repwd = req.getParameter("repwd");
			if (admin.getApwd().equals(oldpwd)) {
				if (newpwd.equals(repwd)) {
					admin.setApwd(newpwd);
					admindao.update(admin);
					resp.sendRedirect("adminlist.do");
				} else {
					System.out.println("两次密码不一致");
					req.setAttribute("admin", admin);
					req.setAttribute("error_message2", "两次密码不一致");
					req.getRequestDispatcher("member-password.jsp").forward(req, resp);
				}
			} else {
				System.out.println("原始密码不正确");
				req.setAttribute("admin", admin);
				req.setAttribute("error_message1", "原始密码不正确");
				req.getRequestDispatcher("member-password.jsp").forward(req, resp);
			}
		} else if ("/productlist".equals(path)) {
			List<Product> products = productdao.findAll();
			req.setAttribute("products", products);
			req.getRequestDispatcher("product-list.jsp").forward(req, resp);
		}else if ("/productload".equals(path)) {
			List<Category> categories= categorydao.FindAll();
			req.setAttribute("categories", categories);
			req.getRequestDispatcher("product-add.jsp").forward(req, resp);
		}else if ("/productadd".equals(path)) {
			String name=req.getParameter("productname");
			String descr=req.getParameter("productdescr");
			double normalprice=Double.valueOf(req.getParameter("normalprice"));
			Integer categoryid=Integer.parseInt(req.getParameter("fid"));
			Category category=categorydao.findById(categoryid);
			Product p=new Product(name, descr, normalprice, null, category);
			productdao.add(p);
			resp.sendRedirect("productlist.do");
		}else if ("/productsimplsearch".equals(path)) {
			String keywords=req.getParameter("keywords");
			List<Product> products= productdao.simplsearch(keywords);
			if(products.size()==0) {
				req.setAttribute("nothing", "没有查询结果");
				req.getRequestDispatcher("product-list.jsp").forward(req, resp);
			}else {
				req.setAttribute("products", products);
				req.getRequestDispatcher("product-list.jsp").forward(req, resp);
			}
		}else if ("/usersimplsearch".equals(path)) {
			String keywords=req.getParameter("keywords");
			List<Product> products= productdao.simplsearch(keywords);
			if(products.size()==0) {
				req.setAttribute("nothing", "没有查询结果");
				req.getRequestDispatcher("product-list.jsp").forward(req, resp);
			}else {
				req.setAttribute("products", products);
				req.getRequestDispatcher("product-list.jsp").forward(req, resp);
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}

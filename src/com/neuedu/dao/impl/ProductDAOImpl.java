package com.neuedu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.dao.ProductDAO;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.User;
import com.neuedu.util.DBUtil;

public class ProductDAOImpl implements ProductDAO {
	Connection conn = null;

	@Override
	public List<Product> findAll() {
		conn = DBUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Product> products = null;
		try {
			stmt = DBUtil.getStatement(conn);
			rs = DBUtil.getResultset(stmt,
					"select p.id,p.name,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade from t_product p join t_category c on p.categoryid=c.id");
			products = new ArrayList<>();
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("p.id"));
				p.setName(rs.getString("p.name"));
				p.setDescr(rs.getString("p.descr"));
				p.setNormalprice(rs.getDouble("normalprice"));
				p.setMemberprice(rs.getDouble("memberprice"));
				p.setPdate(rs.getTimestamp("pdate"));
				Category category = new Category();
				category.setId(rs.getInt("c.id"));
				category.setName(rs.getString("c.name"));
				category.setDescr(rs.getString("c.descr"));
				category.setPid(rs.getInt("pid"));
				category.setLeaf(rs.getBoolean("leaf"));
				category.setGrade(rs.getInt("grade"));
				p.setCategory(category);
				products.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, stmt, rs);
		}

		return products;
	}

	@Override
	public void add(Product p) {
		conn = DBUtil.getConnection();
		String sql = "insert into t_product values(null,?,?,?,?,?,?)";

		try {
			boolean autocommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			PreparedStatement prep = DBUtil.getPreparedStatement(conn, sql);
			prep.setString(1, p.getName());
			prep.setString(2, p.getDescr());
			prep.setDouble(3, p.getNormalprice());
			prep.setDouble(4, p.getMemberprice());
			prep.setTimestamp(5, p.getPdate());
			prep.setDouble(6, p.getCategory().getId());
			prep.executeUpdate();
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			e.printStackTrace();
		}
	}

	public void add(Connection conn, Product p) {
		String sql = "insert into t_product values(null,?,?,?,?,?,?)";

		try {
			boolean autocommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			PreparedStatement prep = DBUtil.getPreparedStatement(conn, sql);
			prep.setString(1, p.getName());
			prep.setString(2, p.getDescr());
			prep.setDouble(3, p.getNormalprice());
			prep.setDouble(4, p.getMemberprice());
			prep.setTimestamp(5, p.getPdate());
			prep.setDouble(6, p.getCategory().getId());
			prep.executeUpdate();
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			e.printStackTrace();
		}
	}

	@Override
	public Product findById(Integer id) {
		return null;
	}

	@Override
	public void delete(Integer id) {

	}

	@Override
	public void update(Product product) {

	}

	public List<Product> simplsearch(String keywords) {
		conn = DBUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Product> products = null;
		try {
			stmt = DBUtil.getStatement(conn);
			rs = DBUtil.getResultset(stmt,
					"select p.id,p.name,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade from "
							+ "t_product p join t_category c on" + " p.categoryid=c.id where p.name like '%" + keywords
							+ "%'");
			products = new ArrayList<>();
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("p.id"));
				p.setName(rs.getString("p.name"));
				p.setDescr(rs.getString("p.descr"));
				p.setNormalprice(rs.getDouble("normalprice"));
				p.setMemberprice(rs.getDouble("memberprice"));
				p.setPdate(rs.getTimestamp("pdate"));
				Category category = new Category();
				category.setId(rs.getInt("c.id"));
				category.setName(rs.getString("c.name"));
				category.setDescr(rs.getString("c.descr"));
				category.setPid(rs.getInt("pid"));
				category.setLeaf(rs.getBoolean("leaf"));
				category.setGrade(rs.getInt("grade"));
				p.setCategory(category);
				products.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, stmt, rs);
		}

		return products;
	}

	public List<Product> lastProduct(int pageNo) {
		conn = DBUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Product> products = null;
		try {
			stmt = DBUtil.getStatement(conn);
			rs = DBUtil.getResultset(stmt,
					"select p.id,p.name,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade from "
							+ "t_product p join t_category c on" + " p.categoryid=c.id order by p.pdate desc "
									+ "limit "+(pageNo-1)*5+",5");
			products = new ArrayList<>();	
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("p.id"));
				p.setName(rs.getString("p.name"));
				p.setDescr(rs.getString("p.descr"));
				p.setNormalprice(rs.getDouble("normalprice"));
				p.setMemberprice(rs.getDouble("memberprice"));
				p.setPdate(rs.getTimestamp("pdate"));
				Category category = new Category();
				category.setId(rs.getInt("c.id"));
				category.setName(rs.getString("c.name"));
				category.setDescr(rs.getString("c.descr"));
				category.setPid(rs.getInt("pid"));
				category.setLeaf(rs.getBoolean("leaf"));
				category.setGrade(rs.getInt("grade"));
				p.setCategory(category);
				products.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, stmt, rs);
		}

		return products;
	}
	public List<Product> priceProduct(int pageNo) {
		conn = DBUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Product> products = null;
		try {
			stmt = DBUtil.getStatement(conn);
			rs = DBUtil.getResultset(stmt,
					"select p.id,p.name,p.descr,p.normalprice,p.memberprice,p.pdate,p.categoryid,c.id,c.`name`,c.descr,c.pid,c.leaf,c.grade from "
							+ "t_product p join t_category c on" + " p.categoryid=c.id order by p.normalprice "
									+ "limit "+(pageNo-1)*5+",5");
			products = new ArrayList<>();	
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("p.id"));
				p.setName(rs.getString("p.name"));
				p.setDescr(rs.getString("p.descr"));
				p.setNormalprice(rs.getDouble("normalprice"));
				p.setMemberprice(rs.getDouble("memberprice"));
				p.setPdate(rs.getTimestamp("pdate"));
				Category category = new Category();
				category.setId(rs.getInt("c.id"));
				category.setName(rs.getString("c.name"));
				category.setDescr(rs.getString("c.descr"));
				category.setPid(rs.getInt("pid"));
				category.setLeaf(rs.getBoolean("leaf"));
				category.setGrade(rs.getInt("grade"));
				p.setCategory(category);
				products.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, stmt, rs);
		}

		return products;
	}
	
}

package com.neuedu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.dao.CategoryDAO;
import com.neuedu.pojo.Category;
import com.neuedu.util.DBUtil;

public class CategoryDAOImpl implements CategoryDAO {
	Connection conn = null;

	@Override
	public List<Category> FindAll() {
		conn = DBUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Category> categorys = null;
		stmt = DBUtil.getStatement(conn);
		String sql = "select * from t_category where leaf=1";
		rs = DBUtil.getResultset(stmt, sql);
		categorys = new ArrayList<>();
		try {
			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				category.setDescr(rs.getString("descr"));
				category.setPid(rs.getInt("pid"));
				category.setLeaf(rs.getBoolean("leaf"));
				category.setGrade(rs.getInt("grade"));
				categorys.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorys;
	}

	// @Override
	public void add(Category category) {
		conn = DBUtil.getConnection();
		String sql = "insert into t_category values(null,?,?,?,?,?)";

		try {
			boolean autocommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			PreparedStatement prep = DBUtil.getPreparedStatement(conn, sql);
			prep.setString(1, category.getName());
			prep.setString(2, category.getDescr());
			prep.setInt(3, category.getPid());
			prep.setInt(4, category.isLeaf() ? 1 : 0);
			prep.setInt(5, category.getGrade());
			prep.executeUpdate();
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			e.printStackTrace();
		}
	}


	public void delete(Connection conn,Integer id) {
		Statement stmt=null;
		ResultSet rSelectChildren=null;
		String sql = "delete from t_category where id=" + id;
		String sqlselect = "select * from t_category where pid=" + id;// 找孩子
		try {
			boolean autocommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt=DBUtil.getStatement(conn);
			rSelectChildren=DBUtil.getResultset(stmt, sqlselect);
			while(rSelectChildren.next()) {
				delete(conn,rSelectChildren.getInt("id"));
			}
			
			//删除自己
			stmt.executeUpdate(sql);
			conn.commit();
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			e.printStackTrace();
		}
	}
	@Override
    public void delete(Integer id,Integer pid) {
    	conn=DBUtil.getConnection();
    	ResultSet rsselectbrother=null;
    	Statement stmt=null;
    	String sqlselectbrother="select count(*) from t_category where pid="+pid;//找兄弟节点
    	String sqlUpdate="update t_category set leaf=1 where id="+pid;
    	//先更新
    	
    	//兄弟数
    	try {
    		boolean autoCommit=conn.getAutoCommit();
    		conn.setAutoCommit(false);
    		stmt=DBUtil.getStatement(conn);
    		rsselectbrother=DBUtil.getResultset(stmt, sqlselectbrother);
    		rsselectbrother.next();
    		//先删除
    		delete(conn,id);
			int brothers=rsselectbrother.getInt("count(*)");
			if(brothers<=0) {
				stmt.executeUpdate(sqlUpdate);
			}
				
			conn.commit();
			conn.setAutoCommit(true);
			
			
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			e.printStackTrace();
		}
    }
	@Override
	public void update(Category c) {
		conn = DBUtil.getConnection();
		String sql = "update t_category set name=?,descr=? where id=?";
		PreparedStatement prep = DBUtil.getPreparedStatement(conn, sql);
		try {
			boolean autocommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			prep.setString(1, c.getName());
			prep.setString(2, c.getDescr());
			prep.setInt(3, c.getId());
			prep.executeUpdate();
			conn.commit();
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, prep);
		}
	}

	@Override
	public Category findById(Integer id) {
		conn = DBUtil.getConnection();
		Category c = null;
		try {
			boolean autocommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			Statement stmt = DBUtil.getStatement(conn);
			String sql = "select * from t_category where id=" + id;
			ResultSet rs = DBUtil.getResultset(stmt, sql);

			while (rs.next()) {
				c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDescr(rs.getString("descr"));
				c.setPid(rs.getInt("pid"));
				c.setLeaf(rs.getBoolean("leaf"));
				c.setGrade(rs.getInt("grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * 添加跟类别
	 */
	@Override
	public void add(String name, String descr) {
		Category c = new Category(name, descr, 0, true, 1);
		add(c);
	}

	/**
	 * 添加子类别
	 */
	public void add(String name, String descr, Integer pid) {
		conn = DBUtil.getConnection();
		Statement pselectstmt = null;
		Statement updatestmt = null;
		ResultSet prs = null;
		PreparedStatement prep;
		int rgrade = 0;
		try {
			// 1查询到父类
			String sql = "select * from t_category where id=" + pid;
			pselectstmt = DBUtil.getStatement(conn);
			prs = DBUtil.getResultset(pselectstmt, sql);
			while (prs.next()) {
				rgrade = prs.getInt("grade");
			}
			// 2添加子类别
			String addsql = "insert into t_category values(null,?,?,?,?,?)";
			prep = DBUtil.getPreparedStatement(conn, addsql);
			prep.setString(1, name);
			prep.setString(2, descr);
			prep.setInt(3, pid);
			prep.setInt(4, 1);
			prep.setInt(5, rgrade + 1);
			prep.executeUpdate();
			// 3更新父类
			String upsql = "update t_category set leaf=0 where id=" + pid;
			updatestmt = DBUtil.getStatement(conn);
			updatestmt.executeUpdate(upsql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Category> findToTree() {
		List<Category> cs = new ArrayList<>();
		findToTree(cs, 0);
		return cs;
	}

	private void findToTree(List<Category> cs, int pid) {
		Connection conn = DBUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DBUtil.getStatement(conn);
			String sql = "select * from t_category where pid=" + pid;
			rs = DBUtil.getResultset(stmt, sql);
			while (rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDescr(rs.getString("descr"));
				c.setPid(rs.getInt("pid"));
				c.setLeaf(rs.getBoolean("leaf"));
				c.setGrade(rs.getInt("grade"));
				cs.add(c);

				if (!c.isLeaf()) {
					findToTree(cs, rs.getInt("id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

     public List<Category> findByPid(Integer id){
    	 conn = DBUtil.getConnection();
 		List<Category> categories=new ArrayList<>();
 		try {
 			boolean autocommit = conn.getAutoCommit();
 			conn.setAutoCommit(false);
 			Statement stmt = DBUtil.getStatement(conn);
 			String sql = "select * from t_category where pid=" + id;
 			ResultSet rs = DBUtil.getResultset(stmt, sql);

 			while (rs.next()) {
 				Category c = new Category();
 				c.setId(rs.getInt("id"));
 				c.setName(rs.getString("name"));
 				c.setDescr(rs.getString("descr"));
 				c.setPid(rs.getInt("pid"));
 				c.setLeaf(rs.getBoolean("leaf"));
 				c.setGrade(rs.getInt("grade"));
 				categories.add(c);
 			}
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 		return categories;
	}
}

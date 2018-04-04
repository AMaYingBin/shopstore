package com.neuedu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.dao.AdminDAO;
import com.neuedu.pojo.Admin;
import com.neuedu.util.DBUtil;



public class AdminDAOImpl implements AdminDAO{
	Connection conn=null;
	
	@Override
	public List<Admin> findAll() {
		conn=DBUtil.getConnection();
		Statement stmt=null;
		ResultSet rs=null;
		List<Admin> admins=null;
		stmt=DBUtil.getStatement(conn);
		String sql="select * from t_admin";
		rs=DBUtil.getResultset(stmt, sql);
		admins=new ArrayList<>();
		try {
			while(rs.next()) {
				Admin admin=new Admin();
				admin.setId(rs.getInt("id"));
				admin.setAname(rs.getString("aname"));
				admin.setApwd(rs.getString("apwd"));
				admins.add(admin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admins;
	}

	@Override
	public void add(Admin admin) {
		conn=DBUtil.getConnection();
		String sql="insert into t_admin values(null,?,?)";
		PreparedStatement stmt=DBUtil.getPreparedStatement(conn, sql);
		try {
			boolean autocommit=conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt.setString(1, admin.getAname());
			stmt.setString(2, admin.getApwd());
			stmt.executeUpdate();
			conn.commit();
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			e.printStackTrace();
		}finally {
			DBUtil.close(conn, stmt);
		}
	}

	@Override
	public void delete(Integer id) {
		conn=DBUtil.getConnection();
		String sql="delete from t_admin where id="+id;
		PreparedStatement prep=DBUtil.getPreparedStatement(conn, sql);
		try {
			boolean autocommit=conn.getAutoCommit();
			conn.setAutoCommit(false);
			prep.executeUpdate();
			conn.commit();
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			e.printStackTrace();
		}finally {
				DBUtil.close(conn, prep);
		}
	}

	@Override
	public void update(Admin admin) {
		conn=DBUtil.getConnection();
		String sql="update t_admin set aname=?,apwd=? where id=?";
		PreparedStatement prep=DBUtil.getPreparedStatement(conn, sql);
		try {
			boolean autocommit=conn.getAutoCommit();
			conn.setAutoCommit(false);
			prep.setString(1, admin.getAname());
			prep.setString(2, admin.getApwd());
			prep.setInt(3, admin.getId());
			prep.executeUpdate();
			conn.commit();
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			e.printStackTrace();
		}finally {
			DBUtil.close(conn, prep);
		}
		
	}

	@Override
	public Admin findById(Integer id) {
		conn=DBUtil.getConnection();
		String sql="select * from t_admin where id="+id;
		ResultSet rs=null;
		Statement stmt=DBUtil.getStatement(conn);
	    rs=DBUtil.getResultset(stmt, sql);
	    Admin admin=null;
	    try {
			while(rs.next()) {
				admin =new Admin();
				admin.setId(rs.getInt("id"));
				admin.setAname(rs.getString("aname"));
				admin.setApwd(rs.getString("apwd"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(conn, stmt,rs);
		}
		return admin;
	}

}

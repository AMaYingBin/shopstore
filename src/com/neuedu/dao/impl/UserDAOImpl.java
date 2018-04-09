package com.neuedu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.dao.UserDAO;
import com.neuedu.pojo.User;
import com.neuedu.util.DBUtil;


public class UserDAOImpl implements UserDAO{
	Connection conn=null;
	@Override
	public List<User> findAll() {
		conn=DBUtil.getConnection();
		Statement stmt=null;
		ResultSet rs=null;
		List<User> users=null;
		try {
			stmt=DBUtil.getStatement(conn);
			rs=DBUtil.getResultset(stmt, "select * from t_user");
			users=new ArrayList<>();
			
			while(rs.next()) {
				User user=new User();
			     user.setId(rs.getInt("id"));
			     user.setUsername(rs.getString("username"));
			     user.setPassword(rs.getString("password"));
			     user.setPhone(rs.getString("phone"));
			     user.setAddr(rs.getString("addr"));
			     user.setRdate(rs.getTimestamp("rdate"));
			     users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(conn, stmt, rs);
		}
		
		return users;
	}

	@Override
	public void add(User user) {
		conn=DBUtil.getConnection();
		PreparedStatement prep=null;
		String sql="insert into t_user values(null,?,md5(?),?,?,now())";
		prep=DBUtil.getPreparedStatement(conn, sql);
		try {
			prep.setString(1, user.getUsername());
			prep.setString(2, user.getPassword());
			prep.setString(3, user.getPhone());
			prep.setString(4, user.getAddr());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(conn, prep);
		}
	}

	@Override
	public void delete(Integer id) {
		conn=DBUtil.getConnection();
		PreparedStatement prep=null;
		String sql="delete from t_user where id="+id;
		prep=DBUtil.getPreparedStatement(conn, sql);
		try {
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(conn, prep);
		}
	}

	@Override
	public void update(User user) {
		conn=DBUtil.getConnection();
		PreparedStatement prep=null;
		String sql="update t_user set username=?,password=?,phone=?,addr=? where id=?";
		prep=DBUtil.getPreparedStatement(conn, sql);
		try {
			prep.setString(1, user.getUsername());
			prep.setString(2, user.getPassword());
			prep.setString(3, user.getPhone());
			prep.setString(4, user.getAddr());
			prep.setInt(5, user.getId());
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(conn, prep);
		}
	}
	public User findById(Integer id) {
		conn=DBUtil.getConnection();
		Statement stmt=null;
		ResultSet rs=null;
	    String sql="select * from t_user where id="+id;
	     stmt=DBUtil.getStatement(conn);
	    rs=DBUtil.getResultset(stmt, sql);
	     User user=null;
	try {
		while(rs.next()){
			user=new User();
			 user.setId(rs.getInt("id"));
		     user.setUsername(rs.getString("username"));
		     user.setPassword(rs.getString("password"));
		     user.setPhone(rs.getString("phone"));
		     user.setAddr(rs.getString("addr"));
		     user.setRdate(rs.getTimestamp("rdate"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		DBUtil.close(conn, stmt);
	}
		return user;
	}

	public User findByUsername(String name) {
		conn=DBUtil.getConnection();
		Statement stmt=null;
		ResultSet rs=null;
	    String sql="select * from t_user where username='"+name+"'";
	     User user=null;
	try {
		stmt=DBUtil.getStatement(conn);
		rs=DBUtil.getResultset(stmt, sql);
		if(rs.next()){
			user=new User();
			 user.setId(rs.getInt("id"));
		     user.setUsername(rs.getString("username"));
		     user.setPassword(rs.getString("password"));
		     user.setPhone(rs.getString("phone"));
		     user.setAddr(rs.getString("addr"));
		     user.setRdate(rs.getTimestamp("rdate"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		DBUtil.close(conn, stmt);
	}
		return user;
	}
	
	
}

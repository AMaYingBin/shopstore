package com.neuedu.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;



public class DBUtil {
	public static Properties prop=new Properties();
	static {
		try {
			prop.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String DRIVER_NAME=prop.getProperty("driver_name");
	public static String URL=prop.getProperty("url");
	public static String USERNAME=prop.getProperty("username");
	public static String PASSWORD=prop.getProperty("password");
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER_NAME);
			conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Statement getStatement(Connection conn) {
		Statement stmt=null;
		try {
			stmt=conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
	public static PreparedStatement getPreparedStatement(Connection conn,String sql) {
		PreparedStatement prep=null;
		try {
			prep=conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prep;
	}
	public static int executrUpdate(Statement stmt,String sql) {
		int recorcount=0;
		try {
			recorcount = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recorcount;
		
	}
	public static ResultSet getResultset(Statement stmt,String sql) {
		ResultSet rs=null;
		try {
			rs=stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void close(Connection conn,Statement stmt,ResultSet rs) {
			try {
				if(conn!=null) {
				conn.close();
				conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
				try {
					if(stmt!=null) {
					stmt.close();
					stmt=null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
					try {
						if(rs!=null) {
						rs.close();
						rs=null;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
	}
	public static void close(Connection conn,Statement stmt) {
		try {
			if(conn!=null) {
			conn.close();
			conn=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			try {
				if(stmt!=null) {
				stmt.close();
				stmt=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	/**
	 * »Ø¹ö
	 */

	public static void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

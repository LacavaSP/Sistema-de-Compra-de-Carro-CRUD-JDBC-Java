package com.ZZCjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class ConexaoFactory {

public static Connection getConexao() {
	String url = "jdbc:mysql://localhost:3306/agencia";
	String user = "root";
	String password = "261203";
	try {
		return DriverManager.getConnection(url,user,password);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
public static JdbcRowSet getRowSetConnection() {
	String url = "jdbc:mysql://localhost:3306/agencia";
	String user = "root";
	String password = "261203";
	try {
		JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet();
		jdbc.setUrl(url);
		jdbc.setUsername(user);
		jdbc.setPassword(password);
		return jdbc;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
	public static  void fecharConexao(Connection connection) {
		  
		try {
			if(connection != null) {
			connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static CachedRowSet getRowSetConnectionCached() {
		String url = "jdbc:mysql://localhost:3306/agencia?useSSL=false&relaxAutoCommit=true";
		String user = "root";
		String password = "261203";
		try {
			CachedRowSet jdbc = RowSetProvider.newFactory().createCachedRowSet();
			jdbc.setUrl(url);
			jdbc.setUsername(user);
			jdbc.setPassword(password);
			return jdbc;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static  void fecharConexao(JdbcRowSet connection) {
		  
		try {
			if(connection != null) {
			connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static  void fecharConexao(Connection connection, Statement stmt) {
		  fecharConexao(connection);
		try {
			if(stmt != null) {
			stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static  void fecharConexao(Connection connection, Statement stmt, ResultSet rs) {
		  fecharConexao(connection,stmt);
		try {
			if(rs != null) {
			rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

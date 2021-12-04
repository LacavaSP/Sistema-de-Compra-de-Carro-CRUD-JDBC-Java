package com.variaveisSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VarSQL {

	public static Connection getVariable() {
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
	
	public static void closeConnection(Connection connection) {
		  
		try {
			if(connection != null) {
			connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static  void closeConnection(Connection connection, Statement stmt) {
		closeConnection(connection);
		try {
			if(stmt != null) {
			stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static  void closeConnection(Connection connection, Statement stmt, ResultSet rs) {
		closeConnection(connection,stmt);
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

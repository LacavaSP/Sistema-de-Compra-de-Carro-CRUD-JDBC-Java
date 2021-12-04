package com.variaveisSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.db.compradorDB;

public class VarClass {

	public VarClass(){}

	public static void resetTable() throws SQLException {
		String	create = "UPDATE `agencia`.`variaveis` SET `var1` = '0', `var2` = '0', `var3` = '0', `var4` = '0' WHERE (`id` = '1');";
		String sql = "SELECT id, var1, var2, var3, var4 from variaveis;";
		String ai = "ALTER TABLE variaveis AUTO_INCREMENT = 1;";
		Connection var = VarSQL.getVariable();
		Statement stmtv = var.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		
		stmtv.execute(sql);
		stmtv.executeUpdate(ai);
		
		stmtv.executeUpdate(create);
		compradorDB.deleteAllRows();
		VarSQL.closeConnection(var, stmtv);
	}
	
	public static void var1Manager(boolean rule) throws SQLException {

		String sql = "SELECT id, var1, var2, var3, var4 from variaveis;";
		
		Connection var = VarSQL.getVariable();
		Statement stmtv = var.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet sr = stmtv.executeQuery(sql);
		if(sr.next()) {
			sr.updateBoolean("var1", rule);
			sr.updateRow();
		}
		VarSQL.closeConnection(var, stmtv, sr);
	}

	public static void var2Manager(boolean rule) throws SQLException {

		String sql = "SELECT id, var1, var2, var3, var4 from variaveis;";
 
		Connection var = VarSQL.getVariable();
		Statement stmtv = var.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet sr = stmtv.executeQuery(sql);
		if(sr.next()) {
			sr.updateBoolean("var2", rule);
			sr.updateRow();
		}
		VarSQL.closeConnection(var, stmtv, sr);
	 	}
	
	public static void var3Manager(boolean rule) throws SQLException {

		String sql = "SELECT id, var1, var2, var3, var4 from variaveis;";

		Connection var = VarSQL.getVariable();
		Statement stmtv = var.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet sr = stmtv.executeQuery(sql);
		if(sr.next()) {
			sr.updateBoolean("var3", rule);
			sr.updateRow();
		}
		VarSQL.closeConnection(var, stmtv, sr);
	 
	}

	public static void var4Manager(boolean rule) throws SQLException {

		String sql = "SELECT id, var1, var2, var3, var4 from variaveis;";

		Connection var = VarSQL.getVariable();
		Statement stmtv = var.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet sr = stmtv.executeQuery(sql);
		if(sr.next()) {
			sr.updateBoolean("var4", rule);
			sr.updateRow();
		}
		VarSQL.closeConnection(var, stmtv, sr);
	}

	public static boolean var1State() throws SQLException {
		
		String sql = "SELECT id, var1, var2, var3, var4 from variaveis;";
		
		Connection var = VarSQL.getVariable();
		Statement stmtv = var.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet sr = stmtv.executeQuery(sql);
		
		boolean bool = false;
		if(sr.next()) {
			bool = sr.getBoolean("var1");
		}
		VarSQL.closeConnection(var, stmtv, sr);
		return bool;
		}

	public static boolean var2State() throws SQLException {
		
		String sql = "SELECT id, var1, var2, var3, var4 from variaveis;";
		
		Connection var = VarSQL.getVariable();
		Statement stmtv = var.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet sr = stmtv.executeQuery(sql);
		
		boolean bool = false;
		if(sr.next()) {
			bool = sr.getBoolean("var2");
		}
		VarSQL.closeConnection(var, stmtv, sr);
		return bool;
		}

	public static boolean var3State() throws SQLException {
			
			String sql = "SELECT id, var1, var2, var3, var4 from variaveis;";
			
			Connection var = VarSQL.getVariable();
			Statement stmtv = var.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet sr = stmtv.executeQuery(sql);
			
			boolean bool = false;
			if(sr.next()) {
				bool = sr.getBoolean("var3");
			}
			VarSQL.closeConnection(var, stmtv, sr);
			return bool;
			}
	
	public static boolean var4State() throws SQLException {
			
			String sql = "SELECT id, var1, var2, var3, var4 from variaveis;";
			
			Connection var = VarSQL.getVariable();
			Statement stmtv = var.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet sr = stmtv.executeQuery(sql);
			
			boolean bool = false;
			if(sr.next()) {
				bool = sr.getBoolean("var4");
			}
			VarSQL.closeConnection(var, stmtv, sr);
			return bool;
			}
	}




package db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;

import com.ZZCjdbc.ConexaoFactory;
import com.rowset.myRowSetListener;
import com.variaveisSQL.VarClass;

import classes.Comprador;
 

public class compradorDB {
static Scanner entrada = new Scanner(System.in);
	public static void save(Comprador comprador) {
		String sql = "INSERT INTO `agencia`.`comprador` (`CPF`, `nome`) VALUES (?, ?);";
	

		try(Connection conn = ConexaoFactory.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, comprador.getCpf());
			ps.setString(2, comprador.getNome());
			ps.execute();
			System.out.println("Registro inserido com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveTransaction() throws SQLException {
		String sql = "INSERT INTO `agencia`.`comprador` (`CPF`, `nome`) VALUES ('TESTE1', 'TESTE1');";
		String sql2 = "INSERT INTO `agencia`.`comprador` (`CPF`, `nome`) VALUES ('TESTE2', 'TESTE2');";
		String sql3 = "INSERT INTO `agencia`.`comprador` (`CPF`, `nome`) VALUES ('TESTE3', 'TESTE3');";
		
		Connection conn = ConexaoFactory.getConexao();
		Savepoint savepoint = null;
		try {
			Statement stmt = conn.createStatement();
			conn.setAutoCommit(false);
			stmt.executeUpdate(sql);
			savepoint = conn.setSavepoint("One");
			stmt.executeUpdate(sql2);
			conn.releaseSavepoint(savepoint);
			stmt.executeUpdate(sql3);
			conn.commit();
			conn.setAutoCommit(true);
			ConexaoFactory.fecharConexao(conn, stmt);
			System.out.println("Registro inserido com sucesso!");
		} catch (SQLException e) {
		 e.printStackTrace();
		conn.rollback(savepoint);
		conn.commit();
		}
	}


	public static void delete(Comprador comprador) {
		if(comprador == null || comprador.getId() == null) {
			System.out.println("Impossível excluir registro, ID ou comprador inexistentes!");
			return;
		}
		String sql = "DELETE FROM `agencia`.`comprador` WHERE (`id` = '" + comprador.getId()+ "');";
 
		try (Connection conn = ConexaoFactory.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, comprador.getId());
			ps.executeUpdate();
			System.out.println("Registro excluído com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void update(Comprador comprador) {
		if(comprador == null || comprador.getId() == null) {
			System.out.println("Impossível atualizar registro, ID ou comprador inexistentes!");
			return;
		}
		String sql = "UPDATE `agencia`.`comprador` SET `cpf`= ?, `nome`= ? WHERE  `id`= ? ";


		try(Connection conn = ConexaoFactory.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);) {

			ps.setString(1, comprador.getCpf());
			ps.setString(2, comprador.getNome());
			ps.setInt(3,comprador.getId());
			ps.executeUpdate();
			System.out.println("Registro atualizado com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateRowSetCached(Comprador comprador) {
		if(comprador == null || comprador.getId() == null) {
			System.out.println("Impossível atualizar registro, ID ou comprador inexistentes!");
			return;
		}
		String sql = "SELECT * FROM comprador WHERE  `id`= ? ";
		CachedRowSet crs = ConexaoFactory.getRowSetConnectionCached();

		try {
			crs.setCommand(sql);
			crs.setInt(1, comprador.getId());
			crs.execute();
			crs.next();
			crs.updateString("nome", comprador.getNome());
			crs.updateRow();
			crs.acceptChanges();
			System.out.println("Registro atualizado com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static List<Comprador> selectAll() {
		String sql = "SELECT id, nome, cpf  from comprador;";
		
		try(Connection conn = ConexaoFactory.getConexao();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs= ps.executeQuery();) {

			List<Comprador> compradorList = new ArrayList<>();
			while(rs.next()) {

				compradorList.add(new Comprador(rs.getInt("id"),rs.getString("nome"),rs.getString("cpf") ));

			}
 
			return compradorList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Comprador> searchByName(String nome) {
		String sql = "SELECT id, nome, cpf  from comprador where nome like ?";
 
		List<Comprador> compradorList = new ArrayList<>();
		try(Connection conn = ConexaoFactory.getConexao();
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, "%"+nome+"%");
			ResultSet rs= ps.executeQuery();
	
			while(rs.next()) {

				compradorList.add(new Comprador(rs.getInt("id"),rs.getString("nome"),rs.getString("cpf") ));

			}
			ConexaoFactory.fecharConexao(conn, ps, rs);
			return compradorList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Comprador> searchByNameCallableStatement(String nome) {
 
		String sql = "CALL `agencia`.`SP_GetCompradoresPorNome`( ? );";
		Connection conn = ConexaoFactory.getConexao();
		List<Comprador> compradorList = new ArrayList<>();
		try {
			CallableStatement cs = conn.prepareCall(sql);
			cs.setString(1, "%"+nome+"%");
			ResultSet rs= cs.executeQuery();
			while(rs.next()) {

				compradorList.add(new Comprador(rs.getInt("id"),rs.getString("nome"),rs.getString("cpf") ));

			}
			ConexaoFactory.fecharConexao(conn, cs, rs);
			return compradorList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Comprador> searchByNameRowSet(String nome) {
		 
		String sql = "SELECT id, nome, cpf  from comprador where nome like ?";
		JdbcRowSet jrs = ConexaoFactory.getRowSetConnection();
		List<Comprador> compradorList = new ArrayList<>();
		jrs.addRowSetListener(new myRowSetListener());
		try {
		jrs.setCommand(sql);
		jrs.setString(1, "%"+nome+"%");
		jrs.execute();
			while(jrs.next()) {

				compradorList.add(new Comprador(jrs.getInt("id"),jrs.getString("nome"),jrs.getString("cpf") ));

			}
			ConexaoFactory.fecharConexao(jrs);
			return compradorList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void selectMetaData() {
		String sql = "SELECT * FROM agencia.comprador";
		Connection conn = ConexaoFactory.getConexao();

		try {

			Statement stmt = conn.createStatement();

			ResultSet rs= stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			rs.next();
			int qtdColunas = rsmd.getColumnCount();

			System.out.println("Quantidade coluna: "+ qtdColunas);

			for (int i = 1; i<= qtdColunas; i++) {
				System.out.println("tabela: "+ rsmd.getTableName(i));
				System.out.println("Nome da coluna: " + rsmd.getColumnName(i));
				System.out.println("Tamanho da coluna: " + rsmd.getColumnDisplaySize(i));
			}
			ConexaoFactory.fecharConexao(conn, stmt, rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void checkDriverStatus() {
		Connection conn = ConexaoFactory.getConexao();

		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			if (dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)) {
				System.out.println("Suporta TYPE_FORWARD_ONLY");
				if(dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
					System.out.println("E também suporta CONCUR_UPDATABLE");
				}
			}
			if (dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)) {
				System.out.println("Suporta TYPE_SCROLL_INSENSITIVE)");
				if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
					System.out.println("E também suporta CONCUR_UPDATABLE");
				}
			}
			if (dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)) {
				System.out.println("Suporta TYPE_SCROLL_SENSITIVE)");
				if(dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
					System.out.println("E também suporta CONCUR_UPDATABLE");
				}
			}

			ConexaoFactory.fecharConexao(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void testTypeScroll() {
		String sql = "SELECT id, nome, cpf  from comprador;";
		Connection conn = ConexaoFactory.getConexao();
		try {
			Statement stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs= stmt.executeQuery(sql);
			if(rs.last()) {
				System.out.println(new Comprador(rs.getInt("id"),rs.getString("nome"),rs.getString("cpf")));
				System.out.println("Ultima linha: " + rs.getRow());
			}
			ConexaoFactory.fecharConexao(conn, stmt, rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static int rowsCount() throws SQLException  {

		//ARMAZENA O NÚMERO DE LINHAS DA TABELA


		Connection con = ConexaoFactory.getConexao();
		String sql = "SELECT id, nome, cpf  from comprador;";
		Statement s1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet rt= s1.executeQuery(sql);
		rt.last();
		int external = rt.getRow();
		ConexaoFactory.fecharConexao(con, s1, rt);
		//-------------------------------------------------------------------------------------------------
	
		return external;
	}

	public static void inserirWRS() {

		String sql = "SELECT id, nome, cpf  from comprador;";
		String rec = "INSERT INTO `agencia`.`comprador` (`id`, `nome`, `cpf`) VALUES ('1', 'nome', 'cpf');";
	 
		try {
			
			Connection conn = ConexaoFactory.getConexao();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs= stmt.executeQuery(sql);
			
			if(!VarClass.var1State()) {
			if(rs.next()) {
			if(rowsCount()<2) {	
			switch (rowsCount()) {
			case 0:
				stmt.executeUpdate(rec);
				ConexaoFactory.fecharConexao(conn, stmt, rs);
				break;
			case 1:
			
				System.out.println("----------------------------------------------");
				System.out.println("==>Insira o nome da pessoa a ser registrada<==");
				System.out.println("----------------------------------------------");
				rs.updateString("nome", entrada.next());
				System.out.println("---------------------------------------------");
				System.out.println("==>Insira o cpf da pessoa a ser registrada<==");
				System.out.println("---------------------------------------------");
				rs.updateString("cpf", entrada.next());
				rs.updateRow();
				VarClass.var1Manager(true);
				ConexaoFactory.fecharConexao(conn, stmt, rs);
				break;
			default:
				ConexaoFactory.fecharConexao(conn, stmt, rs);
				break;
			}
		}
	}
}  
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertRegister() throws SQLException {
		
		String sql = "SELECT id, nome, cpf  from comprador;";
		Connection conn = ConexaoFactory.getConexao();
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet rs= stmt.executeQuery(sql);
		rs.absolute(rowsCount());
		rs.moveToInsertRow();
		System.out.println("----------------------------------------------");
		System.out.println("==>Insira o nome da pessoa a ser registrada<==");
		System.out.println("----------------------------------------------");
		rs.updateString("nome", entrada.next());
		System.out.println("---------------------------------------------");
		System.out.println("==>Insira o cpf da pessoa a ser registrada<==");
		System.out.println("---------------------------------------------");
		rs.updateString("cpf", entrada.next());
		rs.insertRow();  
		rs.moveToCurrentRow();
		entrada.close();
		ConexaoFactory.fecharConexao(conn, stmt, rs);
	}
	
	public static void deleteRow() throws SQLException {
		String sql = "SELECT id, nome, cpf  from comprador;";
		Connection conn = ConexaoFactory.getConexao();
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet rs= stmt.executeQuery(sql);
		
 
		rs.absolute(rowsCount());
		rs.deleteRow();
		ConexaoFactory.fecharConexao(conn, stmt, rs);
	 
	}
	
	public static void deleteAllRows() throws SQLException{
		for(int i=0; i<= compradorDB.rowsCount();i++) {
			compradorDB.deleteRow();
			}
	}
}




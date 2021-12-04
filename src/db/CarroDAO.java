package db;

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

import com.ZZCjdbc.ConexaoFactory;
import com.variaveisSQL.VarClass;

import classes.Carro;
import classes.Comprador;
	 

	public class CarroDAO {
	static Scanner entrada = new Scanner(System.in);
		public static void save(Carro carro) {
			String sql = "INSERT INTO `agencia`.`carro` (`nome`, `placa`, compradorid) VALUES (?, ?, ?);";
		

			try(Connection conn = ConexaoFactory.getConexao();
				PreparedStatement ps = conn.prepareStatement(sql);) {
				ps.setString(1, carro.getNome());
				ps.setString(2, carro.getPlaca());
				ps.setInt(3,carro.getComprador().getId());
				ps.execute();
				System.out.println("Registro inserido com sucesso!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void saveTransaction() throws SQLException {
			String sql = "INSERT INTO `agencia`.`carro` (`CPF`, `nome`) VALUES ('TESTE1', 'TESTE1');";
			String sql2 = "INSERT INTO `agencia`.`carro` (`CPF`, `nome`) VALUES ('TESTE2', 'TESTE2');";
			String sql3 = "INSERT INTO `agencia`.`carro` (`CPF`, `nome`) VALUES ('TESTE3', 'TESTE3');";
			
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


		public static void delete(Carro carro) {
			if(carro == null || carro.getId() == null) {
				System.out.println("Impossível excluir registro, ID ou carro inexistentes!");
				return;
			}
			String sql = "DELETE FROM `agencia`.`carro` WHERE `id` = ?;";
	 
			try (Connection conn = ConexaoFactory.getConexao();
				PreparedStatement ps = conn.prepareStatement(sql);) {
				ps.setInt(1, carro.getId());
				ps.executeUpdate();
				System.out.println("Registro excluído com sucesso!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public static void update(Carro carro) {
			if(carro == null || carro.getId() == null) {
				System.out.println("Impossível atualizar registro, ID ou carro inexistentes!");
				return;
			}
			String sql = "UPDATE `agencia`.`carro` SET `placa`= ?, `nome`= ? WHERE  `id`= ? ";


			try(Connection conn = ConexaoFactory.getConexao();
				PreparedStatement ps = conn.prepareStatement(sql);) {

				ps.setString(1, carro.getPlaca());
				ps.setString(2, carro.getNome());
				ps.setInt(3,carro.getId());
				ps.executeUpdate();
				System.out.println("Registro atualizado com sucesso!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void updateRowSetCached(Carro carro) {
			if(carro == null || carro.getId() == null) {
				System.out.println("Impossível atualizar registro, ID ou carro inexistentes!");
				return;
			}
			String sql = "SELECT * FROM carro WHERE  `id`= ? ";
			CachedRowSet crs = ConexaoFactory.getRowSetConnectionCached();

			try {
				crs.setCommand(sql);
				crs.setInt(1, carro.getId());
				crs.execute();
				crs.next();
				crs.updateString("nome", carro.getNome());
				crs.updateRow();
				crs.acceptChanges();
				System.out.println("Registro atualizado com sucesso!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public static List<Carro> selectAll() {
			String sql = "SELECT id, nome, placa, compradorid FROM carro;";
			List<Carro> carroList = new ArrayList<>();
			try(Connection conn = ConexaoFactory.getConexao();
					PreparedStatement ps = conn.prepareStatement(sql);
					ResultSet rs= ps.executeQuery();) {

			
				while(rs.next()) {
					Comprador c = CompradorDAO.searchById(rs.getInt("compradorid"));
					carroList.add(new Carro(rs.getInt("id"),rs.getString("nome"),rs.getString("placa"),c));

				}
	 
				return carroList;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

		public static List<Carro> searchByName(String nome) {
			String sql = "SELECT id, nome, placa, compradorid FROM carro where nome like ?";
	 
			List<Carro> carroList = new ArrayList<>();
			try(Connection conn = ConexaoFactory.getConexao();
					PreparedStatement ps = conn.prepareStatement(sql);) {
				ps.setString(1, "%"+nome+"%");
				ResultSet rs= ps.executeQuery();
		
				while(rs.next()) {
					Comprador c = CompradorDAO.searchById(rs.getInt("compradorid"));
					carroList.add(new Carro(rs.getInt("id"),rs.getString("nome"),rs.getString("placa"), c ));

				}
				ConexaoFactory.fecharConexao(conn, ps, rs);
				return carroList;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}


		public static void selectMetaData() {
			String sql = "SELECT * FROM agencia.carro";
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

		public static int rowsCount() throws SQLException  {

			//ARMAZENA O NÚMERO DE LINHAS DA TABELA


			Connection con = ConexaoFactory.getConexao();
			String sql = "SELECT id, nome, cpf  from carro;";
			Statement s1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rt= s1.executeQuery(sql);
			rt.last();
			int external = rt.getRow();
			ConexaoFactory.fecharConexao(con, s1, rt);
			//-------------------------------------------------------------------------------------------------
		
			return external;
		}

		public static void inserirWRS() {

			String sql = "SELECT id, nome, cpf  from carro;";
			String rec = "INSERT INTO `agencia`.`carro` (`id`, `nome`, `cpf`) VALUES ('1', 'nome', 'cpf');";
		 
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
			
			String sql = "SELECT id, nome, cpf  from carro;";
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
			String sql = "SELECT id, nome, cpf  from carro;";
			Connection conn = ConexaoFactory.getConexao();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs= stmt.executeQuery(sql);
			
	 
			rs.absolute(rowsCount());
			rs.deleteRow();
			ConexaoFactory.fecharConexao(conn, stmt, rs);
		 
		}
		
		public static void deleteAllRows() throws SQLException{
			for(int i=0; i<= CarroDAO.rowsCount();i++) {
				CarroDAO.deleteRow();
				}
		}
	}






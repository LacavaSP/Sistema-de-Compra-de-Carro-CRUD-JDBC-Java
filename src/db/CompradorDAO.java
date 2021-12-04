package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ZZCjdbc.ConexaoFactory;

import classes.Comprador;

public class CompradorDAO {

	public static void update(Comprador comprador) {
		if(comprador == null || comprador.getId() == null) {
			System.out.println("Impossível atualizar registro, ID ou comprador inexistentes!");
			return;
		}
		String sql = "UPDATE `agencia`.`comprador` SET `cpf`= ?, `nome`= ? WHERE  `id`= ? ";
		Connection conn = ConexaoFactory.getConexao();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, comprador.getCpf());
			ps.setString(2, comprador.getNome());
			ps.setInt(3,comprador.getId());
			ps.executeUpdate();
			ConexaoFactory.fecharConexao(conn, ps);
			System.out.println("Registro atualizado com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void delete(Comprador comprador) {
		if(comprador == null || comprador.getId() == null) {
			System.out.println("Impossível excluir registro, ID ou comprador inexistentes!");
			return;
		}
		String sql = "DELETE FROM `agencia`.`comprador` WHERE (`id` = '" + comprador.getId()+ "');";
		Connection conn = ConexaoFactory.getConexao();

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			ConexaoFactory.fecharConexao(conn, stmt);
			System.out.println("Registro excluído com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void save(Comprador comprador) {
		String sql = "INSERT INTO `agencia`.`comprador` (`CPF`, `nome`) VALUES ('" + comprador.getCpf()+ "', '" + comprador.getNome()+"');";
		Connection conn = ConexaoFactory.getConexao();

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			ConexaoFactory.fecharConexao(conn, stmt);
			System.out.println("Registro inserido com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Comprador searchById(Integer id) {
		String sql = "SELECT id, nome, cpf  from comprador where id =?";
 
		Comprador comprador = null;
		try(Connection conn = ConexaoFactory.getConexao();
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs= ps.executeQuery();
	
			while(rs.next()) {

				comprador = new Comprador(rs.getInt("id"),rs.getString("nome"),rs.getString("cpf"));

			}
			ConexaoFactory.fecharConexao(conn, ps, rs);
			return comprador;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Comprador> selectAll() {
		String sql = "SELECT id, nome, cpf  from comprador;";
		Connection conn = ConexaoFactory.getConexao();

		try {

			Statement stmt = conn.createStatement();

			ResultSet rs= stmt.executeQuery(sql);

			List<Comprador> compradorList = new ArrayList<>();
			while(rs.next()) {

				compradorList.add(new Comprador(rs.getInt("id"),rs.getString("nome"),rs.getString("cpf") ));

			}
			ConexaoFactory.fecharConexao(conn, stmt, rs);
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
}


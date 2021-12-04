package com.ZZCjdbc.test;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ZZCjdbc.ConexaoFactory;
import com.db.compradorDB;
 

import classes.Comprador;

public class teste {
	//estudar métodos de rs estudarestudarestudarestudarestudarestudarestudar
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		//deletar();
		//atualizar();
		//List<Comprador> listaComprador = selecionarTudo();
		//List<Comprador> buscarNome = buscarNome("Elias");
		//System.out.println(buscarNome);
		//compradorDB.selectMetaData();
		//compradorDB.checkDriverStatus();
		// compradorDB.testTypeScroll();
		//System.out.print(compradorDB.searchByName("u"));
		//compradorDB.update(new Comprador (1,"111.111.111-22", "Priscila"));
//compradorDB.insertRegister();
 
//compradorDB.updateRowSetCached(new Comprador(1, "222.222.222-22", "Fernando"));
		Connection conn = ConexaoFactory.getConexao();
		System.out.println("Olá");
	}
}

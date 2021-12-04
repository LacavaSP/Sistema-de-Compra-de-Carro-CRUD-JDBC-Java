package com.ZZCjdbc.test;

import java.util.List;
import java.util.Scanner;

import classes.Comprador;
import db.CompradorDAO;

public class CompradorCRUD {
	private static Scanner teclado = new Scanner(System.in);
	public static void executar (int op) {

		switch(op) {
		case 1:
		inserir();
		break;
		case 2:
			atualizar();
			break;
		case 3:
		listar();
		break;
		case 4:
		searchByName();
		break;
		case 5:
		deletar();
		break;
			default:
				break;
		}
	}
	public static List<Comprador> listar() {
		List<Comprador> compradorList = CompradorDAO.selectAll();
		compradorList.stream()
		.forEach(consumer -> System.out.println(consumer));
		return compradorList;
	}
	public static void inserir() {
		Comprador c = new Comprador();
		System.out.println("Nome: ");
		c.setNome(teclado.nextLine());
		System.out.println("CPF: ");
		c.setCpf(teclado.nextLine());
		CompradorDAO.save(c);
	}
	private static void atualizar() {

		System.out.println("Selecione um dos compradores abaixo");
		List<Comprador> compradorlist = listar();
		String index = teclado.nextLine();
		Comprador c = compradorlist.get(Integer.parseInt(index)-1);
		System.out.println("Novo nome ou enter para manter o mesmo");
		String nome = teclado.nextLine();
		System.out.println("Novo cpf ou enter para manter o mesmo");
		String cpf = teclado.nextLine();
		if(!nome.isEmpty()) {
			c.setNome(nome);
		}
		if(!cpf.isEmpty()) {
			c.setCpf(cpf);
		}
		CompradorDAO.update(c);
	}
	private static void searchByName() {
		System.out.println("Insira o nome a ser pesquisado: ");
		System.out.print(CompradorDAO.searchByName(teclado.nextLine())+"\n");
	}
	private static void deletar() {
		System.out.println("Selecione um dos compradores abaixo para deletar");
		listar();
		Comprador c = new Comprador();
		c.setId(Integer.parseInt(teclado.nextLine()));
		CompradorDAO.delete(c);
	}
}

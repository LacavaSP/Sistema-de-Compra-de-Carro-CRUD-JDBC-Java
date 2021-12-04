package com.ZZCjdbc.test;

import java.util.List;
import java.util.Scanner;

import classes.Carro;
import classes.Comprador;
import db.CarroDAO;

public class CarroCRUD {
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
	private static List<Carro> listar() {
		List<Carro> carroList = CarroDAO.selectAll();
		carroList.stream()
		.forEach(consumer -> System.out.println(consumer));
		return carroList;
	}
	public static void inserir() {
		Carro c = new Carro();
		System.out.println("Nome: ");
		c.setNome(teclado.nextLine());
		System.out.println("Placa: ");
		c.setPlaca(teclado.nextLine());
		System.out.println("Selecione um dos compradores abaixo");
		List<Comprador> compradorList = CompradorCRUD.listar();
		c.setComprador(compradorList.get(Integer.parseInt(teclado.nextLine())-1));
		CarroDAO.save(c);
	}
	private static void atualizar() {

		System.out.println("Selecione um dos carros abaixo");
		List<Carro> carrolist = listar();
		String index = teclado.nextLine();
		Carro c = carrolist.get(Integer.parseInt(index)-1);
		System.out.println("Novo nome ou enter para manter o mesmo");
		String nome = teclado.nextLine();
		System.out.println("Nova placa ou enter para manter a mesma");
		String placa = teclado.nextLine();
		if(!nome.isEmpty()) {
			c.setNome(nome);
		}
		if(!placa.isEmpty()) {
			c.setPlaca(placa);
		}
		CarroDAO.update(c);
	}
	private static void searchByName() {
		System.out.println("Insira o nome a ser pesquisado: ");
		System.out.print(CarroDAO.searchByName(teclado.nextLine())+"\n");
	}
	private static void deletar() {
		System.out.println("Selecione um dos carroes abaixo para deletar");
		listar();
		Carro c = new Carro();
		c.setId(Integer.parseInt(teclado.nextLine()));
		CarroDAO.delete(c);
	}
}

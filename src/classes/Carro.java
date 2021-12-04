package classes;

import java.util.Objects;

public class Carro {
	private Integer id;
	private String nome;
	private String placa;
	private Comprador comprador;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public Comprador getComprador() {
		return comprador;
	}
	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}
	public Carro() {
	
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carro other = (Carro) obj;
		return Objects.equals(id, other.id);
	}
	public Carro(Integer id, String nome, String placa, Comprador comprador) {
		super();
		this.id = id;
		this.nome = nome;
		this.placa = placa;
		this.comprador = comprador;
	}
	@Override
	public String toString() {
		return "Carro [id=" + id + ", nome=" + nome + ", placa=" + placa + ", comprador=" + comprador + "]";
	}
	
	
	
}

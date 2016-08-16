package br.com.wt.agendadoador.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Doador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String nome;
	@Column
	private String email;
	@Column
	private String cpf;
	@Column
	private String cep;
	@Column
	private String endereco;
	
	
	public Doador(){}
	
	public Doador(String nome, String email, String cpf, String cep, String endereco) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.cep = cep;
		this.endereco = endereco;
	}

	public Doador(Long id, String nome, String email, String cpf, String cep, String endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.cep = cep;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Doador [id=" + id + ", nome=" + nome + ", email=" + email + ", cpf=" + cpf + ", cep=" + cep
				+ ", endereco=" + endereco + "]";
	}
	
}

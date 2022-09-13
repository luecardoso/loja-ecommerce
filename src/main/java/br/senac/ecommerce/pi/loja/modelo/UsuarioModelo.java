package br.senac.ecommerce.pi.loja.modelo;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "usuario")
public class UsuarioModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@Column(length = 50)
	@NotBlank(message = "Preencha o nome")
	//@NotNull
	private String nome;
	
	///@NotBlank(message = "Email é obrigatório")
	//@Email(message = "Não é um e-mail válido")
	private String email;
	
	//@NotEmpty(message = "Informe uma senha")
	private String senha;
	
	//@CPF(message = "Informe um CPF válido.")
	private String cpf;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;
	
	//@Size(min=11,max = 14, message = "Infome um telefone válido")
	private String telefone;
	
	private boolean ativo;
	
	@ManyToOne
	private CargoModelo cargo;

	public CargoModelo getCargo() {
		return cargo;
	}

	public void setCargo(CargoModelo cargo) {
		this.cargo = cargo;
	}

	public UsuarioModelo() {
		
	}


	@Override
	public String toString() {
		return "UsuarioModelo [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", dataNascimento=" + dataNascimento + ", telefone=" + telefone + ", ativo=" + ativo + ", cargo="
				+ cargo + "]";
	}

	public UsuarioModelo(Long id, String nome, String email, String senha, String cpf, LocalDate dataNascimento,
			String telefone, boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.ativo = ativo;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}

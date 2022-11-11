package br.senac.ecommerce.pi.loja.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name = "usuario")
public class UsuarioModelo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Column(length = 50)
	@NotBlank(message = "Preencha o nome")
	// @NotNull
	private String nome;

	@NotBlank(message = "Email é obrigatório")
	@Email(message = "Não é um e-mail válido")
	private String email;

	//@NotEmpty(message = "Informe uma senha")
	private String senha;

	@CPF(message = "Informe um CPF válido.")
	private String cpf;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;

	///@Size(min=11,max = 14, message = "Infome um telefone válido")
	private String telefone;

	private boolean ativo;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UsuarioModelo(Long id, @NotBlank(message = "Preencha o nome") String nome, String email, String senha,
			String cpf, LocalDate dataNascimento, String telefone, boolean ativo, Set<CargoModelo> cargos) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.ativo = ativo;
		this.cargos = cargos;
	}

	public UsuarioModelo() {
		
	}
	

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_cargos", joinColumns = @JoinColumn(name = " usuario_id"), inverseJoinColumns = @JoinColumn(name = "cargo_id"))
	private Set<CargoModelo> cargos = new HashSet<>();

	public Set<CargoModelo> getCargos() {
		return cargos;
	}

	public void setCargos(Set<CargoModelo> cargos) {
		this.cargos = cargos;
	}

	public void adicionarCargo(CargoModelo cargo) {
		this.cargos.add(cargo);
	}

	@Override
	public String toString() {
		return "UsuarioModelo [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", dataNascimento=" + dataNascimento + ", telefone=" + telefone + ", ativo=" + ativo + ", cargo="
				+ cargos + "]";
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

	
	public UsuarioModelo(@NotBlank(message = "Preencha o nome") String nome,
			@NotBlank(message = "Email é obrigatório") @Email(message = "Não é um e-mail válido") String email,
			String senha, @CPF(message = "Informe um CPF válido.") String cpf, LocalDate dataNascimento,
			String telefone, boolean ativo, Set<CargoModelo> cargos) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.ativo = ativo;
		this.cargos = cargos;
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

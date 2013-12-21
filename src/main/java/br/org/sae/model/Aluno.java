package br.org.sae.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name = "tbaluno")
public class Aluno extends Entidade {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotBlank
	private String nome;

	@NotNull
	@NotBlank
	private String cpf;

	@NotNull
	@NotBlank
	private String rg;

	@NotNull
	@NotBlank
	private String orgaoExpedidor;

	@NotNull
	@NotBlank
	private String RM;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@NotNull
	@NotBlank
	private String email;
	
	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;
	
	private String necessidaEspecial;
	private String necessidadeTipo;
	private boolean afroDescendentes;

	@NotNull
	private Endereco endereco;

	@NotNull
	private Telefone telefonePrincipal;
	private Telefone telefoneSecundario;
	
	@ManyToMany
	@JoinTable(name="matricula", joinColumns={@JoinColumn(name="codAluno")}, inverseJoinColumns={@JoinColumn(name="codTurma")})
	private List<Turma> turmas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getOrgaoExpedidor() {
		return orgaoExpedidor;
	}

	public void setOrgaoExpedidor(String orgaoExpedidor) {
		this.orgaoExpedidor = orgaoExpedidor;
	}

	public String getRM() {
		return RM;
	}

	public void setRM(String rM) {
		RM = rM;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getNecessidaEspecial() {
		return necessidaEspecial;
	}

	public void setNecessidaEspecial(String necessidaEspecial) {
		this.necessidaEspecial = necessidaEspecial;
	}

	public String getNecessidadeTipo() {
		return necessidadeTipo;
	}

	public void setNecessidadeTipo(String necessidadeTipo) {
		this.necessidadeTipo = necessidadeTipo;
	}

	public boolean isAfroDescendentes() {
		return afroDescendentes;
	}

	public void setAfroDescendentes(boolean afroDescendentes) {
		this.afroDescendentes = afroDescendentes;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Telefone getTelefonePrincipal() {
		return telefonePrincipal;
	}

	public void setTelefonePrincipal(Telefone telefonePrincipal) {
		this.telefonePrincipal = telefonePrincipal;
	}

	public Telefone getTelefoneSecundario() {
		return telefoneSecundario;
	}

	public void setTelefoneSecundario(Telefone telefoneSecundario) {
		this.telefoneSecundario = telefoneSecundario;
	}

}

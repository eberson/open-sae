package br.org.sae.model;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
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
	@Column(unique=true)
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

	private String RA;

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
	private boolean afroDescendente;

	@NotNull
	private Endereco endereco;

	@NotNull
	@Embedded
	private Telefone telefonePrincipal;

	@AttributeOverrides({
			@AttributeOverride(name = "ddd", column = @Column(name = "ddd2")),
			@AttributeOverride(name = "telefone", column = @Column(name = "telefone2")),
			@AttributeOverride(name = "ramal", column = @Column(name = "ramal2")) })
	@Embedded
	private Telefone telefoneSecundario;

	@OneToMany(mappedBy = "aluno")
	private List<Matricula> matriculas;

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
	
	public String getRA() {
		return RA;
	}
	
	public void setRA(String rA) {
		RA = rA;
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

	public boolean isAfroDescendente() {
		return afroDescendente;
	}

	public void setAfroDescendente(boolean afroDescendente) {
		this.afroDescendente = afroDescendente;
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

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

}

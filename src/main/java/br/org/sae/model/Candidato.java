package br.org.sae.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Classe responsável por representar um Candidato interessado em alguns dos
 * Cursos da Unidade
 * 
 * @author Éberson
 * @since 12/12/2013
 * 
 */
@Entity
@Table(name="tbcandidato")
@NamedQueries({@NamedQuery(name="CandidatoPorNomeOuCPF", query="select c from Candidato c where c.cpf = :cpf or c.nome = :nome" )})
public class Candidato extends Entidade {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotBlank
	private String cpf;
	
	@NotNull
	@NotBlank
	private String nome;

	@NotNull
	private String rg;
	
	@NotNull
	private String orgaoExpedidor;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;

	@NotNull
	@NotBlank
	private String email;

	private String necessidadeEspecial;
	private String necessidadeTipo;

	private boolean afroDescendente;

	@NotNull
	private Endereco endereco;

	@NotNull
	private Telefone telefonePrincipal;

	@AttributeOverrides({
			@AttributeOverride(name = "ddd", column = @Column(name = "ddd2")),
			@AttributeOverride(name = "telefone", column = @Column(name = "telefone2")),
			@AttributeOverride(name = "ramal", column = @Column(name = "ramal2")) })
	private Telefone telefoneSecundario;

	@OneToMany(mappedBy = "candidato")
	private List<VestibulinhoPrestado> vestibulinhos;
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getNecessidadeEspecial() {
		return necessidadeEspecial;
	}

	public void setNecessidadeEspecial(String necessidadeEspecial) {
		this.necessidadeEspecial = necessidadeEspecial;
	}
	
	public List<VestibulinhoPrestado> getVestibulinhos() {
		if(vestibulinhos == null){
			vestibulinhos = new ArrayList<>();
		}
		
		return vestibulinhos;
	}
	
	public void setVestibulinhos(List<VestibulinhoPrestado> vestibulinhos) {
		this.vestibulinhos = vestibulinhos;
	}
	
	public void addVestibulinho(VestibulinhoPrestado vestibulinho){
		vestibulinho.setCandidato(this);
		getVestibulinhos().add(vestibulinho);
	}

}

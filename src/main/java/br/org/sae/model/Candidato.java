package br.org.sae.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe responsável por representar um Candidato interessado em alguns dos
 * Cursos da Unidade
 * 
 * @author Éberson
 * @since 12/12/2013
 * 
 */
public class Candidato implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String nome;

	private String rg;
	private String orgaoExpedidor;

	private Sexo sexo;

	private Date dataNascimento;

	private EstadoCivil estadoCivil;

	private String email;

	private String necessidadeEspecial;
	private String necessidadeTipo;

	private boolean afroDescendente;

	private Endereco endereco;

	private Telefone telefonePrincipal;
	private Telefone telefoneSecundario;
	
	private OpcaoPrestada primeiraOpcao;
	private OpcaoPrestada segundaOpcao;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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
	
	public OpcaoPrestada getPrimeiraOpcao() {
		return primeiraOpcao;
	}
	
	public void setPrimeiraOpcao(OpcaoPrestada primeiraOpcao) {
		this.primeiraOpcao = primeiraOpcao;
	}
	
	public OpcaoPrestada getSegundaOpcao() {
		return segundaOpcao;
	}
	
	public void setSegundaOpcao(OpcaoPrestada segundaOpcao) {
		this.segundaOpcao = segundaOpcao;
	}

}

package br.org.sae.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class VestibulinhoPrestado implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	
	private Candidato candidato;
	private Vestibulinho vestibulinho;
	private String tipoProva;
	
	@NotNull
	private OpcaoPrestada primeiraOpcao;
	private OpcaoPrestada segundaOpcao;

	public String getTipoProva() {
		return tipoProva;
	}
	
	public void setTipoProva(String tipoProva) {
		this.tipoProva = tipoProva;
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

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
}

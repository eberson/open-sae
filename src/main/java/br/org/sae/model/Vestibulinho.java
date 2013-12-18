package br.org.sae.model;

import java.io.Serializable;

public class Vestibulinho implements Serializable {

	private static final long serialVersionUID = 1L;

	private int ano;
	private int semestre;
	private String tipoProva;

	private OpcaoPrestada primeiraOpcao;
	private OpcaoPrestada segundaOpcao;

	public int getAno() {
		return ano;
	}

	public String getTipoProva() {
		return tipoProva;
	}
	
	public void setTipoProva(String tipoProva) {
		this.tipoProva = tipoProva;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
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

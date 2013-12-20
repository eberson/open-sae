package br.org.sae.model;

import java.io.Serializable;

public class Vestibulinho implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private int ano;
	private int semestre;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public int getAno() {
		return ano;
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

}

package br.org.sae.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbvestibulinho")
public class Vestibulinho extends Entidade {

	private static final long serialVersionUID = 1L;

	private int ano;
	private int semestre;
	
	public Vestibulinho(int ano, int semestre) {
		super();
		this.ano = ano;
		this.semestre = semestre;
	}

	public Vestibulinho() {
		super();
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

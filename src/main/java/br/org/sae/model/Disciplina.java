package br.org.sae.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbdisciplina")
public class Disciplina extends Entidade {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String nome;
	private String sigla;
	private double cargaSemanal;

	@ManyToOne
	@JoinColumn(name="modulo")
	private Modulo modulo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public double getCargaSemanal() {
		return cargaSemanal;
	}

	public void setCargaSemanal(double cargaSemanal) {
		this.cargaSemanal = cargaSemanal;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

}

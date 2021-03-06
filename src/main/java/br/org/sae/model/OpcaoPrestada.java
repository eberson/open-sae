package br.org.sae.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class OpcaoPrestada implements Serializable {

	private static final long serialVersionUID = 1L;
	private int classificacao;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "curso")
	private Curso curso;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Periodo periodo;
	
	@Enumerated(EnumType.STRING)
	private StatusCandidato status = StatusCandidato.INSCRITO;
	
	public StatusCandidato getStatus() {
		return status;
	}
	
	public void setStatus(StatusCandidato status) {
		this.status = status;
	}

	public int getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(int classificacao) {
		this.classificacao = classificacao;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

}

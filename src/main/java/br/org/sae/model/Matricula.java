package br.org.sae.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbmatricula")
public class Matricula extends Entidade {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "aluno")
	private Aluno aluno;

	@ManyToOne
	@JoinColumn(name="etapa")
	private Etapa etapa;

	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Enumerated(EnumType.STRING)
	private StatusMatricula status = StatusMatricula.ATIVO;
	
	@Enumerated(EnumType.STRING)
	private SituacaoAluno situacao = SituacaoAluno.ATIVO; 
	
	public Etapa getEtapa() {
		return etapa;
	}
	
	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}
	
	public SituacaoAluno getSituacao() {
		return situacao;
	}
	
	public void setSituacao(SituacaoAluno situacao) {
		this.situacao = situacao;
	}
	
	public StatusMatricula getStatus() {
		return status;
	}
	
	public void setStatus(StatusMatricula status) {
		this.status = status;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}

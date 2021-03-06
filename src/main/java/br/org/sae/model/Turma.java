package br.org.sae.model;

import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.org.sae.util.OrdenadorEtapas;
import br.org.sae.util.TurmaListener;

@Entity
@Table(name = "tbturma")
@EntityListeners({TurmaListener.class})
public class Turma extends Entidade {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@NotNull
	private String descricao;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Periodo periodo;

	private int ano;
	
	private String numeroProdesp;

	private int semestre;
	@ManyToOne
	@JoinColumn(name = "curso")
	private Curso curso;

	@Transient
	private Etapa etapaAtual;
	
	@OneToMany(mappedBy="turma", fetch=FetchType.EAGER)
	private List<Etapa> etapas;

	private boolean encerrada;
	
	public Turma() {
		super();
	}
	
	public Turma(String descricao, Periodo periodo, int ano, int semestre, Curso curso, boolean encerrada) {
		super();
		this.descricao = descricao;
		this.periodo = periodo;
		this.ano = ano;
		this.semestre = semestre;
		this.curso = curso;
		this.encerrada = encerrada;
	}
	
	public List<Etapa> getEtapas() {
		return etapas;
	}
	
	public void setEtapas(List<Etapa> etapas) {
		this.etapas = etapas;
	}
	
	public Etapa getEtapaAtual() {
		if(etapaAtual == null){
			List<Etapa> etapas = getEtapas();
			
			if(etapas != null && etapas.size() > 0){
				Collections.sort(etapas, new OrdenadorEtapas());
				etapaAtual = etapas.get(etapas.size() - 1);
			}
		}
		
		return etapaAtual;
	}
	
	public void setEtapaAtual(Etapa etapaAtual) {
		this.etapaAtual = etapaAtual;
	}

	public void setEncerrada(boolean encerrada) {
		this.encerrada = encerrada;
	}
	
	public String getNumeroProdesp() {
		return numeroProdesp;
	}
	
	public void setNumeroProdesp(String numeroProdesp) {
		this.numeroProdesp = numeroProdesp;
	}
	
	public boolean isEncerrada() {
		return encerrada;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
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

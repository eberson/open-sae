package br.org.sae.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tbturma")
public class Turma extends Entidade {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@NotNull
	private String descricao;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Periodo periodo;

	private int ano;

	private int semestre;
	@ManyToOne
	@JoinColumn(name = "curso")
	private Curso curso;
	
	@ManyToMany(mappedBy = "turmas")
	private List<Aluno>alunos;

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

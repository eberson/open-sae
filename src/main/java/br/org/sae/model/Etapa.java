package br.org.sae.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbetapa")
public class Etapa extends Entidade {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private int ano;
	private int semestre;

	private boolean encerrada;
	private boolean listaPiloto;

	@ManyToOne
	@JoinColumn(name = "turma")
	private Turma turma;

	@ManyToOne
	@JoinColumn(name = "modulo")
	private Modulo modulo;

	@OneToMany(mappedBy = "etapa")
	private List<Aula> aulas;
	
	public void setListaPiloto(boolean listaPiloto) {
		this.listaPiloto = listaPiloto;
	}
	
	public boolean isListaPiloto() {
		return listaPiloto;
	}

	public boolean hasListaPiloto() {
		return isListaPiloto();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public boolean isEncerrada() {
		return encerrada;
	}

	public void setEncerrada(boolean encerrada) {
		this.encerrada = encerrada;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}
}

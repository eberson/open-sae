package br.org.sae.model;

import java.io.Serializable;

public class OpcaoPrestada implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoOpcao opcao;
	private int classificacao;
	private int ano;
	private int semestre;
	private Curso curso;
	private Periodo periodo;
	private String tipoProva;
	private int codCurso;

	public int getCodCurso() {
		return codCurso;
	}

	public void setCodCurso(int codCurso) {
		this.codCurso = codCurso;
	}

	public String getTipoProva() {
		return tipoProva;
	}

	public void setTipoProva(String tipoProva) {
		this.tipoProva = tipoProva;
	}

	public TipoOpcao getOpcao() {
		return opcao;
	}

	public void setOpcao(TipoOpcao opcao) {
		this.opcao = opcao;
	}

	public int getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(int classificacao) {
		this.classificacao = classificacao;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

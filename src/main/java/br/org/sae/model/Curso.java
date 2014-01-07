package br.org.sae.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tbcurso")
@NamedQueries({@NamedQuery(name="CursoByNome", query="select c from Curso c where upper(c.nome) = upper(:nome)"),
			   @NamedQuery(name="CursoByCodigoEscola", query="select c from Curso c where c.codigoEscolaCurso = :codigo") })
public class Curso extends Entidade {

	private static final long serialVersionUID = 1L;
	
	private int codigoEscolaCurso;
	
	@NotNull
	@NotBlank
	private String nome;

	private int vagas;
	
	public Curso(int codigoEscolaCurso, String nome, int vagas) {
		super();
		this.codigoEscolaCurso = codigoEscolaCurso;
		this.nome = nome;
		this.vagas = vagas;
	}

	public Curso() {
		super();
	}

	public int getCodigoEscolaCurso() {
		return codigoEscolaCurso;
	}
	
	public void setCodigoEscolaCurso(int codigoEscolaCurso) {
		this.codigoEscolaCurso = codigoEscolaCurso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) {
		this.vagas = vagas;
	}

}

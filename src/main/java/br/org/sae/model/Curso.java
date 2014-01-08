package br.org.sae.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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

	@OrderBy("codigo asc")
	@OneToMany(mappedBy="curso")
	private List<Modulo> modulos;
	
	public Curso(int codigoEscolaCurso, String nome, int vagas) {
		super();
		this.codigoEscolaCurso = codigoEscolaCurso;
		this.nome = nome;
		this.vagas = vagas;
	}

	public Curso() {
		super();
	}
	
	public List<Modulo> getModulos() {
		if(modulos == null){
			modulos = new ArrayList<>();
		}
		
		return modulos;
	}
	
	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}
	
	public void addModulo(Modulo modulo){
		modulo.setCurso(this);
		getModulos().add(modulo);
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

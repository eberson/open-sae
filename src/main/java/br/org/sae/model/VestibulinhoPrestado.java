package br.org.sae.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbvestibulinhoprestado")
@NamedQueries({@NamedQuery(name="VestibulinhoPorCandidato", query="select v from VestibulinhoPrestado v where v.candidato = :candidato"),
	           @NamedQuery(name="VestibulinhoPrestadoPorAnoSemestre", query="select v from VestibulinhoPrestado v where v.candidato = :candidato and v.vestibulinho.ano = :ano and v.vestibulinho.semestre = :semestre")})
public class VestibulinhoPrestado extends Entidade {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "candidato")
	private Candidato candidato;

	@ManyToOne
	@JoinColumn(name = "vestibulinho")
	private Vestibulinho vestibulinho;
	private String tipoProva;
	
	private double nota;
	
	@Embedded
	@NotNull
	private OpcaoPrestada primeiraOpcao;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "classificacao", column = @Column(name = "classificacao2")),
			@AttributeOverride(name = "periodo", column = @Column(name = "periodo2")),
			@AttributeOverride(name = "status", column = @Column(name = "status2")) })
	@AssociationOverrides({ @AssociationOverride(name = "curso", joinColumns = @JoinColumn(name = "curso2")) })
	private OpcaoPrestada segundaOpcao;

	public double getNota() {
		return nota;
	}
	
	public void setNota(double nota) {
		this.nota = nota;
	}
	
	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public Vestibulinho getVestibulinho() {
		return vestibulinho;
	}

	public void setVestibulinho(Vestibulinho vestibulinho) {
		this.vestibulinho = vestibulinho;
	}

	public String getTipoProva() {
		return tipoProva;
	}
	
	public void setTipoProva(String tipoProva) {
		this.tipoProva = tipoProva;
	}

	public OpcaoPrestada getPrimeiraOpcao() {
		return primeiraOpcao;
	}

	public void setPrimeiraOpcao(OpcaoPrestada primeiraOpcao) {
		this.primeiraOpcao = primeiraOpcao;
	}

	public OpcaoPrestada getSegundaOpcao() {
		return segundaOpcao;
	}

	public void setSegundaOpcao(OpcaoPrestada segundaOpcao) {
		this.segundaOpcao = segundaOpcao;
	}
}

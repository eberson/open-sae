package br.org.sae.importador.leitor;

import java.util.Date;

import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.Endereco;
import br.org.sae.model.OpcaoPrestada;
import br.org.sae.model.Telefone;
import br.org.sae.model.Vestibulinho;
import br.org.sae.model.VestibulinhoPrestado;

public class LeitorUtil {

	private DadoLegivel<Candidato> leitorCandidato;
	private DadoLegivel<Endereco> leitorEndereco;
	private DadoLegivel<OpcaoPrestada[]> leitorOpcao;
	private DadoLegivel<Telefone[]> leitorTelefone;
	private DadoLegivel<VestibulinhoPrestado> leitorVestibulinho;

	private ColunaLegivel<Boolean> leitorColunaAfrodescendente;
	private ColunaLegivel<Curso> leitorColunaCurso;
	private ColunaLegivel<Date> leitorColunaDataNascimento;
	private ColunaLegivel<String> leitorColunaRGCandidato;
	
	private Vestibulinho vestibulinho;
	
	public LeitorUtil(int ano, int semestre) {
		super();
		
		vestibulinho = new Vestibulinho(ano, semestre); 
		
		leitorCandidato = new LeitorCandidato();
		leitorEndereco = new LeitorEndereco();
		leitorOpcao = new LeitorOpcao();
		leitorTelefone = new LeitorTelefone();
		leitorVestibulinho = new LeitorVestibulinho();
		
		leitorColunaAfrodescendente = new LeitorColunaAfroDescendente();
		leitorColunaCurso = new LeitorColunaCurso();
		leitorColunaDataNascimento = new LeitorColunaDataNascimento();
		leitorColunaRGCandidato = new LeitorColunaRGCandidato();
	}
	
	public Vestibulinho vestibulinho(){
		return vestibulinho;
	}

	public DadoLegivel<Candidato> leitorCandidato() {
		return leitorCandidato;
	}

	public DadoLegivel<Endereco> leitorEndereco() {
		return leitorEndereco;
	}

	public DadoLegivel<OpcaoPrestada[]> leitorOpcao() {
		return leitorOpcao;
	}

	public DadoLegivel<Telefone[]> leitorTelefone() {
		return leitorTelefone;
	}

	public DadoLegivel<VestibulinhoPrestado> leitorVestibulinho() {
		return leitorVestibulinho;
	}

	public ColunaLegivel<Boolean> leitorColunaAfrodescendente() {
		return leitorColunaAfrodescendente;
	}

	public ColunaLegivel<Curso> leitorColunaCurso() {
		return leitorColunaCurso;
	}

	public ColunaLegivel<Date> leitorColunaDataNascimento() {
		return leitorColunaDataNascimento;
	}

	public ColunaLegivel<String> leitorColunaRGCandidato() {
		return leitorColunaRGCandidato;
	}

}

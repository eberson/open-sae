package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.OpcaoPrestada;
import br.org.sae.model.Vestibulinho;

public class LeitorVestibulinho implements DadoLegivel<Vestibulinho>{

	private int ano;
	private int semestre;
	
	public LeitorVestibulinho(int ano, int semestre) {
		super();
		this.ano = ano;
		this.semestre = semestre;
	}

	@Override
	public Vestibulinho le(Row row, LeitorUtil util) {
		Vestibulinho vestibulinho = new Vestibulinho();
		
		vestibulinho.setAno(ano);
		vestibulinho.setSemestre(semestre);

		OpcaoPrestada[] opcoes = util.leitorOpcao().le(row, util);
		
		vestibulinho.setPrimeiraOpcao(opcoes[0]);
		vestibulinho.setPrimeiraOpcao(opcoes[1]);
		
		return vestibulinho;
	}

}

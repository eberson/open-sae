package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.OpcaoPrestada;
import br.org.sae.model.VestibulinhoPrestado;

public class LeitorVestibulinho implements DadoLegivel<VestibulinhoPrestado>{

	@Override
	public VestibulinhoPrestado le(Row row, LeitorUtil util) {
		VestibulinhoPrestado vestibulinho = new VestibulinhoPrestado();
		
		OpcaoPrestada[] opcoes = util.leitorOpcao().le(row, util);
		
		vestibulinho.setPrimeiraOpcao(opcoes[0]);
		vestibulinho.setSegundaOpcao(opcoes[1]);
		vestibulinho.setVestibulinho(util.vestibulinho());
		
		return vestibulinho;
	}

}

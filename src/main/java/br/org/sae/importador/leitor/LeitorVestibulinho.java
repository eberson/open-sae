package br.org.sae.importador.leitor;

import static br.org.sae.importador.ImportadorConstants.VESTIBULINHO_NOTA;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.importador.ImportadorUtil;
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
		vestibulinho.setNota(ImportadorUtil.getNumericValue(row.getCell(VESTIBULINHO_NOTA)));
		
		return vestibulinho;
	}

}

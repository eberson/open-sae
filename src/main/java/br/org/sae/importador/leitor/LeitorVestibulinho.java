package br.org.sae.importador.leitor;

import static br.org.sae.importador.ImportadorConstants.VESTIBULINHO_NOTA;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.OpcaoPrestada;
import br.org.sae.model.VestibulinhoPrestado;

public class LeitorVestibulinho implements DadoLegivel<VestibulinhoPrestado>{

	@Override
	public VestibulinhoPrestado le(Row row, LeitorUtil util) {
		VestibulinhoPrestado prestado = new VestibulinhoPrestado();
		
		OpcaoPrestada[] opcoes = util.leitorOpcao().le(row, util);
		
		prestado.setPrimeiraOpcao(opcoes[0]);
		prestado.setSegundaOpcao(opcoes[1]);
		prestado.setVestibulinho(util.vestibulinho());
		prestado.setNota(util.reader(Double.class).value(row, VESTIBULINHO_NOTA));
		
		Cell cell = row.getCell(VESTIBULINHO_NOTA);
		
		prestado.setAusente(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK);
		
		return prestado;
	}

}

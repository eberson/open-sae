package br.org.sae.importador.leitor.cell.reader;

import org.apache.poi.ss.usermodel.Cell;

import br.org.sae.importador.ImportadorUtil;

public class DoubleCellReader extends CellReader<Double>{

	@Override
	protected Double read(Cell cell) {
		return ImportadorUtil.getNumericValue(cell);
	}

	@Override
	protected Double defaultValue() {
		return 0d;
	}


}

package br.org.sae.importador.leitor.cell.reader;

import org.apache.poi.ss.usermodel.Cell;

import br.org.sae.importador.ImportadorUtil;

public class IntegerCellReader extends CellReader<Integer>{

	@Override
	protected Integer read(Cell cell) {
		return ImportadorUtil.getIntValue(cell);
	}

	@Override
	protected Integer defaultValue() {
		return 0;
	}


}

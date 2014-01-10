package br.org.sae.importador.leitor.cell.reader;

import org.apache.poi.ss.usermodel.Cell;

public class StringCellReader extends CellReader<String>{

	@Override
	protected String read(Cell cell) {
		return cell.getStringCellValue();
	}

	@Override
	protected String defaultValue() {
		return "";
	}


}

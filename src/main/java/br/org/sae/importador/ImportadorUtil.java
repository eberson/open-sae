package br.org.sae.importador;

import org.apache.poi.ss.usermodel.Cell;

public class ImportadorUtil {
	
	public static int getIntValue(Cell cell){
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String conteudo = cell.getStringCellValue();
			
			if (conteudo != null && !conteudo.isEmpty()) {
				return Integer.parseInt(conteudo);
			}
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return (int) cell.getNumericCellValue();
		}
		
		return 0;
	}

}

package br.org.sae.importador;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ImportadorUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T read(Row row, int cellNumber, Class<T> type){
		Cell cell = row.getCell(cellNumber);
		
		if(String.class.isAssignableFrom(type)){
			if(cell == null){
				return (T) "";
			}
			
			return (T) cell.getStringCellValue();
		}
		
		return null;
	}
	
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

	public static double getNumericValue(Cell cell){
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String conteudo = cell.getStringCellValue();
			conteudo = conteudo.replace(",", ".");
			
			if (conteudo != null && !conteudo.isEmpty()) {
				return Double.parseDouble(conteudo);
			}
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return cell.getNumericCellValue();
		}
		
		return 0;
	}

}

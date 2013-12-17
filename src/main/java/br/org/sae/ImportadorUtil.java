package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class ImportadorUtil {
	
	public static int getIntValue(HSSFCell cell){
		if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			String conteudo = cell.getStringCellValue();
			
			if (conteudo != null && !conteudo.isEmpty()) {
				return Integer.parseInt(conteudo);
			}
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return (int) cell.getNumericCellValue();
		}
		
		return 0;
	}

}

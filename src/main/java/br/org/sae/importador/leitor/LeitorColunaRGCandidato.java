package br.org.sae.importador.leitor;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

public class LeitorColunaRGCandidato implements ColunaLegivel<String>{

	@Override
	public String le(Cell cell, LeitorUtil util) {
		if(cell != null){
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case HSSFCell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			}
		}
		
		return "";
	}

}

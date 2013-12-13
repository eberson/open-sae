package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class RGCandidatoLeitor implements ColunaLegivel<String>{

	@Override
	public String le(HSSFCell cell) {
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case HSSFCell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
		}
		
		return "";
	}

}

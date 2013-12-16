package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class LeitorAfroDescendente implements ColunaLegivel<Boolean>{

	@Override
	public Boolean le(HSSFCell cell) {
		if (cell.getStringCellValue() == "SIM") {
			return true;
		}
		else
			return false;
	}
}

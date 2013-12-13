package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class NomeCandidatoLeitor implements ColunaLegivel<String>{

	@Override
	public String le(HSSFCell cell) {
		return cell.getStringCellValue();
	}
	
	

}

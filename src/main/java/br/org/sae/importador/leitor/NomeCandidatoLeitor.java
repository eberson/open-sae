package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Cell;

public class NomeCandidatoLeitor implements ColunaLegivel<String>{

	@Override
	public String le(Cell cell) {
		return cell.getStringCellValue();
	}
	
	

}

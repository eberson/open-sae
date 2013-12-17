package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Cell;

public class LeitorAfroDescendente implements ColunaLegivel<Boolean>{

	@Override
	public Boolean le(Cell cell) {
		if (cell.getStringCellValue().equals("SIM")) {
			return true;
		}
		else
			return false;
	}
}

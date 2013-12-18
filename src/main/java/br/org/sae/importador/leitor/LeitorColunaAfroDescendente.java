package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Cell;

public class LeitorColunaAfroDescendente implements ColunaLegivel<Boolean>{

	@Override
	public Boolean le(Cell cell, LeitorUtil util) {
		if (cell.getStringCellValue().equals("SIM")) {
			return true;
		}
		else
			return false;
	}
}

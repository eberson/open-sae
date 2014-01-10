package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Cell;

public class LeitorColunaAfroDescendente implements ColunaLegivel<Boolean>{

	@Override
	public Boolean le(Cell cell, LeitorUtil util) {
		return cell != null && cell.getStringCellValue().equals("SIM");
	}
}

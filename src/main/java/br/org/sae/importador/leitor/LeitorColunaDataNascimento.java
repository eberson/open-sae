package br.org.sae.importador.leitor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

public class LeitorColunaDataNascimento implements ColunaLegivel<Date> {

	private DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Date le(Cell cell, LeitorUtil util) {
		try {
			return formatador.parse(cell.getStringCellValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}

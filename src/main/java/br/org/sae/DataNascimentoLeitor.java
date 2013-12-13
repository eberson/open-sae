package br.org.sae;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class DataNascimentoLeitor implements ColunaLegivel<Date> {

	private DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Date le(HSSFCell cell) {
		try {
			return formatador.parse(cell.getStringCellValue());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}

package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFRow;

import br.org.sae.model.Telefone;

public class LeitorTelefone implements DadoLegivel<Telefone>{

	@Override
	public Telefone le(HSSFRow row) {
		Telefone telefone = new Telefone();
		
		telefone.setDdd(row.getCell(11).getStringCellValue());
		telefone.setTelefone(row.getCell(12).getStringCellValue());
		telefone.setRamal(row.getCell(13).getStringCellValue());

		return telefone;
		
	}
	
	public Telefone le2(HSSFRow row) {
		Telefone telefone = new Telefone();
		
		telefone.setDdd(row.getCell(24).getStringCellValue());
		telefone.setTelefone(row.getCell(25).getStringCellValue());
		telefone.setRamal(row.getCell(26).getStringCellValue());
		
		return telefone;
	}

}

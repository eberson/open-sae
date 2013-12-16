package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFRow;

import br.org.sae.model.Endereco;

public class LeitorEndereco implements DadoLegivel<Endereco>{

	@Override
	public Endereco le(HSSFRow row) {
		Endereco endereco = new Endereco();
		
		endereco.setEndereco(row.getCell(7).getStringCellValue());
		endereco.setNumero(row.getCell(8).getStringCellValue());
		endereco.setComplemento(row.getCell(9).getStringCellValue());
		endereco.setBairro(row.getCell(10).getStringCellValue());
		endereco.setCidade(row.getCell(14).getStringCellValue());
		endereco.setUf(row.getCell(15).getStringCellValue());
		endereco.setCep(row.getCell(16).getStringCellValue());
		
		return endereco;
	}

}

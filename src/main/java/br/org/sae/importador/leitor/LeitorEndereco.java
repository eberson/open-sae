package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.Endereco;

public class LeitorEndereco implements DadoLegivel<Endereco>{

	@Override
	public Endereco le(Row row) {
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

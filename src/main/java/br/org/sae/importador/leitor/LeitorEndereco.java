package br.org.sae.importador.leitor;

import static br.org.sae.importador.ImportadorConstants.ENDERECO_BAIRRO;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_CEP;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_CIDADE;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_COMPLEMENTO;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_LOGRADOURO;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_NUMERO;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_UF;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.Endereco;

public class LeitorEndereco implements DadoLegivel<Endereco>{

	@Override
	public Endereco le(Row row, LeitorUtil util) {
		Endereco endereco = new Endereco();
		
		endereco.setEndereco(row.getCell(ENDERECO_LOGRADOURO).getStringCellValue());
		endereco.setNumero(row.getCell(ENDERECO_NUMERO).getStringCellValue());
		endereco.setComplemento(row.getCell(ENDERECO_COMPLEMENTO).getStringCellValue());
		endereco.setBairro(row.getCell(ENDERECO_BAIRRO).getStringCellValue());
		endereco.setCidade(row.getCell(ENDERECO_CIDADE).getStringCellValue());
		endereco.setUf(row.getCell(ENDERECO_UF).getStringCellValue());
		endereco.setCep(row.getCell(ENDERECO_CEP).getStringCellValue());
		
		return endereco;
	}

}

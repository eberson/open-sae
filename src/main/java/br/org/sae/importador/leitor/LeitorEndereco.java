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
		
		endereco.setEndereco(util.reader(String.class).value(row, ENDERECO_LOGRADOURO));
		endereco.setNumero(util.reader(String.class).value(row, ENDERECO_NUMERO));
		endereco.setComplemento(util.reader(String.class).value(row, ENDERECO_COMPLEMENTO));
		endereco.setBairro(util.reader(String.class).value(row, ENDERECO_BAIRRO));
		endereco.setCidade(util.reader(String.class).value(row, ENDERECO_CIDADE));
		endereco.setUf(util.reader(String.class).value(row, ENDERECO_UF));
		endereco.setCep(util.reader(String.class).value(row, ENDERECO_CEP));
		
		return endereco;
	}

}

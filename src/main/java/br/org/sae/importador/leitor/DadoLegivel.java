package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Row;

public interface DadoLegivel<T> {
	
	public T le(Row row, LeitorUtil util);

}

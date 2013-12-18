package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Cell;

public interface ColunaLegivel<T> {
	
	public T le(Cell cell, LeitorUtil util);

}

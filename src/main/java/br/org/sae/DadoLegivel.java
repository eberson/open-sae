package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFRow;

public interface DadoLegivel<T> {
	
	public T le(HSSFRow row);

}

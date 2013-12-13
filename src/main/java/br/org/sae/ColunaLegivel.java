package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFCell;

public interface ColunaLegivel<T> {
	
	public T le(HSSFCell cell);

}

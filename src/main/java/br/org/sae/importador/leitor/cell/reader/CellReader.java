package br.org.sae.importador.leitor.cell.reader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public abstract class CellReader<T> {

	public CellReader() {
	}
	
	protected abstract T read(Cell cell);
	protected abstract T defaultValue();
	
	public T value(Row row, int cellNumber){
		if(row == null){
			throw new IllegalArgumentException("Linha inválida para leitura!!");
		}
		
		Cell cell = row.getCell(cellNumber);
		
		if(cell == null){
			return defaultValue();
		}
		
		return read(cell);
	}
}

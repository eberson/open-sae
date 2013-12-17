package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.Telefone;

public class LeitorTelefone implements DadoLegivel<Telefone[]>{

	@Override
	public Telefone[] le(Row row) {
		Telefone[] telefones = new Telefone[2];
		
		telefones[0] = leImpl(row, 11, 12, 13);
		telefones[1] = leImpl(row, 24, 25, 26);
		
		return telefones;
	}
	
	public Telefone leImpl(Row row, int dddCell, int telefoneCell, int ramalCell) {
		Telefone telefone = new Telefone();
		
		telefone.setDdd(row.getCell(dddCell).getStringCellValue());
		telefone.setTelefone(row.getCell(telefoneCell).getStringCellValue());
		telefone.setRamal(row.getCell(ramalCell).getStringCellValue());
		
		return telefone;
	}

}

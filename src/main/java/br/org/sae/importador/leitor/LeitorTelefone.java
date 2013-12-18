package br.org.sae.importador.leitor;

import static br.org.sae.importador.ImportadorConstants.TELEFONE_P_DDD;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_P_NUMERO;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_P_RAMAL;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_S_DDD;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_S_NUMERO;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_S_RAMAL;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.Telefone;

public class LeitorTelefone implements DadoLegivel<Telefone[]>{

	@Override
	public Telefone[] le(Row row, LeitorUtil util) {
		Telefone[] telefones = new Telefone[2];
		
		telefones[0] = leImpl(row, TELEFONE_P_DDD, TELEFONE_P_NUMERO, TELEFONE_P_RAMAL);
		telefones[1] = leImpl(row, TELEFONE_S_DDD, TELEFONE_S_NUMERO, TELEFONE_S_RAMAL);
		
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

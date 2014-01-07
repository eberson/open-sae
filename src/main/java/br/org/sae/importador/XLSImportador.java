package br.org.sae.importador;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import br.org.sae.importador.leitor.LeitorCandidato;
import br.org.sae.importador.leitor.LeitorUtil;
import br.org.sae.model.Candidato;

class XLSImportador extends Importador{
	
	XLSImportador() {
		super();
	}

	@Override
	public Workbook create(InputStream in) throws IOException{
		return new HSSFWorkbook(in);
	}

	@Override
	public List<Candidato> processa(Sheet sheet, LeitorUtil util) {
		HSSFSheet sheetImpl = (HSSFSheet) sheet;
		LeitorCandidato leitor = new LeitorCandidato();
		List<Candidato> candidatos = new ArrayList<>();
		
		for(int i = limiteInferior; i <= limiteSuperior; i++){
			HSSFRow row = sheetImpl.getRow(i);
			
			candidatos.add(leitor.le(row, util));
		}
		
		return candidatos;
	}
}

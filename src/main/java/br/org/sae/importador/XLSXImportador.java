package br.org.sae.importador;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.org.sae.importador.leitor.LeitorCandidato;
import br.org.sae.importador.leitor.LeitorUtil;
import br.org.sae.model.Candidato;

class XLSXImportador extends Importador {
	
	XLSXImportador() {
		super();
	}

	@Override
	public Workbook create(InputStream in) throws IOException {
		return new XSSFWorkbook(in);
	}

	@Override
	public List<Candidato> processa(Sheet sheet, LeitorUtil util) {
		LeitorCandidato leitor = new LeitorCandidato();
		List<Candidato> candidatos = new ArrayList<>();
		
		XSSFSheet sheetImpl = (XSSFSheet) sheet;
		
		for(int i = limiteInferior; i <= limiteSuperior; i++){
			XSSFRow row = sheetImpl.getRow(i);
			candidatos.add(leitor.le(row, util));
		}
		
		return candidatos;
	}

}

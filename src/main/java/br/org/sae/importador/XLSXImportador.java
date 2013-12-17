package br.org.sae.importador;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.org.sae.importador.leitor.LeitorCandidato;
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
	public List<Candidato> processa(Workbook workbook) {
		XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(1);
		LeitorCandidato leitor = new LeitorCandidato();
		List<Candidato> candidatos = new ArrayList<>();
		
		for(int i = 1; i < 200; i++){
			XSSFRow row = sheet.getRow(i);
			
			candidatos.add(leitor.le(row));
		}
		
		return candidatos;
	}

}

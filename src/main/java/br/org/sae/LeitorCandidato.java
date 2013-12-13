package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import br.org.sae.model.Candidato;

public class LeitorCandidato implements DadoLegivel<Candidato>{

	public Candidato le(HSSFRow row) {
		Candidato c = new Candidato();
		
		c.setNome(row.getCell(0).getStringCellValue());
		
		HSSFCell cellRG = row.getCell(2);
		
		switch (cellRG.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				c.setRg(String.valueOf(cellRG.getNumericCellValue()));
				break;
			case HSSFCell.CELL_TYPE_STRING:
				c.setRg(cellRG.getStringCellValue());
				break;
		}
		
		c.setOrgaoExpedidor(row.getCell(3).getStringCellValue());
		
		return c;
	}

}

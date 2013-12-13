package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFRow;

import br.org.sae.model.Curso;

public class LeitorCurso implements DadoLegivel<Curso> {

	@Override
	public Curso le(HSSFRow row) {
		Curso c = new Curso();
		c.setNome(row.getCell(1).getStringCellValue());

		return c;
	}

}

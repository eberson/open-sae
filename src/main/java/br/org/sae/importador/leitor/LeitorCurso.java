package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.Curso;

public class LeitorCurso implements DadoLegivel<Curso> {

	@Override
	public Curso le(Row row) {
		Curso c = new Curso();
		c.setNome(row.getCell(1).getStringCellValue());

		return c;
	}

}

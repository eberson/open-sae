package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Cell;

import br.org.sae.model.Curso;

public class LeitorColunaCurso implements ColunaLegivel<Curso> {

	@Override
	public Curso le(Cell cell, LeitorUtil util) {
		Curso c = new Curso();
		c.setNome(cell.getStringCellValue());

		return c;
	}

}

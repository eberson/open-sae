package br.org.sae;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.apache.poi.hssf.usermodel.HSSFRow;

import br.org.sae.model.OpcaoPrestada;
import br.org.sae.model.Periodo;

public class LeitorOpcao implements DadoLegivel<OpcaoPrestada> {

	private LeitorCurso leitorcurso = new LeitorCurso();

	private int classificacao;
	private int codCurso;
	private int periodo;

	public LeitorOpcao(int classificacao, int codCurso, int periodo) {
		super();
		this.classificacao = classificacao;
		this.codCurso = codCurso;
		this.periodo = periodo;
	}

	public OpcaoPrestada le(HSSFRow row) {
		OpcaoPrestada op = new OpcaoPrestada();

		op.setCurso(leitorcurso.le(row));

		op.setCodCurso(ImportadorUtil.getIntValue(row.getCell(codCurso)));
		op.setClassificacao(ImportadorUtil.getIntValue(row.getCell(classificacao)));
		
		String speriodo = Normalizer.normalize(row.getCell(periodo).getStringCellValue(), Form.NFD);
		speriodo = speriodo.replaceAll("[^\\p{ASCII}]", "");
		speriodo = speriodo.replaceAll(" ", "_");
		op.setPeriodo(Periodo.valueOf(speriodo));

		return op;
	}
}

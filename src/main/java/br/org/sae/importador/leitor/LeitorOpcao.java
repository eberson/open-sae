package br.org.sae.importador.leitor;

import static br.org.sae.importador.ImportadorConstants.CURSO1_CLASSIFICACAO;
import static br.org.sae.importador.ImportadorConstants.CURSO1_NOME;
import static br.org.sae.importador.ImportadorConstants.CURSO1_PERIODO;
import static br.org.sae.importador.ImportadorConstants.CURSO2_CLASSIFICACAO;
import static br.org.sae.importador.ImportadorConstants.CURSO2_NOME;
import static br.org.sae.importador.ImportadorConstants.CURSO2_PERIODO;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.OpcaoPrestada;
import br.org.sae.model.Periodo;

public class LeitorOpcao implements DadoLegivel<OpcaoPrestada[]> {

	public OpcaoPrestada[] le(Row row, LeitorUtil util) {
		OpcaoPrestada[] opcoes = new OpcaoPrestada[2];
		
		opcoes[0] = leImpl(row, util, CURSO1_NOME, CURSO1_CLASSIFICACAO, CURSO1_PERIODO);
		opcoes[1] = leImpl(row, util, CURSO2_NOME, CURSO2_CLASSIFICACAO, CURSO2_PERIODO);

		return opcoes;
	}

	public OpcaoPrestada leImpl(Row row, LeitorUtil util, int nomeCurso, int classificacao, int periodo) {
		OpcaoPrestada op = new OpcaoPrestada();
		
		op.setCurso(util.leitorColunaCurso().le(row.getCell(nomeCurso), util));
		op.setClassificacao(util.reader(Integer.class).value(row, classificacao));
		
		String speriodo = Normalizer.normalize(util.reader(String.class).value(row, periodo), Form.NFD);
		speriodo = speriodo.replaceAll("[^\\p{ASCII}]", "");
		speriodo = speriodo.replaceAll(" ", "_");
		
		try {
			op.setPeriodo(Periodo.valueOf(speriodo));
		} catch (IllegalArgumentException e) {
			op.setPeriodo(null);
		}
		
		return op;
	}
}

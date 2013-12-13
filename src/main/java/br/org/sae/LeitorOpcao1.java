package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFRow;

import br.org.sae.model.OpcaoPrestada;

public class LeitorOpcao1 implements DadoLegivel<OpcaoPrestada> {

	public OpcaoPrestada le(HSSFRow row) {
		OpcaoPrestada op = new OpcaoPrestada();

		return op;
	}
}

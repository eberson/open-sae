package br.org.sae;

import org.apache.poi.hssf.usermodel.HSSFRow;

import br.org.sae.model.Opcao;
import br.org.sae.model.OpcaoPrestada;

public class LeitorOpcao1 extends LeitorOpcao {
	
	public LeitorOpcao1() {
		super(37, 27, 19);
	}

	public OpcaoPrestada le(HSSFRow row) {
		OpcaoPrestada opcao = super.le(row);
		
		opcao.setOpcao(Opcao.PRIMEIRA);
			
		return opcao;
	}
}

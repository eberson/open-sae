package br.org.sae.util;

import java.util.Collections;
import java.util.List;

import javax.persistence.PostLoad;

import br.org.sae.model.Etapa;
import br.org.sae.model.Turma;

public class TurmaListener {
	
	@PostLoad
	public void postLoad(Turma turma){
		List<Etapa> etapas = turma.getEtapas();
		Collections.sort(etapas, new OrdenadorEtapas());
		
		turma.setEtapaAtual(etapas.get(etapas.size() - 1));
	}


}

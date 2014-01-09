package br.org.sae.util;

import java.util.Comparator;

import br.org.sae.model.Etapa;

public class OrdenadorEtapas implements Comparator<Etapa>{

	@Override
	public int compare(Etapa o1, Etapa o2) {
		int result = Integer.valueOf(o1.getAno()).compareTo(o2.getAno());
		
		if(result == 0){
			return Integer.valueOf(o1.getSemestre()).compareTo(o2.getSemestre());
		}
		
		return result;
	}

}

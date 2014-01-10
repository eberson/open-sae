package br.org.sae.util;

import java.util.Comparator;

import br.org.sae.model.Candidato;

public class ComparadorCandidatosCPF implements Comparator<Candidato>{

	@Override
	public int compare(Candidato o1, Candidato o2) {
		return o1.getCpf().compareTo(o2.getCpf());
	}


}

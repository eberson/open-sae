package br.org.sae.service.impl;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Candidato;
import br.org.sae.repository.impl.RepositoryImpl;

@Repository
public class CandidatoRepository extends RepositoryImpl<Candidato>{
	
	{
		withType(Candidato.class);
	}

}

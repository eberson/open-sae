package br.org.sae.repository.impl;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Aula;
import br.org.sae.repository.AulaRepository;

@Repository
public class AulaRepositoryImpl extends RepositoryImpl<Aula> implements AulaRepository{

	@Override
	protected Class<Aula> type() {
		return Aula.class;
	}

}

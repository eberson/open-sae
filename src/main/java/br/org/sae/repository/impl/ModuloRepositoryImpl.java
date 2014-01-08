package br.org.sae.repository.impl;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Modulo;
import br.org.sae.repository.ModuloRepository;

@Repository
public class ModuloRepositoryImpl extends RepositoryImpl<Modulo> implements ModuloRepository{

	@Override
	protected Class<Modulo> type() {
		return Modulo.class;
	}

}

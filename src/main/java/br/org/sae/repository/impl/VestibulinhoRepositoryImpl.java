package br.org.sae.repository.impl;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Vestibulinho;
import br.org.sae.repository.VestibulinhoRepository;

@Repository
public class VestibulinhoRepositoryImpl extends RepositoryImpl<Vestibulinho> implements VestibulinhoRepository{

	@Override
	protected Class<Vestibulinho> type() {
		return Vestibulinho.class;
	}


}

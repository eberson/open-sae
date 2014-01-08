package br.org.sae.repository.impl;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Etapa;
import br.org.sae.repository.EtapaRepository;

@Repository
public class EtapaRepositoryImpl extends RepositoryImpl<Etapa> implements EtapaRepository{

	@Override
	protected Class<Etapa> type() {
		return Etapa.class;
	}

}

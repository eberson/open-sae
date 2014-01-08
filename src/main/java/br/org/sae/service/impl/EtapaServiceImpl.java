package br.org.sae.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Etapa;
import br.org.sae.repository.EtapaRepository;
import br.org.sae.repository.Repository;
import br.org.sae.service.EtapaService;

@Service
public class EtapaServiceImpl extends EntityServiceImpl<Etapa> implements EtapaService{

	@Autowired
	private EtapaRepository repository;
	
	@Override
	protected Repository<Etapa> repository() {
		return repository;
	}


}

package br.org.sae.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Aula;
import br.org.sae.repository.AulaRepository;
import br.org.sae.repository.Repository;
import br.org.sae.service.AulaService;

@Service
public class AulaServiceImpl extends EntityServiceImpl<Aula> implements AulaService{

	@Autowired
	private AulaRepository repository;
	
	@Override
	protected Repository<Aula> repository() {
		return repository;
	}



}

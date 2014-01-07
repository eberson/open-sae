package br.org.sae.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Vestibulinho;
import br.org.sae.repository.Repository;
import br.org.sae.repository.VestibulinhoRepository;
import br.org.sae.service.VestibulinhoService;

@Service
public class VestibulinhoServiceImpl extends EntityServiceImpl<Vestibulinho> implements VestibulinhoService{

	@Autowired
	private VestibulinhoRepository repository;
	
	@Override
	protected Repository<Vestibulinho> repository() {
		return repository;
	}

}

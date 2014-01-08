package br.org.sae.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Modulo;
import br.org.sae.repository.ModuloRepository;
import br.org.sae.repository.Repository;
import br.org.sae.service.ModuloService;

@Service
public class ModuloServiceImpl extends EntityServiceImpl<Modulo> implements ModuloService{

	@Autowired
	private ModuloRepository repository;
	
	@Override
	protected Repository<Modulo> repository() {
		return repository;
	}


}

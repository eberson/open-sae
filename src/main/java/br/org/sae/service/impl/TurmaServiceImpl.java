package br.org.sae.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Turma;
import br.org.sae.repository.Repository;
import br.org.sae.repository.TurmaRepository;
import br.org.sae.service.TurmaService;

@Service
public class TurmaServiceImpl extends EntityServiceImpl<Turma> implements TurmaService{

	@Autowired
	private TurmaRepository repository;
	
	@Override
	protected Repository<Turma> repository() {
		return repository;
	}

}

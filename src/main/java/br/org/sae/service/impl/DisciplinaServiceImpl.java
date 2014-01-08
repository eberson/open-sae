package br.org.sae.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Disciplina;
import br.org.sae.repository.DisciplinaRepository;
import br.org.sae.repository.Repository;
import br.org.sae.service.DisciplinaService;

@Service
public class DisciplinaServiceImpl extends EntityServiceImpl<Disciplina> implements DisciplinaService{

	@Autowired
	private DisciplinaRepository repository;
	
	@Override
	protected Repository<Disciplina> repository() {
		return repository;
	}


}

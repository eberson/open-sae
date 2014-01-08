package br.org.sae.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Professor;
import br.org.sae.repository.ProfessorRepository;
import br.org.sae.repository.Repository;
import br.org.sae.service.ProfessorService;

@Service
public class ProfessorServiceImpl extends EntityServiceImpl<Professor> implements ProfessorService{

	@Autowired
	private ProfessorRepository repository;
	
	@Override
	protected Repository<Professor> repository() {
		return repository;
	}



}

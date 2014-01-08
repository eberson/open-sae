package br.org.sae.repository.impl;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Professor;
import br.org.sae.repository.ProfessorRepository;

@Repository
public class ProfessorRepositoryImpl extends RepositoryImpl<Professor> implements ProfessorRepository{

	@Override
	protected Class<Professor> type() {
		return Professor.class;
	}


}

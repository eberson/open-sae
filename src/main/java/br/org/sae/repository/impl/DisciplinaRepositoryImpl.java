package br.org.sae.repository.impl;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Disciplina;
import br.org.sae.repository.DisciplinaRepository;

@Repository
public class DisciplinaRepositoryImpl extends RepositoryImpl<Disciplina> implements DisciplinaRepository{

	@Override
	protected Class<Disciplina> type() {
		return Disciplina.class;
	}

}

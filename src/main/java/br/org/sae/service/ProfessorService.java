package br.org.sae.service;

import java.util.List;

import br.org.sae.model.Professor;

public interface ProfessorService extends EntityService<Professor> {
	
	List<Professor> findAll(String nome);

}

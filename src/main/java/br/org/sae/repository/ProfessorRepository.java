package br.org.sae.repository;

import java.util.List;

import br.org.sae.model.Professor;

public interface ProfessorRepository extends Repository<Professor> {
	
	List<Professor> findAll(String nome);

}

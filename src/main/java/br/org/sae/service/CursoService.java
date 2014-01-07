package br.org.sae.service;

import java.util.List;

import br.org.sae.model.Curso;

public interface CursoService extends EntityService<Curso>{
	
	List<Curso> findAll(String nome);
	Curso findByEscolaCode(int code);
	Curso findByNome(String nome);

}

package br.org.sae.repository;

import java.util.List;

import br.org.sae.model.Curso;

public interface CursoRepository extends Repository<Curso>{
	
	public List<Curso> all(String nome);
	public Curso findByName(String nome);
	public Curso findByEscolaCode(int code);

}

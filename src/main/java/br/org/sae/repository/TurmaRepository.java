package br.org.sae.repository;

import java.util.List;

import br.org.sae.model.Curso;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;

public interface TurmaRepository extends Repository<Turma>{
	
	List<Turma> all(int ano, int semestre);
	
	List<Turma> find(int ano, int semestre, Curso curso, Periodo periodo);

}

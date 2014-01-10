package br.org.sae.repository;

import java.util.List;
import java.util.Map;

import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;

public interface CandidatoRepository extends Repository<Candidato>{
	
	Candidato find(String cpf);
	
	List<Candidato> findAll(String nome);
	
	void saveOrUpdate(Candidato candidato);
	
	Map<Curso, List<Candidato>> findByVestibulinho(int ano, int semestre, boolean primeiraOpcao);
	
	List<Candidato> findByVestibulinhoAndCurso(int ano, int semestre, long curso, boolean primeiraOpcao);

	List<Candidato> findByVestibulinhoAndCurso(int ano, int semestre, String curso, boolean primeiraOpcao);
}

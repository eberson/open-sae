package br.org.sae.service;

import java.util.List;

import br.org.sae.model.Aluno;
import br.org.sae.model.Candidato;

public interface AlunoService extends EntityService<Aluno>{
	
	Aluno from(Candidato candidato);
	
	List<Aluno> findAll(String nome);
	
	Aluno findByCPF(String cpf);

}

package br.org.sae.repository;

import java.util.List;

import br.org.sae.model.Aluno;

public interface AlunoRepository extends Repository<Aluno>{
	
	List<Aluno> findAll(String nome);
	Aluno findByCPF(String cpf);

}

package br.org.sae.repository;

import br.org.sae.model.Aluno;

public interface AlunoRepository extends Repository<Aluno>{
	
	Aluno findByNome(String nome);
	Aluno findByCPF(String cpf);

}

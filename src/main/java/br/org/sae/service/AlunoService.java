package br.org.sae.service;

import br.org.sae.model.Aluno;
import br.org.sae.model.Candidato;

public interface AlunoService extends EntityService<Aluno>{
	
	Aluno from(Candidato candidato);
	Aluno findByNome(String nome);
	Aluno findByCPF(String cpf);

}

package br.org.sae.repository;

import java.util.List;

import br.org.sae.model.Etapa;
import br.org.sae.model.Turma;

public interface EtapaRepository extends Repository<Etapa>{
	
	List<Etapa> findAll(Turma turma);
	
	Etapa findAtual(Turma turma);
	

}

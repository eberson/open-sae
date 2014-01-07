package br.org.sae.service;

import br.org.sae.model.Candidato;

public interface CandidatoService extends EntityService<Candidato>{
	
	public Candidato find(String nome, String cpf);
	
	public RespostaCRUDService saveOrUpdate(Candidato candidato);

}

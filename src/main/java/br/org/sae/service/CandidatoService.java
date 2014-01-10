package br.org.sae.service;

import java.util.List;

import br.org.sae.model.Candidato;

public interface CandidatoService extends EntityService<Candidato>{
	
	public Candidato find(String cpf);
	
	public List<Candidato> findAll(String nome);
	
	public RespostaCRUDService saveOrUpdate(Candidato candidato);

}

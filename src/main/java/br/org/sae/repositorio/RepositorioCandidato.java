package br.org.sae.repositorio;

import java.util.List;

import br.org.sae.model.Candidato;


public interface RepositorioCandidato {
	
	public void insert();
	public List<Candidato> pesquisa(String cpf,String nome, int semestre, int ano); 
	
}

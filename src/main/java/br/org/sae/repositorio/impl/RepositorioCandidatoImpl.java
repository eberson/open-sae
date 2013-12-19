package br.org.sae.repositorio.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Candidato;
import br.org.sae.repositorio.RepositorioCandidato;

@Repository
public class RepositorioCandidatoImpl implements RepositorioCandidato {

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Candidato> pesquisa(String cpf, String nome, int semestre, int ano) {
		// TODO Auto-generated method stub
		return null;
	}

}

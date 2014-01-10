package br.org.sae.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Candidato;
import br.org.sae.repository.CandidatoRepository;
import br.org.sae.repository.Repository;
import br.org.sae.service.CandidatoService;
import br.org.sae.service.RespostaCRUDService;

@Service
public class CandidatoServiceImpl extends EntityServiceImpl<Candidato> implements CandidatoService {

	@Autowired
	private CandidatoRepository repository;
	
	@Override
	public Candidato find(String cpf) {
		return repository.find(cpf);
	}
	
	@Override
	public List<Candidato> findAll(String nome) {
		return repository.findAll(nome);
	}
	
	@Override
	public RespostaCRUDService saveOrUpdate(Candidato candidato) {
		try {
			repository.saveOrUpdate(candidato);
			return RespostaCRUDService.SUCESSO;
		} catch (Exception e) {
			e.printStackTrace();
			return RespostaCRUDService.ERRO_DESCONHECIDO;
		}
	}
	
	@Override
	protected Repository<Candidato> repository() {
		return repository;
	}
}

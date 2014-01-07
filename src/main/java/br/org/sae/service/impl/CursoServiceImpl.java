package br.org.sae.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Curso;
import br.org.sae.repository.CursoRepository;
import br.org.sae.repository.Repository;
import br.org.sae.service.CursoService;

@Service
public class CursoServiceImpl extends EntityServiceImpl<Curso> implements CursoService{

	@Autowired
	private CursoRepository repository;
	
	@Override
	public List<Curso> findAll(String nome) {
		return repository.all(nome);
	}

	@Override
	public Curso findByEscolaCode(int code) {
		return repository.findByEscolaCode(code);
	}

	@Override
	public Curso findByNome(String nome) {
		return repository.findByName(nome);
	}

	@Override
	protected Repository<Curso> repository() {
		return repository;
	}

}

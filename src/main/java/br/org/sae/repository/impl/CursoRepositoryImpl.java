package br.org.sae.repository.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Curso;
import br.org.sae.repository.CursoRepository;

@Repository
public class CursoRepositoryImpl extends RepositoryImpl<Curso> implements CursoRepository{
	
	@Override
	protected Class<Curso> type() {
		return Curso.class;
	}

	@Override
	public List<Curso> all(String nome) {
		return null;
	}

	@Override
	public Curso findByName(String nome) {
		TypedQuery<Curso> query = em().createNamedQuery("CursoByNome", Curso.class);
		query.setParameter("nome", nome);
		query.setMaxResults(1);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	

	@Override
	public Curso findByEscolaCode(int code) {
		TypedQuery<Curso> query = em().createNamedQuery("CursoByCodigoEscola", Curso.class);
		query.setParameter("codigo", code);
		query.setMaxResults(1);
		
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}

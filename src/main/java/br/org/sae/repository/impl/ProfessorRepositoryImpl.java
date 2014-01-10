package br.org.sae.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Professor;
import br.org.sae.repository.ProfessorRepository;

@Repository
public class ProfessorRepositoryImpl extends RepositoryImpl<Professor> implements ProfessorRepository{

	@Override
	protected Class<Professor> type() {
		return Professor.class;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Professor> findAll(String nome) {
		CriteriaBuilder cb = em().getCriteriaBuilder();
		CriteriaQuery<Professor> cq = cb.createQuery(Professor.class);
		Root<Professor> from = cq.from(Professor.class);
		
		cq.where(cb.like(cb.upper((Expression)from.get("nome")), nome.toUpperCase() + "%"));
		
		try {
			return em().createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}


}

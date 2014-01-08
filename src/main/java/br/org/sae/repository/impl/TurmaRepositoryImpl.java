package br.org.sae.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.sae.model.Curso;
import br.org.sae.model.Etapa;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;
import br.org.sae.repository.TurmaRepository;

@Repository
public class TurmaRepositoryImpl extends RepositoryImpl<Turma> implements TurmaRepository{

	@Override
	protected Class<Turma> type() {
		return Turma.class;
	}
	
	@Override
	@Transactional
	public void save(Turma value) {
		Etapa etapaAtual = value.getEtapaAtual();
		
		if(etapaAtual != null && etapaAtual.getCodigo() == null){
			em().persist(etapaAtual);
		}
		
		super.save(value);
	}

	@Override
	public List<Turma> all(int ano, int semestre) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Turma> cq = qb.createQuery(Turma.class);
		
		Root<Turma> from = cq.from(Turma.class);
		
		from.fetch("curso");
		
		Predicate predicate = qb.and(qb.equal(from.get("ano"), ano), qb.equal(from.get("semestre"), semestre));
		
		CriteriaQuery<Turma> select = cq.select(from);
		select.where(predicate);
		
		try {
			TypedQuery<Turma> query = em().createQuery(select);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<Turma> find(int ano, int semestre, Curso curso, Periodo periodo) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Turma> cq = qb.createQuery(Turma.class);
		
		Root<Turma> from = cq.from(Turma.class);
		
		from.fetch("curso");
		
		Predicate predicate = qb.and(qb.equal(from.get("ano"), ano), 
				                     qb.equal(from.get("semestre"), semestre),
				                     qb.equal(from.get("periodo"), periodo),
				                     qb.equal(from.get("curso"), curso));
		
		CriteriaQuery<Turma> select = cq.select(from);
		select.where(predicate);
		
		try {
			TypedQuery<Turma> query = em().createQuery(select);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

}

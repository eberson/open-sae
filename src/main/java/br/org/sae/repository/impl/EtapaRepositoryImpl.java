package br.org.sae.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Etapa;
import br.org.sae.model.Turma;
import br.org.sae.repository.EtapaRepository;

@Repository
public class EtapaRepositoryImpl extends RepositoryImpl<Etapa> implements EtapaRepository{

	@Override
	protected Class<Etapa> type() {
		return Etapa.class;
	}

	@Override
	public List<Etapa> findAll(Turma turma) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Etapa> cq = qb.createQuery(Etapa.class);
		Root<Etapa> from = cq.from(Etapa.class);
		
		cq.where(qb.equal(from.get("turma"), turma));
		
		try {
			return em().createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Etapa findAtual(Turma turma) {
//		select * 
//		from tbetapa
//		where turma = 1 and 
//		      ano = (select max(ano) from tbetapa where turma = 1) and
//		      semestre = (select max(semestre) from tbetapa where turma = 1 and ano = (select max(ano) from tbetapa where turma = 1));
		
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Etapa> cq = qb.createQuery(Etapa.class);
		Root<Etapa> from = cq.from(Etapa.class);
		
		Subquery<Etapa> sqAno = cq.subquery(Etapa.class);
		Root<Etapa> sqAnofrom = sqAno.from(Etapa.class);
		sqAno.select(qb.max((Expression)sqAnofrom.get("ano")));
		sqAno.where(qb.equal(sqAnofrom.get("turma"), turma));
		
		Subquery<Etapa> sqSemestre = cq.subquery(Etapa.class);
		Root<Etapa> sqSemestrefrom = sqSemestre.from(Etapa.class);
		sqSemestre.select(qb.max((Expression)sqSemestrefrom.get("semestre")));
		
		Subquery<Etapa> ssqAno = sqSemestre.subquery(Etapa.class);
		Root<Etapa> ssqAnofrom = ssqAno.from(Etapa.class);
		ssqAno.select(qb.max((Expression)ssqAnofrom.get("ano")));
		ssqAno.where(qb.equal(ssqAnofrom.get("turma"), turma));
		
		sqSemestre.where(qb.and(qb.equal(sqSemestrefrom.get("turma"), turma),
				         qb.equal(sqSemestrefrom.get("ano"), ssqAno)));
		
		cq.where(qb.and(qb.equal(from.get("turma"), turma),
				        qb.equal(from.get("ano"), sqAno),
				        qb.equal(from.get("semestre"), sqSemestre)));
		
		
		try {
			TypedQuery<Etapa> query = em().createQuery(cq);
			query.setMaxResults(1);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}

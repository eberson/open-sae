package br.org.sae.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
	public Etapa findAtual(Turma turma) {
		String jpql = "select e from Etapa e where (e.codigo, e.ano, e.semestre) in "
				+ "(select i.codigo, max(i.ano) as ano, max(i.semestre) as semestre "
				+ "from Etapa i "
				+ "where i.turma.codigo = :codigo "
				+ "group by i.codigo)";
		
		try {
			TypedQuery<Etapa> query = em().createQuery(jpql, Etapa.class);
			query.setParameter("codigo", turma.getCodigo());
			query.setMaxResults(1);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}

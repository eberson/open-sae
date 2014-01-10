package br.org.sae.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.org.sae.model.Aluno;
import br.org.sae.repository.AlunoRepository;

@Repository
public class AlunoRepositoryImpl extends RepositoryImpl<Aluno> implements AlunoRepository{

	@Override
	protected Class<Aluno> type() {
		return Aluno.class;
	}

	@Override
	public boolean exists(Aluno value) {
		try {
			CriteriaBuilder qb = em().getCriteriaBuilder();
			CriteriaQuery<Long> cq = qb.createQuery(Long.class);
			Root<Aluno> from = cq.from(Aluno.class);
			CriteriaQuery<Long> select = cq.select(qb.count(from));
			
			select.where(qb.or(qb.equal(from.get("codigo"), value.getCodigo()),
					qb.equal(from.get("cpf"), value.getCpf())));
			
			return em().createQuery(select).getSingleResult() > 0;
		} catch (Exception e) {
			return super.exists(value);
		}
	}
	
	@Override
	public Aluno findByCPF(String cpf) {
		try {
			CriteriaBuilder qb = em().getCriteriaBuilder();
			CriteriaQuery<Aluno> cq = qb.createQuery(Aluno.class);
			Root<Aluno> from = cq.from(Aluno.class);
			CriteriaQuery<Aluno> select = cq.select(from);
			
			select.where(qb.equal(from.get("cpf"), cpf));
			
			TypedQuery<Aluno> query = em().createQuery(select);
			query.setMaxResults(1);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Aluno> findAll(String nome) {
		try {
			CriteriaBuilder qb = em().getCriteriaBuilder();
			CriteriaQuery<Aluno> cq = qb.createQuery(Aluno.class);
			Root<Aluno> from = cq.from(Aluno.class);
			CriteriaQuery<Aluno> select = cq.select(from);
			
			select.where(qb.like(qb.lower((Expression)from.get("nome")), nome.toLowerCase() + "%"));
			
			TypedQuery<Aluno> query = em().createQuery(select);
			return query.getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

}

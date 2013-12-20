package br.org.sae.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.orm.jpa.JpaTemplate;

import br.org.sae.repository.Repository;

@org.springframework.stereotype.Repository
public class RepositoryImpl<T> implements Repository<T>{

	private Class<T> type;
	private JpaTemplate jpa;
	
	public Repository<T> withType(Class<T> type) {
		this.type = type;
		return this;
	}
	
	public JpaTemplate getJpa() {
		return jpa;
	}
	
	public void setJpa(JpaTemplate jpa) {
		this.jpa = jpa;
	}
	
	@Override
	public T find(Object id) {
		return jpa.find(type, id);
	}

	@Override
	public List<T> all() {
		EntityManager em = jpa.getEntityManager();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(type);
		cq.from(type);
		
		TypedQuery<T> query = em.createQuery(cq);
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public void save(T value) {
		jpa.persist(value);
	}

	@Override
	public void delete(Object id) {
		jpa.remove(find(id));
	}

}

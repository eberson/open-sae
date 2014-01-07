package br.org.sae.repository.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.org.sae.exception.SaeValidationException;
import br.org.sae.repository.Repository;

public abstract class RepositoryImpl<T> implements Repository<T>{

	protected abstract Class<T> type();
	
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@Autowired
	private Validator validator;
	
	public EntityManager em() {
		return em;
	}
	
	@Override
	public T find(Object id) {
		return em.find(type(), id);
	}

	@Override
	public List<T> all() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(type());
		cq.from(type());
		
		TypedQuery<T> query = em.createQuery(cq);
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Transactional
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void save(T value) {
		Set<ConstraintViolation> violations = new HashSet(validator.validate(value));
		
		if(!violations.isEmpty()){
			throw new SaeValidationException(violations);
		}
		
		em.persist(value);
	}
	
	@Transactional
	@Override
	public void update(T value) {
		em.merge(value);
	}

	@Transactional
	@Override
	public void delete(Object id) {
		em.remove(find(id));
	}

}

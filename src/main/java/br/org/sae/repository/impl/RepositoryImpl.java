package br.org.sae.repository.impl;

import java.lang.reflect.Field;
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
import javax.persistence.criteria.Root;
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
	
	public Repository<T> withEm(EntityManager em){
		this.em = em;
		return this;
	}
	
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

	@Override
	@Transactional
	public void save(T value) {
		saveImpl(value);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	protected <E> void saveImpl(E value) {
		Set<ConstraintViolation> violations = new HashSet(validator.validate(value));
		
		if(!violations.isEmpty()){
			throw new SaeValidationException(violations);
		}
		
		em.persist(value);
	}
	
	@Override
	@Transactional
	public void update(T value) {
		em.merge(value);
	}

	@Override
	@Transactional
	public void delete(Object id) {
		em.remove(find(id));
	}

	@Override
	@Transactional
	public void deleteAll() {
		List<T> all = all();
		
		for (T element : all) {
			em.remove(element);
		}
	}
	
	@Override
	public boolean exists(T value) {
		return existsImpl(value, type());
	}

	protected final <E> boolean existsImpl(E value, Class<E> type) {
		try {
			Long codigo = extractCodigo(value);
			
			if(codigo == null){
				return false;
			}
			
			CriteriaBuilder qb = em.getCriteriaBuilder();
			CriteriaQuery<Long> cq = qb.createQuery(Long.class);
			Root<E> from = cq.from(type);
			CriteriaQuery<Long> select = cq.select(qb.count(from));
			
			select.where(qb.equal(from.get("codigo"), codigo));
			
			return em.createQuery(select).getSingleResult() > 0;
		} catch (Exception e) {
			return false;
		}
	}
	
	private <E> Long extractCodigo(E value){
		Field field = findField(value, "codigo", value.getClass());
		
		if(field == null){
			return null;
		}
		
		boolean accessible = field.isAccessible();
		
		try{
			field.setAccessible(true);
			return (Long) field.get(value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		finally{
			field.setAccessible(accessible);
		}
	}

	@SuppressWarnings("rawtypes")
	private <E> Field findField(E value, String fieldName, Class type){
		if(type == null){
			return null;
		}
		
		try {
			return type.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			return findField(value, fieldName, type.getSuperclass());
		}
	}

}

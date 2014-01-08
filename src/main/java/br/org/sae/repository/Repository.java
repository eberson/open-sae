package br.org.sae.repository;

import java.util.List;

import javax.persistence.EntityManager;

public interface Repository<T> {
	
	Repository<T> withEm(EntityManager em);
	
	T find(Object id);
	
	List<T> all();
	
	void save(T value);
	
	void update(T value);
	
	void delete(Object id);

	void deleteAll();
	
	boolean exists(T value);

}

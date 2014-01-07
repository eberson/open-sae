package br.org.sae.service;

import java.util.List;

public interface EntityService<T> {
	
	List<T> findAll();
	T findByCode(long code);
	RespostaCRUDService save(T element);
	RespostaCRUDService update(T element);

}

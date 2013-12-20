package br.org.sae.repository;

import java.util.List;

public interface Repository<T> {
	
	T find(Object id);
	
	List<T> all();
	
	void save(T value);
	
	void delete(Object id);

}

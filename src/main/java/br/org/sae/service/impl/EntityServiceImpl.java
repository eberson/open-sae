package br.org.sae.service.impl;

import java.util.List;

import br.org.sae.repository.Repository;
import br.org.sae.service.EntityService;
import br.org.sae.service.RespostaCRUDService;

public abstract class EntityServiceImpl<T> implements EntityService<T> {

	protected abstract Repository<T> repository();
	
	@Override
	public List<T> findAll() {
		return repository().all();
	}

	@Override
	public T findByCode(long code) {
		return repository().find(code);
	}

	@Override
	public RespostaCRUDService save(T element) {
		try {
			repository().save(element);
			return RespostaCRUDService.SUCESSO;
		} catch (Exception e) {
			e.printStackTrace();
			return RespostaCRUDService.ERRO_DESCONHECIDO;
		}
	}

	@Override
	public RespostaCRUDService update(T element) {
		try {
			return RespostaCRUDService.SUCESSO;
		} catch (Exception e) {
			e.printStackTrace();
			return RespostaCRUDService.ERRO_DESCONHECIDO;
		}
	}
}

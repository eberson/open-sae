package br.org.sae.repository.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.sae.model.Curso;
import br.org.sae.model.Etapa;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;
import br.org.sae.repository.EtapaRepository;
import br.org.sae.repository.TurmaRepository;

@Repository
public class TurmaRepositoryImpl extends RepositoryImpl<Turma> implements TurmaRepository{
	
	@Autowired
	private EtapaRepository etapaRepository;

	@Override
	protected Class<Turma> type() {
		return Turma.class;
	}
	
	@Override
	public Turma find(Object id) {
		Turma turma = super.find(id);
		loadEtapas(turma);
		return turma;
	}
	
	@Override
	public List<Turma> all() {
		List<Turma> all = super.all();
		loadEtapas(all);
		return all;
	}
	
	@Override
	@Transactional
	public void save(Turma value) {
		Etapa etapaAtual = value.getEtapaAtual();
		
		if(etapaAtual != null && etapaAtual.getCodigo() == null){
			em().persist(etapaAtual);
		}
		
		super.save(value);
	}

	@Override
	public List<Turma> all(int ano, int semestre) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Turma> cq = qb.createQuery(Turma.class);
		
		Root<Turma> from = cq.from(Turma.class);
		
		from.fetch("curso");
		
		Predicate predicate = qb.and(qb.equal(from.get("ano"), ano), qb.equal(from.get("semestre"), semestre));
		
		CriteriaQuery<Turma> select = cq.select(from);
		select.where(predicate);
		
		try {
			TypedQuery<Turma> query = em().createQuery(select);
			List<Turma> resultList = query.getResultList();
			loadEtapas(resultList);
			return resultList;
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly=true)
	@Override
	public List<Turma> find(int ano, int semestre, Curso curso, Periodo periodo) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Turma> cq = qb.createQuery(Turma.class);
		
		Root<Turma> from = cq.from(Turma.class);
		
		from.fetch("curso");

		Predicate predicate = qb.and(qb.equal(from.get("ano"), ano), 
				                     qb.equal(from.get("semestre"), semestre),
				                     qb.equal(from.get("periodo"), periodo),
				                     qb.equal(from.get("curso"), curso));
		
		CriteriaQuery<Turma> select = cq.select(from);
		select.where(predicate);
		
		try {
			TypedQuery<Turma> query = em().createQuery(select);
			List<Turma> lista = query.getResultList();
			loadEtapas(lista);
			return lista;
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	private void loadEtapas(Turma... turmas){
		for (Turma turma : turmas) {
			turma.setEtapaAtual(etapaRepository.findAtual(turma));
			turma.setEtapas(etapaRepository.findAll(turma));
		}
	}

	private void loadEtapas(Collection<Turma> turmas){
		for (Turma turma : turmas) {
			turma.setEtapaAtual(etapaRepository.findAtual(turma));
			turma.setEtapas(etapaRepository.findAll(turma));
		}
	}
}

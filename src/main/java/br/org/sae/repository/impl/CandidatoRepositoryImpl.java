package br.org.sae.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.Vestibulinho;
import br.org.sae.model.VestibulinhoPrestado;
import br.org.sae.repository.CandidatoRepository;
import br.org.sae.repository.VestibulinhoRepository;

@Repository
public class CandidatoRepositoryImpl extends RepositoryImpl<Candidato> implements CandidatoRepository{

	@Autowired
	private VestibulinhoRepository repository;
	
	@Override
	protected Class<Candidato> type() {
		return Candidato.class;
	}

	@Transactional(noRollbackFor={NoResultException.class})
	@Override
	public void saveOrUpdate(Candidato value) {
		Candidato stored = findByCpfOrNome(value.getCpf(), value.getNome());
		
		if(stored == null){
			super.save(value);
		}
		else{
			value.setCodigo(stored.getCodigo());
			super.update(value);
		}

		List<VestibulinhoPrestado> vestibulinhos = value.getVestibulinhos();
		
		for (VestibulinhoPrestado vestibulinho : vestibulinhos) {
			TypedQuery<VestibulinhoPrestado> query = em().createNamedQuery("VestibulinhoPrestadoPorAnoSemestre", VestibulinhoPrestado.class);
			query.setParameter("candidato", value);
			query.setParameter("ano", vestibulinho.getVestibulinho().getAno());
			query.setParameter("semestre", vestibulinho.getVestibulinho().getSemestre());

			try {
				query.getSingleResult();
			} catch (NoResultException e) {
				em().persist(vestibulinho);
			}
		}
		
	}
	
	@Override
	public Candidato findByCpfOrNome(String cpf, String nome) {
		TypedQuery<Candidato> query = em().createNamedQuery("CandidatoPorNomeOuCPF", Candidato.class);
		
		query.setParameter("cpf", cpf);
		query.setParameter("nome", nome);
		
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	@Override
	public Map<Curso, List<Candidato>> findByVestibulinho(int ano, int semestre, boolean primeiraOpcao) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<VestibulinhoPrestado> cq = qb.createQuery(VestibulinhoPrestado.class);
		Root<VestibulinhoPrestado> from = cq.from(VestibulinhoPrestado.class);
		Join<Object, Object> join = from.join("vestibulinho");
		
		Predicate whereAno = qb.equal(join.get("ano"), qb.parameter(Integer.class, "ano"));
		Predicate whereSemestre = qb.equal(join.get("semestre"), qb.parameter(Integer.class, "semestre"));
		
		from.fetch("vestibulinho");
		
		CriteriaQuery<VestibulinhoPrestado> select = cq.select(from);
		select.where(qb.and(whereAno, whereSemestre));
		
		TypedQuery<VestibulinhoPrestado> query = em().createQuery(cq);
		
		try {
			query.setParameter("ano", ano);
			query.setParameter("semestre", semestre);
			List<VestibulinhoPrestado> list = query.getResultList();

			return null;
		} catch (NoResultException e) {
			return Collections.emptyMap();
		}
	}
	
	@Override
	public List<Candidato> findByVestibulinhoAndCurso(int ano, int semestre, long curso, boolean primeiraOpcao) {
		return null;
	}
	
	@Override
 	public List<Candidato> findByVestibulinhoAndCurso(int ano, int semestre, String curso, boolean primeiraOpcao) {
		return null;
	}
}

package br.org.sae.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.OpcaoPrestada;
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

	@Override
	@Transactional(noRollbackFor={NoResultException.class})
	public void saveOrUpdate(Candidato value) {
		Candidato stored = findByCpfOrNome(value.getCpf(), value.getNome());
		
		if(stored == null){
			super.save(value);
		}
		else{
			value.setCodigo(stored.getCodigo());
			List<VestibulinhoPrestado> vestibulinhos = stored.getVestibulinhos();
			
			for (VestibulinhoPrestado vestibulinho : vestibulinhos) {
				value.addVestibulinho(vestibulinho);
			}
			
			super.update(value);
		}

		List<VestibulinhoPrestado> prestados = value.getVestibulinhos();
		
		for (VestibulinhoPrestado prestado : prestados) {
			TypedQuery<VestibulinhoPrestado> query = em().createNamedQuery("VestibulinhoPrestadoPorAnoSemestre", VestibulinhoPrestado.class);
			query.setParameter("candidato", value);
			query.setParameter("ano", prestado.getVestibulinho().getAno());
			query.setParameter("semestre", prestado.getVestibulinho().getSemestre());

			try {
				query.getSingleResult();
			} catch (NoResultException e) {
				em().persist(prestado);
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
		Predicate whereAno = qb.equal(join.get("ano"), ano);
		Predicate whereSemestre = qb.equal(join.get("semestre"), semestre);
		
		cq.where(qb.and(whereAno, whereSemestre));
		
		TypedQuery<VestibulinhoPrestado> query = em().createQuery(cq);
		
		try {
			List<VestibulinhoPrestado> list = query.getResultList();
			Map<Curso, List<Candidato>> resultado =  new HashMap<>();
			
			for (VestibulinhoPrestado prestado : list) {
				OpcaoPrestada opcao = primeiraOpcao ? prestado.getPrimeiraOpcao() : prestado.getSegundaOpcao();
				
				if(opcao == null){
					continue;
				}
				
				Curso curso = opcao.getCurso();
				
				if(resultado.containsKey(curso)){
					resultado.get(curso).add(prestado.getCandidato());
				}
				else{
					List<Candidato> candidatos = new ArrayList<>();
					candidatos.add(prestado.getCandidato());
					
					resultado.put(curso, candidatos);
				}
			}

			return resultado;
		} catch (NoResultException e) {
			return Collections.emptyMap();
		}
	}
	
	@Override
	public List<Candidato> findByVestibulinhoAndCurso(int ano, int semestre, long curso, boolean primeiraOpcao) {
		return findByVestibulinhoAndCurso(ano, semestre, em().find(Curso.class, curso), primeiraOpcao);
	}
	
	@Override
 	public List<Candidato> findByVestibulinhoAndCurso(int ano, int semestre, String curso, boolean primeiraOpcao) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Curso> cq = qb.createQuery(Curso.class);
		Root<Curso> from = cq.from(Curso.class);
		cq.where(qb.equal(from.get("nome"), curso));
		
		TypedQuery<Curso> query = em().createQuery(cq).setMaxResults(1);
		
		try {
			return findByVestibulinhoAndCurso(ano, semestre, query.getSingleResult(), primeiraOpcao);
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	private List<Candidato> findByVestibulinhoAndCurso(int ano, int semestre, Curso curso, boolean primeiraOpcao) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Candidato> cq = qb.createQuery(Candidato.class);
		Root<Candidato> from = cq.from(Candidato.class);
		Join<Object, Object> joinPrestado = from.join("vestibulinhos");
		Join<Object, Object> joinVestibulinho = joinPrestado.join("vestibulinho");
		
		Predicate whereAno = qb.equal(joinVestibulinho.get("ano"), ano);
		Predicate whereSemestre = qb.equal(joinVestibulinho.get("semestre"), semestre);
		Predicate whereCurso = primeiraOpcao ? 
									qb.equal(joinPrestado.get("primeiraOpcao").get("curso"), curso) :
									qb.equal(joinPrestado.get("segundaOpcao").get("curso"), curso);
		
		CriteriaQuery<Candidato> select = cq.select(from);
		select.where(qb.and(whereAno, whereSemestre, whereCurso));
		
		try {
			return em().createQuery(select).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}

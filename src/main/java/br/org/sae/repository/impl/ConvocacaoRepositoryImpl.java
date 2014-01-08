package br.org.sae.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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
import br.org.sae.model.Matricula;
import br.org.sae.model.StatusCandidato;
import br.org.sae.model.Turma;
import br.org.sae.model.VestibulinhoPrestado;
import br.org.sae.repository.ConvocacaoRepository;
import br.org.sae.repository.TurmaRepository;
import br.org.sae.util.OpcaoVestibulinho;

@Repository
public class ConvocacaoRepositoryImpl implements ConvocacaoRepository{

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Override
	@Transactional
	public void resetConvocados(int ano, int semestre) {
		List<Turma> turmas = turmaRepository.all(ano, semestre);
		
		for (Turma turma : turmas) {
			OpcaoVestibulinho[] values = OpcaoVestibulinho.values();
			
			for (OpcaoVestibulinho opcao : values) {
				List<Candidato> convocados = getCandidatosConvocados(turma, opcao);
				
				for (Candidato candidato : convocados) {
					List<VestibulinhoPrestado> prestados = candidato.getVestibulinhos();
					
					for (VestibulinhoPrestado prestado : prestados) {
						prestado.getPrimeiraOpcao().setStatus(StatusCandidato.INSCRITO);
						
						if(prestado.getSegundaOpcao() != null){
							prestado.getSegundaOpcao().setStatus(StatusCandidato.INSCRITO);
						}
						
						em.merge(prestado);
					}
					
					em.merge(candidato);
				}
			}
		}
	}
	
	@Override
	public List<Candidato> getCandidatosInscritos(Turma turma, OpcaoVestibulinho opcao){
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Candidato> cq = qb.createQuery(Candidato.class);
		
		Root<Candidato> from = cq.from(Candidato.class);
		Join<Object, Object> joinPrestado = from.join("vestibulinhos");
		Join<Object, Object> joinVestibulinho = joinPrestado.join("vestibulinho");
		
		Predicate whereAno = qb.equal(joinVestibulinho.get("ano"), turma.getAno());
		Predicate whereSemestre = qb.equal(joinVestibulinho.get("semestre"), turma.getSemestre());
		Predicate where = qb.and(whereAno, whereSemestre);
		
		CriteriaQuery<Candidato> select = cq.select(from);
		
		if(opcao==OpcaoVestibulinho.PRIMEIRA_OPCAO){
			where = qb.and(where, 
					       qb.equal(joinPrestado.get("primeiraOpcao").get("status"), StatusCandidato.INSCRITO),
					       qb.equal(joinPrestado.get("primeiraOpcao").get("curso"), turma.getCurso()),
					       qb.equal(joinPrestado.get("primeiraOpcao").get("periodo"), turma.getPeriodo()));
		}
		else if (opcao ==OpcaoVestibulinho.SEGUNDA_OPCAO){
			where = qb.and(where, 
						   qb.equal(joinPrestado.get("segundaOpcao").get("status"), StatusCandidato.INSCRITO),
					       qb.equal(joinPrestado.get("segundaOpcao").get("curso"), turma.getCurso()),
				           qb.equal(joinPrestado.get("segundaOpcao").get("periodo"), turma.getPeriodo()));
		}
		else{
			where = qb.and(where, qb.and(qb.notEqual(joinPrestado.get("primeiraOpcao").get("status"), StatusCandidato.MATRICULADO), 
              					         qb.notEqual(joinPrestado.get("primeiraOpcao").get("status"), StatusCandidato.DESMATRICULADO), 
					                     qb.notEqual(joinPrestado.get("segundaOpcao").get("status"), StatusCandidato.MATRICULADO),
					                     qb.notEqual(joinPrestado.get("segundaOpcao").get("status"), StatusCandidato.DESMATRICULADO)));
		}
		
		select.where(where);

		try {
			TypedQuery<Candidato> query = em.createQuery(select);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<Candidato> getCandidatosConvocados(Turma turma, OpcaoVestibulinho opcao){
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Candidato> cq = qb.createQuery(Candidato.class);
		
		Root<Candidato> from = cq.from(Candidato.class);
		Join<Object, Object> joinPrestado = from.join("vestibulinhos");
		Join<Object, Object> joinVestibulinho = joinPrestado.join("vestibulinho");
		
		Predicate whereAno = qb.equal(joinVestibulinho.get("ano"), turma.getAno());
		Predicate whereSemestre = qb.equal(joinVestibulinho.get("semestre"), turma.getSemestre());
		Predicate where = qb.and(whereAno, whereSemestre);
		
		CriteriaQuery<Candidato> select = cq.select(from);
		
		if(opcao==OpcaoVestibulinho.PRIMEIRA_OPCAO){
			where = qb.and(where, 
					       qb.equal(joinPrestado.get("primeiraOpcao").get("status"), StatusCandidato.CONVOCADO),
					       qb.equal(joinPrestado.get("primeiraOpcao").get("curso"), turma.getCurso()),
					       qb.equal(joinPrestado.get("primeiraOpcao").get("periodo"), turma.getPeriodo()));
		}
		else if (opcao ==OpcaoVestibulinho.SEGUNDA_OPCAO){
			where = qb.and(where, 
					       qb.equal(joinPrestado.get("segundaOpcao").get("status"), StatusCandidato.CONVOCADO),
					       qb.equal(joinPrestado.get("segundaOpcao").get("curso"), turma.getCurso()),
					       qb.equal(joinPrestado.get("segundaOpcao").get("periodo"), turma.getPeriodo()));
		}
		
		select.where(where);
		
		try {
			TypedQuery<Candidato> query = em.createQuery(select);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public int vagasDisponiveis(Turma turma){
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Matricula> cq = qb.createQuery(Matricula.class);

		Root<Matricula> from = cq.from(Matricula.class);
		
		Predicate whereTurma = qb.equal(from.get("turma"), turma);
		
		CriteriaQuery<Matricula> select = cq.select(from);
		select.where(qb.and(whereTurma));
		
		TypedQuery<Matricula> query = em.createQuery(select);
		
		try {
			List<Matricula> list = query.getResultList();
			
			return turma.getCurso().getVagas() - list.size();
		} catch (NoResultException e) {
			return turma.getCurso().getVagas();
		}
	}
}

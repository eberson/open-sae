package br.org.sae.repository.impl;

import java.util.Collections;
import java.util.List;

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

import br.org.sae.model.Aluno;
import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.Etapa;
import br.org.sae.model.Matricula;
import br.org.sae.model.Periodo;
import br.org.sae.model.StatusCandidato;
import br.org.sae.model.StatusMatricula;
import br.org.sae.model.Turma;
import br.org.sae.model.Vestibulinho;
import br.org.sae.model.VestibulinhoPrestado;
import br.org.sae.repository.AlunoRepository;
import br.org.sae.repository.CandidatoRepository;
import br.org.sae.repository.MatriculaRepository;
import br.org.sae.repository.TurmaRepository;
import br.org.sae.util.OpcaoVestibulinho;

@Repository
public class MatriculaRepositoryImpl extends RepositoryImpl<Matricula> implements MatriculaRepository{

	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private CandidatoRepository candidatoRepository;
	
	@Transactional
	public void marcaConvocado(Turma turma, List<Candidato> candidatos) {
		for (Candidato candidato : candidatos) {
			List<VestibulinhoPrestado> vestibulinhos = candidato.getVestibulinhosPrestados();
			
			for (VestibulinhoPrestado prestado : vestibulinhos) {
				Vestibulinho vestibulinho = prestado.getVestibulinho();
				
				if(vestibulinho.getAno() != turma.getAno() || vestibulinho.getSemestre() != turma.getSemestre()){
					continue;
				}
				
				if(prestado.getPrimeiraOpcao().getCurso().equals(turma.getCurso())){
					prestado.getPrimeiraOpcao().setStatus(StatusCandidato.CONVOCADO);
				}
				else {
					prestado.getSegundaOpcao().setStatus(StatusCandidato.CONVOCADO);
				}

				em().merge(prestado);
			}

			em().merge(candidato);
		}
	}
	
	@Override
	@Transactional
	public void marcaExpirado(Turma turma, List<Candidato> candidatos) {
		for (Candidato candidato : candidatos) {
			List<VestibulinhoPrestado> vestibulinhos = candidato.getVestibulinhosPrestados();
			
			for (VestibulinhoPrestado prestado : vestibulinhos) {
				Vestibulinho vestibulinho = prestado.getVestibulinho();
				
				if(vestibulinho.getAno() != turma.getAno() || vestibulinho.getSemestre() != turma.getSemestre()){
					continue;
				}
				
				if(prestado.getPrimeiraOpcao().getCurso().equals(turma.getCurso())){
					prestado.getPrimeiraOpcao().setStatus(StatusCandidato.EXPIRADO);
				}
				else {
					prestado.getSegundaOpcao().setStatus(StatusCandidato.EXPIRADO);
				}

				em().merge(prestado);
			}

			em().merge(candidato);
		}
	}
	
	@Override
	@Transactional
	public void marcaMatriculado(Turma turma, Candidato... candidatos) {
		for (Candidato candidato : candidatos) {
			List<VestibulinhoPrestado> vestibulinhos = candidato.getVestibulinhosPrestados();
			
			for (VestibulinhoPrestado prestado : vestibulinhos) {
				Vestibulinho vestibulinho = prestado.getVestibulinho();
				
				if(vestibulinho.getAno() != turma.getAno() || vestibulinho.getSemestre() != turma.getSemestre()){
					continue;
				}
				
				if(prestado.getPrimeiraOpcao().getCurso().equals(turma.getCurso())){
					prestado.getPrimeiraOpcao().setStatus(StatusCandidato.MATRICULADO);
				}
				else {
					prestado.getSegundaOpcao().setStatus(StatusCandidato.MATRICULADO);
				}

				em().merge(prestado);
			}
			
			em().merge(candidato);
		}
	}

	@Override
	@Transactional
	public void matricular(Matricula matricula) {
		if(!alunoRepository.exists(matricula.getAluno())){
			saveImpl(matricula.getAluno());
		}
		else{
			Aluno aluno = alunoRepository.findByCPF(matricula.getAluno().getCpf());
			matricula.getAluno().setCodigo(aluno.getCodigo());
		}
		
		save(matricula);
	}
	
	@Override
	@Transactional
	public void matricularExcepcionalmente(Matricula matricula) {
		matricular(matricula);
	}

	@Override
	@Transactional
	public void cancelarMatricula(Matricula matricula) {
		Candidato candidato = findCandidato(matricula);
		
		Etapa etapa = matricula.getEtapa();
		Turma turma = etapa.getTurma();
		
		List<VestibulinhoPrestado> prestados = getVestibulinhosPrestados(candidato);
		VestibulinhoPrestado prestado = null;
		
		for (VestibulinhoPrestado aprestado : prestados) {
			Vestibulinho vestibulinho = aprestado.getVestibulinho();
			
			if(vestibulinho.getAno() == etapa.getAno() && vestibulinho.getSemestre() == etapa.getSemestre()){
				prestado = aprestado;
				break;
			}
		}
		
		if(prestado != null){
			// significa que o aluno está sendo desmatriculado no primeiro módulo... 
			
			if(!etapa.hasListaPiloto()){
				//se a lista piloto não foi gerada, podemos remover o aluno, a matrícula e ajustar o candidato para desmatriculado
				em().remove(em().getReference(Matricula.class, matricula.getCodigo()));
				em().remove(em().getReference(Aluno.class, matricula.getAluno().getCodigo()));

				if(prestado.getPrimeiraOpcao().getCurso().equals(turma.getCurso())){
					prestado.getPrimeiraOpcao().setStatus(StatusCandidato.DESMATRICULADO);
				}
				else {
					prestado.getSegundaOpcao().setStatus(StatusCandidato.DESMATRICULADO);
				}

				em().merge(prestado);
				em().merge(candidato);
				return;
			}
		}
		
		matricula.setStatus(StatusMatricula.MATRICULA_CANCELADA);
		em().merge(matricula);
	}
	
	@Override
	public Matricula find(Aluno aluno, Turma turma) {
		if(aluno.getCodigo() == null){
			return null;
		}
		
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Matricula> cq = qb.createQuery(Matricula.class);
		Root<Matricula> from = cq.from(Matricula.class);
		
		CriteriaQuery<Matricula> select = cq.select(from);
		select.where(qb.and(qb.equal(from.get("etapa"), turma.getEtapaAtual()),
				            qb.equal(from.get("aluno"), aluno)));
		
		try {
			return em().createQuery(select).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public List<Matricula> find(Aluno aluno, int ano, int semestre) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Matricula> cq = qb.createQuery(Matricula.class);
		Root<Matricula> from = cq.from(Matricula.class);
		
		CriteriaQuery<Matricula> select = cq.select(from);
		select.where(qb.and(qb.equal(from.get("etapa").get("ano"), ano),
				            qb.equal(from.get("etapa").get("semestre"), semestre),
				            qb.equal(from.get("aluno"), aluno)));
		
		try {
			return em().createQuery(select).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<Matricula> find(Aluno aluno) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Matricula> cq = qb.createQuery(Matricula.class);
		Root<Matricula> from = cq.from(Matricula.class);
		
		CriteriaQuery<Matricula> select = cq.select(from);
		select.where(qb.equal(from.get("aluno"), aluno));
		
		try {
			return em().createQuery(select).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<VestibulinhoPrestado> getVestibulinhosPrestados(Candidato candidato) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<VestibulinhoPrestado> cq = qb.createQuery(VestibulinhoPrestado.class);
		Root<VestibulinhoPrestado> from = cq.from(VestibulinhoPrestado.class);
		
		CriteriaQuery<VestibulinhoPrestado> select = cq.select(from);
		select.where(qb.equal(from.get("candidato"), candidato));
		
		try {
			return em().createQuery(select).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	private Candidato findCandidato(Matricula matricula) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Candidato> cq = qb.createQuery(Candidato.class);
		Root<Candidato> from = cq.from(Candidato.class);
		
		CriteriaQuery<Candidato> select = cq.select(from);
		select.where(qb.equal(from.get("cpf"), matricula.getAluno().getCpf()));
		
		TypedQuery<Candidato> query = em().createQuery(select);
		Candidato candidato = query.getSingleResult();
		return candidato;
	}
	
	@Override
	public boolean candidatoEstaVestibulinho(Candidato candidato, Turma turma) {
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Candidato> cq = qb.createQuery(Candidato.class);
		
		Root<Candidato> from = cq.from(Candidato.class);
		Join<Object, Object> joinPrestado = from.join("vestibulinhosPrestados");
		Join<Object, Object> joinVestibulinho = joinPrestado.join("vestibulinho");
		
		Predicate whereAno = qb.equal(joinVestibulinho.get("ano"), turma.getAno());
		Predicate whereSemestre = qb.equal(joinVestibulinho.get("semestre"), turma.getSemestre());
		Predicate whereStatus = qb.equal(from.get("status"), StatusCandidato.CONVOCADO);
		Predicate whereCodigo = qb.equal(from.get("codigo"), candidato.getCodigo());
		Predicate where = qb.and(whereCodigo, whereAno, whereSemestre, whereStatus);
		
		CriteriaQuery<Candidato> select = cq.select(from);
		
		where = qb.and(where,
					   qb.or(qb.and(qb.equal(joinPrestado.get("primeiraOpcao").get("curso"), turma.getCurso()),
						            qb.equal(joinPrestado.get("primeiraOpcao").get("periodo"), turma.getPeriodo())),
						     qb.and(qb.equal(joinPrestado.get("segundaOpcao").get("curso"), turma.getCurso()),
							        qb.equal(joinPrestado.get("segundaOpcao").get("periodo"), turma.getPeriodo()))));
		
		select.where(where);

		try {
			TypedQuery<Candidato> query = em().createQuery(select);
			return query.getSingleResult() != null;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	protected Class<Matricula> type() {
		return Matricula.class;
	}
	
	@Override
	@Transactional
	public void resetConvocados(int ano, int semestre) {
		List<Turma> turmas = turmaRepository.all(ano, semestre);
		
		for (Turma turma : turmas) {
			OpcaoVestibulinho[] values = OpcaoVestibulinho.values();
			
			for (OpcaoVestibulinho opcao : values) {
				List<Candidato> convocados = getCandidatosConvocados(turma, opcao);
				
				for (Candidato candidato : convocados) {
					List<VestibulinhoPrestado> prestados = candidato.getVestibulinhosPrestados();
					
					for (VestibulinhoPrestado prestado : prestados) {
						prestado.getPrimeiraOpcao().setStatus(StatusCandidato.INSCRITO);
						
						if(prestado.getSegundaOpcao() != null){
							prestado.getSegundaOpcao().setStatus(StatusCandidato.INSCRITO);
						}
						
						em().merge(prestado);
					}
					
					em().merge(candidato);
				}
			}
		}
	}
	
	@Override
	public List<Candidato> getCandidatosInscritos(Turma turma, OpcaoVestibulinho opcao){
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Candidato> cq = qb.createQuery(Candidato.class);
		
		Root<Candidato> from = cq.from(Candidato.class);
		Join<Object, Object> joinPrestado = from.join("vestibulinhosPrestados");
		Join<Object, Object> joinVestibulinho = joinPrestado.join("vestibulinho");
		
		Predicate whereAno = qb.equal(joinVestibulinho.get("ano"), turma.getAno());
		Predicate whereSemestre = qb.equal(joinVestibulinho.get("semestre"), turma.getSemestre());
		Predicate whereAusente = qb.equal(joinPrestado.get("ausente"), false);
		Predicate where = qb.and(whereAusente, whereAno, whereSemestre);
		
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
			TypedQuery<Candidato> query = em().createQuery(select);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<Candidato> getCandidatosConvocados(Turma turma, OpcaoVestibulinho opcao){
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Candidato> cq = qb.createQuery(Candidato.class);
		
		Root<Candidato> from = cq.from(Candidato.class);
		Join<Object, Object> joinPrestado = from.join("vestibulinhosPrestados");
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
			TypedQuery<Candidato> query = em().createQuery(select);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public int vagasDisponiveis(Turma turma){
		CriteriaBuilder qb = em().getCriteriaBuilder();
		CriteriaQuery<Matricula> cq = qb.createQuery(Matricula.class);

		Root<Matricula> from = cq.from(Matricula.class);
		
		CriteriaQuery<Matricula> select = cq.select(from);
		select.where(qb.and(qb.equal(from.get("etapa"), turma.getEtapaAtual()),
				            qb.equal(from.get("status"), StatusMatricula.ATIVO)));
		
		TypedQuery<Matricula> query = em().createQuery(select);
		
		try {
			List<Matricula> list = query.getResultList();
			
			return turma.getCurso().getVagas() - list.size();
		} catch (NoResultException e) {
			return turma.getCurso().getVagas();
		}
	}

	@Override
	public List<Aluno> getAlunosMatriculados(Curso curso, Periodo periodo) {
		return Collections.emptyList();
	}
}

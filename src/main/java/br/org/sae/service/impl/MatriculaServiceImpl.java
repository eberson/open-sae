package br.org.sae.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.exception.MatriculaInvalidaException;
import br.org.sae.model.Aluno;
import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.Etapa;
import br.org.sae.model.Matricula;
import br.org.sae.model.OpcaoPrestada;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;
import br.org.sae.model.VestibulinhoPrestado;
import br.org.sae.repository.CandidatoRepository;
import br.org.sae.repository.CursoRepository;
import br.org.sae.repository.MatriculaRepository;
import br.org.sae.repository.Repository;
import br.org.sae.repository.TurmaRepository;
import br.org.sae.service.AlunoService;
import br.org.sae.service.MatriculaService;
import br.org.sae.service.RespostaCancelamentoMatricula;
import br.org.sae.service.RespostaMatricula;
import br.org.sae.service.RespostaRematricula;
import br.org.sae.util.ClassificadorCandidatos;
import br.org.sae.util.OpcaoVestibulinho;

@Service
public class MatriculaServiceImpl extends EntityServiceImpl<Matricula> implements MatriculaService{

	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private MatriculaRepository repository;

	@Autowired
	private CandidatoRepository candidatoRepository;

	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private MatriculaRepository mrepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Override
	protected Repository<Matricula> repository() {
		return repository;
	}

	@Override
	public int vagasDisponiveis(Turma turma) {
		return repository.vagasDisponiveis(turma);
	}

	@Override
	public Map<Turma, List<Candidato>> convoca(int ano, int semestre) {
		if(!vestibulinhoValido(ano, semestre)){
			return Collections.emptyMap();
		}
		
		List<Turma> turmas = turmaRepository.all(ano, semestre);
		Map<Turma, List<Candidato>> resultado = new HashMap<>();
		
		for (Turma turma : turmas) {
			List<Candidato> chamados = convocaImpl(turma);
			mrepository.marcaConvocado(turma, chamados);
			
			resultado.put(turma, chamados);
		}
		
		return resultado;
	}

	@Override
	public Map<Turma, List<Candidato>> carregaConvocados(int ano, int semestre) {
		List<Turma> turmas = turmaRepository.all(ano, semestre);
		Map<Turma, List<Candidato>> resultado = new HashMap<>();
		
		for (Turma turma : turmas) {
			List<Candidato> chamados = carregaConvocadosImpl(turma);
			resultado.put(turma, chamados);
		}
		
		return resultado;
	}
	
	@Override
	public RespostaMatricula matricular(Candidato candidato, Turma turma, Date data) {
		try {
			checkCandidatoMatricula(candidato, turma);
			matricularImpl(candidato, turma, data);
			return RespostaMatricula.SUCESSO;
		} catch (MatriculaInvalidaException e) {
			return RespostaMatricula.MATRICULA_INVALIDA;
		} catch (Exception e) {
			return RespostaMatricula.ERRO_DESCONHECIDO;
		}
	}

	@Override
	public RespostaMatricula matricularExcepcionalmente(Candidato candidato, Turma turma, Date data) {
		try {
			matricularImpl(candidato, turma, data);
			return RespostaMatricula.SUCESSO;
		} catch (MatriculaInvalidaException e) {
			return RespostaMatricula.MATRICULA_INVALIDA;
		} catch (Exception e) {
			return RespostaMatricula.ERRO_DESCONHECIDO;
		}
	}

	@Override
	public RespostaCancelamentoMatricula cancelarMatricula(Aluno aluno, Turma turma) {
		try {
			repository.cancelarMatricula(repository.find(aluno, turma));
			return RespostaCancelamentoMatricula.SUCESSO;
		} catch (Exception e) {
			return RespostaCancelamentoMatricula.ERRO_DESCONHECIDO;
		}
	}
	
	@Override
	public RespostaRematricula rematricular(Aluno aluno, Turma turma, Date data) {
		try {
			if(aluno == null){
				return RespostaRematricula.REMATRICULA_INVALIDA;
			}

			checkAlunoMatriculado(turma, aluno);
			
			Matricula matricula = createMatricula(aluno, turma, data);
			
			repository.matricular(matricula);
			
			return RespostaRematricula.SUCESSO;
		} catch (MatriculaInvalidaException e) {
			return RespostaRematricula.REMATRICULA_INVALIDA;
		} catch (Exception e) {
			return RespostaRematricula.ERRO_DESCONHECIDO;
		}
	}

	private Matricula createMatricula(Aluno aluno, Turma turma, Date data) {
		Matricula matricula = new Matricula();
		matricula.setAluno(aluno);
		matricula.setData(data);
		matricula.setEtapa(turma.getEtapaAtual());
		
		return matricula;
	}
	
	@Override
	public List<Matricula> findAll(Aluno aluno) {
		return repository.find(aluno);
	}
	
	private void checkCandidatoMatricula(Candidato candidato, Turma turma){
		if(candidato == null || turma == null){
			throw new MatriculaInvalidaException();
		}
		
		if(vagasDisponiveis(turma) <= 0){
			throw new MatriculaInvalidaException("Não existem vagas disponíveis para a turma informada!");
		}
		
		List<VestibulinhoPrestado> vestibulinhos = repository.getVestibulinhosPrestados(candidato);
		
		for (VestibulinhoPrestado vestibulinho : vestibulinhos) {
			if(validaCandidatoMatricula(vestibulinho, vestibulinho.getPrimeiraOpcao(), turma)){
				return;
			}

			if(validaCandidatoMatricula(vestibulinho, vestibulinho.getSegundaOpcao(), turma)){
				return;
			}
		}
		
		throw new MatriculaInvalidaException("O candidato não prestou vestibulinho para o curso que prentede matriculá-lo!");
	}
	
	private boolean validaCandidatoMatricula(VestibulinhoPrestado vestibulinho, OpcaoPrestada opcao, Turma turma){
		if(opcao == null){
			return false;
		}
		
		return opcao.getCurso().equals(turma.getCurso()) &&
				vestibulinho.getVestibulinho().getAno() == turma.getEtapaAtual().getAno() &&
				vestibulinho.getVestibulinho().getSemestre() == turma.getEtapaAtual().getSemestre();
	}
	
	@Override
	public List<Candidato> carregaConvocados(Curso curso, Periodo periodo) {
		SemestreInfo info = atual();
		
		List<Turma> turmas = turmaRepository.find(info.ano, info.semestre, curso, periodo);
		List<Candidato> convocados = new ArrayList<>();
		
		for (Turma turma : turmas) {
			List<Candidato> chamados = carregaConvocadosImpl(turma);
			convocados.addAll(chamados);
		}
		
		return convocados;
	}
	
	private List<Candidato> carregaConvocadosImpl(Turma turma){
		int vagasDisponiveis = vagasDisponiveis(turma);
		
		List<Candidato> chamados = new ArrayList<>();
		OpcaoVestibulinho[] opcoes = {OpcaoVestibulinho.PRIMEIRA_OPCAO, OpcaoVestibulinho.SEGUNDA_OPCAO};
		
		for (OpcaoVestibulinho opcao : opcoes) {
			List<Candidato> candidatos = repository.getCandidatosConvocados(turma, opcao);
			Collections.sort(candidatos, new ClassificadorCandidatos(turma.getAno(), turma.getSemestre(), opcao));
			chamados.addAll(candidatos);
			
			if(chamados.size() == vagasDisponiveis){
				break;
			}
		}
		
		return chamados;
	}

	private boolean vestibulinhoValido(int ano, int semestre){
		Calendar calendar = Calendar.getInstance();
		
		if(ano != calendar.get(Calendar.YEAR)){
			return false;
		}

		int semestreAtual = (calendar.get(Calendar.MONTH) + 1) < 7 ? 1 : 2; 
		
		if(semestre != semestreAtual){
			return false;
		}
		
		return true;
	}
	
	@Override
	public List<Candidato> convoca(Curso curso, Periodo periodo) {
		SemestreInfo info = atual();
		
		List<Turma> turmas = turmaRepository.find(info.ano, info.semestre, curso, periodo);
		List<Candidato> convocados = new ArrayList<>();
		
		for (Turma turma : turmas) {
			List<Candidato> chamados = convocaImpl(turma);
			mrepository.marcaConvocado(turma, chamados);
			convocados.addAll(chamados);
		}
		
		return convocados;
	}
	
	private List<Candidato> convocaImpl(Turma turma){
		int vagasDisponiveis = vagasDisponiveis(turma);
		
		List<Candidato> chamados = new ArrayList<>();
		OpcaoVestibulinho[] opcoes = {OpcaoVestibulinho.PRIMEIRA_OPCAO, OpcaoVestibulinho.SEGUNDA_OPCAO};
		
		for (OpcaoVestibulinho opcao : opcoes) {
			//antes de chamar marca os que já foram chamados para que não sejam chamados novamente
			mrepository.marcaExpirado(turma, repository.getCandidatosConvocados(turma, opcao));
			
			List<Candidato> candidatos = repository.getCandidatosInscritos(turma, opcao);
			Collections.sort(candidatos, new ClassificadorCandidatos(turma.getAno(), turma.getSemestre(), opcao));
			
			int totalChamados = candidatos.size() + chamados.size();
			
			if(totalChamados > vagasDisponiveis){
				while (totalChamados > vagasDisponiveis){
					candidatos.remove(candidatos.size() - 1);
					totalChamados--;
				}
			}
			
			chamados.addAll(candidatos);
			
			if(chamados.size() == vagasDisponiveis){
				break;
			}
		}
		
		return chamados;
	}
	
	private void matricularImpl(Candidato candidato, Turma turma, Date data) {
		Aluno aluno = alunoService.findByCPF(candidato.getCpf());

		if(aluno != null){
			checkAlunoMatriculado(turma, aluno);
		}
		else{
			aluno = alunoService.from(candidato);
		}
		
		Matricula matricula = createMatricula(aluno, turma, data);
		
		repository.matricular(matricula);
		repository.marcaMatriculado(turma, candidato);
	}

	private void checkAlunoMatriculado(Turma turma, Aluno aluno) {
		if(aluno != null){
			Matricula m1 = repository.find(aluno, turma);
			
			if(m1 != null){
				throw new MatriculaInvalidaException("O aluno já se encontra matriculado neste curso!");
			}
			
			Etapa etapaAtual = turma.getEtapaAtual();
			
			List<Matricula> matriculas = repository.find(aluno, etapaAtual.getAno(), etapaAtual.getSemestre());
			
			for (Matricula matricula : matriculas) {
				Turma stored = matricula.getEtapa().getTurma();
				if(stored.getPeriodo() == turma.getPeriodo()){
					throw new MatriculaInvalidaException("O aluno já se encontra matriculado em outro curso neste período!");
				}
			}
		}
	}
	
	public SemestreInfo atual(){
		Calendar calendar = Calendar.getInstance();
		
		SemestreInfo info = new SemestreInfo();
		info.ano = calendar.get(Calendar.YEAR);
		info.semestre  = calendar.get(Calendar.MONTH) <= 5 ? 1 : 2;
		
		return info;
	}
	
	private class SemestreInfo{
		private int ano;
		private int semestre;
	}

}
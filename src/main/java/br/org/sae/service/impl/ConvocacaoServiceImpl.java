package br.org.sae.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;
import br.org.sae.repository.CandidatoRepository;
import br.org.sae.repository.ConvocacaoRepository;
import br.org.sae.repository.CursoRepository;
import br.org.sae.repository.MatriculaRepository;
import br.org.sae.repository.TurmaRepository;
import br.org.sae.service.AlunoService;
import br.org.sae.service.ConvocacaoService;
import br.org.sae.util.ClassificadorCandidatos;
import br.org.sae.util.OpcaoVestibulinho;

@Service
public class ConvocacaoServiceImpl implements ConvocacaoService{

	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private CandidatoRepository candidatoRepository;

	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private MatriculaRepository mrepository;
	
	@Autowired
	private ConvocacaoRepository repository;
	
	@Autowired
	private TurmaRepository turmaRepository;

	@Override
	public int vagasDisponiveis(Turma turma) {
		return mrepository.vagasDisponiveis(turma);
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
	public List<Candidato> convoca(int ano, int semestre, long curso, Periodo periodo) {
		return convoca(ano, semestre, cursoRepository.find(curso), periodo);
	}

	@Override
	public List<Candidato> convoca(int ano, int semestre, String curso, Periodo periodo) {
		return convoca(ano, semestre, cursoRepository.findByName(curso), periodo);
	}

	private List<Candidato> convoca(int ano, int semestre, Curso curso, Periodo periodo) {
		if(!vestibulinhoValido(ano, semestre)){
			return Collections.emptyList();
		}
		
		List<Turma> turmas = turmaRepository.find(ano, semestre, curso, periodo);
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
	public List<Candidato> carregaConvocados(int ano, int semestre, long curso, Periodo periodo) {
		return carregaConvocados(ano, semestre, cursoRepository.find(curso), periodo);
	}
	
	@Override
	public List<Candidato> carregaConvocados(int ano, int semestre, String curso, Periodo periodo) {
		return carregaConvocados(ano, semestre, cursoRepository.findByName(curso), periodo);
	}

	private List<Candidato> carregaConvocados(int ano, int semestre, Curso curso, Periodo periodo) {
		List<Turma> turmas = turmaRepository.find(ano, semestre, curso, periodo);
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
}

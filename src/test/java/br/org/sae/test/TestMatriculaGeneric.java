package br.org.sae.test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import br.org.sae.model.Curso;
import br.org.sae.model.Etapa;
import br.org.sae.model.Modulo;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;
import br.org.sae.repository.AlunoRepository;
import br.org.sae.repository.CandidatoRepository;
import br.org.sae.repository.CursoRepository;
import br.org.sae.repository.EtapaRepository;
import br.org.sae.repository.MatriculaRepository;
import br.org.sae.repository.ModuloRepository;
import br.org.sae.repository.TurmaRepository;
import br.org.sae.service.ImportFileType;
import br.org.sae.service.ImportService;
import br.org.sae.service.MatriculaService;
import br.org.sae.service.RespostaImportService;

public class TestMatriculaGeneric {
	
	protected static final String ADMINISTRACAO = "ADMINISTRAÇÃO - (EAD - TELECURSO TEC)";
	protected static final String ENFERMAGEM = "ENFERMAGEM";
	protected static final String INFORMATICA = "INFORMÁTICA";
	protected static final String MECATRONICA = "MECATRÔNICA";
	protected static final String FINANCAS = "FINANÇAS";

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	protected EtapaRepository etapaRepository;
	
	@Autowired
	protected ModuloRepository moduloRepository;
	
	@Autowired
	protected TurmaRepository turmaRepository;
	
	@Autowired
	protected CursoRepository cursoRepository;
	
	@Autowired
	protected ImportService importador;
	
	@Autowired
	protected MatriculaRepository matriculaRepository;
	
	@Autowired
	protected AlunoRepository alunoRepository;

	@Autowired
	protected CandidatoRepository candidatoRepository;
	
	@Autowired
	protected MatriculaService service;
	
	protected static boolean loaded = false;

	protected static Curso financas;
	protected static Curso mecatronica;
	protected static Curso informatica;
	protected static Curso enfermagem;
	protected static Curso administracao;
	
	protected static int semestre;
	protected static int ano;

	protected static Turma tfinancas;
	protected static Turma tmecatronica;
	protected static Turma tinformartica;
	protected static Turma tenfermagem;
	protected static Turma tadministracao;
	
	protected void before(boolean hasVest2013, boolean hasVestAtual) throws Exception{
		if(loaded){
			return;
		}
		
		Calendar calendar = Calendar.getInstance();
		ano = calendar.get(Calendar.YEAR);
		semestre = ((calendar.get(Calendar.MONTH) + 1) < 7) ? 1 : 2;
		
		financas = new Curso(FINANCAS, 40);
		mecatronica = new Curso(MECATRONICA, 40);
		informatica = new Curso(INFORMATICA, 40);
		enfermagem = new Curso(ENFERMAGEM, 30);
		administracao = new Curso(ADMINISTRACAO, 40);
		
		cursoRepository.save(financas);
		cursoRepository.save(mecatronica);
		cursoRepository.save(informatica);
		cursoRepository.save(enfermagem);
		cursoRepository.save(administracao);

		generateModulos(financas);
		generateModulos(mecatronica);
		generateModulos(informatica);
		generateModulos(enfermagem);
		generateModulos(administracao);
		
		tfinancas = new Turma("Turma A", Periodo.NOITE, ano, semestre, financas, false);
		tmecatronica = new Turma("Turma B", Periodo.NOITE, ano, semestre, mecatronica, false);
		tinformartica = new Turma("Turma C", Periodo.NOITE, ano, semestre, informatica, false);
		tenfermagem = new Turma("Turma D", Periodo.MANHA, ano, semestre, enfermagem, false);
		tadministracao = new Turma("Turma E", Periodo.SABADOS_PE, ano, semestre, administracao, false);

		turmaRepository.save(tfinancas);
		turmaRepository.save(tmecatronica);
		turmaRepository.save(tinformartica);
		turmaRepository.save(tenfermagem);
		turmaRepository.save(tadministracao);
		
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tfinancas, financas.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tmecatronica, mecatronica.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tinformartica, informatica.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tenfermagem, enfermagem.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tadministracao, administracao.getModulos().get(0)));
		
		tfinancas = em.find(Turma.class, tfinancas.getCodigo());
		tmecatronica = em.find(Turma.class, tmecatronica.getCodigo());
		tinformartica = em.find(Turma.class, tinformartica.getCodigo());
		tenfermagem = em.find(Turma.class, tenfermagem.getCodigo());
		tadministracao = em.find(Turma.class, tadministracao.getCodigo());
		
		if(hasVest2013){
			importaCandidatos2013();
		}
		
		if(hasVestAtual){
			importaCandidatosAtual();
		}
		
		loaded = true;
	}
	
	protected void importaCandidatos2013() throws Exception{
		URI uri = getClass().getResource("matricula-dados-reais.xls").toURI();
		RespostaImportService resposta = importador.importar(ImportFileType.XLS, new FileInputStream(new File(uri)), 2013, 2);
		Assert.assertEquals(RespostaImportService.SUCESSO, resposta);
	}
	
	protected void importaCandidatosAtual() throws Exception{
		URI uri = getClass().getResource("matricula-dados-reais.xls").toURI();
		RespostaImportService resposta = importador.importar(ImportFileType.XLS, new FileInputStream(new File(uri)), ano, semestre);
		Assert.assertEquals(RespostaImportService.SUCESSO, resposta);
	}
	
	protected void after(){
		matriculaRepository.deleteAll();
		matriculaRepository.resetConvocados(ano, semestre);
		
		resetEtapa(tfinancas);
		resetEtapa(tmecatronica);
		resetEtapa(tinformartica);
		resetEtapa(tenfermagem);
		resetEtapa(tadministracao);
	}
	
	private void resetEtapa(Turma turma){
		List<Etapa> etapas = turma.getEtapas();
		
		for (Etapa etapa : etapas) {
			etapa.setListaPiloto(false);
			etapaRepository.update(etapa);
		}
	}
	
	protected void generateModulos(Curso curso) {
		Modulo primeiro = new Modulo();
		primeiro.setDescricao("Primeiro Módulo");

		Modulo segundo = new Modulo();
		segundo.setDescricao("Segundo Módulo");

		Modulo terceiro = new Modulo();
		terceiro.setDescricao("Terceiro Módulo");
		
		curso.addModulo(primeiro);
		curso.addModulo(segundo);
		curso.addModulo(terceiro);
		
		moduloRepository.save(primeiro);
		moduloRepository.save(segundo);
		moduloRepository.save(terceiro);
	}


}

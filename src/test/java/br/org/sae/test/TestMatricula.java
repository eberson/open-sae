package br.org.sae.test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import br.org.sae.exception.MatriculaInvalidaException;
import br.org.sae.model.Candidato;
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
import br.org.sae.service.RespostaMatricula;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/br/org/sae/test/opensae.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TestMatricula {

	private static final String ADMINISTRACAO = "ADMINISTRAÇÃO - (EAD - TELECURSO TEC)";
	private static final String ENFERMAGEM = "ENFERMAGEM";
	private static final String INFORMATICA = "INFORMÁTICA";
	private static final String MECATRONICA = "MECATRÔNICA";
	private static final String FINANCAS = "FINANÇAS";

	@Autowired
	private EtapaRepository etapaRepository;
	
	@Autowired
	private ModuloRepository moduloRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private ImportService importador;
	
	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private CandidatoRepository candidatoRepository;
	
	@Autowired
	private MatriculaService service;
	
	private static boolean loaded = false;

	private static Curso financas;
	private static Curso mecatronica;
	private static Curso informatica;
	private static Curso enfermagem;
	private static Curso administracao;
	
	private static int semestre;
	private static int ano;

	private static Turma tfinancas;
	private static Turma tmecatronica;
	private static Turma tinformartica;
	private static Turma tenfermagem;
	private static Turma tadministracao;
	
	@Test
	public void testMatriculaTurmaInteira(){
		List<Candidato> convocados = service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		for (Candidato candidato : convocados) {
			service.matricular(candidato, tinformartica, new Date());
		}
		
		Assert.assertEquals(0, service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE).size());
		Assert.assertEquals(0, service.carregaConvocados(ano, semestre, INFORMATICA, Periodo.NOITE).size());
	}
	
	@Test
	public void testMatriculaTodosDesmatriculaMetade(){
		List<Candidato> convocados = service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		List<Candidato> desmatriculados = new ArrayList<>();
		
		for (Candidato candidato : convocados) {
			service.matricular(candidato, tinformartica, new Date());
			
			if(desmatriculados.size() < 20){
				service.cancelarMatricula(alunoRepository.findByCPF(candidato.getCpf()), tinformartica);
				desmatriculados.add(candidato);
			}
		}
		
		Assert.assertEquals(0, service.carregaConvocados(ano, semestre, INFORMATICA, Periodo.NOITE).size());
		
		convocados = service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE);
		Assert.assertEquals(20, convocados.size());
		Assert.assertEquals(20, service.carregaConvocados(ano, semestre, INFORMATICA, Periodo.NOITE).size());
		Assert.assertNotEquals(desmatriculados, convocados);
		Assert.assertEquals(20, service.findAll().size());
	}

	@Test
	public void testMatriculaCursoTrocado(){
		List<Candidato> convocados = service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		RespostaMatricula resposta = service.matricular(convocados.get(0), tmecatronica, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}

	@Test
	public void testMatriculaMesmoCursoDuasVezes(){
		List<Candidato> convocados = service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Candidato candidato = convocados.get(0);
		RespostaMatricula resposta = service.matricular(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.SUCESSO, resposta);
		
		resposta = service.matricular(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}

	@Test
	public void testMatriculaDoisCursosMesmoPeriodo(){
		List<Candidato> convocados = service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Candidato candidato = convocados.get(0);

		RespostaMatricula resposta = service.matricular(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.SUCESSO, resposta);
		
		resposta = service.matricular(candidato, tmecatronica, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}
	
	@Test
	public void testMatriculaExcepcionalDoisCursosMesmoPeriodo(){
		List<Candidato> convocados = service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Candidato candidato = convocados.get(0);
		RespostaMatricula resposta = service.matricular(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.SUCESSO, resposta);
		
		resposta = service.matricularExcepcionalmente(candidato, tmecatronica, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}

	@Test
	public void testMatriculaDuasVezesMesmaTurmaEtapasDiferentes(){
		List<Candidato> convocados = service.convoca(ano, semestre, MECATRONICA, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Candidato candidato = convocados.get(0);
		RespostaMatricula resposta = service.matricular(candidato, tmecatronica, new Date());
		Assert.assertEquals(RespostaMatricula.SUCESSO, resposta);
		
		Etapa etapa = new Etapa();
		etapa.setAno(2014);
		etapa.setSemestre(1);
		etapa.setDescricao("2º Módulo");
		etapa.setModulo(mecatronica.getModulos().get(1));
		etapa.setTurma(tmecatronica);
		tmecatronica.setEtapaAtual(etapa);
		
		etapaRepository.save(etapa);
		turmaRepository.update(tmecatronica);
		
		resposta = service.matricular(candidato, tmecatronica, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}

	@Test
	public void testMatriculaExcepcionalMesmoCursoDuasVezes(){
		List<Candidato> convocados = service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Candidato candidato = convocados.get(0);
		RespostaMatricula resposta = service.matricular(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.SUCESSO, resposta);
		
		resposta = service.matricularExcepcionalmente(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}


	@Test
	public void testMatriculaTodosDesmatriculaMetadeDepoisListaPiloto(){
		List<Candidato> convocados = service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		List<Candidato> desmatriculados = new ArrayList<>();
		
		for (Candidato candidato : convocados) {
			service.matricular(candidato, tinformartica, new Date());
			
			if(desmatriculados.size() < 20){
				desmatriculados.add(candidato);
			}
		}
		
		tinformartica.getEtapaAtual().setListaPiloto(true);
		etapaRepository.update(tinformartica.getEtapaAtual());
		
		for (Candidato candidato : desmatriculados) {
			service.cancelarMatricula(alunoRepository.findByCPF(candidato.getCpf()), tinformartica);
		}
		
		Assert.assertEquals(0, service.carregaConvocados(ano, semestre, INFORMATICA, Periodo.NOITE).size());
		
		convocados = service.convoca(ano, semestre, INFORMATICA, Periodo.NOITE);
		Assert.assertEquals(20, convocados.size());
		Assert.assertEquals(20, service.carregaConvocados(ano, semestre, INFORMATICA, Periodo.NOITE).size());
		Assert.assertNotEquals(desmatriculados, convocados);
		Assert.assertEquals(40, service.findAll().size());
	}
	
	
	@Before
	public void before() throws Exception{
		if(loaded){
			return;
		}
		
		Calendar calendar = Calendar.getInstance();
		ano = calendar.get(Calendar.YEAR);
		semestre = ((calendar.get(Calendar.MONTH) + 1) < 7) ? 1 : 2;
		
		financas = new Curso(903, FINANCAS, 40);
		mecatronica = new Curso(914, MECATRONICA, 40);
		informatica = new Curso(912, INFORMATICA, 40);
		enfermagem = new Curso(910, ENFERMAGEM, 30);
		administracao = new Curso(898, ADMINISTRACAO, 40);
		
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
		
		tfinancas = new Turma("Turma A", Periodo.NOITE, ano, semestre, financas, false, true);
		tmecatronica = new Turma("Turma B", Periodo.NOITE, ano, semestre, mecatronica, false, true);
		tinformartica = new Turma("Turma C", Periodo.NOITE, ano, semestre, informatica, false, true);
		tenfermagem = new Turma("Turma D", Periodo.MANHA, ano, semestre, enfermagem, false, true);
		tadministracao = new Turma("Turma E", Periodo.SABADOS_PE, ano, semestre, administracao, false, true);

		turmaRepository.save(tfinancas);
		turmaRepository.save(tmecatronica);
		turmaRepository.save(tinformartica);
		turmaRepository.save(tenfermagem);
		turmaRepository.save(tadministracao);
		
		URI uri = getClass().getResource("matricula-dados-reais.xls").toURI();
		RespostaImportService resposta = importador.importar(ImportFileType.XLS, new FileInputStream(new File(uri)), ano, semestre);
		Assert.assertEquals(RespostaImportService.SUCESSO, resposta);
		
		loaded = true;
	}
	
	@After
	public void after(){
		matriculaRepository.deleteAll();
		matriculaRepository.resetConvocados(ano, semestre);
		
		tinformartica.getEtapaAtual().setListaPiloto(false);
		etapaRepository.update(tinformartica.getEtapaAtual());
	}
	
	private void generateModulos(Curso curso) {
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

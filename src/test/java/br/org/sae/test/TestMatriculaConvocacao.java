package br.org.sae.test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import br.org.sae.model.Modulo;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;
import br.org.sae.repository.AlunoRepository;
import br.org.sae.repository.CandidatoRepository;
import br.org.sae.repository.CursoRepository;
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
public class TestMatriculaConvocacao {
	
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
	
	@Before
	public void before() throws Exception{
		if(loaded){
			return;
		}
		
		Calendar calendar = Calendar.getInstance();
		ano = calendar.get(Calendar.YEAR);
		semestre = ((calendar.get(Calendar.MONTH) + 1) < 7) ? 1 : 2;
		
		financas = new Curso(903, "FINANÇAS", 40);
		mecatronica = new Curso(914, "MECATRÔNICA", 40);
		informatica = new Curso(912, "INFORMÁTICA", 40);
		enfermagem = new Curso(910, "ENFERMAGEM", 30);
		administracao = new Curso(898, "ADMINISTRAÇÃO - (EAD - TELECURSO TEC)", 40);
		
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
		RespostaImportService resposta = importador.importar(ImportFileType.XLS, new FileInputStream(new File(uri)), 2013, 2);
		Assert.assertEquals(RespostaImportService.SUCESSO, resposta);

		resposta = importador.importar(ImportFileType.XLS, new FileInputStream(new File(uri)), ano, 1);
		Assert.assertEquals(RespostaImportService.SUCESSO, resposta);
		
		loaded = true;
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

	@After
	public void after(){
		matriculaRepository.deleteAll();
		matriculaRepository.resetConvocados(ano, semestre);
	}
	
	@Test
	public void testChamadaMatriculaGeralSemCandidatos(){
		Map<Turma, List<Candidato>> chamada = service.convoca(2013, 1);
		Assert.assertEquals(0, chamada.size());
	}

	@Test
	public void testChamadaMatriculaGeralSemestreAnterior(){
		Map<Turma, List<Candidato>> chamada = service.convoca(2013, 2);
		Assert.assertEquals(0, chamada.size());
	}

	@Test
	public void testNovaChamadaMatriculaGeral(){
		Map<Turma, List<Candidato>> chamada = service.convoca(ano, semestre);
		
		Assert.assertEquals(30, chamada.get(enfermagem).size());
		Assert.assertEquals(40, chamada.get(financas).size());
		Assert.assertEquals(40, chamada.get(mecatronica).size());
		Assert.assertEquals(40, chamada.get(informatica).size());
		Assert.assertEquals(40, chamada.get(administracao).size());
	}
	
	@Test
	public void testRepeteChamadaMatriculaGeral(){
		Map<Turma, List<Candidato>> chamada = service.convoca(ano, semestre);
		
		Set<Entry<Turma, List<Candidato>>> set = chamada.entrySet();
		
		for (Entry<Turma, List<Candidato>> entry : set) {
			Turma turma = entry.getKey();
			List<Candidato> candidatos = entry.getValue();
			
			for(int i = 0; i < 4; i++){
				service.matricular(candidatos.get(i), turma, new Date());
			}
		}
		
		chamada = service.carregaConvocados(ano, semestre);
		
		Assert.assertEquals(26, chamada.get(enfermagem).size());
		Assert.assertEquals(36, chamada.get(financas).size());
		Assert.assertEquals(36, chamada.get(mecatronica).size());
		Assert.assertEquals(36, chamada.get(informatica).size());
		Assert.assertEquals(36, chamada.get(administracao).size());
	}
	
	@Test
	public void testPrimeiraChamadaInformaticaPorNomeCurso(){
		List<Candidato> convocados = service.convoca(ano, semestre, "INFORMÁTICA", Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
	}

	@Test
	public void testRepetePrimeiraChamadaInformaticaPorNomeCurso(){
		List<Candidato> convocados = service.convoca(ano, semestre, "INFORMÁTICA", Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Iterator<Candidato> iterator = convocados.iterator();
		
		while (iterator.hasNext()) {
			Candidato candidato = (Candidato) iterator.next();
			iterator.remove();
			
			service.matricular(candidato, tinformartica, new Date());
			
			if(convocados.size() == 20){
				break;
			}
		}
		
		convocados = service.carregaConvocados(ano, semestre, "INFORMÁTICA", Periodo.NOITE);
		Assert.assertEquals(20, convocados.size());
	}

	@Test
	public void testPrimeiraChamadaInformaticaPorCodigoCurso(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica.getCodigo(), Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
	}
	
	@Test
	public void testRepetePrimeiraChamadaInformaticaPorCodigoCurso(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica.getCodigo(), Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Iterator<Candidato> iterator = convocados.iterator();
		
		while (iterator.hasNext()) {
			Candidato candidato = (Candidato) iterator.next();
			iterator.remove();
			
			service.matricular(candidato, tinformartica, new Date());
			
			if(convocados.size() == 20){
				break;
			}
		}
		
		convocados = service.carregaConvocados(ano, semestre, informatica.getCodigo(), Periodo.NOITE);
		Assert.assertEquals(20, convocados.size());
	}
	
	@Test
	public void testSegundaChamadaFinancas(){
		List<Candidato> convocados = service.convoca(ano, semestre, "FINANÇAS", Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		List<Candidato> ref = new ArrayList<>();
		
		for(Candidato candidato : convocados){
			if(ref.size() == 20){
				service.matricular(candidato, tfinancas, new Date());
			}
			else{
				ref.add(candidato);
			}
		}
		
		convocados = service.convoca(ano, semestre, "FINANÇAS", Periodo.NOITE);
		Assert.assertEquals(20, convocados.size());
		Assert.assertNotEquals(ref, convocados);
	}
	
	@Test
	public void testVagasIndisponiveis(){
		List<Candidato> convocados = service.convoca(ano, semestre, "MECATRÔNICA", Periodo.NOITE);
		
		for (Candidato candidato : convocados) {
			service.matricular(candidato, tmecatronica, new Date());
		}
		
		Assert.assertEquals(0, service.vagasDisponiveis(tmecatronica));
		Assert.assertEquals(0, service.convoca(ano, semestre, "MECATRÔNICA", Periodo.NOITE).size());
		Assert.assertEquals(0, service.carregaConvocados(ano, semestre, "MECATRÔNICA", Periodo.NOITE).size());
	}
	
	@Test
	public void testMatriculaVagaIndisponivel(){
		List<Candidato> convocados = service.convoca(ano, semestre, "MECATRÔNICA", Periodo.NOITE);
		Candidato aux = convocados.remove(0);
		
		for (Candidato candidato : convocados) {
			RespostaMatricula reposta = service.matricular(candidato, tmecatronica, new Date());
			Assert.assertEquals(RespostaMatricula.SUCESSO, reposta);
		}
		
		convocados = service.convoca(ano, semestre, "MECATRÔNICA", Periodo.NOITE);
		
		for (Candidato candidato : convocados) {
			RespostaMatricula reposta = service.matricular(candidato, tmecatronica, new Date());
			Assert.assertEquals(RespostaMatricula.SUCESSO, reposta);
		}
		
		RespostaMatricula reposta = service.matricular(aux, tmecatronica, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, reposta);
	}

	@Test
	public void testEsgotarChamadas(){
		//tem 55 candidatos na primeira opção e 3 na segunda opção.. a turma tem 30 alunos
		List<Candidato> convocados = service.convoca(ano, semestre, "ENFERMAGEM", Periodo.MANHA);
		Assert.assertEquals(30, convocados.size());
		
		//na segunda chamada deve vir somente 28 alunos
		convocados = service.convoca(ano, semestre, "ENFERMAGEM", Periodo.MANHA);
		Assert.assertEquals(28, convocados.size());
		
		//na terceira chamada não deve vir nenhum
		convocados = service.convoca(ano, semestre, "ENFERMAGEM", Periodo.MANHA);
		Assert.assertEquals(0, convocados.size());
	}

}

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
	
	protected static final String ENSINOMEDIO = "ENSINO MÉDIO";
	protected static final String ENFERMAGEM = "ENFERMAGEM";
	protected static final String INFORMATICA = "INFORMÁTICA";
	protected static final String MECATRONICA = "MECATRÔNICA";
	protected static final String ELETROTECNICA = "ELETROTÉCNICA";
	protected static final String ETIM = "INFORMÁTICA PARA INTERNET - INTEGRADO AO ENSINO MÉDIO";
	protected static final String MECANICA = "MECÂNICA";
	
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

	protected static Curso eletrotecnica;
	protected static Curso mecatronica;
	protected static Curso informatica;
	protected static Curso enfermagem;
	protected static Curso ensinomedio;
	protected static Curso etim;
	protected static Curso mecanica;
	
	protected static int semestre;
	protected static int ano;

	protected static Turma teletrotecnica;
	protected static Turma tmecatronicaM;
	protected static Turma tmecatronicaN;
	protected static Turma tinformartica;
	protected static Turma tenfermagem;
	protected static Turma tensinomedio;
	protected static Turma tetim;
	protected static Turma tmecanica;
	
	protected void before(boolean hasVest2013, boolean hasVestAtual) throws Exception{
		if(loaded){
			return;
		}
		
		Calendar calendar = Calendar.getInstance();
		ano = calendar.get(Calendar.YEAR);
		semestre = ((calendar.get(Calendar.MONTH) + 1) < 7) ? 1 : 2;
		
		eletrotecnica = new Curso(ELETROTECNICA, 40);
		enfermagem = new Curso(ENFERMAGEM, 30);
		mecatronica = new Curso(MECATRONICA, 40);
		informatica = new Curso(INFORMATICA, 40);
		ensinomedio = new Curso(ENSINOMEDIO, 40);
		etim = new Curso(ETIM, 40);
		mecanica = new Curso(MECANICA, 40);
		
		cursoRepository.save(eletrotecnica);
		cursoRepository.save(mecatronica);
		cursoRepository.save(informatica);
		cursoRepository.save(enfermagem);
		cursoRepository.save(ensinomedio);
		cursoRepository.save(etim);
		cursoRepository.save(mecanica);

		generateModulos(eletrotecnica);
		generateModulos(mecatronica);
		generateModulos(informatica);
		generateModulos(enfermagem);
		generateModulos(ensinomedio);
		generateModulos(etim);
		generateModulos(mecanica);
		
		teletrotecnica = new Turma("Turma A", Periodo.NOITE, ano, semestre, eletrotecnica, false);
		tmecatronicaM = new Turma("Turma B", Periodo.NOITE, ano, semestre, mecatronica, false);
		tmecatronicaN = new Turma("Turma B", Periodo.NOITE, ano, semestre, mecatronica, false);
		tinformartica = new Turma("Turma C", Periodo.TARDE, ano, semestre, informatica, false);
		tenfermagem = new Turma("Turma D", Periodo.NOITE, ano, semestre, enfermagem, false);
		tensinomedio = new Turma("Turma E", Periodo.MANHA, ano, semestre, ensinomedio, false);
		tetim = new Turma("Turma E", Periodo.INTEGRAL, ano, semestre, etim, false);
		tmecanica = new Turma("Turma E", Periodo.NOITE, ano, semestre, mecanica, false);

		turmaRepository.save(teletrotecnica);
		turmaRepository.save(tmecatronicaN);
		turmaRepository.save(tmecatronicaM);
		turmaRepository.save(tinformartica);
		turmaRepository.save(tenfermagem);
		turmaRepository.save(tensinomedio);
		turmaRepository.save(tetim);
		turmaRepository.save(tmecanica);
		
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, teletrotecnica, eletrotecnica.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tmecatronicaM, mecatronica.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tmecatronicaN, mecatronica.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tinformartica, informatica.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tenfermagem, enfermagem.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tensinomedio, ensinomedio.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tetim, etim.getModulos().get(0)));
		etapaRepository.save(new Etapa("1º Módulo", ano, semestre, tmecanica, mecanica.getModulos().get(0)));
		
		teletrotecnica = em.find(Turma.class, teletrotecnica.getCodigo());
		tmecatronicaM = em.find(Turma.class, tmecatronicaM.getCodigo());
		tmecatronicaN = em.find(Turma.class, tmecatronicaN.getCodigo());
		tinformartica = em.find(Turma.class, tinformartica.getCodigo());
		tenfermagem = em.find(Turma.class, tenfermagem.getCodigo());
		tensinomedio = em.find(Turma.class, tensinomedio.getCodigo());
		tetim = em.find(Turma.class, tetim.getCodigo());
		tmecanica = em.find(Turma.class, tmecanica.getCodigo());
		
		if(hasVest2013){
			importaCandidatos2013();
		}
		
		if(hasVestAtual){
			importaCandidatosAtual();
		}
		
		loaded = true;
	}
	
	protected void importaCandidatos2013() throws Exception{
		URI uri = getClass().getResource("matricula-dados-reais.xlsx").toURI();
		RespostaImportService resposta = importador.importar(ImportFileType.XLSX, new FileInputStream(new File(uri)), 2013, 2);
		Assert.assertEquals(RespostaImportService.SUCESSO, resposta);
	}
	
	protected void importaCandidatosAtual() throws Exception{
		URI uri = getClass().getResource("matricula-dados-reais.xlsx").toURI();
		RespostaImportService resposta = importador.importar(ImportFileType.XLSX, new FileInputStream(new File(uri)), ano, semestre);
		Assert.assertEquals(RespostaImportService.SUCESSO, resposta);
	}
	
	protected void after(){
		matriculaRepository.deleteAll();
		matriculaRepository.resetConvocados(ano, semestre);
		
		resetEtapa(teletrotecnica);
		resetEtapa(tmecatronicaM);
		resetEtapa(tmecatronicaN);
		resetEtapa(tinformartica);
		resetEtapa(tenfermagem);
		resetEtapa(tensinomedio);
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

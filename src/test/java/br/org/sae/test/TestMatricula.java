package br.org.sae.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import br.org.sae.model.Candidato;
import br.org.sae.model.Etapa;
import br.org.sae.model.Periodo;
import br.org.sae.service.RespostaMatricula;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/br/org/sae/test/opensae.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TestMatricula extends TestMatriculaGeneric{

	@Test
	public void testMatriculaTurmaInteira(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		for (Candidato candidato : convocados) {
			service.matricular(candidato, tinformartica, new Date());
		}
		
		Assert.assertEquals(0, service.convoca(ano, semestre, informatica, Periodo.NOITE).size());
		Assert.assertEquals(0, service.carregaConvocados(ano, semestre, informatica, Periodo.NOITE).size());
	}
	
	@Test
	public void testMatriculaTodosDesmatriculaMetade(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		List<Candidato> desmatriculados = new ArrayList<>();
		
		for (Candidato candidato : convocados) {
			service.matricular(candidato, tinformartica, new Date());
			
			if(desmatriculados.size() < 20){
				service.cancelarMatricula(alunoRepository.findByCPF(candidato.getCpf()), tinformartica);
				desmatriculados.add(candidato);
			}
		}
		
		Assert.assertEquals(0, service.carregaConvocados(ano, semestre, informatica, Periodo.NOITE).size());
		
		convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(20, convocados.size());
		Assert.assertEquals(20, service.carregaConvocados(ano, semestre, informatica, Periodo.NOITE).size());
		Assert.assertNotEquals(desmatriculados, convocados);
		Assert.assertEquals(20, service.findAll().size());
	}

	@Test
	public void testMatriculaCursoTrocado(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		RespostaMatricula resposta = service.matricular(convocados.get(0), tmecatronicaN, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}

	@Test
	public void testMatriculaMesmoCursoDuasVezes(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Candidato candidato = convocados.get(0);
		RespostaMatricula resposta = service.matricular(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.SUCESSO, resposta);
		
		resposta = service.matricular(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}

	@Test
	public void testMatriculaDoisCursosMesmoPeriodo(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Candidato candidato = convocados.get(0);

		RespostaMatricula resposta = service.matricular(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.SUCESSO, resposta);
		
		resposta = service.matricular(candidato, tmecatronicaN, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}
	
	@Test
	public void testMatriculaExcepcionalDoisCursosMesmoPeriodo(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Candidato candidato = convocados.get(0);
		RespostaMatricula resposta = service.matricular(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.SUCESSO, resposta);
		
		resposta = service.matricularExcepcionalmente(candidato, tmecatronicaN, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}

	@Test
	public void testMatriculaDuasVezesMesmaTurmaEtapasDiferentes(){
		List<Candidato> convocados = service.convoca(ano, semestre, mecatronica, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Candidato candidato = convocados.get(0);
		RespostaMatricula resposta = service.matricular(candidato, tmecatronicaN, new Date());
		Assert.assertEquals(RespostaMatricula.SUCESSO, resposta);
		
		Etapa etapa = new Etapa();
		etapa.setAno(2014);
		etapa.setSemestre(1);
		etapa.setDescricao("2º Módulo");
		etapa.setModulo(mecatronica.getModulos().get(1));
		etapa.setTurma(tmecatronicaN);
		tmecatronicaN.setEtapaAtual(etapa);
		
		etapaRepository.save(etapa);
		turmaRepository.update(tmecatronicaN);
		
		resposta = service.matricular(candidato, tmecatronicaN, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}

	@Test
	public void testMatriculaExcepcionalMesmoCursoDuasVezes(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		Candidato candidato = convocados.get(0);
		RespostaMatricula resposta = service.matricular(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.SUCESSO, resposta);
		
		resposta = service.matricularExcepcionalmente(candidato, tinformartica, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, resposta);
	}


	@Test
	public void testMatriculaTodosDesmatriculaMetadeDepoisListaPiloto(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
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
		
		Assert.assertEquals(0, service.carregaConvocados(ano, semestre, informatica, Periodo.NOITE).size());
		
		convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(20, convocados.size());
		Assert.assertEquals(20, service.carregaConvocados(ano, semestre, informatica, Periodo.NOITE).size());
		Assert.assertNotEquals(desmatriculados, convocados);
		Assert.assertEquals(40, service.findAll().size());
	}
	
	
	@Before
	public void before() throws Exception{
		super.before(false, true);
	}
	
	@After
	public void after(){
		super.after();
	}
}

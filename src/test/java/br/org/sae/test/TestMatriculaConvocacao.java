package br.org.sae.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import br.org.sae.model.Candidato;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;
import br.org.sae.service.RespostaMatricula;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/br/org/sae/test/opensae.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TestMatriculaConvocacao extends TestMatriculaGeneric{
	
	@Before
	public void before() throws Exception{
		super.before(true, true);
	}
	
	@After
	public void after(){
		super.after();
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
		Assert.assertEquals(40, chamada.get(eletrotecnica).size());
		Assert.assertEquals(40, chamada.get(mecatronica).size());
		Assert.assertEquals(40, chamada.get(informatica).size());
		Assert.assertEquals(40, chamada.get(etim).size());
		Assert.assertEquals(40, chamada.get(ensinomedio).size());
		Assert.assertEquals(40, chamada.get(mecanica).size());
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
		Assert.assertEquals(36, chamada.get(eletrotecnica).size());
		Assert.assertEquals(36, chamada.get(mecatronica).size());
		Assert.assertEquals(36, chamada.get(informatica).size());
		Assert.assertEquals(36, chamada.get(ensinomedio).size());
	}
	
	@Test
	public void testPrimeiraChamadaInformatica(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
	}

	@Test
	public void testRepetePrimeiraChamadaInformatica(){
		List<Candidato> convocados = service.convoca(ano, semestre, informatica, Periodo.NOITE);
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
		
		convocados = service.carregaConvocados(ano, semestre, informatica, Periodo.NOITE);
		Assert.assertEquals(20, convocados.size());
	}

	@Test
	public void testSegundaChamadaEletrotecnica(){
		List<Candidato> convocados = service.convoca(ano, semestre, eletrotecnica, Periodo.NOITE);
		Assert.assertEquals(40, convocados.size());
		
		List<Candidato> ref = new ArrayList<>();
		
		for(Candidato candidato : convocados){
			if(ref.size() == 20){
				service.matricular(candidato, teletrotecnica, new Date());
			}
			else{
				ref.add(candidato);
			}
		}
		
		convocados = service.convoca(ano, semestre, eletrotecnica, Periodo.NOITE);
		Assert.assertEquals(20, convocados.size());
		Assert.assertNotEquals(ref, convocados);
	}
	
	@Test
	public void testVagasIndisponiveis(){
		List<Candidato> convocados = service.convoca(ano, semestre, mecatronica, Periodo.MANHA);
		
		for (Candidato candidato : convocados) {
			service.matricular(candidato, tmecatronicaM, new Date());
		}
		
		Assert.assertEquals(0, service.vagasDisponiveis(tmecatronicaM));
		Assert.assertEquals(0, service.convoca(ano, semestre, mecatronica, Periodo.MANHA).size());
		Assert.assertEquals(0, service.carregaConvocados(ano, semestre, mecatronica, Periodo.MANHA).size());
	}
	
	@Test
	public void testMatriculaVagaIndisponivel(){
		List<Candidato> convocados = service.convoca(ano, semestre, mecatronica, Periodo.NOITE);
		Candidato aux = convocados.remove(0);
		
		for (Candidato candidato : convocados) {
			RespostaMatricula reposta = service.matricular(candidato, tmecatronicaN, new Date());
			Assert.assertEquals(RespostaMatricula.SUCESSO, reposta);
		}
		
		convocados = service.convoca(ano, semestre, mecatronica, Periodo.NOITE);
		
		for (Candidato candidato : convocados) {
			RespostaMatricula reposta = service.matricular(candidato, tmecatronicaN, new Date());
			Assert.assertEquals(RespostaMatricula.SUCESSO, reposta);
		}
		
		RespostaMatricula reposta = service.matricular(aux, tmecatronicaN, new Date());
		Assert.assertEquals(RespostaMatricula.MATRICULA_INVALIDA, reposta);
	}

	@Test
	public void testEsgotarChamadas(){
		//tem 110 candidatos na primeira opção e 9 na segunda opção.. a turma tem 30 alunos
		List<Candidato> convocados = service.convoca(ano, semestre, enfermagem, Periodo.NOITE);
		Assert.assertEquals(30, convocados.size());

		//na segunda chamada deve vir somente 30 alunos
		convocados = service.convoca(ano, semestre, enfermagem, Periodo.NOITE);
		Assert.assertEquals(30, convocados.size());

		//na segunda chamada deve vir somente 30 alunos
		convocados = service.convoca(ano, semestre, enfermagem, Periodo.NOITE);
		Assert.assertEquals(30, convocados.size());
		
		//na segunda chamada deve vir somente 30 alunos
		convocados = service.convoca(ano, semestre, enfermagem, Periodo.NOITE);
		Assert.assertEquals(29, convocados.size());
		
		//na terceira chamada não deve vir nenhum
		convocados = service.convoca(ano, semestre, enfermagem, Periodo.NOITE);
		Assert.assertEquals(0, convocados.size());
	}

}

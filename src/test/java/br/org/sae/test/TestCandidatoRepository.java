package br.org.sae.test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import br.org.sae.exception.SaeValidationException;
import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.Endereco;
import br.org.sae.model.EstadoCivil;
import br.org.sae.model.Sexo;
import br.org.sae.model.Telefone;
import br.org.sae.repository.CandidatoRepository;
import br.org.sae.repository.CursoRepository;
import br.org.sae.service.ImportService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/br/org/sae/test/opensae.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TestCandidatoRepository extends TestDatabaseGeneric{
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private CandidatoRepository repository;
	
	@Autowired
	private ImportService importService;
	
	@Before
	public void before() throws Exception{
		carregaCursos(cursoRepository);
		carregaCandidatos(importService, repository);
	}
	
	@Test(expected=SaeValidationException.class)
	public void testNovoCandidatoInvalido(){
		Candidato c = new Candidato();
		repository.save(c);
	}

	@Test
	public void testNovoCandidatoInvalidoTodosCampos(){
		try {
			Candidato c = new Candidato();
			repository.save(c);
			Assert.fail();
		} catch (SaeValidationException e) {
			Assert.assertEquals(13, e.getViolations().size());
		}
	}

	@Test
	public void testNovoCandidatoInvalidoTodosCamposMenos2(){
		try {
			Candidato c = new Candidato();
			c.setNome("Manuel");
			repository.save(c);
			Assert.fail();
		} catch (SaeValidationException e) {
			Assert.assertEquals(11, e.getViolations().size());
		}
	}
	
	@Test
	public void testNovoCandidato(){
		Candidato c = new Candidato();
		c.setAfroDescendente(false);
		c.setCpf("11111111111");
		c.setDataNascimento(new Date());
		c.setEmail("eberson.oliveira@gmail.com");
		
		Endereco endereco = new Endereco();
		endereco.setBairro("Jardim Santa Marta");
		endereco.setCep("15996170");
		endereco.setCidade("Matão");
		endereco.setEndereco("Rua Aparecido Ferreira de Carvalho");
		endereco.setNumero("75");
		endereco.setUf("SP");
		
		c.setEndereco(endereco);
		c.setEstadoCivil(EstadoCivil.CASADO);
		c.setNome("Éberson Silva Oliveira");
		c.setOrgaoExpedidor("SSP");
		c.setRg("1234");
		c.setSexo(Sexo.MASCULINO);
		
		Telefone telefone = new Telefone();
		telefone.setDdd("16");
		telefone.setTelefone("33821226");
		
		c.setTelefonePrincipal(telefone);
		
		int armazenados = repository.all().size();
		repository.save(c);
		
		Assert.assertEquals(armazenados + 1, repository.all().size());
	}
	
	@Test
	public void testFindCandidatoNotFound(){
		Assert.assertNull(repository.findByCpfOrNome("", ""));
	}

	@Test
	public void testFindCandidatoSomenteNome(){
		carregaCandidato(repository);
		Assert.assertNotNull(repository.findByCpfOrNome("22222222222", ""));
	}

	@Test
	public void testFindCandidatoSomenteCPF(){
		carregaCandidato(repository);
		Assert.assertNotNull(repository.findByCpfOrNome("", "Joaquim Manuel dos Santos"));
	}
	
	@Test
	public void testFindCandidatoPorAnoVestibulinoSemResultado(){
		Map<Curso, List<Candidato>> mapa = repository.findByVestibulinho(2014, 2, true);
		Assert.assertEquals(0, mapa.size());
	}

	@Test
	public void testFindCandidatoPorAnoVestibulinoSegundaOpcaoSemResultado(){
		Map<Curso, List<Candidato>> mapa = repository.findByVestibulinho(2014, 2, false);
		Assert.assertEquals(0, mapa.size());
	}

	@Test
	public void testFindCandidatoPorAnoVestibulino(){
		Map<Curso, List<Candidato>> mapa = repository.findByVestibulinho(2013, 2, true);
		Set<Entry<Curso,List<Candidato>>> entrySet = mapa.entrySet();
		
		for (Entry<Curso, List<Candidato>> entry : entrySet) {
			String nome = entry.getKey().getNome();
			
			if("MECATRÔNICA".equals(nome)){
				Assert.assertEquals(6, entry.getValue().size());
				continue;
			}
			
			if("FINANÇAS".equals(nome)){
				Assert.assertEquals(4, entry.getValue().size());
				continue;
			}
			
			if("ENFERMAGEM".equals(nome)){
				Assert.assertEquals(2, entry.getValue().size());
				continue;
			}
			
			if("INFORMÁTICA".equals(nome)){
				Assert.assertEquals(2, entry.getValue().size());
				continue;
			}
			
			Assert.fail();
		}
	}

	@Test
	public void testFindCandidatoPorAnoVestibulinoSegundaOpcao(){
		Map<Curso, List<Candidato>> mapa = repository.findByVestibulinho(2013, 2, false);
		Set<Entry<Curso,List<Candidato>>> entrySet = mapa.entrySet();
		
		for (Entry<Curso, List<Candidato>> entry : entrySet) {
			String nome = entry.getKey().getNome();
			
			if("FINANÇAS".equals(nome)){
				Assert.assertEquals(2, entry.getValue().size());
				continue;
			}
			
			if("INFORMÁTICA".equals(nome)){
				Assert.assertEquals(4, entry.getValue().size());
				continue;
			}
			
			Assert.fail();
		}
	}

	@Test
	public void testFindCandidatoPorCursoVestibulinoSemResultado(){
		List<Candidato> lista = repository.findByVestibulinhoAndCurso(2015, 2, "MECATRÔNICA", true);
		Assert.assertEquals(0, lista.size());
	}

	@Test
	public void testFindCandidatoPorCursoVestibulino(){
		List<Candidato> lista = repository.findByVestibulinhoAndCurso(2013, 2, "MECATRÔNICA", true);
		Assert.assertEquals(6, lista.size());
	}

	@Test
	public void testFindCandidatoPorCursoVestibulinoSegundaOpcaoSemResultado(){
		List<Candidato> lista = repository.findByVestibulinhoAndCurso(2013, 2, "MECATRÔNICA", false);
		Assert.assertEquals(0, lista.size());
	}

	@Test
	public void testFindCandidatoPorCursoVestibulinoSegundaOpcao(){
		List<Candidato> lista = repository.findByVestibulinhoAndCurso(2013, 2, "INFORMÁTICA", false);
		Assert.assertEquals(4, lista.size());
	}
}

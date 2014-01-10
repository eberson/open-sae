package br.org.sae.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import br.org.sae.repository.CursoRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/br/org/sae/test/opensae.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class TestCursoRepository extends TestDatabaseGeneric {
	@Autowired
	private CursoRepository repository;

	@Before
	@Transactional
	public void before() {
		carregaCursos(repository);
	}
	
	@Test
	public void testListaTodos(){
		Assert.assertEquals(7, repository.all().size());
	}
	
	@Test
	public void testCursoByNomeNotFound(){
		Assert.assertNull(repository.findByName("teste"));
	}

	@Test
	public void testCursoByNomeMinusculas(){
		Assert.assertNotNull(repository.findByName("eletrotécnica"));
	}

	@Test
	public void testCursoByNomeMaiusculas(){
		Assert.assertNotNull(repository.findByName("ELETROTÉCNICA"));
	}
}

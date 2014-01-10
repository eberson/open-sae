package br.org.sae.test;


import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import br.org.sae.model.Curso;
import br.org.sae.repository.CandidatoRepository;
import br.org.sae.service.CandidatoService;
import br.org.sae.service.CursoService;
import br.org.sae.service.ImportFileType;
import br.org.sae.service.ImportService;
import br.org.sae.service.RespostaImportService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/br/org/sae/test/opensae.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TestImportacaoDatabase {

	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private CandidatoService candidatoService;
	
	@Autowired
	private CandidatoRepository repository;
	
	@Autowired
	private ImportService importService;
	
	@Before
	public void before(){
		repository.deleteAll();
		
		if(cursoService.findAll().size() == 0){
			cursoService.save(new Curso("ELETROTÉCNICA", 40));
			cursoService.save(new Curso("ENFERMAGEM", 30));
			cursoService.save(new Curso("ENSINO MÉDIO", 40));
			cursoService.save(new Curso("INFORMÁTICA", 40));
			cursoService.save(new Curso("INFORMÁTICA PARA INTERNET - INTEGRADO AO ENSINO MÉDIO", 40));
			cursoService.save(new Curso("MECÂNICA", 40));
			cursoService.save(new Curso("MECATRÔNICA", 40));
		}
		
		Assert.assertEquals(7, cursoService.findAll().size());
	}
	
	@Test
	public void testDadosCarregadosXLS() throws Exception{
		URI uri = getClass().getResource("matricula-dados-reais.xlsx").toURI();
		
		RespostaImportService resposta = importService.importar(ImportFileType.XLSX, new FileInputStream(new File(uri)), 2013, 2);
		
		Assert.assertEquals(RespostaImportService.SUCESSO, resposta);
		Assert.assertEquals(1133, candidatoService.findAll().size());
	}
}

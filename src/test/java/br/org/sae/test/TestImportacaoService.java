package br.org.sae.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import br.org.sae.service.ImportFileType;
import br.org.sae.service.ImportService;
import br.org.sae.service.RespostaImportService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/br/org/sae/test/opensae.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TestImportacaoService {

	@Autowired
	private ImportService service;

	@Test
	public void testImportacaoArquivoInexistente() throws Exception{
		URI uri = getClass().getResource("matricula-real.docx").toURI();
		FileInputStream fis = new FileInputStream(new File(uri));
		
		RespostaImportService resposta = service.importar(ImportFileType.XLSX, fis, 2013, 2);
		
		assertEquals(RespostaImportService.ARQUIVO_FORMATO_INVALIDO, resposta);
	}
	
	@Test
	public void testImportacaoArquivoVazioFormatoInvalido() throws Exception{
		URI uri = getClass().getResource("arquivo-vazio-formato-invalido.xlsx").toURI();
		FileInputStream fis = new FileInputStream(new File(uri));

		RespostaImportService resposta = service.importar(ImportFileType.XLSX, fis, 2013, 2);
		
		assertEquals(RespostaImportService.ARQUIVO_ESTRUTURA_INVALIDA, resposta);
	}

	@Test
	public void testImportacaoArquivoVazio() throws Exception{
		URI uri = getClass().getResource("arquivo-vazio.xlsx").toURI();
		FileInputStream fis = new FileInputStream(new File(uri));

		RespostaImportService resposta = service.importar(ImportFileType.XLSX, fis, 2013, 2);
		
		assertEquals(RespostaImportService.ARQUIVO_VAZIO, resposta);
	}
	
	@Test
	public void testImportacaoFormatoInvalidoXLS() throws Exception{
		URI uri = getClass().getResource("formato-invalido.xls").toURI();
		FileInputStream fis = new FileInputStream(new File(uri));
		
		RespostaImportService resposta = service.importar(ImportFileType.XLS, fis, 2013, 2);
		
		assertEquals(RespostaImportService.ARQUIVO_ESTRUTURA_INVALIDA, resposta);
	}

	@Test
	public void testFormatoInvalidoXLSX() throws Exception{
		URI uri = getClass().getResource("formato-invalido.xlsx").toURI();
		FileInputStream fis = new FileInputStream(new File(uri));
		
		RespostaImportService resposta = service.importar(ImportFileType.XLSX, fis, 2013, 2);
		
		assertEquals(RespostaImportService.ARQUIVO_ESTRUTURA_INVALIDA, resposta);
	}

	@Test
	public void testDadosCarregadosXLSX() throws Exception{
		URI uri = getClass().getResource("arquivo-completo.xlsx").toURI();
		FileInputStream fis = new FileInputStream(new File(uri));
		
		RespostaImportService resposta = service.importar(ImportFileType.XLSX, fis, 2013, 2);
		
		assertEquals(RespostaImportService.SUCESSO, resposta);
	}

	@Test
	public void testDadosCarregadosXLS() throws Exception{
		URI uri = getClass().getResource("arquivo-completo.xls").toURI();
		FileInputStream fis = new FileInputStream(new File(uri));
		
		RespostaImportService resposta = service.importar(ImportFileType.XLS, fis, 2013, 2);
		
		assertEquals(RespostaImportService.SUCESSO, resposta);
	}
}

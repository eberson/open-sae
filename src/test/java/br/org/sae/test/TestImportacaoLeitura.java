package br.org.sae.test;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.org.sae.exception.ArquivoInvalidoImportacaoException;
import br.org.sae.exception.ArquivoVazioException;
import br.org.sae.exception.EstruturaInvalidaException;
import br.org.sae.importador.Importador;
import br.org.sae.importador.ImportadorBuilder;
import br.org.sae.model.Candidato;
import br.org.sae.service.ImportFileType;

public class TestImportacaoLeitura {
	
	@Test(expected=ArquivoInvalidoImportacaoException.class)
	public void testArquivoXLSOrXLSX() throws EstruturaInvalidaException, URISyntaxException, ArquivoVazioException, ArquivoInvalidoImportacaoException{
		URI uri = getClass().getResource("matricula-real.docx").toURI();
		
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setFileType(ImportFileType.XLSX).setSemestre(2).setSource(new File(uri));
		builder.build().importar();
	}
	
	@Test(expected=EstruturaInvalidaException.class)
	public void testArquivoVazioFormatoInvalido() throws EstruturaInvalidaException, ArquivoVazioException, URISyntaxException, ArquivoInvalidoImportacaoException{
		URI uri = getClass().getResource("arquivo-vazio-formato-invalido.xlsx").toURI();

		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setFileType(ImportFileType.XLSX).setSemestre(2).setSource(new File(uri));

		Importador importador = builder.build();
		importador.importar();
	}

	@Test(expected=ArquivoVazioException.class)
	public void testArquivoVazio() throws EstruturaInvalidaException, ArquivoVazioException, URISyntaxException, ArquivoInvalidoImportacaoException{
		URI uri = getClass().getResource("arquivo-vazio.xlsx").toURI();

		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setFileType(ImportFileType.XLSX).setSemestre(2).setSource(new File(uri));
		
		Importador importador = builder.build();
		importador.importar();
	}
	
	@Test(expected=EstruturaInvalidaException.class)
	public void testFormatoInvalidoXLS() throws EstruturaInvalidaException, ArquivoVazioException, URISyntaxException, ArquivoInvalidoImportacaoException{
		URI uri = getClass().getResource("formato-invalido.xls").toURI();

		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setFileType(ImportFileType.XLS).setSemestre(2).setSource(new File(uri));
		
		Importador importador = builder.build();
		importador.importar();
	}

	@Test(expected=EstruturaInvalidaException.class)
	public void testFormatoInvalidoXLSX() throws EstruturaInvalidaException, ArquivoVazioException, URISyntaxException, ArquivoInvalidoImportacaoException{
		URI uri = getClass().getResource("formato-invalido.xlsx").toURI();
		
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setFileType(ImportFileType.XLSX).setSemestre(2).setSource(new File(uri));
		
		Importador importador = builder.build();
		importador.importar();
	}

	@Test
	public void testDadosCarregadosXLSX() throws EstruturaInvalidaException, ArquivoVazioException, URISyntaxException, ArquivoInvalidoImportacaoException{
		URI uri = getClass().getResource("arquivo-completo.xlsx").toURI();
		
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setFileType(ImportFileType.XLSX).setAno(2013).setSemestre(2).setSource(new File(uri));
		
		Importador importador = builder.build();
		List<Candidato> candidatos = importador.importar();
		
		for (Candidato candidato : candidatos) {
			assertNotNull(candidato.getNome());
			assertNotNull(candidato.getEndereco());
			assertNotNull(candidato.getTelefonePrincipal());
			assertNotNull(candidato.getRg());
			assertNotNull(candidato.getCpf());
			assertNotNull(candidato.getNome());
		}
	}

	@Test
	public void testDadosCarregadosXLS() throws EstruturaInvalidaException, ArquivoVazioException, URISyntaxException, ArquivoInvalidoImportacaoException{
//		URI uri = getClass().getResource("arquivo-completo.xls").toURI();
		URI uri = getClass().getResource("matricula-dados-reais.xlsx").toURI();
		
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setFileType(ImportFileType.XLSX).setAno(2013).setSemestre(2).setSource(new File(uri));
		
		Importador importador = builder.build();
		List<Candidato> candidatos = importador.importar();
		
		Assert.assertEquals(1133, candidatos.size());
		
		for (Candidato candidato : candidatos) {
			assertNotNull(candidato.getNome());
			assertNotNull(candidato.getEndereco());
			assertNotNull(candidato.getTelefonePrincipal());
			assertNotNull(candidato.getRg());
			assertNotNull(candidato.getCpf());
			assertNotNull(candidato.getNome());
		}
	}
}

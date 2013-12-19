package br.org.sae.test;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.Test;
import br.org.sae.exception.ArquivoVazioException;
import br.org.sae.exception.FormatoInvalidoException;
import br.org.sae.exception.ImpossivelLerException;
import br.org.sae.importador.Importador;
import br.org.sae.importador.ImportadorBuilder;
import br.org.sae.model.Candidato;

public class TestImportacao {
	
	@Test(expected=IllegalArgumentException.class)
	public void testArquivoXLSOrXLSX() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, URISyntaxException{
		URI uri = getClass().getResource("matricula-real.docx").toURI();
		
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource(new File(uri));
		builder.build();
	}
	
	@Test(expected=FileNotFoundException.class)
	public void testArquivoInexistente() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException{
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource("matricula-real-teste.xls");

		Importador importador = builder.build();
		importador.importar();
	}

	@Test(expected=FormatoInvalidoException.class)
	public void testArquivoVazioFormatoInvalido() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException, URISyntaxException{
		URI uri = getClass().getResource("arquivo-vazio-formato-invalido.xlsx").toURI();

		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource(new File(uri));

		File file = new File("./br/org/sae/test");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.exists());
		
		Importador importador = builder.build();
		importador.importar();
	}

	@Test(expected=ArquivoVazioException.class)
	public void testArquivoVazio() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException, URISyntaxException{
		URI uri = getClass().getResource("arquivo-vazio.xlsx").toURI();

		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource(new File(uri));
		
		Importador importador = builder.build();
		importador.importar();
	}
	
	@Test(expected=FormatoInvalidoException.class)
	public void testFormatoInvalidoXLS() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException, URISyntaxException{
		URI uri = getClass().getResource("formato-invalido.xls").toURI();

		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource(new File(uri));
		
		Importador importador = builder.build();
		importador.importar();
	}

	@Test(expected=FormatoInvalidoException.class)
	public void testFormatoInvalidoXLSX() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException, URISyntaxException{
		URI uri = getClass().getResource("formato-invalido.xlsx").toURI();
		
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource(new File(uri));
		
		Importador importador = builder.build();
		importador.importar();
	}

	@Test
	public void testDadosCarregadosXLSX() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException, URISyntaxException{
		URI uri = getClass().getResource("arquivo-completo.xlsx").toURI();
		
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource(new File(uri));
		
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
	public void testDadosCarregadosXLS() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException, URISyntaxException{
		URI uri = getClass().getResource("arquivo-completo.xls").toURI();
		
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource(new File(uri));
		
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
	public void testImportacao(){
		File xls = new File("./br/org/sae/test/resources/arquivo-completo.xls");
		
//		ApplicationContext context = new classpa
		
		
	}
}

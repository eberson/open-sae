package br.org.sae.test;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import br.org.sae.exception.ArquivoVazioException;
import br.org.sae.exception.FormatoInvalidoException;
import br.org.sae.exception.ImpossivelLerException;
import br.org.sae.importador.Importador;
import br.org.sae.importador.ImportadorBuilder;
import br.org.sae.model.Candidato;

import static org.junit.Assert.*;

public class TestImportacao {
	
	@Test(expected=IllegalArgumentException.class)
	public void testArquivoXLSOrXLSX() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException{
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource("C:\\Users\\aluno\\Desktop\\matricula-real.doc");
		
		builder.build();
	}
	
	@Test(expected=FileNotFoundException.class)
	public void testArquivoInexistente() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException{
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource("C:\\Users\\aluno\\Desktop\\matricula-real-teste.xls");
		
		Importador importador = builder.build();
		importador.importar();
	}

	@Test(expected=FormatoInvalidoException.class)
	public void testArquivoVazioFormatoInvalido() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException{
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource("C:\\Users\\aluno\\Desktop\\arquivo-vazio-formato-invalido.xlsx");
		
		Importador importador = builder.build();
		importador.importar();
	}

	@Test(expected=ArquivoVazioException.class)
	public void testArquivoVazio() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException{
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource("C:\\Users\\aluno\\Desktop\\arquivo-vazio.xlsx");
		
		Importador importador = builder.build();
		importador.importar();
	}
	
	@Test(expected=FormatoInvalidoException.class)
	public void testFormatoInvalidoXLS() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException{
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource("C:\\Users\\aluno\\Desktop\\formato-invalido.xls");
		
		Importador importador = builder.build();
		importador.importar();
	}

	@Test(expected=FormatoInvalidoException.class)
	public void testFormatoInvalidoXLSX() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException{
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource("C:\\Users\\aluno\\Desktop\\formato-invalido.xlsx");
		
		Importador importador = builder.build();
		importador.importar();
	}

	@Test
	public void testDadosCarregadosXLSX() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException{
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource("C:\\Users\\aluno\\Desktop\\arquivo-completo.xlsx");
		
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
	public void testDadosCarregadosXLS() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException{
		ImportadorBuilder builder = new ImportadorBuilder();
		builder.setAno(2013).setSemestre(2).setSource("C:\\Users\\aluno\\Desktop\\arquivo-completo.xls");
		
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

}

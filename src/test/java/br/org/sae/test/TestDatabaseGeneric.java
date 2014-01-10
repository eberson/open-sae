package br.org.sae.test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Date;

import org.junit.Assert;
import org.springframework.transaction.annotation.Transactional;

import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.Endereco;
import br.org.sae.model.EstadoCivil;
import br.org.sae.model.Sexo;
import br.org.sae.model.Telefone;
import br.org.sae.repository.CandidatoRepository;
import br.org.sae.repository.CursoRepository;
import br.org.sae.service.ImportFileType;
import br.org.sae.service.ImportService;
import br.org.sae.service.RespostaImportService;

public abstract class TestDatabaseGeneric {

	private static boolean loadedCursos;
	private static boolean loadedCandidatos;
	
	@Transactional
	protected void carregaCandidato(CandidatoRepository repository){
		if(repository.find("22222222222") != null){
			return;
		}
		
		Candidato c = new Candidato();
		c.setAfroDescendente(false);
		c.setCpf("22222222222");
		c.setDataNascimento(new Date());
		c.setEmail("joaquim.manuel@gmail.com");
		
		Endereco endereco = new Endereco();
		endereco.setBairro("Jardim Santa Marta");
		endereco.setCep("15996170");
		endereco.setCidade("Matão");
		endereco.setEndereco("Rua Aparecido Ferreira de Carvalho");
		endereco.setNumero("75");
		endereco.setUf("SP");
		
		c.setEndereco(endereco);
		c.setEstadoCivil(EstadoCivil.CASADO);
		c.setNome("Joaquim Manuel dos Santos");
		c.setOrgaoExpedidor("SSP");
		c.setRg("1234");
		c.setSexo(Sexo.MASCULINO);
		
		Telefone telefone = new Telefone();
		telefone.setDdd("16");
		telefone.setTelefone("33821226");
		
		c.setTelefonePrincipal(telefone);
		
		repository.save(c);
	}
	
	@Transactional
	protected void carregaCursos(CursoRepository cursoService){
		if(loadedCursos){
			return;
		}
		
		cursoService.save(new Curso("ELETROTÉCNICA", 40));
		cursoService.save(new Curso("ENFERMAGEM", 30));
		cursoService.save(new Curso("ENSINO MÉDIO", 40));
		cursoService.save(new Curso("INFORMÁTICA", 40));
		cursoService.save(new Curso("INFORMÁTICA PARA INTERNET - INTEGRADO AO ENSINO MÉDIO", 40));
		cursoService.save(new Curso("MECÂNICA", 40));
		cursoService.save(new Curso("MECATRÔNICA", 40));

		Assert.assertEquals(7, cursoService.all().size());
		loadedCursos = true;
	}
	
	@Transactional
	protected void carregaCandidatos(ImportService importService, CandidatoRepository candidatoRepository) throws Exception{
		if(loadedCandidatos){
			return;
		}
		
		URI uri = getClass().getResource("arquivo-completo.xlsx").toURI();
		
		RespostaImportService resposta = importService.importar(ImportFileType.XLSX, new FileInputStream(new File(uri)), 2013, 2);
		
		Assert.assertEquals(RespostaImportService.SUCESSO, resposta);
		Assert.assertEquals(20, candidatoRepository.all().size());
		loadedCandidatos = true;
	}

}

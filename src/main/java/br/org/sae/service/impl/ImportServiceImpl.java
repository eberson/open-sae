package br.org.sae.service.impl;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.POIXMLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.exception.ArquivoInvalidoImportacaoException;
import br.org.sae.exception.ArquivoVazioException;
import br.org.sae.exception.EstruturaInvalidaException;
import br.org.sae.importador.Importador;
import br.org.sae.importador.ImportadorBuilder;
import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.OpcaoPrestada;
import br.org.sae.model.VestibulinhoPrestado;
import br.org.sae.service.CandidatoService;
import br.org.sae.service.CursoService;
import br.org.sae.service.ImportFileType;
import br.org.sae.service.ImportService;
import br.org.sae.service.RespostaImportService;
import br.org.sae.service.VestibulinhoService;

@Service
public class ImportServiceImpl implements ImportService{

	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private VestibulinhoService vestibulinhoService;
	
	@Autowired
	private CandidatoService candidatoService;
	
	@Override
	public RespostaImportService importar(ImportFileType fileType, InputStream input, int ano, int semestre) {
		ImportadorBuilder impBuilder = new ImportadorBuilder();
		impBuilder.setSource(input).setFileType(fileType).setAno(ano).setSemestre(semestre);
		
		Importador importador = impBuilder.build();
		
		try {
			List<Candidato> candidatos = importador.importar();
			
			vestibulinhoService.save(importador.getVestibulinhoAtual());
			
			save(candidatos);
			
			return RespostaImportService.SUCESSO;
		} catch (ArquivoInvalidoImportacaoException e) {
			return RespostaImportService.ARQUIVO_FORMATO_INVALIDO;
		} catch (EstruturaInvalidaException e) {
			return RespostaImportService.ARQUIVO_ESTRUTURA_INVALIDA;
		} catch (ArquivoVazioException e) {
			return RespostaImportService.ARQUIVO_VAZIO;
		} catch (POIXMLException e) {
			return RespostaImportService.ERRO_DESCONHECIDO;
		}
	}

	private void save(List<Candidato> candidatos) {
		for (Candidato candidato : candidatos) {
			List<VestibulinhoPrestado> prestados = candidato.getVestibulinhosPrestados();
			
			for (VestibulinhoPrestado prestado : prestados) {
				loadCursos(prestado);
			}
			
			candidatoService.saveOrUpdate(candidato);
		}
	}

	private void loadCursos(VestibulinhoPrestado vestibulinho) {
		loadCurso(vestibulinho.getPrimeiraOpcao());
		
		OpcaoPrestada segundaOpcao = vestibulinho.getSegundaOpcao();
		
		if(segundaOpcao == null || "-".equals(segundaOpcao.getCurso().getNome())){
			vestibulinho.setSegundaOpcao(null);
		}
		else{
			loadCurso(segundaOpcao);
		}
	}
	
	private void loadCurso(OpcaoPrestada opcao){
		Curso curso = cursoService.findByNome(opcao.getCurso().getNome());
		opcao.setCurso(curso);
	}
}

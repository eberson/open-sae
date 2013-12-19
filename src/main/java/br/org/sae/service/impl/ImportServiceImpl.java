package br.org.sae.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.stereotype.Service;

import br.org.sae.exception.ArquivoVazioException;
import br.org.sae.exception.FormatoInvalidoException;
import br.org.sae.exception.ImpossivelLerException;
import br.org.sae.importador.Importador;
import br.org.sae.importador.ImportadorBuilder;
import br.org.sae.model.Candidato;
import br.org.sae.repositorio.RepositorioCandidato;
import br.org.sae.service.ImportService;
import br.org.sae.service.RespostaImportService;

@Service
public class ImportServiceImpl implements ImportService{

	
	private RepositorioCandidato repositorio;
	
	@Override
	public RespostaImportService importar(File xlsFile, int ano, int semestre) {
		ImportadorBuilder impBuilder = new ImportadorBuilder();
		impBuilder.setSource(xlsFile).setAno(ano).setSemestre(semestre);
		
		Importador importador = impBuilder.build();
		
		try {
			List<Candidato> candidatos = importador.importar();
			
			return RespostaImportService.SUCESSO;
		} catch (FileNotFoundException e) {
			return RespostaImportService.ARQUIVO_NAO_ENCONTRADO;
		} catch (ImpossivelLerException e) {
			return RespostaImportService.ARQUIVO_NAO_ENCONTRADO;
		} catch (FormatoInvalidoException e) {
			return RespostaImportService.FORMATO_ARQUIVO_INVALIDO;
		} catch (ArquivoVazioException e) {
			return RespostaImportService.ARQUIVO_VAZIO;
		}
	}

}

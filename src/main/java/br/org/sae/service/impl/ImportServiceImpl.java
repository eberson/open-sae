package br.org.sae.service.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.POIXMLException;
import org.springframework.stereotype.Service;

import br.org.sae.exception.ArquivoInvalidoImportacaoException;
import br.org.sae.exception.ArquivoVazioException;
import br.org.sae.exception.EstruturaInvalidaException;
import br.org.sae.exception.ImpossivelLerException;
import br.org.sae.importador.Importador;
import br.org.sae.importador.ImportadorBuilder;
import br.org.sae.model.Candidato;
import br.org.sae.repositorio.RepositorioCandidato;
import br.org.sae.service.ImportFileType;
import br.org.sae.service.ImportService;
import br.org.sae.service.RespostaImportService;

@Service(value="excelImport")
@SuppressWarnings("unused")
public class ImportServiceImpl implements ImportService{

	
	private RepositorioCandidato repositorio;
	
	@Override
	public RespostaImportService importar(ImportFileType fileType, InputStream input, int ano, int semestre) {
		ImportadorBuilder impBuilder = new ImportadorBuilder();
		impBuilder.setSource(input).setFileType(fileType).setAno(ano).setSemestre(semestre);
		
		Importador importador = impBuilder.build();
		
		try {
			List<Candidato> candidatos = importador.importar();
			
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

}

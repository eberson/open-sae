package br.org.sae.service;

import java.io.InputStream;

public interface ImportService {

	public RespostaImportService importar(ImportFileType fileType, InputStream input, int ano, int semestre);

}

package br.org.sae.service;

import java.io.File;

public interface ImportService {

	public RespostaImportService importar(File xlsFile, int ano, int semestre);

}

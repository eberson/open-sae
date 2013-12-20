package br.org.sae.importador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import br.org.sae.service.ImportFileType;

public class ImportadorBuilder {
	
	private ImportFileType ft;
	private InputStream input;
	private int ano;
	private int semestre;
	
	public ImportadorBuilder setFileType(ImportFileType fileType) {
		this.ft = fileType;
		return this;
	}
	
	public ImportadorBuilder setSource(InputStream input) {
		this.input = input;
		return this;
	}

	public ImportadorBuilder setSource(File source) {
		try {
			return setSource(new FileInputStream(source));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public ImportadorBuilder setSource(String source) {
		return setSource(new File(source));
	}
	
	public ImportadorBuilder setAno(int ano) {
		this.ano = ano;
		return this;
	}
	
	public ImportadorBuilder setSemestre(int semestre) {
		this.semestre = semestre;
		return this;
	}
	
	public Importador build(){
		if(input == null){
			throw new IllegalStateException("Não foi informado nenhum arquivo para importação.");
		}
		
		Importador importador;
		
		if(ft == null){
			throw new IllegalArgumentException("Um tipo de arquivo deve ser informado na criação do Importador.");
		}

		if(ft== ImportFileType.XLSX){
			importador = new XLSXImportador();
		}
		else{
			importador = new XLSImportador();
		}
		
		return importador.withAno(ano).withSemestre(semestre).withSource(input);
	}
	
	

}

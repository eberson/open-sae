package br.org.sae.importador;

import java.io.File;

import com.google.common.io.Files;

public class ImportadorBuilder {
	
	private File source;
	private int ano;
	private int semestre;
	
	public ImportadorBuilder setSource(File source) {
		this.source = source;
		return this;
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
		if(source == null){
			throw new IllegalStateException("Não foi informado nenhum arquivo para importação.");
		}
		
		Importador importador;
		
		String path = source.getPath();
		String extension = Files.getFileExtension(path);
		
		if("xls".equals(extension)){
			importador = new XLSImportador();
		}
		else if ("xlsx".equals(extension)){
			importador = new XLSXImportador();
		}
		else{
			throw new IllegalArgumentException("O arquivo para importação deve ser do tipo xls ou xlsx.");
		}
		
		return importador.withAno(ano).withSemestre(semestre).withSource(source);
	}
	
	

}

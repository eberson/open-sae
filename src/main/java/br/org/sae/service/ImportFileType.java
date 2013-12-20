package br.org.sae.service;

public enum ImportFileType {
	
	XLS("xls"),
	XLSX("xlsx");
	
	private String mime;
	
	private ImportFileType(String mime){
		this.mime = mime;
	}
	
	@Override
	public String toString() {
		return mime;
	}
	
	public static ImportFileType from(String value){
		ImportFileType[] values = values();
		
		for (ImportFileType ift : values) {
			if(ift.mime.equals(value)){
				return ift;
			}
		}
		
		throw new IllegalArgumentException("Não foi possível encontrar nenhuma constante para o valor informado.");
	}

}

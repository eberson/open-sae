package br.org.sae.exception;

public class ArquivoInvalidoImportacaoException extends Exception{

	private static final long serialVersionUID = 1L;

	public ArquivoInvalidoImportacaoException() {
		super("O arquivo informado não é considerado válido na Importação!");
	}

}

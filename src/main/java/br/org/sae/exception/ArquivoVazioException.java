package br.org.sae.exception;

public class ArquivoVazioException extends Exception{

	private static final long serialVersionUID = 1L;

	public ArquivoVazioException() {
		super("O arquivo informado está vazio.");
	}

}

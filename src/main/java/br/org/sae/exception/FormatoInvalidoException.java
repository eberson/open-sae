package br.org.sae.exception;

public class FormatoInvalidoException extends Exception{

	private static final long serialVersionUID = 1L;

	public FormatoInvalidoException() {
		super("O arquivo informado não contém o formato esperado.");
	}

}

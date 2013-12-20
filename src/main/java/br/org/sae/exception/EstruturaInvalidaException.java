package br.org.sae.exception;

public class EstruturaInvalidaException extends Exception{

	private static final long serialVersionUID = 1L;

	public EstruturaInvalidaException() {
		super("O arquivo informado não contém o formato esperado.");
	}

}

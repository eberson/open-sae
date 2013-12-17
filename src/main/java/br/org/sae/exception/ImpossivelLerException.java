package br.org.sae.exception;

import java.io.IOException;

public class ImpossivelLerException extends IOException{

	private static final long serialVersionUID = 1L;

	public ImpossivelLerException() {
		super("Não é possível ler o arquivo informado!");
	}
}

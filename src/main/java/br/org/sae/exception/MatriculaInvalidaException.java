package br.org.sae.exception;

public class MatriculaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MatriculaInvalidaException() {
		super("Não foi possível realizar a matrícula. Dados inválidos!");
	}

	public MatriculaInvalidaException(String message) {
		super(message);
	}

}

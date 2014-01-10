package br.org.sae.exception;

public class NaoPrestouVestibulinhoException extends MatriculaInvalidaException{

	private static final long serialVersionUID = 1L;

	public NaoPrestouVestibulinhoException() {
		super("O candidato não prestou vestibulinho. Portanto, não pode se candidatar para esta matrícula.");
	}
	
	


}

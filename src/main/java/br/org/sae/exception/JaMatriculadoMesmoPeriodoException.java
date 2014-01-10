package br.org.sae.exception;

public class JaMatriculadoMesmoPeriodoException extends MatriculaInvalidaException{

	private static final long serialVersionUID = 1L;

	public JaMatriculadoMesmoPeriodoException() {
		super("O aluno já possui uma matrícula para o mesmo período.");
	}

}

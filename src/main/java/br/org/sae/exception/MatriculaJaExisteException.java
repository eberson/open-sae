package br.org.sae.exception;

import br.org.sae.model.Turma;

public class MatriculaJaExisteException extends  MatriculaInvalidaException{

	private static final long serialVersionUID = 1L;

	private Turma turma;
	
	public MatriculaJaExisteException(Turma turma) {
		super("O aluno já está matriculado nesta turma! ("+turma.getDescricao()+")");
	}
	
	public Turma getTurma() {
		return turma;
	}

}

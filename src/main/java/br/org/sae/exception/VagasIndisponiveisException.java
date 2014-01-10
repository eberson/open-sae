package br.org.sae.exception;

import br.org.sae.model.Turma;

public class VagasIndisponiveisException extends MatriculaInvalidaException{

	private static final long serialVersionUID = 1L;

	private Turma turma;
	
	public VagasIndisponiveisException(Turma turma) {
		super("Não existem vagas na turma selecionada. ("+turma.getDescricao()+")");
	}
	
	public Turma getTurma() {
		return turma;
	}

}

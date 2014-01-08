package br.org.sae.repository;

import java.util.List;

import br.org.sae.model.Candidato;
import br.org.sae.model.Turma;
import br.org.sae.util.OpcaoVestibulinho;

public interface ConvocacaoRepository {
	
	int vagasDisponiveis(Turma turma);
	
	void resetConvocados(int ano, int semestre);
	
	List<Candidato> getCandidatosInscritos(Turma turma, OpcaoVestibulinho opcao);

	List<Candidato> getCandidatosConvocados(Turma turma, OpcaoVestibulinho opcao);

}

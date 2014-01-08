package br.org.sae.repository;

import java.util.List;

import br.org.sae.model.Aluno;
import br.org.sae.model.Candidato;
import br.org.sae.model.Matricula;
import br.org.sae.model.Turma;
import br.org.sae.util.OpcaoVestibulinho;

public interface MatriculaRepository extends Repository<Matricula>{
	
	int vagasDisponiveis(Turma turma);
	
	void resetConvocados(int ano, int semestre);
	
	List<Candidato> getCandidatosInscritos(Turma turma, OpcaoVestibulinho opcao);

	List<Candidato> getCandidatosConvocados(Turma turma, OpcaoVestibulinho opcao);
	
	boolean candidatoEstaVestibulinho(Candidato candidato, Turma turma);
	
	void marcaConvocado(Turma turma, List<Candidato> candidatos);

	void marcaMatriculado(Turma turma, Candidato... candidatos);

	void marcaExpirado(Turma turma, List<Candidato> candidatos);
	
	void matricular(Matricula matricula);

	void matricularExcepcionalmente(Matricula matricula);
	
	void cancelarMatricula(Matricula matricula);
	
	Matricula find(Aluno aluno, Turma turma);

	List<Matricula> find(Aluno aluno, int ano, int semestre);
	
	List<Matricula> find(Aluno aluno);
	
}

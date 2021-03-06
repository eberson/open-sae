package br.org.sae.repository;

import java.util.List;

import br.org.sae.model.Aluno;
import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.Etapa;
import br.org.sae.model.Matricula;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;
import br.org.sae.model.VestibulinhoPrestado;
import br.org.sae.util.OpcaoVestibulinho;

public interface MatriculaRepository extends Repository<Matricula>{
	
	int vagasDisponiveis(Turma turma);
	
	boolean candidatoEstaVestibulinho(Candidato candidato, Turma turma);

	void resetConvocados(int ano, int semestre);
	
	void marcaConvocado(Turma turma, List<Candidato> candidatos);

	void marcaMatriculado(Turma turma, Candidato... candidatos);

	void marcaExpirado(Turma turma, List<Candidato> candidatos);
	
	void matricular(Matricula matricula);

	void matricularExcepcionalmente(Matricula matricula);
	
	void cancelarMatricula(Matricula matricula);
	
	Matricula find(Aluno aluno, Turma turma);
	
	List<Aluno> getAlunosMatriculados(Curso curso, Periodo periodo);

	List<Candidato> getCandidatosInscritos(Turma turma, OpcaoVestibulinho opcao);
	
	List<Candidato> getCandidatosConvocados(Turma turma, OpcaoVestibulinho opcao);

	List<Matricula> find(Aluno aluno, int ano, int semestre);
	
	List<Matricula> find(Aluno aluno);
	
	List<Aluno> findMatriculados(Turma turma);

	List<Aluno> findMatriculados(Etapa etapa);
	
	List<VestibulinhoPrestado> getVestibulinhosPrestados(Candidato candidato);
	
}

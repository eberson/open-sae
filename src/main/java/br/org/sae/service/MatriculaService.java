package br.org.sae.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import br.org.sae.exception.MatriculaInvalidaException;
import br.org.sae.model.Aluno;
import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;
import br.org.sae.model.Matricula;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;

public interface MatriculaService extends EntityService<Matricula> {
	
	int vagasDisponiveis(Turma turma);
	
	/**
	 * Repete a chamada que foi efetuada anteriormente.
	 * 
	 * @param ano
	 *            ano que será tomado como referência
	 * @param semestre
	 *            semestre que será tomado como referência
	 * @return um mapa contendo os dados dos convocados por turma ou mapa vazio
	 *         caso não tenha dados ou não tenham sido passados parâmetros
	 *         adequados. (não será permitido fazer chamada para um vestibulinho
	 *         que não seja o atual)
	 */
	Map<Turma, List<Candidato>> carregaConvocados(int ano, int semestre);
	
	/**
	 * Faz uma nova chamada para os alunos que prestaram o vestibulinho em todos
	 * os cursos oferecidos.
	 * 
	 * @param ano
	 *            ano que será tomado como referência
	 * @param semestre
	 *            semestre que será tomado como referência
	 * @return um mapa contendo os dados dos convocados por turma ou mapa vazio
	 *         caso não tenha dados ou não tenham sido passados parâmetros
	 *         adequados. (não será permitido fazer chamada para um vestibulinho
	 *         que não seja o atual)
	 */
	Map<Turma, List<Candidato>> convoca(int ano, int semestre);
	
	/**
	 * Faz uma nova chamada para os candidatos que prestaram o vestibulinho no curso
	 * e período informado
	 * 
	 * @param ano
	 *            ano que será tomado como referência
	 * @param semestre
	 *            semestre que será tomado como referência
	 * @param curso
	 *            curso que será tomado como referência.
	 * @param periodo
	 *            período a ser usado como referência
	 * @return uma lista contando todos os convocados para cenário proposto
	 */
	List<Candidato> convoca(int ano, int semestre, Curso curso, Periodo periodo);


	/**
	 * 
	 * Repete a chamada dos candidatos que prestaram o vestibulinho no curso e
	 * período informado
	 * 
	 * @param ano
	 *            ano que será tomado como referência
	 * @param semestre
	 *            semestre que será tomado como referência
	 * @param curso
	 *            curso que será tomado como referência.
	 * @param periodo
	 *            período a ser usado como referência
	 * @return uma lista contando todos os convocados para cenário proposto
	 */
	List<Candidato> carregaConvocados(int ano, int semestre, Curso curso, Periodo periodo);
	
	/**
	 * Matricula um candidato em curso.
	 * 
	 * @param candidato
	 *            candidato a ser matriculado
	 * @param turma
	 *            turma onde ele vai ser matriculado
	 * @param data
	 *            data da matrícula
	 * 
	 * @return status da operação
	 * 
	 * @throws MatriculaInvalidaException
	 *             caso o aluno já esteja matriculado nesta turma, ou caso ela
	 *             já esteja matriculado no mesmo período, ou caso ele não tenha
	 *             participado do vestibulinho no semestre da turma
	 */
	RespostaMatricula matricular(Candidato candidato, Turma turma, Date data);

	/**
	 * Matricula um candidato sem passar por verificação de vestibulinho
	 * 
	 * @param candidato
	 *            candidato a ser matriculado
	 * @param turma
	 *            turma onde ele vai ser matriculado
	 * @param data
	 *            data da matrícula
	 *            
	 * @return status da operação
	 * 
	 * @throws MatriculaInvalidaException
	 *             caso o aluno já esteja matriculado nesta turma, ou caso ela
	 *             já esteja matriculado no mesmo período
	 */
	RespostaMatricula matricularExcepcionalmente(Candidato candidato, Turma turma, Date data);

	/**
	 * Realiza a rematrícula do aluno na etapa atual em que a turma se encontra.
	 * 
	 * @param aluno
	 *            aluno a ser rematriculado
	 * @param turma
	 *            turma onde o aluno será rematriculado
	 * @param data
	 *            data da rematrícula
	 * @return status da operação
	 * @throws MatriculaInvalidaException
	 *             caso o aluno já esteja matriculado nesta etapa em que a turma
	 *             se encontra, ou, caso ele já esteja matriculado no mesmo
	 *             período, ou, caso seja a primeira matrícula do aluno
	 * 
	 */
	RespostaRematricula rematricular(Aluno aluno, Turma turma, Date data);
	
	/**
	 * Cancela a matrícula de um aluno em uma determinada turma. Caso ainda não
	 * tenha sido gerada a lista piloto para a turma na etapa em que se encontra
	 * o cancelamento exclui a matrícula. Se o aluno esta ingressando no curso e
	 * a lista piloto ainda não foi gerada, então o cancelamento irá excluir o
	 * aluno e a matrícula. Do contrário apenas irá mudar o status da matrícula
	 * para matrícula cancelada.
	 * 
	 * @param aluno
	 *            aluno que deseja cancelar a matrícula
	 * @param turma
	 *            turma na qual a matrícula será cancelada
	 * 
	 * @return status da operação
	 */
	RespostaCancelamentoMatricula cancelarMatricula(Aluno aluno, Turma turma);
	
	/**
	 * Busca todas as matrículas já efetuadas pelo aluno.
	 * 
	 * @param aluno
	 *            aluno para consultar matrículas.
	 * @return uma lista contendo todas as matrículas efetuadas pelo aluno ou
	 *         uma lista vazia caso não haja nenhuma.
	 */
	List<Matricula> findAll(Aluno aluno);

}

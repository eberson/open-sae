package br.org.sae.service;

import java.util.List;
import java.util.Map;

import br.org.sae.model.Candidato;
import br.org.sae.model.Periodo;
import br.org.sae.model.Turma;

public interface ConvocacaoService {
	
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
	List<Candidato> convoca(int ano, int semestre, long curso, Periodo periodo);

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
	List<Candidato> carregaConvocados(int ano, int semestre, long curso, Periodo periodo);

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
	List<Candidato> convoca(int ano, int semestre, String curso, Periodo periodo);

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
	List<Candidato> carregaConvocados(int ano, int semestre, String curso, Periodo periodo);

}

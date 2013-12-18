package br.org.sae.importador;

public interface ImportadorConstants {
	
	int CANDIDATO_NOME = 0;
	int CANDIDATO_RG_NUMERO = 2;
	int CANDIDATO_RG_ORGAO_EXPED = 3;
	int CANDIDATO_SEXO = 4;
	int CANDIDATO_DT_NASCIMENTO = 5;
	int CANDIDATO_ESTADO_CIVIL = 6;
	int CANDIDATO_AFRODESCENDENTE = 17;
	int CANDIDATO_ESCOLARIDADE = 18;
	int CANDIDATO_EMAIL = 20;
	int CANDIDATO_NECESSIDADE = 21;
	int CANDIDATO_NECESSIDADE_TIPO = 22;
	int CANDIDATO_CPF = 59;
	
	int VESTIBULINHO_TIPO_PROVA = 23;
	
	int CURSO1_NOME = 1;
	int CURSO1_PERIODO = 19;
	int CURSO1_CODESCOLACURSO = 27;
	int CURSO1_CLASSIFICACAO = 37;

	int CURSO2_NOME = 55;
	int CURSO2_PERIODO = 56;
	int CURSO2_CODESCOLACURSO = 54;
	int CURSO2_CLASSIFICACAO = 57;
	

	int TELEFONE_P_DDD = 11;
	int TELEFONE_P_NUMERO = 12;
	int TELEFONE_P_RAMAL = 13;

	int TELEFONE_S_DDD = 24;
	int TELEFONE_S_NUMERO = 25;
	int TELEFONE_S_RAMAL = 26;
	
	int ENDERECO_LOGRADOURO = 7;
	int ENDERECO_NUMERO = 8;
	int ENDERECO_COMPLEMENTO = 9;
	int ENDERECO_BAIRRO = 10;
	int ENDERECO_CIDADE = 14;
	int ENDERECO_UF = 15;
	int ENDERECO_CEP = 16;

	int[] COLUNAS = { CANDIDATO_NOME,
                      CANDIDATO_RG_NUMERO,
                      CANDIDATO_RG_ORGAO_EXPED,
                      CANDIDATO_SEXO,
                      CANDIDATO_DT_NASCIMENTO,
                      CANDIDATO_ESTADO_CIVIL,
                      CANDIDATO_AFRODESCENDENTE,
                      CANDIDATO_ESCOLARIDADE,
                      CANDIDATO_EMAIL,
                      CANDIDATO_NECESSIDADE,
                      CANDIDATO_NECESSIDADE_TIPO,
                      CANDIDATO_CPF,
                      VESTIBULINHO_TIPO_PROVA,
                      CURSO1_NOME,
                      CURSO1_PERIODO,
                      CURSO1_CODESCOLACURSO,
                      CURSO1_CLASSIFICACAO,
                      CURSO2_NOME,
                      CURSO2_PERIODO,
                      CURSO2_CODESCOLACURSO,
                      CURSO2_CLASSIFICACAO,
                      TELEFONE_P_DDD,
                      TELEFONE_P_NUMERO,
                      TELEFONE_P_RAMAL,
                      TELEFONE_S_DDD,
                      TELEFONE_S_NUMERO,
                      TELEFONE_S_RAMAL,
                      ENDERECO_LOGRADOURO,
                      ENDERECO_NUMERO,
                      ENDERECO_COMPLEMENTO,
                      ENDERECO_BAIRRO,
                      ENDERECO_CIDADE,
                      ENDERECO_UF,
                      ENDERECO_CEP };
}

package br.org.sae.importador;

import static br.org.sae.importador.ImportadorConstants.CANDIDATO_AFRODESCENDENTE;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_CPF;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_DT_NASCIMENTO;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_EMAIL;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_ESCOLARIDADE;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_ESTADO_CIVIL;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_NECESSIDADE;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_NECESSIDADE_TIPO;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_NOME;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_RG_NUMERO;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_RG_ORGAO_EXPED;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_SEXO;
import static br.org.sae.importador.ImportadorConstants.CURSO1_CLASSIFICACAO;
import static br.org.sae.importador.ImportadorConstants.CURSO1_CODESCOLACURSO;
import static br.org.sae.importador.ImportadorConstants.CURSO1_NOME;
import static br.org.sae.importador.ImportadorConstants.CURSO1_PERIODO;
import static br.org.sae.importador.ImportadorConstants.CURSO2_CLASSIFICACAO;
import static br.org.sae.importador.ImportadorConstants.CURSO2_CODESCOLACURSO;
import static br.org.sae.importador.ImportadorConstants.CURSO2_NOME;
import static br.org.sae.importador.ImportadorConstants.CURSO2_PERIODO;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_BAIRRO;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_CEP;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_CIDADE;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_COMPLEMENTO;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_LOGRADOURO;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_NUMERO;
import static br.org.sae.importador.ImportadorConstants.ENDERECO_UF;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_P_DDD;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_P_NUMERO;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_P_RAMAL;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_S_DDD;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_S_NUMERO;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_S_RAMAL;
import static br.org.sae.importador.ImportadorConstants.VESTIBULINHO_TIPO_PROVA;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import br.org.sae.exception.ArquivoInvalidoImportacaoException;
import br.org.sae.exception.ArquivoVazioException;
import br.org.sae.exception.EstruturaInvalidaException;
import br.org.sae.importador.leitor.LeitorUtil;
import br.org.sae.model.Candidato;

public abstract class Importador {
	
	protected int limiteInferior = 1;
	protected int limiteSuperior = 1000;
	
	private InputStream source;
	private int ano;
	private int semestre;
	
	public abstract Workbook create(InputStream in) throws IOException;
	public abstract List<Candidato> processa(Sheet sheet, LeitorUtil util);
	
	public Importador withSource(InputStream source) {
		this.source = source;
		return this;
	}
	
	public Importador withAno(int ano) {
		this.ano = ano;
		return this;
	}
	
	public Importador withSemestre(int semestre) {
		this.semestre = semestre;
		return this;
	}
	
	protected int getAno() {
		return ano;
	}
	
	protected int getSemestre() {
		return semestre;
	}
	
	public List<Candidato> importar() throws EstruturaInvalidaException, ArquivoVazioException, ArquivoInvalidoImportacaoException{
		try {
			Workbook workbook = create(source);
			
			checkFormato(workbook);
			
			Sheet sheet = workbook.getSheet("dados");
			
			limiteInferior = 1;
			limiteSuperior = sheet.getLastRowNum();

			LeitorUtil util = new LeitorUtil(ano, semestre);
			
			return processa(sheet, util);
			
		} catch (POIXMLException e) {
			if(e.getCause() != null && e.getCause() instanceof InvalidFormatException ){
				throw new ArquivoInvalidoImportacaoException();
			}
			
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void checkFormato(Workbook workbook) throws ArquivoVazioException, EstruturaInvalidaException{
		Sheet sheet = workbook.getSheet("dados");
		
		if(sheet == null){
			throw new EstruturaInvalidaException();
		}
		
		if(sheet.getLastRowNum() == 0){
			throw new ArquivoVazioException();
		}
		
		int inicio = sheet.getFirstRowNum() + 1;
		int fim = sheet.getLastRowNum();
		
		for(int i = inicio; i <= fim; i++ ){
			checkColumn(sheet.getRow(i));
		}
		
	}
	
	private void checkColumn(Row row) throws EstruturaInvalidaException {
		short lastCellNum = row.getLastCellNum();
		
		for (int i = 0; i < lastCellNum; i++) {
			Cell cell = row.getCell(i);
			
			if(cell == null){
				continue;
			}

			if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				continue;
			}
			
			switch (i) {
				case CURSO1_CODESCOLACURSO:
				case CURSO1_CLASSIFICACAO:
				case CURSO2_CODESCOLACURSO:
				case CURSO2_CLASSIFICACAO:
				case TELEFONE_P_DDD:
				case TELEFONE_S_DDD:
					if (cell.getCellType() != Cell.CELL_TYPE_NUMERIC && cell.getCellType() != Cell.CELL_TYPE_STRING) {
						throw new EstruturaInvalidaException();
					}
					break;
				case CANDIDATO_NOME:
				case CANDIDATO_RG_NUMERO:
				case CANDIDATO_RG_ORGAO_EXPED:
				case CANDIDATO_SEXO:
				case CANDIDATO_DT_NASCIMENTO:
				case CANDIDATO_ESTADO_CIVIL:
				case CANDIDATO_AFRODESCENDENTE:
				case CANDIDATO_ESCOLARIDADE:
				case CANDIDATO_EMAIL:
				case CANDIDATO_NECESSIDADE:
				case CANDIDATO_NECESSIDADE_TIPO:
				case CANDIDATO_CPF:
				case VESTIBULINHO_TIPO_PROVA:
				case CURSO1_NOME:
				case CURSO1_PERIODO:
				case CURSO2_NOME:
				case CURSO2_PERIODO:
				case TELEFONE_P_NUMERO:
				case TELEFONE_P_RAMAL:
				case TELEFONE_S_NUMERO:
				case TELEFONE_S_RAMAL:
				case ENDERECO_LOGRADOURO:
				case ENDERECO_NUMERO:
				case ENDERECO_COMPLEMENTO:
				case ENDERECO_BAIRRO:
				case ENDERECO_CIDADE:
				case ENDERECO_UF:
				case ENDERECO_CEP:
					if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
						throw new EstruturaInvalidaException();
					}
					break;
			}
		}

	}
	
	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\aluno\\Desktop\\matricula-real.xlsx");

		DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		
		ImportadorBuilder builder = new ImportadorBuilder();
		Importador importador = builder.setAno(2013).setSemestre(2).setSource(file).build();
		
		List<Candidato> candidatos = importador.importar();
		
		for (Candidato candidato : candidatos) {
			System.out.println("Nome: " + candidato.getNome());
			System.out.println("RG: " + candidato.getRg());
			System.out.println("Orgao Expedidor: " + candidato.getOrgaoExpedidor());
			System.out.println("Sexo: " + candidato.getSexo());
			System.out.println("Data Nascimento: " + formatador.format(candidato.getDataNascimento()));
			System.out.println("Estado Civil: " + candidato.getEstadoCivil());
			System.out.println("Endereco: " + candidato.getEndereco().getEndereco());
			System.out.println("Numero: " + candidato.getEndereco().getNumero());
			System.out.println("Complemento: " + candidato.getEndereco().getComplemento());
			System.out.println("Bairro: " + candidato.getEndereco().getBairro());
			System.out.println("Cidade: " + candidato.getEndereco().getCidade());
			System.out.println("UF: " + candidato.getEndereco().getUf());
			System.out.println("CEP: " + candidato.getEndereco().getCep());
			System.out.println("E-mail: " + candidato.getEmail());
			System.out.println("Necessidade Especial: " + candidato.getNecessidadeEspecial());
			System.out.println("Tipo de Necessidade: " + candidato.getNecessidadeTipo());
			System.out.println("Afro Descendente: " + candidato.isAfroDescendente());
			System.out.println("DDD1: " + candidato.getTelefonePrincipal().getDdd());
			System.out.println("Telefone1: " + candidato.getTelefonePrincipal().getTelefone());
			System.out.println("Ramal1: " + candidato.getTelefonePrincipal().getRamal());
			System.out.println("DDD2: " + candidato.getTelefoneSecundario().getDdd());
			System.out.println("Telefone2: " + candidato.getTelefoneSecundario().getTelefone());
			System.out.println("Ramal2: " + candidato.getTelefoneSecundario().getRamal());
		}
	}

}

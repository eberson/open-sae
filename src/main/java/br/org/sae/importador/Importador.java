package br.org.sae.importador;

import static br.org.sae.importador.ImportadorConstants.CURSO1_CLASSIFICACAO;
import static br.org.sae.importador.ImportadorConstants.CURSO1_CODESCOLACURSO;
import static br.org.sae.importador.ImportadorConstants.CURSO2_CLASSIFICACAO;
import static br.org.sae.importador.ImportadorConstants.CURSO2_CODESCOLACURSO;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_P_DDD;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_S_DDD;

import static br.org.sae.importador.ImportadorConstants.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import br.org.sae.exception.ArquivoVazioException;
import br.org.sae.exception.FormatoInvalidoException;
import br.org.sae.exception.ImpossivelLerException;
import br.org.sae.importador.leitor.LeitorUtil;
import br.org.sae.model.Candidato;

public abstract class Importador {
	
	protected int limiteInferior = 1;
	protected int limiteSuperior = 1000;
	
	private File source;
	private int ano;
	private int semestre;
	
	public abstract Workbook create(InputStream in) throws IOException;
	public abstract List<Candidato> processa(Sheet sheet, LeitorUtil util);
	
	public Importador withSource(File source) {
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
	
	public List<Candidato> importar() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException, ArquivoVazioException{
		if(source == null || !source.exists()){
			throw new FileNotFoundException("Arquivo contendo dados não foi encontrado no caminho especificado.");
		}
		
		if(!source.canRead()){
			throw new ImpossivelLerException();
		}
		
		try {
			FileInputStream in = new FileInputStream(source);
			Workbook workbook = create(in);
			
			checkFormato(workbook);
			
			Sheet sheet = workbook.getSheet("dados");
			
			limiteInferior = 1;
			limiteSuperior = sheet.getLastRowNum();

			LeitorUtil util = new LeitorUtil(ano, semestre);
			
			return processa(sheet, util);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void checkFormato(Workbook workbook) throws ArquivoVazioException, FormatoInvalidoException{
		Sheet sheet = workbook.getSheet("dados");
		
		if(sheet == null){
			throw new FormatoInvalidoException();
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
	
	private void checkColumn(Row row) throws FormatoInvalidoException {
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
						throw new FormatoInvalidoException();
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
						throw new FormatoInvalidoException();
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

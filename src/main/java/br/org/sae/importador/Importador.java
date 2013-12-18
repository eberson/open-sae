package br.org.sae.importador;

import static br.org.sae.importador.ImportadorConstants.COLUNAS;
import static br.org.sae.importador.ImportadorConstants.CURSO1_CLASSIFICACAO;
import static br.org.sae.importador.ImportadorConstants.CURSO1_CODESCOLACURSO;
import static br.org.sae.importador.ImportadorConstants.CURSO2_CLASSIFICACAO;
import static br.org.sae.importador.ImportadorConstants.CURSO2_CODESCOLACURSO;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_P_DDD;
import static br.org.sae.importador.ImportadorConstants.TELEFONE_S_DDD;

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
		for (int i = 0; i <= COLUNAS.length; i++) {
			int indexColuna = COLUNAS[i];
			Cell cell = row.getCell(indexColuna);

			if (cell != null && cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				continue;
			}
			
			switch (indexColuna) {
				case CURSO1_CODESCOLACURSO:
				case CURSO1_CLASSIFICACAO:
				case CURSO2_CODESCOLACURSO:
				case CURSO2_CLASSIFICACAO:
				case TELEFONE_P_DDD:
				case TELEFONE_S_DDD:
					if (cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
						throw new FormatoInvalidoException();
					}
					break;
				default:
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

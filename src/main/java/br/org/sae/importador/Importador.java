package br.org.sae.importador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import br.org.sae.exception.FormatoInvalidoException;
import br.org.sae.exception.ImpossivelLerException;
import br.org.sae.model.Candidato;

abstract class Importador {
	
	private File source;
	private int ano;
	private int semestre;
	
	public abstract Workbook create(InputStream in) throws IOException;
	public abstract List<Candidato> processa(Workbook workbook);
	
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
	
	public List<Candidato> importar() throws FileNotFoundException, ImpossivelLerException, FormatoInvalidoException{
		if(source == null || !source.exists()){
			throw new FileNotFoundException("Arquivo contendo dados não foi encontrado no caminho especificado.");
		}
		
		if(!source.canRead()){
			throw new ImpossivelLerException();
		}
		
		try {
			FileInputStream in = new FileInputStream(source);
			Workbook workbook = create(in);
			
			if(!checkFormato(workbook)){
				throw new FormatoInvalidoException();
			}
			
			return processa(workbook);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected boolean checkFormato(Workbook workbook){
		return true;
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

package br.org.sae;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.org.sae.model.Candidato;
import br.org.sae.model.Curso;

public class Importador {
	
	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\aluno\\Desktop\\Matricula_2013 2ª CHAMADA.xls");
		
		FileInputStream in = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		
		HSSFSheet sheet = workbook.getSheetAt(1);
		LeitorCandidato leitorCandidato = new LeitorCandidato();
		LeitorCurso leitorCurso = new LeitorCurso();
		
		DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		
		for(int i = 1; i < 200; i++){
			HSSFRow row = sheet.getRow(i);
			
			Candidato candidato = leitorCandidato.le(row);
			Curso curso = leitorCurso.le(row);
			
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

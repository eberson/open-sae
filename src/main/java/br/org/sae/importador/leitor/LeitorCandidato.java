package br.org.sae.importador.leitor;

import static br.org.sae.importador.ImportadorConstants.CANDIDATO_AFRODESCENDENTE;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_DT_NASCIMENTO;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_EMAIL;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_ESTADO_CIVIL;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_NECESSIDADE;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_NECESSIDADE_TIPO;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_NOME;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_RG_NUMERO;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_RG_ORGAO_EXPED;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_SEXO;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_CPF;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.Candidato;
import br.org.sae.model.EstadoCivil;
import br.org.sae.model.Sexo;
import br.org.sae.model.Telefone;

public class LeitorCandidato implements DadoLegivel<Candidato>{
	
	public Candidato le(Row row, LeitorUtil util) {
		Candidato c = new Candidato();
		
		c.setNome(row.getCell(CANDIDATO_NOME).getStringCellValue());
		c.setCpf(row.getCell(CANDIDATO_CPF).getStringCellValue());
		c.setRg(util.leitorColunaRGCandidato().le(row.getCell(CANDIDATO_RG_NUMERO), util));
		c.setOrgaoExpedidor(row.getCell(CANDIDATO_RG_ORGAO_EXPED).getStringCellValue());
		c.setSexo(Sexo.valueOf(row.getCell(CANDIDATO_SEXO).getStringCellValue()));
		c.setDataNascimento(util.leitorColunaDataNascimento().le(row.getCell(CANDIDATO_DT_NASCIMENTO), util));
		c.setEstadoCivil(EstadoCivil.valueOf(row.getCell(CANDIDATO_ESTADO_CIVIL).getStringCellValue()));
		c.setEndereco(util.leitorEndereco().le(row, util));
		c.setEmail(row.getCell(CANDIDATO_EMAIL).getStringCellValue());
		c.setNecessidadeEspecial(row.getCell(CANDIDATO_NECESSIDADE).getStringCellValue());
		c.setNecessidadeTipo(row.getCell(CANDIDATO_NECESSIDADE_TIPO).getStringCellValue());
		c.setAfroDescendente(util.leitorColunaAfrodescendente().le(row.getCell(CANDIDATO_AFRODESCENDENTE), util));
		
		Telefone[] telefones = util.leitorTelefone().le(row, util);
		c.setTelefonePrincipal(telefones[0]);
		c.setTelefoneSecundario(telefones[1]);
		
		c.addVestibulinho(util.leitorVestibulinho().le(row, util));
		
		return c;
	}

}

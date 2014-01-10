package br.org.sae.importador.leitor;

import static br.org.sae.importador.ImportadorConstants.CANDIDATO_AFRODESCENDENTE;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_CPF;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_DT_NASCIMENTO;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_EMAIL;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_ESTADO_CIVIL;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_NECESSIDADE;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_NECESSIDADE_TIPO;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_NOME;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_RG_NUMERO;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_RG_ORGAO_EXPED;
import static br.org.sae.importador.ImportadorConstants.CANDIDATO_SEXO;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.Candidato;
import br.org.sae.model.EstadoCivil;
import br.org.sae.model.Sexo;
import br.org.sae.model.Telefone;

public class LeitorCandidato implements DadoLegivel<Candidato>{
	
	public Candidato le(Row row, LeitorUtil util) {
		Candidato c = new Candidato();
		
		c.setNome(util.reader(String.class).value(row, CANDIDATO_NOME));
		c.setCpf(util.reader(String.class).value(row, CANDIDATO_CPF));
		c.setRg(util.leitorColunaRGCandidato().le(row.getCell(CANDIDATO_RG_NUMERO), util));
		c.setOrgaoExpedidor(util.reader(String.class).value(row, CANDIDATO_RG_ORGAO_EXPED));
		c.setSexo(Sexo.valueOf(util.reader(String.class).value(row, CANDIDATO_SEXO)));
		c.setDataNascimento(util.leitorColunaDataNascimento().le(row.getCell(CANDIDATO_DT_NASCIMENTO), util));
		c.setEstadoCivil(EstadoCivil.valueOf(util.reader(String.class).value(row, CANDIDATO_ESTADO_CIVIL)));
		c.setEndereco(util.leitorEndereco().le(row, util));
		c.setEmail(util.reader(String.class).value(row, CANDIDATO_EMAIL));
		c.setNecessidadeEspecial(util.reader(String.class).value(row, CANDIDATO_NECESSIDADE));
		c.setNecessidadeTipo(util.reader(String.class).value(row, CANDIDATO_NECESSIDADE_TIPO));
		c.setAfroDescendente(util.leitorColunaAfrodescendente().le(row.getCell(CANDIDATO_AFRODESCENDENTE), util));
		
		Telefone[] telefones = util.leitorTelefone().le(row, util);
		c.setTelefonePrincipal(telefones[0]);
		c.setTelefoneSecundario(telefones[1]);
		
		c.addVestibulinhoPrestado(util.leitorVestibulinho().le(row, util));
		
		return c;
	}

}

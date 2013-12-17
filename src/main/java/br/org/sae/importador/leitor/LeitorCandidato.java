package br.org.sae.importador.leitor;

import org.apache.poi.ss.usermodel.Row;

import br.org.sae.model.Candidato;
import br.org.sae.model.EstadoCivil;
import br.org.sae.model.Sexo;
import br.org.sae.model.Telefone;

public class LeitorCandidato implements DadoLegivel<Candidato>{
	
	private LeitorEndereco leitorEndereco = new LeitorEndereco();
	private LeitorAfroDescendente leitorAfroDescendente = new LeitorAfroDescendente();
	private RGCandidatoLeitor rgLeitor = new RGCandidatoLeitor();
	private DataNascimentoLeitor nascimentoLeitor = new DataNascimentoLeitor();
	private LeitorTelefone leitorTelefone = new LeitorTelefone();

	public Candidato le(Row row) {
		Candidato c = new Candidato();
		
		c.setNome(row.getCell(0).getStringCellValue());
		c.setRg(rgLeitor.le(row.getCell(2)));
		c.setOrgaoExpedidor(row.getCell(3).getStringCellValue());
		c.setSexo(Sexo.valueOf(row.getCell(4).getStringCellValue()));
		c.setDataNascimento(nascimentoLeitor.le(row.getCell(5)));
		c.setEstadoCivil(EstadoCivil.valueOf(row.getCell(6).getStringCellValue()));
		c.setEndereco(leitorEndereco.le(row));
		c.setEmail(row.getCell(20).getStringCellValue());
		c.setNecessidadeEspecial(row.getCell(21).getStringCellValue());
		c.setNecessidadeTipo(row.getCell(22).getStringCellValue());
		c.setAfroDescendente(leitorAfroDescendente.le(row.getCell(17)));
		
		Telefone[] telefones = leitorTelefone.le(row);
		c.setTelefonePrincipal(telefones[0]);
		c.setTelefoneSecundario(telefones[1]);
		
		return c;
	}

}

package br.org.sae.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.sae.model.Aluno;
import br.org.sae.model.Candidato;
import br.org.sae.repository.AlunoRepository;
import br.org.sae.repository.Repository;
import br.org.sae.service.AlunoService;

@Service
public class AlunoServiceImpl extends EntityServiceImpl<Aluno> implements AlunoService{

	@Autowired
	private AlunoRepository repository;
	
	@Override
	protected Repository<Aluno> repository() {
		return repository;
	}

	@Override
	public Aluno from(Candidato candidato) {
		Aluno aluno = new Aluno();
		aluno.setAfroDescendente(candidato.isAfroDescendente());
		aluno.setCpf(candidato.getCpf());
		aluno.setDataNascimento(candidato.getDataNascimento());
		aluno.setEmail(candidato.getEmail());
		aluno.setEndereco(candidato.getEndereco());
		aluno.setEstadoCivil(candidato.getEstadoCivil());
		aluno.setNecessidadeTipo(candidato.getNecessidadeTipo());
		aluno.setNecessidaEspecial(candidato.getNecessidadeEspecial());
		aluno.setNome(candidato.getNome());
		aluno.setOrgaoExpedidor(candidato.getOrgaoExpedidor());
		aluno.setRA("1234");
		aluno.setRg(candidato.getRg());
		aluno.setRM("12345");
		aluno.setSexo(candidato.getSexo());
		aluno.setTelefonePrincipal(candidato.getTelefonePrincipal());
		aluno.setTelefoneSecundario(candidato.getTelefoneSecundario());
		
		return aluno;
	}
	
	@Override
	public Aluno findByCPF(String cpf) {
		return repository.findByCPF(cpf);
	}
	
	@Override
	public List<Aluno> findAll(String nome) {
		return repository.findAll(nome);
	}

}

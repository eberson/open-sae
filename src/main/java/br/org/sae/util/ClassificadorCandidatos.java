package br.org.sae.util;

import java.util.Comparator;
import java.util.List;

import br.org.sae.model.Candidato;
import br.org.sae.model.OpcaoPrestada;
import br.org.sae.model.VestibulinhoPrestado;

public class ClassificadorCandidatos implements Comparator<Candidato>{

	private OpcaoVestibulinho opcao;
	private int ano;
	private int semestre;
	
	public ClassificadorCandidatos(int ano, int semestre, OpcaoVestibulinho opcao) {
		super();
		this.opcao = opcao;
		this.ano = ano;
		this.semestre = semestre;
	}

	@Override
	public int compare(Candidato o1, Candidato o2) {
		if(opcao == OpcaoVestibulinho.SEM_SEGUNDA_OPCAO){
			VestibulinhoPrestado vp1 = getVestibulinhoPrestado(o1);
			VestibulinhoPrestado vp2 = getVestibulinhoPrestado(o2);
			
			return Double.valueOf(vp1.getNota()).compareTo(vp2.getNota());
		}
		
		OpcaoPrestada op1 = getOpcaoPrestada(o1);
		OpcaoPrestada op2 = getOpcaoPrestada(o2);

		if(op1 != null && op2 != null){
			return Integer.valueOf(op1.getClassificacao()).compareTo(op2.getClassificacao());
		}
		
		if(op1 == null && op2 == null){
			return 0;
		}
		
		if(op1 == null){
			return 1;
		}
		
		return -1;
	}
	
	private OpcaoPrestada getOpcaoPrestada(Candidato candidato){
		List<VestibulinhoPrestado> vestibulinhos = candidato.getVestibulinhosPrestados();
		
		for (VestibulinhoPrestado vestibulinhoPrestado : vestibulinhos) {
			int anoPrestado = vestibulinhoPrestado.getVestibulinho().getAno();
			int semestrePrestado = vestibulinhoPrestado.getVestibulinho().getSemestre();
			
			if(ano == anoPrestado && semestre == semestrePrestado){
				switch (opcao) {
					case PRIMEIRA_OPCAO:
						return vestibulinhoPrestado.getPrimeiraOpcao();
					case SEGUNDA_OPCAO:
						return vestibulinhoPrestado.getSegundaOpcao();
					case SEM_SEGUNDA_OPCAO:
						
						
						return null;
				}
			}
		}
		
		return null;
	}

	private VestibulinhoPrestado getVestibulinhoPrestado(Candidato candidato){
		List<VestibulinhoPrestado> vestibulinhos = candidato.getVestibulinhosPrestados();
		
		for (VestibulinhoPrestado vestibulinhoPrestado : vestibulinhos) {
			int anoPrestado = vestibulinhoPrestado.getVestibulinho().getAno();
			int semestrePrestado = vestibulinhoPrestado.getVestibulinho().getSemestre();
			
			if(ano == anoPrestado && semestre == semestrePrestado){
				return vestibulinhoPrestado;
			}
		}
		
		return null;
	}

}

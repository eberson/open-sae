package br.org.sae.model;

import java.io.Serializable;

public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ddd;
	private String telefone;
	private String ramal;

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

}

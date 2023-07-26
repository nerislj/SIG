package br.gov.sc.visita.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;

@SuppressWarnings("serial")
@Entity
public class Visitante extends GenericDomain {

	@Column(length = 11, nullable = false, unique = true)
	private String cpf;

	@Column(length = 50, nullable = false)
	private String nomeCompleto;

	@Column(length = 11)
	private String rg;


	@ManyToOne
	@JoinColumn(nullable = false)
	private Estado estado;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Cidade municipio;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Cidade getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Cidade municipio) {
		this.municipio = municipio;
	}

	

	

	
	
}

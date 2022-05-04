package br.gov.sc.sgi.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class RastreioRelacao extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private Oficio oficio;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Rastreio rastreio;

	public Oficio getOficio() {
		return oficio;
	}

	public void setOficio(Oficio oficio) {
		this.oficio = oficio;
	}

	public Rastreio getRastreio() {
		return rastreio;
	}

	public void setRastreio(Rastreio rastreio) {
		this.rastreio = rastreio;
	}

	
}


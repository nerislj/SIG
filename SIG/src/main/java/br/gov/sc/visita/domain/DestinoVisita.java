package br.gov.sc.visita.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.gov.sc.sgi.domain.PessoaFisica;

@SuppressWarnings("serial")
@Entity
public class DestinoVisita extends GenericDomain {

	@Column(length = 255)
	private String destino;
	
	@Column(length = 255)
	private String nSala;

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getnSala() {
		return nSala;
	}

	public void setnSala(String nSala) {
		this.nSala = nSala;
	}





}

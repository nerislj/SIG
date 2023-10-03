package br.gov.sc.pesquisa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.visita.domain.GenericDomain;

@SuppressWarnings("serial")
@Entity
public class Pesquisa extends GenericDomain {

	
	@Column(length = 255)
	private String nomeCredenciado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dataHora;


	

	public String getNomeCredenciado() {
		return nomeCredenciado;
	}

	public void setNomeCredenciado(String nomeCredenciado) {
		this.nomeCredenciado = nomeCredenciado;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}


	



	
	

	
	
	



}

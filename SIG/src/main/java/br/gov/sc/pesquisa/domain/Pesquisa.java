package br.gov.sc.pesquisa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.visita.domain.GenericDomain;

@SuppressWarnings("serial")
@Entity
public class Pesquisa extends GenericDomain {

	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cidade municipio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dataHora;

	@Column(length = 255)
	private String tipoCredenciado;
	
	@Column(length = 255)
	private String credenciado;

	public Cidade getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Cidade municipio) {
		this.municipio = municipio;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getTipoCredenciado() {
		return tipoCredenciado;
	}

	public void setTipoCredenciado(String tipoCredenciado) {
		this.tipoCredenciado = tipoCredenciado;
	}

	public String getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(String credenciado) {
		this.credenciado = credenciado;
	}
	
	

	
	
	



}

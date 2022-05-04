package br.gov.sc.sgi.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@SuppressWarnings("serial")
@Entity
public class Rastreio extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuario;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataSaida;

	@Column(nullable = false)
	private String destino;

	@Column(nullable = false)
	private String rastreio;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rastreio")
	@Fetch(FetchMode.SUBSELECT)
	private List<RastreioRelacao> rastreioRelacao;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getRastreio() {
		return rastreio;
	}

	public void setRastreio(String rastreio) {
		this.rastreio = rastreio;
	}

	public List<RastreioRelacao> getRastreioRelacao() {
		return rastreioRelacao;
	}

	public void setRastreioRelacao(List<RastreioRelacao> rastreioRelacao) {
		this.rastreioRelacao = rastreioRelacao;
	}

}
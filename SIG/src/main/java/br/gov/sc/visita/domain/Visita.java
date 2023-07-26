package br.gov.sc.visita.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.sc.sgi.domain.PessoaFisica;

@SuppressWarnings("serial")
@Entity
public class Visita extends GenericDomain {

	
	@OneToOne
	@JoinColumn(nullable = false)
	private Visitante visitante;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private CategoriaVisita categoria;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private DestinoVisita destino;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dataHora;

	@Column(length = 255)
	private String nPessoas;

	public Visitante getVisitante() {
		return visitante;
	}

	public void setVisitante(Visitante visitante) {
		this.visitante = visitante;
	}

	public CategoriaVisita getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaVisita categoria) {
		this.categoria = categoria;
	}

	public DestinoVisita getDestino() {
		return destino;
	}

	public void setDestino(DestinoVisita destino) {
		this.destino = destino;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getnPessoas() {
		return nPessoas;
	}

	public void setnPessoas(String nPessoas) {
		this.nPessoas = nPessoas;
	}

	
	
	



}

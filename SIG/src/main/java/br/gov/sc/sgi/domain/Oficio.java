package br.gov.sc.sgi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Oficio extends GenericDomain {

	@Column(length = 10)
	private int numeroOficio;

	@Column(length = 50, unique = true)
	private String oficio;

	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date dataOficio;

	@ManyToOne
	@JoinColumn
	private Unidade unidadeAbertura;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Setor setorAbertura;

	@Column(length = 30, nullable = true)
	private String destino;

	@Column(length = 30, nullable = true)
	private String assunto;

	@Column(length = 255)
	private String palavraChave;

	@Column(length = 15, nullable = false)
	private String status;

	@ManyToOne
	@JoinColumn
	private Usuario usuario;

	@ManyToOne
	@JoinColumn
	private Usuario usuarioCancelamento;

	@Column(length = 60)
	private String motivoCancelamento;

	@Temporal(TemporalType.DATE)
	@Column
	private Date dataCancelamento;

	public int getNumeroOficio() {
		return numeroOficio;
	}

	public void setNumeroOficio(int numeroOficio) {
		this.numeroOficio = numeroOficio;
	}

	public String getOficio() {
		return oficio;
	}

	public void setOficio(String oficio) {
		this.oficio = oficio;
	}

	public Date getDataOficio() {
		return dataOficio;
	}

	public void setDataOficio(Date dataOficio) {
		this.dataOficio = dataOficio;
	}

	public Setor getSetorAbertura() {
		return setorAbertura;
	}

	public void setSetorAbertura(Setor setorAbertura) {
		this.setorAbertura = setorAbertura;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioCancelamento() {
		return usuarioCancelamento;
	}

	public void setUsuarioCancelamento(Usuario usuarioCancelamento) {
		this.usuarioCancelamento = usuarioCancelamento;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Unidade getUnidadeAbertura() {
		return unidadeAbertura;
	}

	public void setUnidadeAbertura(Unidade unidadeAbertura) {
		this.unidadeAbertura = unidadeAbertura;
	}

}
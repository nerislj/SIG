package br.gov.sc.sgi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class RecursoMultaTramita extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false)
	private RecursoMulta recursomulta;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuario;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataTramita;

	@Column(nullable = false)
	private int acao;

	@ManyToOne
	@JoinColumn
	private Unidade origemUnidade;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Setor origem;

	@Column(length = 30, nullable = false)
	private String destino;

	public RecursoMulta getRecursomulta() {
		return recursomulta;
	}

	public void setRecursomulta(RecursoMulta recursomulta) {
		this.recursomulta = recursomulta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataTramita() {
		return dataTramita;
	}

	public void setDataTramita(Date dataTramita) {
		this.dataTramita = dataTramita;
	}

	@Transient
	public String getAcaoFormatado() {
		String acaoFormatado = null;

		if (acao == 1) {
			acaoFormatado = "Enviado";
		} else if (acao == 2) {
			acaoFormatado = "Recebido";
		} else if (acao == 3) {
			acaoFormatado = "Encaminhado Ofício";
		} else if (acao == 4) {
			acaoFormatado = "Cancelado";
		}

		return acaoFormatado;
	}

	public void setAcao(int acao) {
		this.acao = acao;
	}

	public Setor getOrigem() {
		return origem;
	}

	public void setOrigem(Setor origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Unidade getOrigemUnidade() {
		return origemUnidade;
	}

	public void setOrigemUnidade(Unidade origemUnidade) {
		this.origemUnidade = origemUnidade;
	}

}
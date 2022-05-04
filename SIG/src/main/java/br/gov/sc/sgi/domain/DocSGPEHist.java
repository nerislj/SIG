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
public class DocSGPEHist extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false)
	private DocSGPE docsgpe;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuario;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataAcao;

	@Column(nullable = false)
	private int acao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Unidade origemUnidade;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Setor origemSetor;

	@Column(length = 30, nullable = false)
	private String destino;

	@Column(length = 60)
	private String obsArquivo;

	public DocSGPE getDocsgpe() {
		return docsgpe;
	}

	public void setDocsgpe(DocSGPE docsgpe) {
		this.docsgpe = docsgpe;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataAcao() {
		return dataAcao;
	}

	public void setDataAcao(Date dataAcao) {
		this.dataAcao = dataAcao;
	}

	@Transient
	public String getAcaoFormatado() {
		String acaoFormatado = null;

		if (acao == 1) {
			acaoFormatado = "Recebido";
		} else if (acao == 2) {
			acaoFormatado = "Encaminhado";
		} else if (acao == 3) {
			acaoFormatado = "Arquivado";
		} else if (acao == 4) {
			acaoFormatado = "Reaberto";
		}

		return acaoFormatado;
	}

	public void setAcao(int acao) {
		this.acao = acao;
	}

	public Unidade getOrigemUnidade() {
		return origemUnidade;
	}

	public void setOrigemUnidade(Unidade origemUnidade) {
		this.origemUnidade = origemUnidade;
	}

	public Setor getOrigemSetor() {
		return origemSetor;
	}

	public void setOrigemSetor(Setor origemSetor) {
		this.origemSetor = origemSetor;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getObsArquivo() {
		return obsArquivo;
	}

	public void setObsArquivo(String obsArquivo) {
		this.obsArquivo = obsArquivo;
	}

}
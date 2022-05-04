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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@SuppressWarnings("serial")
@Entity
public class DocSGPE extends GenericDomain {

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataRecebido;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Unidade unidadeAbertura;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Setor setorAbertura;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;

	@Column(length = 15, nullable = false)
	private String sgpe;

	@Column(length = 15, nullable = true)
	private int sgpeNumero;

	@Column(length = 4, nullable = false)
	private int sgpeAno;

	@Column(length = 1, nullable = false)
	private int status;

	@Column(length = 30, nullable = false)
	private String origem;

	@Column(length = 255, nullable = false)
	private String descricao;

	@Column(length = 60)
	private String palavrachave;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "docsgpe")
	@Fetch(FetchMode.SUBSELECT)
	private List<DocSGPEHist> historico;

	public Date getDataRecebido() {
		return dataRecebido;
	}

	public void setDataRecebido(Date dataRecebido) {
		this.dataRecebido = dataRecebido;
	}

	public Unidade getUnidadeAbertura() {
		return unidadeAbertura;
	}

	public void setUnidadeAbertura(Unidade unidadeAbertura) {
		this.unidadeAbertura = unidadeAbertura;
	}

	public Setor getSetorAbertura() {
		return setorAbertura;
	}

	public void setSetorAbertura(Setor setorAbertura) {
		this.setorAbertura = setorAbertura;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public String getSgpe() {
		return sgpe;
	}

	public void setSgpe(String sgpe) {
		this.sgpe = sgpe;
	}

	public int getSgpeNumero() {
		return sgpeNumero;
	}

	public void setSgpeNumero(int sgpeNumero) {
		this.sgpeNumero = sgpeNumero;
	}

	public int getSgpeAno() {
		return sgpeAno;
	}

	public void setSgpeAno(int sgpeAno) {
		this.sgpeAno = sgpeAno;
	}

	@Transient
	public String getStatusFormatado() {
		String statusFormatado = null;

		if (status == 1) {
			statusFormatado = "Recebido";
		} else if (status == 2) {
			statusFormatado = "Encaminhado";
		} else if (status == 3) {
			statusFormatado = "Arquivado";
		} else if (status == 4) {
			statusFormatado = "Reaberto";
		}
		return statusFormatado;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPalavrachave() {
		return palavrachave;
	}

	public void setPalavrachave(String palavrachave) {
		this.palavrachave = palavrachave;
	}

	public List<DocSGPEHist> getHistorico() {
		return historico;
	}

	public void setHistorico(List<DocSGPEHist> historico) {
		this.historico = historico;
	}

	public int getStatus() {
		return status;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}
}
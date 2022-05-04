package br.gov.sc.sgi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class CredencialRelacaoCredHist extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private CredenciadoEmp credenciadoEmp;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Credenciado credenciado;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInclusao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioAlteracao;

	@Column(nullable = false)
	private int acao;

	@Transient
	public String getAcaoFormatado() {
		String acaoFormatado = null;

		if (acao == 1) {
			acaoFormatado = "Incluido";
		} else if (acao == 2) {
			acaoFormatado = "Excluido";
		}

		return acaoFormatado;
	}

	public void setAcao(int acao) {
		this.acao = acao;
	}

	public CredenciadoEmp getCredenciadoEmp() {
		return credenciadoEmp;
	}

	public void setCredenciadoEmp(CredenciadoEmp credenciadoEmp) {
		this.credenciadoEmp = credenciadoEmp;
	}

	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public int getAcao() {
		return acao;
	}

}
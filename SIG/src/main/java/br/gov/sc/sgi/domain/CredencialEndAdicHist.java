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
public class CredencialEndAdicHist extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private CredenciadoEmp credenciadoEmp;

	@Column(length = 20, nullable = false)
	private String tipoendereco;

	@Column(length = 100, nullable = false)
	private String endereco;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInclusao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioInclusao;

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

	public String getTipoendereco() {
		return tipoendereco;
	}

	public void setTipoendereco(String tipoendereco) {
		this.tipoendereco = tipoendereco;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

}
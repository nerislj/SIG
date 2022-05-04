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
public class CredencialRelacaoCred extends GenericDomain {

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
	private Usuario usuarioInclusao;

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

	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

}

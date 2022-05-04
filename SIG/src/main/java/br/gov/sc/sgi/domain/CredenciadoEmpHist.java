package br.gov.sc.sgi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class CredenciadoEmpHist extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false)
	private CredenciadoEmp empresa;

	@ManyToOne
	@JoinColumn(nullable = false)
	private CredencialStatus credencialStatus;

	@ManyToOne
	@JoinColumn(nullable = false)
	private CredencialEmpTipo credencialTipo;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date vencimentoCredencial;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;

	public CredenciadoEmp getEmpresa() {
		return empresa;
	}

	public void setEmpresa(CredenciadoEmp empresa) {
		this.empresa = empresa;
	}

	public CredencialStatus getCredencialStatus() {
		return credencialStatus;
	}

	public void setCredencialStatus(CredencialStatus credencialStatus) {
		this.credencialStatus = credencialStatus;
	}

	public CredencialEmpTipo getCredencialTipo() {
		return credencialTipo;
	}

	public void setCredencialTipo(CredencialEmpTipo credencialTipo) {
		this.credencialTipo = credencialTipo;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getVencimentoCredencial() {
		return vencimentoCredencial;
	}

	public void setVencimentoCredencial(Date vencimentoCredencial) {
		this.vencimentoCredencial = vencimentoCredencial;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

}

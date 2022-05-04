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
public class CredenciadoHist extends GenericDomain {
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Credenciado pessoa;

	@ManyToOne
	@JoinColumn(nullable = false)
	private CredencialStatus credencialStatus;

	@ManyToOne
	@JoinColumn(nullable = false)
	private CredencialTipo credencialTipo;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;

	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date vencimentoCredencial;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;
	
	

	public Credenciado getPessoa() {
		return pessoa;
	}

	public void setPessoa(Credenciado pessoa) {
		this.pessoa = pessoa;
	}

	public CredencialStatus getCredencialStatus() {
		return credencialStatus;
	}

	public void setCredencialStatus(CredencialStatus credencialStatus) {
		this.credencialStatus = credencialStatus;
	}

	public CredencialTipo getCredencialTipo() {
		return credencialTipo;
	}

	public void setCredencialTipo(CredencialTipo credencialTipo) {
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




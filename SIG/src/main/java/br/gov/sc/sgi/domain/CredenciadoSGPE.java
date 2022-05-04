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
public class CredenciadoSGPE extends GenericDomain {

	@OneToOne
	@JoinColumn
	private CredenciadoEmp empresa;

	@OneToOne
	@JoinColumn
	private Credenciado pessoa;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInclusao;

	@Column(length = 25, nullable = false)
	private String SGPE;

	@Column(length = 60, nullable = false)
	private String obs;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;

	public CredenciadoEmp getEmpresa() {
		return empresa;
	}

	public void setEmpresa(CredenciadoEmp empresa) {
		this.empresa = empresa;
	}

	public Credenciado getPessoa() {
		return pessoa;
	}

	public void setPessoa(Credenciado pessoa) {
		this.pessoa = pessoa;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getSGPE() {
		return SGPE;
	}

	public void setSGPE(String sGPE) {
		SGPE = sGPE;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

}



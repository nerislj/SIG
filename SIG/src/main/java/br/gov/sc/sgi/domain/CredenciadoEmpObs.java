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
public class CredenciadoEmpObs extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = true)
	private CredenciadoEmp empresa;
	
	@OneToOne
	@JoinColumn(nullable = true)
	private PessoaJuridica empresaPJ;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInclusao;

	@Column(length = 255)
	private String observacao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;

	public CredenciadoEmp getEmpresa() {
		return empresa;
	}

	public void setEmpresa(CredenciadoEmp empresa) {
		this.empresa = empresa;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public PessoaJuridica getEmpresaPJ() {
		return empresaPJ;
	}

	public void setEmpresaPJ(PessoaJuridica empresaPJ) {
		this.empresaPJ = empresaPJ;
	}
	
	

}




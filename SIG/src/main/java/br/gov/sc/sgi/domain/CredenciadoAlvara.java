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
public class CredenciadoAlvara extends GenericDomain {

	@ManyToOne
	@JoinColumn
	private CredenciadoEmp empresa;
	
	@OneToOne
	@JoinColumn
	private Credenciado empresaPF;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInclusao;

	@Column(length = 50, nullable = false)
	private String alvara;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInicio;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataFim;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;

	
	

	public Credenciado getEmpresaPF() {
		return empresaPF;
	}

	public void setEmpresaPF(Credenciado empresaPF) {
		this.empresaPF = empresaPF;
	}

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

	public String getAlvara() {
		return alvara;
	}

	public void setAlvara(String alvara) {
		this.alvara = alvara;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
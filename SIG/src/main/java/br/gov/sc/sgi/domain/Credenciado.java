package br.gov.sc.sgi.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@SuppressWarnings("serial")
@Entity
public class Credenciado extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private PessoaFisica pessoa;

	@Column(length = 15)
	private String numeroRegistro;

	@Column(length = 60, nullable = true)
	private String carteirinhaSGPE;

	@Column(length = 60, nullable = true)
	private String carteirinhaObs;

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

	@Column(length = 11)
	private String cnh;

	@Column(length = 25)
	private String assinatura;

	@Column(length = 25)
	private String foto;

	@Column(length = 2)
	private String cnhCategoria;

	@Temporal(TemporalType.DATE)
	@Column
	private Date cnhDataValidade;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoDocAdic> documentoAdicional;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoSGPE> SGPE;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "credenciado")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoCredHist> historicoCredenciado;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "credenciado")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoCred> ativioCredenciado;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "credenciado")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoPropHist> historicoProp;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "credenciado")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoProp> ativioProp;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoHist> historicocredencial;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaPF")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoAlvara> historicoAlvara;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaPF")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoPortaria> historicoPortaria;
	
	

	public List<CredenciadoAlvara> getHistoricoAlvara() {
		return historicoAlvara;
	}

	public void setHistoricoAlvara(List<CredenciadoAlvara> historicoAlvara) {
		this.historicoAlvara = historicoAlvara;
	}

	public List<CredenciadoPortaria> getHistoricoPortaria() {
		return historicoPortaria;
	}

	public void setHistoricoPortaria(List<CredenciadoPortaria> historicoPortaria) {
		this.historicoPortaria = historicoPortaria;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getAssinatura() {
		return assinatura;
	}

	public void setAssinatura(String assinatura) {
		this.assinatura = assinatura;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
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

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public String getCnhCategoria() {
		return cnhCategoria;
	}

	public void setCnhCategoria(String cnhCategoria) {
		this.cnhCategoria = cnhCategoria;
	}

	public Date getCnhDataValidade() {
		return cnhDataValidade;
	}

	public void setCnhDataValidade(Date cnhDataValidade) {
		this.cnhDataValidade = cnhDataValidade;
	}

	public List<CredenciadoDocAdic> getDocumentoAdicional() {
		return documentoAdicional;
	}

	public void setDocumentoAdicional(List<CredenciadoDocAdic> documentoAdicional) {
		this.documentoAdicional = documentoAdicional;
	}

	public List<CredenciadoSGPE> getSGPE() {
		return SGPE;
	}

	public void setSGPE(List<CredenciadoSGPE> sGPE) {
		SGPE = sGPE;
	}

	public List<CredencialRelacaoCredHist> getHistoricoCredenciado() {
		return historicoCredenciado;
	}

	public void setHistoricoCredenciado(List<CredencialRelacaoCredHist> historicoCredenciado) {
		this.historicoCredenciado = historicoCredenciado;
	}

	public List<CredencialRelacaoCred> getAtivioCredenciado() {
		return ativioCredenciado;
	}

	public void setAtivioCredenciado(List<CredencialRelacaoCred> ativioCredenciado) {
		this.ativioCredenciado = ativioCredenciado;
	}

	public List<CredencialRelacaoPropHist> getHistoricoProp() {
		return historicoProp;
	}

	public void setHistoricoProp(List<CredencialRelacaoPropHist> historicoProp) {
		this.historicoProp = historicoProp;
	}

	public List<CredencialRelacaoProp> getAtivioProp() {
		return ativioProp;
	}

	public void setAtivioProp(List<CredencialRelacaoProp> ativioProp) {
		this.ativioProp = ativioProp;
	}

	public List<CredenciadoHist> getHistoricocredencial() {
		return historicocredencial;
	}

	public void setHistoricocredencial(List<CredenciadoHist> historicocredencial) {
		this.historicocredencial = historicocredencial;
	}

	public String getCarteirinhaObs() {
		return carteirinhaObs;
	}

	public void setCarteirinhaObs(String carteirinhaObs) {
		this.carteirinhaObs = carteirinhaObs;
	}

	public String getCarteirinhaSGPE() {
		return carteirinhaSGPE;
	}

	public void setCarteirinhaSGPE(String carteirinhaSGPE) {
		this.carteirinhaSGPE = carteirinhaSGPE;
	}

}

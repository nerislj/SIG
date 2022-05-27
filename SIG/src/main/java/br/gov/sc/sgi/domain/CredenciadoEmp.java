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
public class CredenciadoEmp extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false)
	private PessoaJuridica pessoaJuridica;

	@ManyToOne
	@JoinColumn(nullable = true)
	private CredencialStatus credencialStatus;

	@ManyToOne
	@JoinColumn(nullable = true)
	private CredencialEmpTipo credencialTipo;

	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date dataCadastro;

	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date vencimentoCredencial;
	
	
	
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoSGPE> SGPE;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoEmpObs> historicoObs;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoAlvara> historicoAlvara;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoPortaria> historicoPortaria;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "credenciadoEmp")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoCredHist> historicoCredenciado;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "credenciadoEmp")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoCred> ativioCredenciado;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "credenciado")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoPropHist> historicoProp;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "credenciado")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoProp> ativioProp;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoEmpHist> historicoCredencial;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "credenciadoEmp")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialEndAdic> enderecoAdic;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "credenciadoEmp")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialEndAdicHist> enderecoAdicHist;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoVeic> veiculo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoVeicHist> veiculoHist;

	public List<CredencialEndAdic> getEnderecoAdic() {
		return enderecoAdic;
	}

	public void setEnderecoAdic(List<CredencialEndAdic> enderecoAdic) {
		this.enderecoAdic = enderecoAdic;
	}

	public List<CredencialEndAdicHist> getEnderecoAdicHist() {
		return enderecoAdicHist;
	}

	public void setEnderecoAdicHist(List<CredencialEndAdicHist> enderecoAdicHist) {
		this.enderecoAdicHist = enderecoAdicHist;
	}

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

	public List<CredenciadoEmpHist> getHistoricoCredencial() {
		return historicoCredencial;
	}

	public void setHistoricoCredencial(List<CredenciadoEmpHist> historicoCredencial) {
		this.historicoCredencial = historicoCredencial;
	}

	public Date getVencimentoCredencial() {
		return vencimentoCredencial;
	}

	public void setVencimentoCredencial(Date vencimentoCredencial) {
		this.vencimentoCredencial = vencimentoCredencial;
	}

	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
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

	public List<CredenciadoSGPE> getSGPE() {
		return SGPE;
	}

	public void setSGPE(List<CredenciadoSGPE> sGPE) {
		SGPE = sGPE;
	}

	public List<CredenciadoEmpObs> getHistoricoObs() {
		return historicoObs;
	}

	public void setHistoricoObs(List<CredenciadoEmpObs> historicoObs) {
		this.historicoObs = historicoObs;
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

	public List<CredencialRelacaoVeic> getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(List<CredencialRelacaoVeic> veiculo) {
		this.veiculo = veiculo;
	}

	public List<CredencialRelacaoVeicHist> getVeiculoHist() {
		return veiculoHist;
	}

	public void setVeiculoHist(List<CredencialRelacaoVeicHist> veiculoHist) {
		this.veiculoHist = veiculoHist;
	}


	
	

}




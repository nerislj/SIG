package br.gov.sc.sgi.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
@Entity
public class PessoaJuridica extends GenericDomain {

	@Column(length = 14, nullable = false, unique = true)
	private String cnpj;

	@Column(length = 50, nullable = false)
	private String razaoSocial;

	@Column(length = 50)
	private String nomeFantasia;

	@Column(length = 10, nullable = false)
	private String cep;

	@SerializedName("logradouro")
	@Column(length = 50, nullable = false)
	private String endereco;

	@Column(length = 50)
	private String complemento;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Cidade municipioEndereco;


	@ManyToOne
	@JoinColumn(nullable = false)
	private Estado estadoEndereco;

	@Column(length = 15)
	private String telEmp;

	@Column(length = 15)
	private String celEmp;

	@Column(length = 50)
	private String email;
	
	@SerializedName("uf")
	private String uf;

	@SerializedName("localidade")
	private String localidade;
	
	@SerializedName("bairro")
	private String bairro;

	
	@Column(nullable = true)
	private String credenciadoEmpVirtual;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaPJ")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoEmpObs> historicoObs;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaPJ")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredencialRelacaoCred> ativioCredenciado;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaPJ")
	@Fetch(FetchMode.SUBSELECT)
	private List<CredenciadoSGPE> SGPE;
	
	
	
	public List<CredenciadoSGPE> getSGPE() {
		return SGPE;
	}

	public void setSGPE(List<CredenciadoSGPE> sGPE) {
		SGPE = sGPE;
	}

	public List<CredencialRelacaoCred> getAtivioCredenciado() {
		return ativioCredenciado;
	}

	public void setAtivioCredenciado(List<CredencialRelacaoCred> ativioCredenciado) {
		this.ativioCredenciado = ativioCredenciado;
	}

	public List<CredenciadoEmpObs> getHistoricoObs() {
		return historicoObs;
	}

	public void setHistoricoObs(List<CredenciadoEmpObs> historicoObs) {
		this.historicoObs = historicoObs;
	}

	public String getCredenciadoEmpVirtual() {
		return credenciadoEmpVirtual;
	}

	public void setCredenciadoEmpVirtual(String credenciadoEmpVirtual) {
		this.credenciadoEmpVirtual = credenciadoEmpVirtual;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Cidade getMunicipioEndereco() {
		return municipioEndereco;
	}

	public void setMunicipioEndereco(Cidade municipioEndereco) {
		this.municipioEndereco = municipioEndereco;
	}

	public Estado getEstadoEndereco() {
		return estadoEndereco;
	}

	public void setEstadoEndereco(Estado estadoEndereco) {
		this.estadoEndereco = estadoEndereco;
	}

	public String getTelEmp() {
		return telEmp;
	}

	public void setTelEmp(String telEmp) {
		this.telEmp = telEmp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelEmp() {
		return celEmp;
	}

	public void setCelEmp(String celEmp) {
		this.celEmp = celEmp;
	}

}

package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

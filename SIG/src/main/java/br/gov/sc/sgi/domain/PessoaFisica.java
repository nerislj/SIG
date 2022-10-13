package br.gov.sc.sgi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
@Entity
public class PessoaFisica extends GenericDomain {

	@Column(length = 11, nullable = false, unique = true)
	private String cpf;

	@Column(length = 50, nullable = false)
	private String nomeCompleto;

	@Column(length = 50, nullable = false)
	private String nomeMae;

	@Column(length = 50, nullable = false)
	private String nomePai;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataNascimento;

	@Column(length = 9, nullable = false)
	private String sexo;

	@Column(length = 11, nullable = false)
	private String rg;

	@Column(length = 10, nullable = false)
	private String rgOrgaoEmissor;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Estado rgEstadoEmissao;

	@Column(length = 10, nullable = true)
	private String cep;

	@SerializedName("logradouro")
	@Column(length = 255, nullable = false)
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
	private String telRes;

	@Column(length = 15)
	private String telEmp;

	@Column(length = 15)
	private String Cel;

	@Column(length = 50)
	private String email;
	
	@SerializedName("uf")
	private String uf;
	
	@SerializedName("localidade")
	private String localidade;
	
	@SerializedName("bairro")
	private String bairro;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getRgOrgaoEmissor() {
		return rgOrgaoEmissor;
	}

	public void setRgOrgaoEmissor(String rgOrgaoEmissor) {
		this.rgOrgaoEmissor = rgOrgaoEmissor;
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

	public Estado getRgEstadoEmissao() {
		return rgEstadoEmissao;
	}

	public void setRgEstadoEmissao(Estado rgEstadoEmissao) {
		this.rgEstadoEmissao = rgEstadoEmissao;
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

	public Estado getUfEndereco() {
		return estadoEndereco;
	}

	public void setUfEndereco(Estado estadoEndereco) {
		this.estadoEndereco = estadoEndereco;
	}

	public String getTelRes() {
		return telRes;
	}

	public void setTelRes(String telRes) {
		this.telRes = telRes;
	}

	public String getTelEmp() {
		return telEmp;
	}

	public void setTelEmp(String telEmp) {
		this.telEmp = telEmp;
	}

	public String getCel() {
		return Cel;
	}

	public void setCel(String cel) {
		Cel = cel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
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
	

}

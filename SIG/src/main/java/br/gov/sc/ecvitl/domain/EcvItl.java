package br.gov.sc.ecvitl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Nationalized;

import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity
@Table(name = "ecvitl_ecvitl")
public class EcvItl extends GenericDomainEcvItl {

	@Column(length = 255)
	private String credencial;

	@Column(length = 255)
	private String nomeFantasia;

	@Column(length = 255)
	private String razaoSocial;

	@Column(length = 100)
	private String cnpj;

	@Column(length = 100)
	private String ipFixo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@Nationalized
	private Date dataCadastro;

	@Column(length = 10)
	private String tipoCadastro;

	@Column(length = 10)
	private String situacaIp;

	@Column(length = 10)
	private String situacaCadastro;

	@Column(length = 100)
	private String telefoneEcvItl1;

	@Column(length = 100)
	private String telefoneEcvItl2;

	@Column(length = 100)
	private String emailEcvItl;

	@Column(length = 100)
	private String cep;

	@Column(length = 100)
	private String logradouro;

	@Column(length = 100)
	private String numero;

	@Column(length = 100)
	private String complemento;

	@Column(length = 100)
	private String bairro;

	@Column(length = 100)
	private String cidade;

	@Column(length = 100)
	private String estado;


	@ManyToOne
	@JoinColumn
	private Usuario usuarioCadastro;

	public String getCredencial() {
		return credencial;
	}

	public void setCredencial(String credencial) {
		this.credencial = credencial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIpFixo() {
		return ipFixo;
	}

	public void setIpFixo(String ipFixo) {
		this.ipFixo = ipFixo;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getTipoCadastro() {
		return tipoCadastro;
	}

	public void setTipoCadastro(String tipoCadastro) {
		this.tipoCadastro = tipoCadastro;
	}

	

	public String getSituacaIp() {
		return situacaIp;
	}

	public void setSituacaIp(String situacaIp) {
		this.situacaIp = situacaIp;
	}

	public String getSituacaCadastro() {
		return situacaCadastro;
	}

	public void setSituacaCadastro(String situacaCadastro) {
		this.situacaCadastro = situacaCadastro;
	}

	public String getTelefoneEcvItl1() {
		return telefoneEcvItl1;
	}

	public void setTelefoneEcvItl1(String telefoneEcvItl1) {
		this.telefoneEcvItl1 = telefoneEcvItl1;
	}

	public String getTelefoneEcvItl2() {
		return telefoneEcvItl2;
	}

	public void setTelefoneEcvItl2(String telefoneEcvItl2) {
		this.telefoneEcvItl2 = telefoneEcvItl2;
	}

	public String getEmailEcvItl() {
		return emailEcvItl;
	}

	public void setEmailEcvItl(String emailEcvItl) {
		this.emailEcvItl = emailEcvItl;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}



	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

}

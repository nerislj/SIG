package br.gov.sc.cetran.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity

public class Requerente extends GenericDomainCetran {

	@Column(length = 255)
	private String nome;

	@Column(length = 14)
	private String cpf;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataCadastro;

	@Column(length = 18)
	private String cnpj;

	@ManyToOne
	@JoinColumn(nullable = false)
	private TipoRequerente tipoRequerente;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Usuario usuarioCadastro;

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoRequerente getTipoRequerente() {
		return tipoRequerente;
	}

	public void setTipoRequerente(TipoRequerente tipoRequerente) {
		this.tipoRequerente = tipoRequerente;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

}

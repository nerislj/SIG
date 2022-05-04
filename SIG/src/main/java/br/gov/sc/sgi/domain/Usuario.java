package br.gov.sc.sgi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private PessoaFisica pessoa;

	@Column(length = 32, nullable = false)
	private String senha;

	@Transient
	private String senhaSemCripto;

	@ManyToOne
	@JoinColumn(nullable = false)
	private UsuarioStatus status;

	@ManyToOne
	@JoinColumn(nullable = false)
	private UsuarioNivelAcesso nivelAcesso;

	@ManyToOne
	@JoinColumn
	private Unidade unidade;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Setor setor;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaSemCripto() {
		return senhaSemCripto;
	}

	public void setSenhaSemCripto(String senhaSemCripto) {
		this.senhaSemCripto = senhaSemCripto;
	}

	public UsuarioStatus getStatus() {
		return status;
	}

	public void setStatus(UsuarioStatus status) {
		this.status = status;
	}

	public UsuarioNivelAcesso getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(UsuarioNivelAcesso nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
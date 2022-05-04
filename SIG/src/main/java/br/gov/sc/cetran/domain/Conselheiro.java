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
public class Conselheiro extends GenericDomainCetran {
	
	

	@Column(length = 50)
	private String nome;
	
	@Column(length = 11)
	private String cpf;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataCadastro;
	
	@Column(length = 11)
	private String ativo;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Representacao representacao;
	
	@ManyToOne
	@JoinColumn
	private Usuario usuarioCadastro;

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public Representacao getRepresentacao() {
		return representacao;
	}

	public void setRepresentacao(Representacao representacao) {
		this.representacao = representacao;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}


	


	

}

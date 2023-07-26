package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class ListaTelefonica extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private PessoaFisica pessoa;

	@ManyToOne
	@JoinColumn
	private Unidade unidade;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Setor setor;

	@Column(length = 15)
	private String telSet;

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
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

	public String getTelSet() {
		return telSet;
	}

	public void setTelSet(String telSet) {
		this.telSet = telSet;
	}
	
	
}

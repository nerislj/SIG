package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class FrotaCondutor extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private PessoaFisica pessoa;
	
	
	@Column(length = 50, nullable = true)
	private String status;



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	

	


}
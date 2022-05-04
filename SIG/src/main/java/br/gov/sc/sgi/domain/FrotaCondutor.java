package br.gov.sc.sgi.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class FrotaCondutor extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private PessoaFisica pessoa;
	
	
	



	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	

	


}
package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class FuncaoListaTelefonica extends GenericDomain{
	
	@Column(length = 255)
	private String funcao;

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

}

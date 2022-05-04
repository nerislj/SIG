package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class FrotaUnidade extends GenericDomain {

	@Column(length = 50, nullable = false)
	private String frotaUnidade;

	public String getFrotaUnidade() {
		return frotaUnidade;
	}

	public void setFrotaUnidade(String frotaUnidade) {
		this.frotaUnidade = frotaUnidade;
	}

}

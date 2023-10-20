package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Unidade extends GenericDomain {

	@Column(length = 50)
	private String unidadeNome;

	@Column(length = 20, nullable = false)
	private String unidade;
	
	@ManyToOne
	@JoinColumn
	private RegionalUnidade regional;
	
	public RegionalUnidade getRegional() {
		return regional;
	}

	public void setRegional(RegionalUnidade regional) {
		this.regional = regional;
	}

	public String getUnidadeNome() {
		return unidadeNome;
	}

	public void setUnidadeNome(String unidadeNome) {
		this.unidadeNome = unidadeNome;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

}
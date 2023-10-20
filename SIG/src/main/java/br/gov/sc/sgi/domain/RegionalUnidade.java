package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class RegionalUnidade extends GenericDomain {

	@Column(length = 20, nullable = false)
	private String regional;

	public String getRegional() {
		return regional;
	}

	public void setRegional(String regional) {
		this.regional = regional;
	}

}
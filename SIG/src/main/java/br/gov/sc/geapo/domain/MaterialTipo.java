package br.gov.sc.geapo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class MaterialTipo extends GenericDomain {

	@Column(length = 20, unique = true)
	private String tipomaterial;

	public String getTipomaterial() {
		return tipomaterial;
	}

	public void setTipomaterial(String tipomaterial) {
		this.tipomaterial = tipomaterial;
	}


}
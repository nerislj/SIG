package br.gov.sc.geapo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class MaterialStatus extends GenericDomain {

	@Column(length = 20, unique = true)
	private String materialStatus;

	public String getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(String materialStatus) {
		this.materialStatus = materialStatus;
	}



}
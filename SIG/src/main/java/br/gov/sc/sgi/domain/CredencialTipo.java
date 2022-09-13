package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class CredencialTipo extends GenericDomain {

	@Column(length = 60, unique = true)
	private String tipocredencial;

	public String getTipocredencial() {
		return tipocredencial;
	}

	public void setTipocredencial(String tipocredencial) {
		this.tipocredencial = tipocredencial;
	}

}
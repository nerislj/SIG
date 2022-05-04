package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class CredencialEmpTipo extends GenericDomain {

	@Column(length = 20, unique = true)
	private String tipocredencial;

	public String getTipocredencial() {
		return tipocredencial;
	}

	public void setTipocredencial(String tipocredencial) {
		this.tipocredencial = tipocredencial;
	}

}
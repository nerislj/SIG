package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class OficioAno extends GenericDomain {

	@Column(unique = true)
	private int oficioAno;

	public int getOficioAno() {
		return oficioAno;
	}

	public void setOficioAno(int oficioAno) {
		this.oficioAno = oficioAno;
	}

}
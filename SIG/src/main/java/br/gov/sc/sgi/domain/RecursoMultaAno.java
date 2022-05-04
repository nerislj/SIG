package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class RecursoMultaAno extends GenericDomain {

	@Column(unique = true)
	private int recursoMultaAno;

	public int getRecursoMultaAno() {
		return recursoMultaAno;
	}

	public void setRecursoMultaAno(int recursoMultaAno) {
		this.recursoMultaAno = recursoMultaAno;
	}

	

}
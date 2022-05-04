package br.gov.sc.cetran.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.gov.sc.geapo.domain.GenericDomain;

@SuppressWarnings("serial")
@Entity
public class ProcessoAno extends GenericDomain {

	@Column(unique = true)
	private int processoAno;

	public int getProcessoAno() {
		return processoAno;
	}

	public void setProcessoAno(int processoAno) {
		this.processoAno = processoAno;
	}

	

}
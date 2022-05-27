package br.gov.sc.codet.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "codet_situacaoprocesso")
public class SituacaoProcesso extends GenericDomain {

	@Column(length = 255)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	



}

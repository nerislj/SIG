package br.gov.sc.visita.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.gov.sc.sgi.domain.PessoaFisica;

@SuppressWarnings("serial")
@Entity
public class CategoriaVisita extends GenericDomain {

	@Column(length = 255)
	private String categoria;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	



}

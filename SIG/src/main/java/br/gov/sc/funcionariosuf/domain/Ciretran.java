
package br.gov.sc.funcionariosuf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionariosuf_ciretran")
public class Ciretran extends GenericDomain {

	
	@Column(length = 50, nullable = false, unique = true)
	private String nome;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
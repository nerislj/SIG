
package br.gov.sc.geapo.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")

@Entity
public class MaterialSaidaRelacao extends GenericDomain {
	
	@OneToOne
	@JoinColumn(nullable = false)
	private MaterialEntrada materialEntrada;

	@OneToOne
	@JoinColumn(nullable = false)
	private MaterialSaida materialSaida;
	
	public MaterialEntrada getMaterialEntrada() {
		return materialEntrada;
	}

	public void setMaterialEntrada(MaterialEntrada materialEntrada) {
		this.materialEntrada = materialEntrada;
	}

	public MaterialSaida getMaterialSaida() {
		return materialSaida;
	}

	public void setMaterialSaida(MaterialSaida materialSaida) {
		this.materialSaida = materialSaida;
	}

}

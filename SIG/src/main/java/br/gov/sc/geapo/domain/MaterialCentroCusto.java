package br.gov.sc.geapo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@Entity
public class MaterialCentroCusto extends GenericDomain {

	@Column(length = 1000, nullable = false, unique = true)
	private String centroCog;

	@Column(length = 25, nullable = false)
	private String centroSigla;

	@ManyToOne
	@JoinColumn
	private Unidade unidade;

	public String getCentroCog() {
		return centroCog;
	}

	public void setCentroCog(String centroCog) {
		this.centroCog = centroCog;
	}

	public String getCentroSigla() {
		return centroSigla;
	}

	public void setCentroSigla(String centroSigla) {
		this.centroSigla = centroSigla;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}


	
}

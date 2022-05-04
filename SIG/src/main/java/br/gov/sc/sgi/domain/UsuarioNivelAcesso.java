package br.gov.sc.sgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class UsuarioNivelAcesso extends GenericDomain {

	@Column(length = 20, nullable = false)
	private String nivel;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Setor setor;

	@ManyToOne
	@JoinColumn
	private Unidade unidade;

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

}

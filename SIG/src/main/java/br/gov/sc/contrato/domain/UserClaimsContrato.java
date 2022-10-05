package br.gov.sc.contrato.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity
@Table(
		   name = "controle_userclaims"
		)
public class UserClaimsContrato extends GenericDomainContrato {

	@OneToOne
	@JoinColumn(nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Unidade unidade;
	

	@Column(nullable = false)
	private boolean ativo;


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	public Unidade getUnidade() {
		return unidade;
	}


	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	

	

	
}
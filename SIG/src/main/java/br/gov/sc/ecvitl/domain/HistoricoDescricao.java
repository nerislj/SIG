package br.gov.sc.ecvitl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.sc.sgi.domain.Usuario;




@SuppressWarnings("serial")
@Entity
@Table(name = "ecvitl_historicodescricao")
public class HistoricoDescricao extends GenericDomainEcvItl {
	
	
	
	
	@Column(length = 300)
	private String descricao;

	
	@ManyToOne
	@JoinColumn(nullable = false)
	private EcvItl ecvItl;
	
	
	
	
	@ManyToOne
	@JoinColumn
	private Usuario usuarioCadastro;




	public String getDescricao() {
		return descricao;
	}




	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}




	public EcvItl getEcvItl() {
		return ecvItl;
	}




	public void setEcvItl(EcvItl ecvItl) {
		this.ecvItl = ecvItl;
	}




	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}




	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}




	





	


	

}

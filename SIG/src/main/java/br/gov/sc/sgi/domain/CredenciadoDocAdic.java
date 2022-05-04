package br.gov.sc.sgi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class CredenciadoDocAdic extends GenericDomain {

	@OneToOne
	@JoinColumn
	private Credenciado pessoa;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInclusao;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataValidade;
	
	@Column(length = 15, nullable = false)
	private String numeroDocumento;

	@Column(length = 15, nullable = false)
	private String tipoDocumento;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;

	public Credenciado getPessoa() {
		return pessoa;
	}

	public void setPessoa(Credenciado pessoa) {
		this.pessoa = pessoa;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	
	

}



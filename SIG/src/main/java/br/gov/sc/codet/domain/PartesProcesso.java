package br.gov.sc.codet.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.domain.CredencialRelacaoProp;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity
@Table(name = "codet_partesprocesso")
public class PartesProcesso extends GenericDomain {

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;
	
	@OneToOne
	@JoinColumn
	private Credenciado credenciado;
	
	@OneToOne
	@JoinColumn
	private CredenciadoEmp credenciadoEmpresa;
	
	@OneToOne
	@JoinColumn
	private PenalidadeProcesso penalidadeProcesso;
	
	@ManyToOne
	@JoinColumn(name = "processo_codigo")
	private Processo processo;
	
	

	public PenalidadeProcesso getPenalidadeProcesso() {
		return penalidadeProcesso;
	}

	public void setPenalidadeProcesso(PenalidadeProcesso penalidadeProcesso) {
		this.penalidadeProcesso = penalidadeProcesso;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public CredenciadoEmp getCredenciadoEmpresa() {
		return credenciadoEmpresa;
	}

	public void setCredenciadoEmpresa(CredenciadoEmp credenciadoEmpresa) {
		this.credenciadoEmpresa = credenciadoEmpresa;
	}
	
	
	
	
	



}

package br.gov.sc.cetran.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.sc.sgi.domain.Usuario;




@SuppressWarnings("serial")
@Entity
public class HistoricoProcesso extends GenericDomainCetran {
	
	
	@Column(length = 255)
	private String historicoProcessoIDReferencia;
	
	@Column(length = 300)
	private String descricao;


	
	@Column
	private Date dataDistribuicao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dataJulgamento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dataAtualizado;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private ProcessoCetran processoCetran;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Conselheiro conselheiro;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Situacao situacao;
	
	
	@ManyToOne
	@JoinColumn
	private Usuario usuarioCadastro;




	public String getHistoricoProcessoIDReferencia() {
		return historicoProcessoIDReferencia;
	}


	public void setHistoricoProcessoIDReferencia(String historicoProcessoIDReferencia) {
		this.historicoProcessoIDReferencia = historicoProcessoIDReferencia;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Date getDataDistribuicao() {
		return dataDistribuicao;
	}


	public void setDataDistribuicao(Date dataDistribuicao) {
		this.dataDistribuicao = dataDistribuicao;
	}


	public Date getDataJulgamento() {
		return dataJulgamento;
	}


	public void setDataJulgamento(Date dataJulgamento) {
		this.dataJulgamento = dataJulgamento;
	}


	public Date getDataCadastro() {
		return dataCadastro;
	}


	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


	public Date getDataAtualizado() {
		return dataAtualizado;
	}


	public void setDataAtualizado(Date dataAtualizado) {
		this.dataAtualizado = dataAtualizado;
	}


	public ProcessoCetran getProcessoCetran() {
		return processoCetran;
	}


	public void setProcessoCetran(ProcessoCetran processoCetran) {
		this.processoCetran = processoCetran;
	}


	public Conselheiro getConselheiro() {
		return conselheiro;
	}


	public void setConselheiro(Conselheiro conselheiro) {
		this.conselheiro = conselheiro;
	}


	public Situacao getSituacao() {
		return situacao;
	}


	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}


	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}


	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}




	


	

}

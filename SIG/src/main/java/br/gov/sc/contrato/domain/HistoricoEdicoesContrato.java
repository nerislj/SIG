package br.gov.sc.contrato.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity
@Table(name = "controle_historicoedicoes")
public class HistoricoEdicoesContrato extends GenericDomainContrato {

	@ManyToOne
	@JoinColumn(name = "contratoterceirizado_codigo")
	private ContratoTerceirizado contrato;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataCadastro;

	@OneToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;

	@Column(length = 255, nullable = false)
	private Integer qtdFuncionarios;

	@Column(length = 10, nullable = false)
	private Integer hrContrato;

	@Column(length = 255, nullable = false, precision = 10, scale = 2)
	private BigDecimal valorNF;

	@Column(length = 255)
	private String nSGPE;
	
	

	public Integer getQtdFuncionarios() {
		return qtdFuncionarios;
	}

	public void setQtdFuncionarios(Integer qtdFuncionarios) {
		this.qtdFuncionarios = qtdFuncionarios;
	}

	public Integer getHrContrato() {
		return hrContrato;
	}

	public void setHrContrato(Integer hrContrato) {
		this.hrContrato = hrContrato;
	}

	public BigDecimal getValorNF() {
		return valorNF;
	}

	public void setValorNF(BigDecimal valorNF) {
		this.valorNF = valorNF;
	}

	public String getnSGPE() {
		return nSGPE;
	}

	public void setnSGPE(String nSGPE) {
		this.nSGPE = nSGPE;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public ContratoTerceirizado getContrato() {
		return contrato;
	}

	public void setContrato(ContratoTerceirizado contrato) {
		this.contrato = contrato;
	}

}

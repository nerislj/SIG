package br.gov.sc.sgi.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@SuppressWarnings("serial")
@Entity
public class RecursoMulta extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private RecursoMultaTipos tipoProcesso;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;

	@ManyToOne
	@JoinColumn
	private Unidade unidadeAbertura;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Setor setorAbertura;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;

	@Column(length = 7, nullable = false)
	private String placa;

	@Column(length = 15, nullable = false)
	private String autoInfracao;

	@Column(length = 15, nullable = false)
	private String status;

	@Column(length = 60)
	private String observacao;

	@ManyToOne
	@JoinColumn
	private Unidade unidadeAtual;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Setor setorAtual;

	@ManyToOne
	@JoinColumn
	private Usuario usuarioCancelamento;

	@Column(length = 60)
	private String motivoCancelamento;

	@Temporal(TemporalType.DATE)
	@Column
	private Date dataCancelamento;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "recursomulta")
	@Fetch(FetchMode.SUBSELECT)
	private List<RecursoMultaTramita> tramitacao;

	public RecursoMultaTipos getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(RecursoMultaTipos tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Unidade getUnidadeAbertura() {
		return unidadeAbertura;
	}

	public void setUnidadeAbertura(Unidade unidadeAbertura) {
		this.unidadeAbertura = unidadeAbertura;
	}

	public Setor getSetorAbertura() {
		return setorAbertura;
	}

	public void setSetorAbertura(Setor setorAbertura) {
		this.setorAbertura = setorAbertura;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getAutoInfracao() {
		return autoInfracao;
	}

	public void setAutoInfracao(String autoInfracao) {
		this.autoInfracao = autoInfracao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Unidade getUnidadeAtual() {
		return unidadeAtual;
	}

	public void setUnidadeAtual(Unidade unidadeAtual) {
		this.unidadeAtual = unidadeAtual;
	}

	public Setor getSetorAtual() {
		return setorAtual;
	}

	public void setSetorAtual(Setor setorAtual) {
		this.setorAtual = setorAtual;
	}

	public Usuario getUsuarioCancelamento() {
		return usuarioCancelamento;
	}

	public void setUsuarioCancelamento(Usuario usuarioCancelamento) {
		this.usuarioCancelamento = usuarioCancelamento;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public List<RecursoMultaTramita> getTramitacao() {
		return tramitacao;
	}

	public void setTramitacao(List<RecursoMultaTramita> tramitacao) {
		this.tramitacao = tramitacao;
	}

}
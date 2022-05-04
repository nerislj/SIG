package br.gov.sc.sgi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class FrotaVeiculo extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private FrotaUnidade unidade;

	@Column(length = 50, nullable = false)
	private String status;

	@Column(length = 50, nullable = false)
	private String tipoVeiculo;

	@Column(length = 50, nullable = false)
	private String marca;

	@Column(length = 50, nullable = false)
	private String modelo;

	@Column(length = 7, nullable = false, unique = true)
	private String placa;

	@Column(length = 11, nullable = false, unique = true)
	private String renavam;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInclusao;
	
	@Column(nullable = false)
	private Integer km;

	

	public FrotaUnidade getUnidade() {
		return unidade;
	}

	public void setUnidade(FrotaUnidade unidade) {
		this.unidade = unidade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(String tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	

	public String getRenavam() {
		return renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Integer getKm() {
		return km;
	}

	public void setKm(Integer km) {
		this.km = km;
	}


	
	
	

}

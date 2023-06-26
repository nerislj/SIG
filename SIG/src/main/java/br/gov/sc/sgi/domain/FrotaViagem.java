package br.gov.sc.sgi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class FrotaViagem extends GenericDomain {

	
	@ManyToOne
	@JoinColumn(nullable = true)
	private FrotaCondutor frotaCondutor;
	

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInicial;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dataPrevista;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date dataFinal;
	
	@Temporal(TemporalType.TIME)
	@Column(nullable = false)
	private Date horaInicial;
	
	@Temporal(TemporalType.TIME)
	@Column(nullable = true)
	private Date horaFinal;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private FrotaVeiculo frotaVeiculo;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private FrotaUnidade frotaUnidade;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario ultimoUsuario;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInclusao;
	
	@Column(nullable = false)
	private int status;

	@Column(length = 255)
	private String destino;
	
	@Column(length = 255)
	private String observacaoViagem;
	
	@Column(length = 255)
	private String justificativa;
	
	
	
	
	@Transient
	public String getStatusFormatado() {
		String statusFormatado = null;

		if (status == 1) {
			statusFormatado = "Aprovada";
		} else if (status == 2) {
			statusFormatado = "Cancelada";
		} else if (status == 3) {
			statusFormatado = "Reprovada";
		} else if (status == 4) {
			statusFormatado = "Baixada";
		} else if (status == 0) {
			statusFormatado = "Solicitada";
		}
		

		return statusFormatado;
	}


	public FrotaCondutor getFrotaCondutor() {
		return frotaCondutor;
	}


	public void setFrotaCondutor(FrotaCondutor frotaCondutor) {
		this.frotaCondutor = frotaCondutor;
	}


	public Date getDataInicial() {
		return dataInicial;
	}


	public String getObservacaoViagem() {
		return observacaoViagem;
	}


	public void setObservacaoViagem(String observacaoViagem) {
		this.observacaoViagem = observacaoViagem;
	}


	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}


	public Date getDataFinal() {
		return dataFinal;
	}



	public Date getHoraInicial() {
		return horaInicial;
	}


	public void setHoraInicial(Date horaInicial) {
		this.horaInicial = horaInicial;
	}


	public Date getHoraFinal() {
		return horaFinal;
	}


	public void setHoraFinal(Date horaFinal) {
		this.horaFinal = horaFinal;
	}


	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}


	public FrotaVeiculo getFrotaVeiculo() {
		return frotaVeiculo;
	}


	public void setFrotaVeiculo(FrotaVeiculo frotaVeiculo) {
		this.frotaVeiculo = frotaVeiculo;
	}


	public FrotaUnidade getFrotaUnidade() {
		return frotaUnidade;
	}


	public void setFrotaUnidade(FrotaUnidade frotaUnidade) {
		this.frotaUnidade = frotaUnidade;
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


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getDestino() {
		return destino;
	}


	public void setDestino(String destino) {
		this.destino = destino;
	}


	


	public Date getDataPrevista() {
		return dataPrevista;
	}


	public void setDataPrevista(Date dataPrevista) {
		this.dataPrevista = dataPrevista;
	}


	public Usuario getUltimoUsuario() {
		return ultimoUsuario;
	}


	public void setUltimoUsuario(Usuario ultimoUsuario) {
		this.ultimoUsuario = ultimoUsuario;
	}


	public String getJustificativa() {
		return justificativa;
	}


	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	
	
	

	

	
	

	



	
	
	
}

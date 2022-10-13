package br.gov.sc.cetran.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Nationalized;

import br.gov.sc.sgi.domain.RastreioRelacao;
import br.gov.sc.sgi.domain.Usuario;




@SuppressWarnings("serial")
@Entity
@NamedEntityGraph(name= "processocetran_graph", attributeNodes = @NamedAttributeNode("historicoProcesso"))
public class ProcessoCetran extends GenericDomainCetran {
	

	@Column(length = 255)
	private String processoCetranIDReferencia;
	
	@Column(length = 50)
	private String numero;
	
	@Column(length = 50, nullable = false)
	private String placa;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataEntrada;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@Nationalized
	private Date dataCadastro;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Requerente requerente;
	
	@Column(length = 255)
	private String recorrido;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Usuario usuarioCadastro;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "processoCetran")
	@Fetch(FetchMode.SUBSELECT)
	private List<HistoricoProcesso> historicoProcesso;

	

	public List<HistoricoProcesso> getHistoricoProcesso() {
		return historicoProcesso;
	}

	public void setHistoricoProcesso(List<HistoricoProcesso> historicoProcesso) {
		this.historicoProcesso = historicoProcesso;
	}

	public String getProcessoCetranIDReferencia() {
		return processoCetranIDReferencia;
	}

	public void setProcessoCetranIDReferencia(String processoCetranIDReferencia) {
		this.processoCetranIDReferencia = processoCetranIDReferencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Requerente getRequerente() {
		return requerente;
	}

	public void setRequerente(Requerente requerente) {
		this.requerente = requerente;
	}

	public String getRecorrido() {
		return recorrido;
	}

	public void setRecorrido(String recorrido) {
		this.recorrido = recorrido;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}
	
	




	

}

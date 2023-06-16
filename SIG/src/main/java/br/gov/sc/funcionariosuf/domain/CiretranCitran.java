
package br.gov.sc.funcionariosuf.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.gson.annotations.SerializedName;

import br.gov.sc.codet.domain.PartesProcesso;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionariosuf_ciretrancitran")
public class CiretranCitran extends GenericDomain {
	
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Ciretran ciretran;
	
	@Column(length = 50, nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "ciretranCitran")
	@Fetch(FetchMode.SUBSELECT)
	private List<Estagiarios> estagiarios;
	
	@OneToMany(mappedBy = "ciretranCitran")
	@Fetch(FetchMode.SUBSELECT)
	private List<Servidores> servidores;
	
	@OneToMany(mappedBy = "ciretranCitran")
	@Fetch(FetchMode.SUBSELECT)
	private List<Terceirizados> terceirizados;
	
	
	
	

	public List<Estagiarios> getEstagiarios() {
		return estagiarios;
	}

	public void setEstagiarios(List<Estagiarios> estagiarios) {
		this.estagiarios = estagiarios;
	}

	public List<Servidores> getServidores() {
		return servidores;
	}

	public void setServidores(List<Servidores> servidores) {
		this.servidores = servidores;
	}

	public List<Terceirizados> getTerceirizados() {
		return terceirizados;
	}

	public void setTerceirizados(List<Terceirizados> terceirizados) {
		this.terceirizados = terceirizados;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Ciretran getCiretran() {
		return ciretran;
	}

	public void setCiretran(Ciretran ciretran) {
		this.ciretran = ciretran;
	}

	
	

}
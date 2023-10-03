
package br.gov.sc.funcionariosuf.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.gson.annotations.SerializedName;

import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.GenericDomain;
import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionariosuf_unidades")
public class UnidadeFunc extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = true)
	private Unidade unidade;

	@Column(length = 10)
	private String cep;

	@SerializedName("logradouro")
	@Column(length = 255)
	private String endereco;

	@ManyToOne
	@JoinColumn
	private Cidade municipioEndereco;

	@ManyToOne
	@JoinColumn
	private Estado estadoEndereco;

	@Column(length = 255)
	private String horarioAtendimento;

	@Column(length = 255)
	private String email;

	@Column(length = 255)
	private String nCircuito;

	@Column(length = 255)
	private String enderecoIp;

	@Column(length = 255)
	private String telefone;
	
	@Column(length = 255)
	private String latitude;
	
	@Column(length = 255)
	private String logitude;
	
	@Column
	private boolean atendeCNH;
	
	@Column
	private boolean atendeVeiculos;
	
	@Column
	private boolean atendeMulta;
	
	@Column
	private boolean atendePenalidade;
	
	@Column
	private boolean atendeFoto;
	
	
	
	
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidadeFunc")
	@Fetch(FetchMode.SUBSELECT)
	private List<Servidores> servidores;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidadeFunc")
	@Fetch(FetchMode.SUBSELECT)
	private List<Estagiarios> estagiarios;
	
	
	

	

	public Boolean getAtendeCNH() {
		return atendeCNH;
	}

	public void setAtendeCNH(Boolean atendeCNH) {
		this.atendeCNH = atendeCNH;
	}

	public Boolean getAtendeVeiculos() {
		return atendeVeiculos;
	}

	public void setAtendeVeiculos(Boolean atendeVeiculos) {
		this.atendeVeiculos = atendeVeiculos;
	}

	public Boolean getAtendeMulta() {
		return atendeMulta;
	}

	public void setAtendeMulta(Boolean atendeMulta) {
		this.atendeMulta = atendeMulta;
	}

	public Boolean getAtendePenalidade() {
		return atendePenalidade;
	}

	public void setAtendePenalidade(Boolean atendePenalidade) {
		this.atendePenalidade = atendePenalidade;
	}

	public Boolean getAtendeFoto() {
		return atendeFoto;
	}

	public void setAtendeFoto(Boolean atendeFoto) {
		this.atendeFoto = atendeFoto;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLogitude() {
		return logitude;
	}

	public void setLogitude(String logitude) {
		this.logitude = logitude;
	}

	

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Cidade getMunicipioEndereco() {
		return municipioEndereco;
	}

	public void setMunicipioEndereco(Cidade municipioEndereco) {
		this.municipioEndereco = municipioEndereco;
	}

	public Estado getEstadoEndereco() {
		return estadoEndereco;
	}

	public void setEstadoEndereco(Estado estadoEndereco) {
		this.estadoEndereco = estadoEndereco;
	}

	public String getHorarioAtendimento() {
		return horarioAtendimento;
	}

	public void setHorarioAtendimento(String horarioAtendimento) {
		this.horarioAtendimento = horarioAtendimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String getnCircuito() {
		return nCircuito;
	}

	public void setnCircuito(String nCircuito) {
		this.nCircuito = nCircuito;
	}

	public String getEnderecoIp() {
		return enderecoIp;
	}

	public void setEnderecoIp(String enderecoIp) {
		this.enderecoIp = enderecoIp;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Servidores> getServidores() {
		return servidores;
	}

	public void setServidores(List<Servidores> servidores) {
		this.servidores = servidores;
	}

	public List<Estagiarios> getEstagiarios() {
		return estagiarios;
	}

	public void setEstagiarios(List<Estagiarios> estagiarios) {
		this.estagiarios = estagiarios;
	}

	public void setAtendeCNH(boolean atendeCNH) {
		this.atendeCNH = atendeCNH;
	}

	public void setAtendeVeiculos(boolean atendeVeiculos) {
		this.atendeVeiculos = atendeVeiculos;
	}

	public void setAtendeMulta(boolean atendeMulta) {
		this.atendeMulta = atendeMulta;
	}

	public void setAtendePenalidade(boolean atendePenalidade) {
		this.atendePenalidade = atendePenalidade;
	}

	public void setAtendeFoto(boolean atendeFoto) {
		this.atendeFoto = atendeFoto;
	}
	

}
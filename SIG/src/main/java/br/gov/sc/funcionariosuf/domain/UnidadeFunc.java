
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

import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionariosuf_unidades")
public class UnidadeFunc extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = true)
	private Unidade unidade;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Setor setor;

	@Column(length = 10, nullable = true)
	private String cep;

	@SerializedName("logradouro")
	@Column(length = 255, nullable = false)
	private String endereco;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Cidade municipioEndereco;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Estado estadoEndereco;

	@Column(length = 255, nullable = false)
	private String horarioAtendimento;

	@Column(length = 255, nullable = false)
	private String email;

	@Column(length = 255)
	private String nCircuito;

	@Column(length = 255)
	private String enderecoIp;

	@Column(length = 255, nullable = false)
	private String telefone;
	
	/*
	 * @OneToMany(mappedBy = "unidadeFunc")
	 * 
	 * @Fetch(FetchMode.SUBSELECT) private List<Estagiarios> estagiarios;
	 * 
	 * @OneToMany(mappedBy = "unidadeFunc")
	 * 
	 * @Fetch(FetchMode.SUBSELECT) private List<Servidores> servidores;
	 * 
	 * @OneToMany(mappedBy = "unidadeFunc")
	 * 
	 * @Fetch(FetchMode.SUBSELECT) private List<Terceirizados> terceirizados;
	 */
	
	
	

	

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
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

}
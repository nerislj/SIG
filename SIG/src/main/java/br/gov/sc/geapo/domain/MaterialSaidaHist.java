package br.gov.sc.geapo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity
public class MaterialSaidaHist extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false)
	private Material material;

	@OneToOne
	@JoinColumn(nullable = false)
	private MaterialSaida materialSaida;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataAlteracao;
	
	@Column(length = 30, nullable = false)
	private Integer quantidadeHist;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;

	

	public Integer getQuantidadeHist() {
		return quantidadeHist;
	}

	public void setQuantidadeHist(Integer quantidadeHist) {
		this.quantidadeHist = quantidadeHist;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public MaterialSaida getMaterialSaida() {
		return materialSaida;
	}

	public void setMaterialSaida(MaterialSaida materialSaida) {
		this.materialSaida = materialSaida;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}
	
	
	
}

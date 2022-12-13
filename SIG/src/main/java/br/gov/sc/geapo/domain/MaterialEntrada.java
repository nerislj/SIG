package br.gov.sc.geapo.domain;

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
public class MaterialEntrada extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private Material material;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private MaterialTipo materialTipo;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;

	@Column(length = 30, nullable = false)
	private Integer quantidade;
	
	

	
	
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public MaterialTipo getMaterialTipo() {
		return materialTipo;
	}

	public void setMaterialTipo(MaterialTipo materialTipo) {
		this.materialTipo = materialTipo;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	
	

	

}

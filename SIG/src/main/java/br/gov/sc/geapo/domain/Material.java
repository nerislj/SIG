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
public class Material extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private MaterialTipo materialTipo;

	@Column(length = 60)
	private String material;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;

	@Column(length = 50)
	private String patrimonio;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "material")
	@Fetch(FetchMode.SUBSELECT)
	private List<MaterialEntradaHist> historicoEntradaMaterial;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "material")
	@Fetch(FetchMode.SUBSELECT)
	private List<MaterialSaidaHist> historicoSaidaMaterial;

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
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

	public List<MaterialEntradaHist> getHistoricoEntradaMaterial() {
		return historicoEntradaMaterial;
	}

	public void setHistoricoEntradaMaterial(List<MaterialEntradaHist> historicoEntradaMaterial) {
		this.historicoEntradaMaterial = historicoEntradaMaterial;
	}

	public List<MaterialSaidaHist> getHistoricoSaidaMaterial() {
		return historicoSaidaMaterial;
	}

	public void setHistoricoSaidaMaterial(List<MaterialSaidaHist> historicoSaidaMaterial) {
		this.historicoSaidaMaterial = historicoSaidaMaterial;
	}

	public String getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}

}

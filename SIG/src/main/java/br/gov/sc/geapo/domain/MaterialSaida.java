package br.gov.sc.geapo.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.gov.sc.sgi.domain.RecursoMultaTramita;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity
@Table(name = "materialsaida")
public class MaterialSaida extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private Material material;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private MaterialTipo materialTipo;
	
	
	@ManyToOne
	@JoinColumn
	private MaterialCentroCusto materialCentroDeCusto;

	
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private MaterialStatus materialStatus;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;

	@Column(length = 30, nullable = false)
	private Integer quantidade;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;
	
	@Column(length = 255)
	private String ressalva;
	
	@Column(length = 255)
	private String nSGPE;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "materialSaida")
	@Fetch(FetchMode.JOIN)
	private List<MaterialSaidaRelacao> materialSaidaRelacao;
	
	

	public String getnSGPE() {
		return nSGPE;
	}

	public void setnSGPE(String nSGPE) {
		this.nSGPE = nSGPE;
	}


	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
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

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public String getRessalva() {
		return ressalva;
	}

	public void setRessalva(String ressalva) {
		this.ressalva = ressalva;
	}

	public MaterialTipo getMaterialTipo() {
		return materialTipo;
	}

	public void setMaterialTipo(MaterialTipo materialTipo) {
		this.materialTipo = materialTipo;
	}

	

	public MaterialStatus getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(MaterialStatus materialStatus) {
		this.materialStatus = materialStatus;
	}

	public List<MaterialSaidaRelacao> getMaterialSaidaRelacao() {
		return materialSaidaRelacao;
	}

	public void setMaterialSaidaRelacao(List<MaterialSaidaRelacao> materialSaidaRelacao) {
		this.materialSaidaRelacao = materialSaidaRelacao;
	}

	public MaterialCentroCusto getMaterialCentroDeCusto() {
		return materialCentroDeCusto;
	}

	public void setMaterialCentroDeCusto(MaterialCentroCusto materialCentroDeCusto) {
		this.materialCentroDeCusto = materialCentroDeCusto;
	}

	
	
	


}

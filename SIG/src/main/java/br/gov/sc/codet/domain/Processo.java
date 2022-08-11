package br.gov.sc.codet.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.gov.sc.sgi.domain.CredenciadoEmp;

@SuppressWarnings("serial")
@Entity
@Table(name = "codet_processo")
public class Processo extends GenericDomain {
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInstauracao;


	@Column(length = 255)
	private String numProcesso;
	
	@Column(length = 255)
	private String numSGPE;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private SituacaoProcesso situacao;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private NomenclaturaProcesso nomenclatura;
	
	@OneToOne
	@JoinColumn
	private SetorAtual setorAtual;
	
	@ManyToOne
	@JoinColumn(name = "credenciadoPJ_codigo")
	private CredenciadoEmp credenciadoPJ;
	
	@OneToMany(mappedBy = "processo")
	@Fetch(FetchMode.SUBSELECT)
	private List<PartesProcesso> partesProcesso;

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "processo")
	@Fetch(FetchMode.SUBSELECT)
	private List<FasesProcesso> fasesAdminitrativas;
	

	
	

	

	

	public NomenclaturaProcesso getNomenclatura() {
		return nomenclatura;
	}

	public void setNomenclatura(NomenclaturaProcesso nomenclatura) {
		this.nomenclatura = nomenclatura;
	}

	public SetorAtual getSetorAtual() {
		return setorAtual;
	}

	public void setSetorAtual(SetorAtual setorAtual) {
		this.setorAtual = setorAtual;
	}

	public String getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(String numProcesso) {
		this.numProcesso = numProcesso;
	}

	public Date getDataInstauracao() {
		return dataInstauracao;
	}

	public void setDataInstauracao(Date dataInstauracao) {
		this.dataInstauracao = dataInstauracao;
	}

	public SituacaoProcesso getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProcesso situacao) {
		this.situacao = situacao;
	}



	

	public CredenciadoEmp getCredenciadoPJ() {
		return credenciadoPJ;
	}

	public void setCredenciadoPJ(CredenciadoEmp credenciadoPJ) {
		this.credenciadoPJ = credenciadoPJ;
	}

	public String getNumSGPE() {
		return numSGPE;
	}

	public void setNumSGPE(String numSGPE) {
		this.numSGPE = numSGPE;
	}

	

	


	public List<PartesProcesso> getPartesProcesso() {
		return partesProcesso;
	}

	public void setPartesProcesso(List<PartesProcesso> partesProcesso) {
		this.partesProcesso = partesProcesso;
	}

	public List<FasesProcesso> getFasesAdminitrativas() {
		return fasesAdminitrativas;
	}

	public void setFasesAdminitrativas(List<FasesProcesso> fasesAdminitrativas) {
		this.fasesAdminitrativas = fasesAdminitrativas;
	}


	
	
	
	



}

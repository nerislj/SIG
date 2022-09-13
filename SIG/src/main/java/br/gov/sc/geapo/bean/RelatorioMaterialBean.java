package br.gov.sc.geapo.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.gov.sc.codet.dao.SituacaoProcessoDAO;
import br.gov.sc.codet.domain.SituacaoProcesso;
import br.gov.sc.geapo.dao.MaterialDAO;
import br.gov.sc.geapo.dao.MaterialSaidaDAO;
import br.gov.sc.geapo.dao.MaterialStatusDAO;
import br.gov.sc.geapo.domain.Material;
import br.gov.sc.geapo.domain.MaterialSaida;
import br.gov.sc.geapo.domain.MaterialStatus;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.OficioAno;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RelatorioMaterialBean implements Serializable {

	private Material material;
	private MaterialSaida materialSaida;
	private Setor setorAtual;
	private Unidade unidade;
	private MaterialStatus statusMaterial;

	private String setorAtualString;
	private String situacaoProcessoString;

	private List<MaterialSaida> listaMateriaisSaida;
	private List<Material> listaMateriais;
	private List<Setor> listaSetores;
	private List<Unidade> listaUnidades;
	private List<MaterialStatus> listaStatus;

	private Date dataInicial;
	private String dateIni;
	private String dateFini;
	private Date dataFinal;

	private OficioAno Ano;
	private int anoHoje;
	
	

	public MaterialStatus getStatusMaterial() {
		return statusMaterial;
	}

	public void setStatusMaterial(MaterialStatus statusMaterial) {
		this.statusMaterial = statusMaterial;
	}

	public int getAnoHoje() {
		return anoHoje;
	}

	public void setAnoHoje(int anoHoje) {
		this.anoHoje = anoHoje;
	}

	public String getSetorAtualString() {
		return setorAtualString;
	}

	public void setSetorAtualString(String setorAtualString) {
		this.setorAtualString = setorAtualString;
	}

	public String getSituacaoProcessoString() {
		return situacaoProcessoString;
	}

	public void setSituacaoProcessoString(String situacaoProcessoString) {
		this.situacaoProcessoString = situacaoProcessoString;
	}

	public MaterialSaida getMaterialSaida() {
		return materialSaida;
	}

	public void setMaterialSaida(MaterialSaida materialSaida) {
		this.materialSaida = materialSaida;
	}

	public Setor getSetorAtual() {
		return setorAtual;
	}

	public void setSetorAtual(Setor setorAtual) {
		this.setorAtual = setorAtual;
	}

	public List<Setor> getListaSetores() {
		return listaSetores;
	}

	public void setListaSetores(List<Setor> listaSetores) {
		this.listaSetores = listaSetores;
	}

	@PostConstruct
	public void listar() {
		try {

			UnidadeDAO uniDAO = new UnidadeDAO();
			SetorDAO setorDAO = new SetorDAO();
			MaterialStatusDAO statusDAO = new MaterialStatusDAO();
			MaterialDAO materialDAO = new MaterialDAO();

			listaUnidades = uniDAO.listar();
			listaSetores = setorDAO.listar();
			listaStatus = statusDAO.listar();
			listaMateriais = materialDAO.listar();

			
		
			
			
			materialSaida = new MaterialSaida();
			// EDIÇÃO HISTORICO BEAN

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar o Processo.");
			erro.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void popularSetor() {
		try {
			if (materialSaida.getUnidade() != null) {
				SetorDAO setorDAO = new SetorDAO();
				listaSetores = setorDAO.buscarPorUnidade(materialSaida.getUnidade().getCodigo());
			} else {
				listaSetores = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os setores");
			erro.printStackTrace();
		}
	}

	
	
	@SuppressWarnings("deprecation")
	public int brandSubtotal(String brand, int dataCadastro) {
		
		
	    return listaMateriaisSaida.stream().filter(c -> c.getMaterial().getMaterial().equals(brand) && c.getDataCadastro().getMonth()+1 == dataCadastro).mapToInt(c -> c.getQuantidade()).sum();
	    
	  
	}
	
	@SuppressWarnings("deprecation")
	public int subTotalAnual(String brand) {
		
		
	    return listaMateriaisSaida.stream().filter(c -> c.getMaterial().getMaterial().equals(brand)).mapToInt(c -> c.getQuantidade()).sum();
	    
	  
	}
	 public String getTotalAno() {
	        int total = 0;
	 
	        for(MaterialSaida sale :listaMateriaisSaida) {
	            total += sale.getQuantidade();
	        }
	 
	        return new DecimalFormat("###").format(total);
	    }

	@SuppressWarnings("static-access")
	public void blurProcessos() {

		MaterialSaidaDAO materialSaidaDAO = new MaterialSaidaDAO();
		SetorDAO setorDAO = new SetorDAO();
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		MaterialStatusDAO statusDAO = new MaterialStatusDAO();
		MaterialDAO materialDAO = new MaterialDAO();
		
		int anoHoje = new Date().getYear() + 1900;

		try {

			
			if (materialSaida.getSetorAbertura() == null && materialSaida.getUnidade() == null
					&& materialSaida.getMaterialStatus() == null && materialSaida.getMaterial() != null) {
				System.out.println("MATERIAL MATERIAL");

				material = materialDAO.carregarMaterial(materialSaida.getMaterial().getMaterial());

				statusMaterial = null;
				
				listaMateriaisSaida = materialSaidaDAO.listarRelatorioMaterial(unidade, setorAtual, statusMaterial,
						Ano.getOficioAno(), material);
				
				if (listaMateriaisSaida.equals(null)) {
					Messages.addGlobalError("Não existe dados com os campos fornecidos!");
				}
				System.out.println(listaMateriaisSaida);
			}

			

			if (materialSaida.getSetorAbertura() != null && materialSaida.getUnidade() == null
					&& materialSaida.getMaterialStatus() == null) {
				System.out.println("SETOR ATUAL");

				setorAtual = setorDAO.carregarSetorAtual(materialSaida.getSetorAbertura().getSetor());

				statusMaterial = null;
				
				listaMateriaisSaida = materialSaidaDAO.listarRelatorioMaterial(unidade, setorAtual, statusMaterial,
						Ano.getOficioAno(), material);
				
				if (listaMateriaisSaida.isEmpty()) {
					Messages.addGlobalError("Não existe dados com os campos fornecidos!");
				}
				System.out.println(listaMateriaisSaida);
			}

			if (materialSaida.getSetorAbertura() == null && materialSaida.getUnidade() != null
					&& materialSaida.getMaterialStatus() == null) {
				System.out.println("UNIDADE AQUI");

				unidade = unidadeDAO.carregarUnidadeAtual(materialSaida.getUnidade().getUnidadeNome());

				statusMaterial = null;
				
				listaMateriaisSaida = materialSaidaDAO.listarRelatorioMaterial(unidade, setorAtual, statusMaterial,
						Ano.getOficioAno(), material);
				if (listaMateriaisSaida.isEmpty()) {
					Messages.addGlobalError("Não existe dados com os campos fornecidos!");
				}
				System.out.println(listaMateriaisSaida);
			}

			if (materialSaida.getSetorAbertura() == null && materialSaida.getUnidade() == null
					&& materialSaida.getMaterialStatus() != null) {
				System.out.println("AQUI MATERIAL STATUS");

				statusMaterial = statusDAO.carregarStatus(materialSaida.getMaterialStatus().getMaterialStatus());

				setorAtual = null;
				
				listaMateriaisSaida = materialSaidaDAO.listarRelatorioMaterial(unidade, setorAtual, statusMaterial,
						Ano.getOficioAno(), material);
				if (listaMateriaisSaida.isEmpty()) {
					Messages.addGlobalError("Não existe dados com os campos fornecidos!");
				}
				System.out.println(listaMateriaisSaida);
			}

			if (materialSaida.getSetorAbertura() != null && materialSaida.getMaterialStatus() != null
					&& materialSaida.getUnidade() != null && materialSaida.getMaterial() != null) {
				System.out.println("OS TRÊS");
				setorAtual = setorDAO.carregarSetorAtual(materialSaida.getSetorAbertura().getSetor());
				statusMaterial = statusDAO.carregarStatus(materialSaida.getMaterialStatus().getMaterialStatus());
				unidade = unidadeDAO.carregarUnidadeAtual(materialSaida.getUnidade().getUnidadeNome());
				material = materialDAO.carregarMaterial(materialSaida.getMaterial().getMaterial());
				
				listaMateriaisSaida = materialSaidaDAO.listarRelatorioMaterial(unidade, setorAtual, statusMaterial,
						Ano.getOficioAno(), material);
				if (listaMateriaisSaida.isEmpty()) {
					Messages.addGlobalError("Não existe dados com os campos fornecidos!");
				}
				System.out.println(listaMateriaisSaida);
			}
			
			if (materialSaida.getSetorAbertura() == null && materialSaida.getMaterialStatus() == null
					&& materialSaida.getUnidade() == null && materialSaida.getMaterial() == null) {
				System.out.println("OS TRÊS");
				
				
				listaMateriaisSaida = materialSaidaDAO.listarRelatorioMaterial(unidade, setorAtual, statusMaterial,
						Ano.getOficioAno(), material);
				if (listaMateriaisSaida.isEmpty()) {
					Messages.addGlobalError("Não existe dados com os campos fornecidos!");
				}
				System.out.println(listaMateriaisSaida);
			}
			
		

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getDateIni() {
		return dateIni;
	}

	public void setDateIni(String dateIni) {
		this.dateIni = dateIni;
	}

	public String getDateFini() {
		return dateFini;
	}

	public void setDateFini(String dateFini) {
		this.dateFini = dateFini;
	}

	public List<MaterialSaida> getListaMateriaisSaida() {
		return listaMateriaisSaida;
	}

	public void setListaMateriaisSaida(List<MaterialSaida> listaMateriaisSaida) {
		this.listaMateriaisSaida = listaMateriaisSaida;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public List<Unidade> getListaUnidades() {
		return listaUnidades;
	}

	public void setListaUnidades(List<Unidade> listaUnidades) {
		this.listaUnidades = listaUnidades;
	}

	public List<MaterialStatus> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<MaterialStatus> listaStatus) {
		this.listaStatus = listaStatus;
	}

	public OficioAno getAno() {
		return Ano;
	}

	public void setAno(OficioAno ano) {
		Ano = ano;
	}

	public List<Material> getListaMateriais() {
		return listaMateriais;
	}

	public void setListaMateriais(List<Material> listaMateriais) {
		this.listaMateriais = listaMateriais;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}

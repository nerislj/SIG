package br.gov.sc.geapo.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.util.Messages;

import br.gov.sc.geapo.dao.MaterialDAO;
import br.gov.sc.geapo.dao.MaterialEntradaDAO;
import br.gov.sc.geapo.dao.MaterialEntradaHistDAO;
import br.gov.sc.geapo.dao.MaterialSaidaRelacaoDAO;
import br.gov.sc.geapo.dao.MaterialTipoDAO;
import br.gov.sc.geapo.domain.Material;
import br.gov.sc.geapo.domain.MaterialEntrada;
import br.gov.sc.geapo.domain.MaterialEntradaHist;
import br.gov.sc.geapo.domain.MaterialSaidaRelacao;
import br.gov.sc.geapo.domain.MaterialTipo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MaterialBean implements Serializable {

	private Material material;
	
	private MaterialEntrada materialEntrada;
	private MaterialTipo materialTipo;
	
	private List<MaterialEntrada> listaEntradaMateriais;
	private List<Material> listaMateriais;
	private List<MaterialTipo> listaTipos;
	
	private MaterialEntradaHist materialEntradaHist;

	private List<MaterialEntradaHist> listamaterialEntradaHist;




	

	public MaterialEntradaHist getMaterialEntradaHist() {
		return materialEntradaHist;
	}

	public void setMaterialEntradaHist(MaterialEntradaHist materialEntradaHist) {
		this.materialEntradaHist = materialEntradaHist;
	}

	public List<MaterialEntradaHist> getListamaterialEntradaHist() {
		return listamaterialEntradaHist;
	}

	public void setListamaterialEntradaHist(List<MaterialEntradaHist> listamaterialEntradaHist) {
		this.listamaterialEntradaHist = listamaterialEntradaHist;
	}

	public MaterialSaidaRelacao getSaidaRelacao() {
		return saidaRelacao;
	}

	public void setSaidaRelacao(MaterialSaidaRelacao saidaRelacao) {
		this.saidaRelacao = saidaRelacao;
	}

	public List<MaterialSaidaRelacao> getListasaidaRelacao() {
		return listasaidaRelacao;
	}

	public void setListasaidaRelacao(List<MaterialSaidaRelacao> listasaidaRelacao) {
		this.listasaidaRelacao = listasaidaRelacao;
	}

	private MaterialSaidaRelacao saidaRelacao;
	private List<MaterialSaidaRelacao> listasaidaRelacao;
	
	

	public MaterialEntrada getMaterialEntrada() {
		return materialEntrada;
	}

	public void setMaterialEntrada(MaterialEntrada materialEntrada) {
		this.materialEntrada = materialEntrada;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public List<Material> getListaMateriais() {
		return listaMateriais;
	}

	public void setListaMateriais(List<Material> listaMateriais) {
		this.listaMateriais = listaMateriais;
	}
	
	public List<MaterialTipo> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<MaterialTipo> listaTipos) {
		this.listaTipos = listaTipos;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("materialbean");
			MaterialDAO materialDAO = new MaterialDAO();
			MaterialTipoDAO materialTipoDAO = new MaterialTipoDAO();
			
			
			materialEntrada = new MaterialEntrada();
			
			
			listaMateriais = materialDAO.listarPorOrdemASC();
			listaTipos = materialTipoDAO.listar();
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Status.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		material = new Material();
	}

	public void salvar() {
		try {
			MaterialDAO materialDAO = new MaterialDAO();
			
			material.setDataCadastro(new Date());
			
			materialTipo = material.getMaterialTipo();
			
			material.setMaterial(material.getMaterial().toUpperCase());
			
			System.out.println(material.getMaterial().toUpperCase() + "salvar material");
			
			materialDAO.merge(material);
			
			material = materialDAO.carregarMaterial(material.getMaterial());
			
			
			materialDAO.salvarEntradaEstoque(materialEntrada, material, materialTipo);
			
			
			
			
			
			
			MaterialEntradaDAO materialEntradaDAO = new MaterialEntradaDAO();
			setListaEntradaMateriais(materialEntradaDAO.listar());

			material = new Material();
			listaMateriais = materialDAO.listar();

			Messages.addGlobalInfo("Material cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Material.");
			erro.printStackTrace();
		}
	}

	
	public void excluir(ActionEvent evento) throws Exception {

		try {
			
			MaterialEntradaHistDAO histDAO = new MaterialEntradaHistDAO();
			MaterialSaidaRelacaoDAO relacaoDAO = new MaterialSaidaRelacaoDAO();
			MaterialEntradaDAO materialEntradaDAO = new MaterialEntradaDAO();
			
		
			material = (Material) evento.getComponent().getAttributes().get("materialSelecionado");
			
			materialEntrada = materialEntradaDAO.carregaMaterialEntrada(material);
			
			if(materialEntrada!=null) {
				listaEntradaMateriais = materialEntradaDAO.listarPorMaterialEntrada(materialEntrada.getCodigo());
				while (listaEntradaMateriais.size() != 0) {

					materialEntrada = materialEntradaDAO.loadLastExcluir(materialEntrada.getCodigo());

					if (materialEntrada != null) {

						materialEntradaDAO.excluir(materialEntrada);
					}else {
						listaEntradaMateriais = materialEntradaDAO.listarPorOrdemASC();
						break;
					}

				}
				
				
				listasaidaRelacao = relacaoDAO.listarPorMaterialEntrada(materialEntrada);
				while (listasaidaRelacao.size() != 0) {

					saidaRelacao = relacaoDAO.loadLast(materialEntrada);

					if (saidaRelacao != null) {
						relacaoDAO.excluir(saidaRelacao);
					}else {
						listaEntradaMateriais = materialEntradaDAO.listarPorOrdemASC();
						break;
					}

				}

				listamaterialEntradaHist = histDAO.listarPorMaterialEntrada(materialEntrada);
				while (listamaterialEntradaHist.size() != 0) {

					materialEntradaHist = histDAO.loadLast(materialEntrada);

					if (materialEntradaHist != null) {

						histDAO.excluir(materialEntradaHist);
					}else {
						listaEntradaMateriais = materialEntradaDAO.listarPorOrdemASC();
						break;
					}

				}
			} 
			
			
			
		
		
	

			MaterialDAO materialDAO = new MaterialDAO();
			materialDAO.excluir(material);

			material = new Material();
			listaMateriais = materialDAO.listar();

			Messages.addGlobalInfo("Material removido com sucesso.");
			
			 ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Material.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		material = (Material) evento.getComponent().getAttributes().get("materialSelecionado");		
	}

	public MaterialTipo getMaterialTipo() {
		return materialTipo;
	}

	public void setMaterialTipo(MaterialTipo materialTipo) {
		this.materialTipo = materialTipo;
	}

	public List<MaterialEntrada> getListaEntradaMateriais() {
		return listaEntradaMateriais;
	}

	public void setListaEntradaMateriais(List<MaterialEntrada> listaEntradaMateriais) {
		this.listaEntradaMateriais = listaEntradaMateriais;
	}

	
}



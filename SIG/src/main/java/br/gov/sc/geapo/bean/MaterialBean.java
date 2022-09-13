package br.gov.sc.geapo.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.geapo.dao.MaterialDAO;
import br.gov.sc.geapo.dao.MaterialEntradaDAO;
import br.gov.sc.geapo.dao.MaterialTipoDAO;
import br.gov.sc.geapo.domain.Material;
import br.gov.sc.geapo.domain.MaterialEntrada;
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
			MaterialDAO materialDAO = new MaterialDAO();
			MaterialTipoDAO materialTipoDAO = new MaterialTipoDAO();
			
			
			materialEntrada = new MaterialEntrada();
			
			
			listaMateriais = materialDAO.listar();
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

	public void excluir(ActionEvent evento) {

		try {
			material = (Material) evento.getComponent().getAttributes().get("materialSelecionado");

			MaterialDAO materialDAO = new MaterialDAO();
			materialDAO.excluir(material);

			material = new Material();
			listaMateriais = materialDAO.listar();

			Messages.addGlobalInfo("Material removido com sucesso.");
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



package br.gov.sc.geapo.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.geapo.dao.MaterialTipoDAO;
import br.gov.sc.geapo.domain.MaterialTipo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MaterialTipoBean implements Serializable {

	private MaterialTipo materialtipo;
	private List<MaterialTipo> listaTipos;

	

	public MaterialTipo getMaterialtipo() {
		return materialtipo;
	}

	public void setMaterialtipo(MaterialTipo materialtipo) {
		this.materialtipo = materialtipo;
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
			MaterialTipoDAO materialtipoDAO = new MaterialTipoDAO();
			listaTipos = materialtipoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Tipos.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		materialtipo = new MaterialTipo();
	}

	public void salvar() {
		try {
			MaterialTipoDAO materialtipoDAO = new MaterialTipoDAO();
			
			materialtipo.setTipomaterial(materialtipo.getTipomaterial().toUpperCase());
			
			materialtipoDAO.merge(materialtipo);

			materialtipo = new MaterialTipo();
			listaTipos = materialtipoDAO.listar();

			Messages.addGlobalInfo("Tipo cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o tipo.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			materialtipo = (MaterialTipo) evento.getComponent().getAttributes().get("materialstatusSelecionado");

			MaterialTipoDAO materialtipoDAO = new MaterialTipoDAO();
			materialtipoDAO.excluir(materialtipo);

			materialtipo = new MaterialTipo();
			listaTipos = materialtipoDAO.listar();

			Messages.addGlobalInfo("Status removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Status.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		materialtipo = (MaterialTipo) evento.getComponent().getAttributes().get("materialstatusSelecionado");		
	}
}



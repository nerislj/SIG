package br.gov.sc.geapo.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.geapo.dao.MaterialStatusDAO;
import br.gov.sc.geapo.domain.MaterialStatus;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MaterialStatusBean implements Serializable {

	private MaterialStatus materialstatus;
	private List<MaterialStatus> listaStatus;

	public MaterialStatus getMaterialstatus() {
		return materialstatus;
	}

	public void setMaterialstatus(MaterialStatus materialstatus) {
		this.materialstatus = materialstatus;
	}

	public List<MaterialStatus> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<MaterialStatus> listaStatus) {
		this.listaStatus = listaStatus;
	}

	@PostConstruct
	public void listar() {
		try {
			MaterialStatusDAO materialstatusDAO = new MaterialStatusDAO();
			listaStatus = materialstatusDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Status.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		materialstatus = new MaterialStatus();
	}

	public void salvar() {
		try {
			MaterialStatusDAO materialstatusDAO = new MaterialStatusDAO();
			materialstatusDAO.merge(materialstatus);

			materialstatus = new MaterialStatus();
			listaStatus = materialstatusDAO.listar();

			Messages.addGlobalInfo("Status cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Status.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			materialstatus = (MaterialStatus) evento.getComponent().getAttributes().get("materialstatusSelecionado");

			MaterialStatusDAO materialstatusDAO = new MaterialStatusDAO();
			materialstatusDAO.excluir(materialstatus);

			materialstatus = new MaterialStatus();
			listaStatus = materialstatusDAO.listar();

			Messages.addGlobalInfo("Status removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Status.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		materialstatus = (MaterialStatus) evento.getComponent().getAttributes().get("materialstatusSelecionado");		
	}
}



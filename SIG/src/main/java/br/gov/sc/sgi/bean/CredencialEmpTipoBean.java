package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.CredencialEmpTipoDAO;
import br.gov.sc.sgi.domain.CredencialEmpTipo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CredencialEmpTipoBean implements Serializable {

	private CredencialEmpTipo CredencialEmpTipo;
	private List<CredencialEmpTipo> listaTipos;


	public CredencialEmpTipo getCredencialEmpTipo() {
		return CredencialEmpTipo;
	}

	public void setCredencialEmpTipo(CredencialEmpTipo CredencialEmpTipo) {
		this.CredencialEmpTipo = CredencialEmpTipo;
	}

	public List<CredencialEmpTipo> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<CredencialEmpTipo> listaTipos) {
		this.listaTipos = listaTipos;
	}

	@PostConstruct
	public void listar() {
		try {
			CredencialEmpTipoDAO CredencialEmpTipoDAO = new CredencialEmpTipoDAO();
			listaTipos = CredencialEmpTipoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Tipo.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		CredencialEmpTipo = new CredencialEmpTipo();
	}

	public void salvar() {
		try {
			CredencialEmpTipoDAO CredencialEmpTipoDAO = new CredencialEmpTipoDAO();
			CredencialEmpTipoDAO.merge(CredencialEmpTipo);

			CredencialEmpTipo = new CredencialEmpTipo();
			listaTipos = CredencialEmpTipoDAO.listar();

			Messages.addGlobalInfo("Tipo cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Tipo.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			CredencialEmpTipo = (CredencialEmpTipo) evento.getComponent().getAttributes().get("CredencialEmpTipoSelecionado");

			CredencialEmpTipoDAO CredencialEmpTipoDAO = new CredencialEmpTipoDAO();
			CredencialEmpTipoDAO.excluir(CredencialEmpTipo);

			CredencialEmpTipo = new CredencialEmpTipo();
			listaTipos = CredencialEmpTipoDAO.listar();

			Messages.addGlobalInfo("Tipo removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Tipo.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		CredencialEmpTipo = (CredencialEmpTipo) evento.getComponent().getAttributes().get("CredencialEmpTipoSelecionado");		
	}
}



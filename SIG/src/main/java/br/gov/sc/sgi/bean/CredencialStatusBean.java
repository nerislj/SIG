package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.CredencialStatusDAO;
import br.gov.sc.sgi.domain.CredencialStatus;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CredencialStatusBean implements Serializable {

	private CredencialStatus credencialstatus;
	private List<CredencialStatus> listaStatus;

	public CredencialStatus getCredencialstatus() {
		return credencialstatus;
	}

	public void setCredencialstatus(CredencialStatus credencialstatus) {
		this.credencialstatus = credencialstatus;
	}

	public List<CredencialStatus> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<CredencialStatus> listaStatus) {
		this.listaStatus = listaStatus;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("AQUI CREDENCIADOSTATUS BEAN");
			CredencialStatusDAO credencialstatusDAO = new CredencialStatusDAO();
			listaStatus = credencialstatusDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Status.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		credencialstatus = new CredencialStatus();
	}

	public void salvar() {
		try {
			CredencialStatusDAO credencialstatusDAO = new CredencialStatusDAO();
			credencialstatusDAO.merge(credencialstatus);

			credencialstatus = new CredencialStatus();
			listaStatus = credencialstatusDAO.listar();

			Messages.addGlobalInfo("Status cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Status.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			credencialstatus = (CredencialStatus) evento.getComponent().getAttributes().get("credencialstatusSelecionado");

			CredencialStatusDAO credencialtatusDAO = new CredencialStatusDAO();
			credencialtatusDAO.excluir(credencialstatus);

			credencialstatus = new CredencialStatus();
			listaStatus = credencialtatusDAO.listar();

			Messages.addGlobalInfo("Status removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Status.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		credencialstatus = (CredencialStatus) evento.getComponent().getAttributes().get("credencialstatusSelecionado");		
	}
}



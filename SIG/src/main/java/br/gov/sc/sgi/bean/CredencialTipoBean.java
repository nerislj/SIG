package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.CredencialTipoDAO;
import br.gov.sc.sgi.domain.CredencialTipo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CredencialTipoBean implements Serializable {

	private CredencialTipo credencialtipo;
	private List<CredencialTipo> listaTipos;


	public CredencialTipo getCredencialtipo() {
		return credencialtipo;
	}

	public void setCredencialtipo(CredencialTipo credencialtipo) {
		this.credencialtipo = credencialtipo;
	}

	public List<CredencialTipo> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<CredencialTipo> listaTipos) {
		this.listaTipos = listaTipos;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("AQUI CREDENCIADOTIPO BEAN");
			CredencialTipoDAO credencialtipoDAO = new CredencialTipoDAO();
			listaTipos = credencialtipoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Tipo.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		credencialtipo = new CredencialTipo();
	}

	public void salvar() {
		try {
			CredencialTipoDAO credencialtipoDAO = new CredencialTipoDAO();
			credencialtipoDAO.merge(credencialtipo);

			credencialtipo = new CredencialTipo();
			listaTipos = credencialtipoDAO.listar();

			Messages.addGlobalInfo("Tipo cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Tipo.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			credencialtipo = (CredencialTipo) evento.getComponent().getAttributes().get("credencialtipoSelecionado");

			CredencialTipoDAO credencialtipoDAO = new CredencialTipoDAO();
			credencialtipoDAO.excluir(credencialtipo);

			credencialtipo = new CredencialTipo();
			listaTipos = credencialtipoDAO.listar();

			Messages.addGlobalInfo("Tipo removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Tipo.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		credencialtipo = (CredencialTipo) evento.getComponent().getAttributes().get("credencialtipoSelecionado");		
	}
}



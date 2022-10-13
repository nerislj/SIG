package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.UsuarioStatusDAO;
import br.gov.sc.sgi.domain.UsuarioStatus;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioStatusBean implements Serializable {

	private UsuarioStatus usuariostatus;
	private List<UsuarioStatus> ListaStatus;

	public UsuarioStatus getUsuariostatus() {
		return usuariostatus;
	}

	public void setUsuariostatus(UsuarioStatus usuariostatus) {
		this.usuariostatus = usuariostatus;
	}

	public List<UsuarioStatus> getListaStatus() {
		return ListaStatus;
	}

	public void setListaStatus(List<UsuarioStatus> usuarioStatuss) {
		ListaStatus = usuarioStatuss;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("usuariostatus bean");
			UsuarioStatusDAO usuariostatusDAO = new UsuarioStatusDAO();
			ListaStatus = usuariostatusDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Status.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		usuariostatus = new UsuarioStatus();
	}

	public void salvar() {
		try {
			UsuarioStatusDAO usuariostatusDAO = new UsuarioStatusDAO();
			usuariostatusDAO.merge(usuariostatus);

			usuariostatus = new UsuarioStatus();
			ListaStatus = usuariostatusDAO.listar();

			Messages.addGlobalInfo("Status cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Status.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			usuariostatus = (UsuarioStatus) evento.getComponent().getAttributes().get("usuariostatusSelecionado");

			UsuarioStatusDAO usuariostatusDAO = new UsuarioStatusDAO();
			usuariostatusDAO.excluir(usuariostatus);

			usuariostatus = new UsuarioStatus();
			ListaStatus = usuariostatusDAO.listar();

			Messages.addGlobalInfo("Status removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Status.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		usuariostatus = (UsuarioStatus) evento.getComponent().getAttributes().get("usuariostatusSelecionado");		
	}
}



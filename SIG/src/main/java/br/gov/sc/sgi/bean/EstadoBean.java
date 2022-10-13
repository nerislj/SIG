package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable {

	private Estado estado;
	private List<Estado> Estados;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return Estados;
	}

	public void setEstados(List<Estado> estados) {
		Estados = estados;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("aqui estadobean");
			EstadoDAO estadoDAO = new EstadoDAO();
			Estados = estadoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Estados.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		estado = new Estado();
	}

	public void salvar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.merge(estado);

			estado = new Estado();
			Estados = estadoDAO.listar("nome");

			Messages.addGlobalInfo("Estado cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Estado.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionada");

			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.excluir(estado);

			Estados = estadoDAO.listar();

			Messages.addGlobalInfo("Estado removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Estado.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionada");
	}
}
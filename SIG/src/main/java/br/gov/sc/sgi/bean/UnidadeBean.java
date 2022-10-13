package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UnidadeBean implements Serializable {

	private Unidade unidade;
	private List<Unidade> unidades;

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("unidade bean");
			UnidadeDAO unidadeDAO = new UnidadeDAO();
			unidades = unidadeDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os unidades.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		unidade = new Unidade();
	}

	public void salvar() {
		try {
			UnidadeDAO unidadeDAO = new UnidadeDAO();
			unidadeDAO.merge(unidade);

			unidade = new Unidade();
			unidades = unidadeDAO.listar();

			Messages.addGlobalInfo("Unidade cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Unidade.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			unidade = (Unidade) evento.getComponent().getAttributes().get("unidadeSelecionada");

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			unidadeDAO.excluir(unidade);

			unidade = new Unidade();
			unidades = unidadeDAO.listar();

			Messages.addGlobalInfo("Unidade removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Unidade.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		unidade = (Unidade) evento.getComponent().getAttributes().get("unidadeSelecionada");
	}
}

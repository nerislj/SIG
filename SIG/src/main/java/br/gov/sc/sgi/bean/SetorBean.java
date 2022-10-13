package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class SetorBean implements Serializable {

	private Setor setor;
	private List<Setor> Setores;
	private List<Unidade> Unidades;

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public List<Setor> getSetores() {
		return Setores;
	}

	public void setSetores(List<Setor> setores) {
		Setores = setores;
	}

	public List<Unidade> getUnidades() {
		return Unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		Unidades = unidades;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("setor beAN");
			SetorDAO setorDAO = new SetorDAO();
			Setores = setorDAO.listar();
			UnidadeDAO unidadeDAO = new UnidadeDAO();
			Unidades = unidadeDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Setores.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		setor = new Setor();
	}

	public void salvar() {
		try {
			SetorDAO setorDAO = new SetorDAO();
			setorDAO.merge(setor);

			SetorBean.this.listar();

			Messages.addGlobalInfo("Setor cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Setor.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			setor = (Setor) evento.getComponent().getAttributes().get("setorSelecionado");

			SetorDAO setorDAO = new SetorDAO();
			setorDAO.excluir(setor);

			SetorBean.this.listar();

			Messages.addGlobalInfo("Setor removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Setor.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		setor = (Setor) evento.getComponent().getAttributes().get("setorSelecionado");
	}
}

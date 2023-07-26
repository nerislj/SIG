package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.ListaTelefonicaDAO;
import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.ListaTelefonica;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class ListaTelefonicaBean implements Serializable {

	private ListaTelefonica listaTelefonica;
	private List<ListaTelefonica> listaTelefonicas;
	private List<PessoaFisica> pessoas;
	private List<ListaTelefonica> listausuariosCount;
	private Setor setor;
	private List<Setor> listSetor;
	private List<Setor> setores;
	private List<Unidade> Unidades;
	private Unidade unidade;

	public List<Setor> getListSetor() {
		return listSetor;
	}

	public void setListSetor(List<Setor> listSetor) {
		this.listSetor = listSetor;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public List<ListaTelefonica> getListausuariosCount() {
		return listausuariosCount;
	}

	public void setListausuariosCount(List<ListaTelefonica> listausuariosCount) {
		this.listausuariosCount = listausuariosCount;
	}

	public ListaTelefonica getListaTelefonica() {
		return listaTelefonica;
	}

	public void setListaTelefonica(ListaTelefonica listaTelefonica) {
		this.listaTelefonica = listaTelefonica;
	}

	public List<ListaTelefonica> getListaTelefonicas() {
		return listaTelefonicas;
	}

	public void setListaTelefonicas(List<ListaTelefonica> listaTelefonicas) {
		this.listaTelefonicas = listaTelefonicas;
	}

	public List<PessoaFisica> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaFisica> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public List<Unidade> getUnidades() {
		return Unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		Unidades = unidades;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("AQUI ListaTelefonica BEAN");

			ListaTelefonicaDAO listaTelefonicaDAO = new ListaTelefonicaDAO();
			setListausuariosCount(listaTelefonicaDAO.listar());

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a lista.");
			erro.printStackTrace();
		}
	}

	public void listarListaTelefonica() {
		ListaTelefonicaDAO listaTelefonicaDAO = new ListaTelefonicaDAO();
		listaTelefonicas = listaTelefonicaDAO.listar();
	}

	public void novo() {
		try {
			listaTelefonica = new ListaTelefonica();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("cpf");

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			Unidades = unidadeDAO.listar();

			setores = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar nova lista.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ListaTelefonicaDAO listaTelefonicaDAO = new ListaTelefonicaDAO();

			listaTelefonicaDAO.merge(listaTelefonica);

			ListaTelefonicaBean.this.listar();

			Messages.addGlobalInfo("Em lista, cadastrado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cadastrar na lista.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			listaTelefonica = (ListaTelefonica) evento.getComponent().getAttributes().get("itemSelecionado");

			ListaTelefonicaDAO listaTelefonicaDAO = new ListaTelefonicaDAO();
			listaTelefonicaDAO.excluir(listaTelefonica);

			ListaTelefonicaBean.this.listar();

			Messages.addGlobalInfo("Item removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o item.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			listaTelefonica = (ListaTelefonica) evento.getComponent().getAttributes().get("itemSelecionado");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			Unidades = unidadeDAO.listar();

			SetorDAO setorDAO = new SetorDAO();
			setores = setorDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar um item.");
			erro.printStackTrace();
		}
	}

	public void popular() {
		try {
			if (listaTelefonica.getUnidade() != null) {
				SetorDAO setorDAO = new SetorDAO();
				setores = setorDAO.buscarPorUnidade(listaTelefonica.getUnidade().getCodigo());
			} else {
				setores = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as Unidades");
			erro.printStackTrace();
		}
	}

	public void popularSetor() {
		try {
			if (listaTelefonica.getSetor() != null) {
				SetorDAO setorDAO = new SetorDAO();
				listSetor = setorDAO.buscarPorUnidade(listaTelefonica.getSetor().getCodigo());
			} else {
				listSetor = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os Setores");
			erro.printStackTrace();
		}
	}

	public String removeAcentos(String s) {
		if (s == null) {
			return "";
		}
		String semAcentos = s.toLowerCase();
		semAcentos = semAcentos.replaceAll("[áàâãä]", "a");
		semAcentos = semAcentos.replaceAll("[éèêë]", "e");
		semAcentos = semAcentos.replaceAll("[íìîï]", "i");
		semAcentos = semAcentos.replaceAll("[óòôõö]", "o");
		semAcentos = semAcentos.replaceAll("[úùûü]", "u");
		semAcentos = semAcentos.replaceAll("ç", "c");
		semAcentos = semAcentos.replaceAll("ñ", "n");
		return semAcentos;
	}

}

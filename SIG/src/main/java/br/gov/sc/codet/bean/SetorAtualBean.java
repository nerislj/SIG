package br.gov.sc.codet.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.codet.dao.SetorAtualDAO;
import br.gov.sc.codet.domain.SetorAtual;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class SetorAtualBean implements Serializable {

	private SetorAtual setorAtualProcesso;
	private List<SetorAtual> listaSetoresAtuais;

	public SetorAtual getSetorAtualProcesso() {
		return setorAtualProcesso;
	}

	public void setSetorAtualProcesso(SetorAtual setorAtualProcesso) {
		this.setorAtualProcesso = setorAtualProcesso;
	}

	public List<SetorAtual> getListaSetoresAtuais() {
		return listaSetoresAtuais;
	}

	public void setListaSetoresAtuais(List<SetorAtual> listaSetoresAtuais) {
		this.listaSetoresAtuais = listaSetoresAtuais;
	}

	@PostConstruct
	public void listar() {
		try {
			SetorAtualDAO setorDAO = new SetorAtualDAO();
			listaSetoresAtuais = setorDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Setores.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		setorAtualProcesso = new SetorAtual();
	}

	public void salvar() {
		try {
			SetorAtualDAO setorDAO = new SetorAtualDAO();
			setorAtualProcesso.setDescricao(setorAtualProcesso.getDescricao().toUpperCase());
			
			setorDAO.merge(setorAtualProcesso);

			setorAtualProcesso = new SetorAtual();
			listaSetoresAtuais = setorDAO.listar();

			Messages.addGlobalInfo("Setor cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Setor.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			setorAtualProcesso = (SetorAtual) evento.getComponent().getAttributes().get("setorSelecionado");

			SetorAtualDAO setorDAO = new SetorAtualDAO();
			setorDAO.excluir(setorAtualProcesso);

			setorAtualProcesso = new SetorAtual();
			listaSetoresAtuais = setorDAO.listar();

			Messages.addGlobalInfo("Setor removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Setor.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		setorAtualProcesso = (SetorAtual) evento.getComponent().getAttributes().get("setorSelecionado");		
	}
}



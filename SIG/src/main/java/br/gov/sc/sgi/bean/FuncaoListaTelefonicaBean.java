package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.FuncaoListaTelefonicaDAO;
import br.gov.sc.sgi.domain.FuncaoListaTelefonica;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped

public class FuncaoListaTelefonicaBean implements Serializable{
	
	private FuncaoListaTelefonica funcaoListaTelefonica;
	private List<FuncaoListaTelefonica> listaFuncaoListaTelefonica;
	
	

	public FuncaoListaTelefonica getFuncaoListaTelefonica() {
		return funcaoListaTelefonica;
	}

	public void setFuncaoListaTelefonica(FuncaoListaTelefonica funcaoListaTelefonica) {
		this.funcaoListaTelefonica = funcaoListaTelefonica;
	}

	public List<FuncaoListaTelefonica> getListaFuncaoListaTelefonica() {
		return listaFuncaoListaTelefonica;
	}

	public void setListaFuncaoListaTelefonica(List<FuncaoListaTelefonica> listaFuncaoListaTelefonica) {
		this.listaFuncaoListaTelefonica = listaFuncaoListaTelefonica;
	}

	@PostConstruct
	public void listar() {
		try {

			FuncaoListaTelefonicaDAO funcaoListaTelefonicaDAO = new FuncaoListaTelefonicaDAO();
			listaFuncaoListaTelefonica = funcaoListaTelefonicaDAO.listar();
			
		
			

			funcaoListaTelefonica = new FuncaoListaTelefonica();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a função.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			FuncaoListaTelefonicaDAO funcaoListaTelefonicaDAO = new FuncaoListaTelefonicaDAO();

			funcaoListaTelefonicaDAO.merge(funcaoListaTelefonica);

			funcaoListaTelefonica = new FuncaoListaTelefonica();

			listaFuncaoListaTelefonica = funcaoListaTelefonicaDAO.listar();

			Messages.addGlobalInfo("Funçao cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a função.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			funcaoListaTelefonica = (FuncaoListaTelefonica) evento.getComponent().getAttributes().get("funcaoSelecionada");

			FuncaoListaTelefonicaDAO funcaoListaTelefonicaDAO = new FuncaoListaTelefonicaDAO();
			funcaoListaTelefonicaDAO.excluir(funcaoListaTelefonica);

			listaFuncaoListaTelefonica = funcaoListaTelefonicaDAO.listar();

			Messages.addGlobalInfo("Função removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir função.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			funcaoListaTelefonica = (FuncaoListaTelefonica) evento.getComponent().getAttributes().get("funcaoSelecionada");

			FuncaoListaTelefonicaDAO funcaoListaTelefonicaDAO = new FuncaoListaTelefonicaDAO();
			listaFuncaoListaTelefonica = funcaoListaTelefonicaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar função.");
			erro.printStackTrace();
		}
	}

}

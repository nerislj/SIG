package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.RecursoMultaTiposDAO;
import br.gov.sc.sgi.domain.RecursoMultaTipos;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class RecursoMultaTiposBean implements Serializable {

	private RecursoMultaTipos recursomultatipo;
	private List<RecursoMultaTipos> recursomultatipos;

	public RecursoMultaTipos getRecursomultatipo() {
		return recursomultatipo;
	}

	public void setRecursomultatipo(RecursoMultaTipos recursomultatipo) {
		this.recursomultatipo = recursomultatipo;
	}

	public List<RecursoMultaTipos> getRecursomultatipos() {
		return recursomultatipos;
	}

	public void setRecursomultatipos(List<RecursoMultaTipos> recursomultatipos) {
		this.recursomultatipos = recursomultatipos;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("recursomultatipo bean");
			RecursoMultaTiposDAO recursomultatipoDAO = new RecursoMultaTiposDAO();
			recursomultatipos = recursomultatipoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Tipos de Processo de Multa.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		recursomultatipo = new RecursoMultaTipos();
	}

	public void salvar() {
		try {
			RecursoMultaTiposDAO recursomultatipoDAO = new RecursoMultaTiposDAO();
			recursomultatipoDAO.merge(recursomultatipo);

			recursomultatipo = new RecursoMultaTipos();
			recursomultatipos = recursomultatipoDAO.listar();

			Messages.addGlobalInfo("Tipo de Recurso de Multa cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Tipo de Recurso de Multa.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			recursomultatipo = (RecursoMultaTipos) evento.getComponent().getAttributes().get("recursomultatipoSelecionado");

			RecursoMultaTiposDAO recursomultatipoDAO = new RecursoMultaTiposDAO();
			recursomultatipoDAO.excluir(recursomultatipo);

			recursomultatipo = new RecursoMultaTipos();
			recursomultatipos = recursomultatipoDAO.listar();

			Messages.addGlobalInfo("Tipo de Recurso de Multa removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Tipo de Recurso de Multa.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		recursomultatipo = (RecursoMultaTipos) evento.getComponent().getAttributes().get("recursomultatipoSelecionado");		
	}
}
package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.FrotaUnidadeDAO;
import br.gov.sc.sgi.domain.FrotaUnidade;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FrotaUnidadeBean implements Serializable {

	private FrotaUnidade frotaUnidade;
	private List<FrotaUnidade> frotaUnidades;

	public FrotaUnidade getFrotaUnidade() {
		return frotaUnidade;
	}

	public void setFrotaUnidade(FrotaUnidade frotaUnidade) {
		this.frotaUnidade = frotaUnidade;
	}

	public List<FrotaUnidade> getFrotaUnidades() {
		return frotaUnidades;
	}

	public void setFrotaUnidades(List<FrotaUnidade> frotaUnidades) {
		this.frotaUnidades = frotaUnidades;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("frota unidade bean");
			FrotaUnidadeDAO frotaUnidadeDAO = new FrotaUnidadeDAO();
			frotaUnidades = frotaUnidadeDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as unidades.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		frotaUnidade = new FrotaUnidade();
	}

	public void salvar() {
		try {
			FrotaUnidadeDAO frotaUnidadeDAO = new FrotaUnidadeDAO();
			frotaUnidadeDAO.merge(frotaUnidade);

			frotaUnidade = new FrotaUnidade();
			frotaUnidades = frotaUnidadeDAO.listar();

			Messages.addGlobalInfo("Unidade cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Unidade.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			frotaUnidade = (FrotaUnidade) evento.getComponent().getAttributes().get("frotaUnidadeSelecionada");

			FrotaUnidadeDAO frotaUnidadeDAO = new FrotaUnidadeDAO();
			frotaUnidadeDAO.excluir(frotaUnidade);

			frotaUnidade = new FrotaUnidade();
			frotaUnidades = frotaUnidadeDAO.listar();

			Messages.addGlobalInfo("Unidade removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Unidade.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		frotaUnidade = (FrotaUnidade) evento.getComponent().getAttributes().get("frotaUnidadeSelecionada");
	}
}

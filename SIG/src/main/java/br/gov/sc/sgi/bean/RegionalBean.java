package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.RegionalDAO;
import br.gov.sc.sgi.domain.RegionalUnidade;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RegionalBean implements Serializable {

	private RegionalUnidade regional;
	private List<RegionalUnidade> regionais;

	

	@PostConstruct
	public void listar() {
		try {
			
			RegionalDAO regionalDAO = new RegionalDAO();
			regionais = regionalDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Regionais.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		regional = new RegionalUnidade();
	}

	public void salvar() {
		try {
			RegionalDAO regionalDAO = new RegionalDAO();
			regionalDAO.merge(regional);

			regional = new RegionalUnidade();
			regionais = regionalDAO.listar();

			Messages.addGlobalInfo("Regional cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Regional.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			regional = (RegionalUnidade) evento.getComponent().getAttributes().get("regionalSelecionada");

			RegionalDAO regionalDAO = new RegionalDAO();
			regionalDAO.excluir(regional);

			regional = new RegionalUnidade();
			regionais = regionalDAO.listar();

			Messages.addGlobalInfo("Regional removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Unidade.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		regional = (RegionalUnidade) evento.getComponent().getAttributes().get("regionalSelecionada");

	}

	public List<RegionalUnidade> getRegionais() {
		return regionais;
	}

	public void setRegionais(List<RegionalUnidade> regionais) {
		this.regionais = regionais;
	}

	public RegionalUnidade getRegional() {
		return regional;
	}

	public void setRegional(RegionalUnidade regional) {
		this.regional = regional;
	}
}

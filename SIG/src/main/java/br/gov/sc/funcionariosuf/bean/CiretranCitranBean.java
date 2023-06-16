
package br.gov.sc.funcionariosuf.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.funcionariosuf.dao.CiretranCitranDAO;
import br.gov.sc.funcionariosuf.dao.CiretranDAO;
import br.gov.sc.funcionariosuf.domain.Ciretran;
import br.gov.sc.funcionariosuf.domain.CiretranCitran;
import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.Cidade;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CiretranCitranBean implements Serializable {

	private CiretranCitran ciretranCitran;
	private List<CiretranCitran> listaCiretranCitran;
	private List<Ciretran> listaCiretran;

	@PostConstruct
	public void listar() {
		try {
			
			CiretranCitranDAO ciretranCitranDAO = new CiretranCitranDAO();
			listaCiretranCitran = ciretranCitranDAO.listar();
			
			CiretranDAO ciretranDAO = new CiretranDAO();
			listaCiretran = ciretranDAO.listar("nome");
			
			ciretranCitran = new CiretranCitran();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Ciretran Citran.");
			erro.printStackTrace();
		}
	}


	public void salvar() {
		try {
			
			

			CiretranCitranDAO ciretranCitranDAO = new CiretranCitranDAO();
		
			ciretranCitranDAO.merge(ciretranCitran);

			ciretranCitran = new CiretranCitran();
			
			CiretranDAO ciretranDAO = new CiretranDAO();
			listaCiretran = ciretranDAO.listar();
			
			listaCiretranCitran = ciretranCitranDAO.listar();

			Messages.addGlobalInfo("Ciretran/Citran cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Ciretran/Citran.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			ciretranCitran = (CiretranCitran) evento.getComponent().getAttributes().get("ciretranCitranSelecionado");

			CiretranCitranDAO ciretranCitranDAO = new CiretranCitranDAO();
			ciretranCitranDAO.excluir(ciretranCitran);

			listaCiretranCitran = ciretranCitranDAO.listar();

			Messages.addGlobalInfo("Ciretran/Citran removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Ciretran/Citran.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			ciretranCitran = (CiretranCitran) evento.getComponent().getAttributes().get("ciretranCitranSelecionado");

			CiretranDAO ciretranDAO = new CiretranDAO();
			listaCiretran = ciretranDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar uma Ciretran.");
			erro.printStackTrace();
		}
	}


	public CiretranCitran getCiretranCitran() {
		return ciretranCitran;
	}


	public void setCiretranCitran(CiretranCitran ciretranCitran) {
		this.ciretranCitran = ciretranCitran;
	}


	public List<CiretranCitran> getListaCiretranCitran() {
		return listaCiretranCitran;
	}


	public void setListaCiretranCitran(List<CiretranCitran> listaCiretranCitran) {
		this.listaCiretranCitran = listaCiretranCitran;
	}


	public List<Ciretran> getListaCiretran() {
		return listaCiretran;
	}


	public void setListaCiretran(List<Ciretran> listaCiretran) {
		this.listaCiretran = listaCiretran;
	}
	
	

	
}
package br.gov.sc.cetran.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.cetran.dao.TipoRequerenteDAO;
import br.gov.sc.cetran.domain.TipoRequerente;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TipoRequerenteBean implements Serializable {

	private TipoRequerente tipoRequerente;
	
	
	
	private List<TipoRequerente> listatipoRequerentes;
	
	
	
	

	@PostConstruct
	public void listar() {
		try {
			TipoRequerenteDAO TipoRequerenteDAO = new TipoRequerenteDAO();
			
			listatipoRequerentes = TipoRequerenteDAO.listarTudo();
			
			tipoRequerente = new TipoRequerente();
			
			
			
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a TipoRequerente.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		tipoRequerente = new TipoRequerente();
	}

	public void salvar() {
		try {
			
			
			tipoRequerente.setDescricao(tipoRequerente.getDescricao().toUpperCase());
			
			TipoRequerenteDAO TipoRequerenteDAO = new TipoRequerenteDAO();
			TipoRequerenteDAO.merge(tipoRequerente);
			

			Messages.addGlobalInfo("TipoRequerente cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o TipoRequerente.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			
			

			tipoRequerente = new TipoRequerente();
		

			Messages.addGlobalInfo("TipoRequerente removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o TipoRequerente.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		tipoRequerente = (TipoRequerente) evento.getComponent().getAttributes().get("tipoRequerenteSelecionado");		
	}

	public TipoRequerente getTipoRequerente() {
		return tipoRequerente;
	}

	public List<TipoRequerente> getListatipoRequerentes() {
		return listatipoRequerentes;
	}

	public void setListatipoRequerentes(List<TipoRequerente> listatipoRequerentes) {
		this.listatipoRequerentes = listatipoRequerentes;
	}

	public void setTipoRequerente(TipoRequerente tipoRequerente) {
		this.tipoRequerente = tipoRequerente;
	}


	





	
	

	
}



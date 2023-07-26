
package br.gov.sc.visita.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.visita.dao.CategoriaVisitaDAO;
import br.gov.sc.visita.dao.VisitanteDAO;
import br.gov.sc.visita.domain.CategoriaVisita;
import br.gov.sc.visita.domain.Visitante;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CategoriaBean implements Serializable {
	
	private CategoriaVisita categoriaVisita;
	private List<CategoriaVisita> listaCategoriaVisitas;
	
	

	@PostConstruct
	public void listar() {
		try {

			CategoriaVisitaDAO categoriaDAO = new CategoriaVisitaDAO();
			listaCategoriaVisitas = categoriaDAO.listar();
			
		
			

			categoriaVisita = new CategoriaVisita();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a Categoria.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			CategoriaVisitaDAO categoriaDAO = new CategoriaVisitaDAO();

			categoriaDAO.merge(categoriaVisita);

			categoriaVisita = new CategoriaVisita();

			listaCategoriaVisitas = categoriaDAO.listar();

			Messages.addGlobalInfo("Categoria Visita cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Categoria Visita.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			categoriaVisita = (CategoriaVisita) evento.getComponent().getAttributes().get("categoriaVisitaSelecionado");

			CategoriaVisitaDAO categoriaDAO = new CategoriaVisitaDAO();
			categoriaDAO.excluir(categoriaVisita);

			listaCategoriaVisitas = categoriaDAO.listar();

			Messages.addGlobalInfo("Categoria removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Categoria.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			categoriaVisita = (CategoriaVisita) evento.getComponent().getAttributes().get("categoriaVisitaSelecionado");

			CategoriaVisitaDAO categoriaDAO = new CategoriaVisitaDAO();
			listaCategoriaVisitas = categoriaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar a Categoria.");
			erro.printStackTrace();
		}
	}

	public CategoriaVisita getCategoriaVisita() {
		return categoriaVisita;
	}

	public void setCategoriaVisita(CategoriaVisita categoriaVisita) {
		this.categoriaVisita = categoriaVisita;
	}

	public List<CategoriaVisita> getListaCategoriaVisitas() {
		return listaCategoriaVisitas;
	}

	public void setListaCategoriaVisitas(List<CategoriaVisita> listaCategoriaVisitas) {
		this.listaCategoriaVisitas = listaCategoriaVisitas;
	}
	
	

	
}
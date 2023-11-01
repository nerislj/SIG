
package br.gov.sc.visita.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.visita.dao.VisitanteDAO;
import br.gov.sc.visita.domain.Visitante;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VisitanteBean implements Serializable {
	
	private Visitante visitante;
	private List<Visitante> listaVisitantes;
	
	private List<Cidade> cidades;
	private List<Estado> estados;

	@PostConstruct
	public void listar() {
		try {

			VisitanteDAO visitanteDAO = new VisitanteDAO();
			listaVisitantes = visitanteDAO.listar();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("sigla");
			
			

			visitante = new Visitante();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os visitantes.");
			erro.printStackTrace();
		}
	}

	public Date getHj() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 0);
		System.out.println(c.getTime());
		return c.getTime();
	}
	
	
	public void salvar() {
		try {

			VisitanteDAO visitanteDAO = new VisitanteDAO();

			visitante.setNomeCompleto(visitante.getNomeCompleto().toUpperCase());
			
			String celularFormatado = "";
			String celularFormatado2 = "";
			String result = "";
			
			if(visitante.getCelular()!=null) {
			celularFormatado = visitante.getCelular().replace(")9", "");
			celularFormatado2 = celularFormatado;
			result = celularFormatado2.replace("(", "");
			visitante.setCelular("55" + result);
			}
			
			visitante.setDataHoraCadastro(getHj());
			
			
			visitanteDAO.merge(visitante);

			visitante = new Visitante();

			listaVisitantes = visitanteDAO.listar();
			
			this.refresh();

			Messages.addGlobalInfo("Visitante cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Visitante.");
			erro.printStackTrace();
		}
	}
	
	public void refresh() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse();

	}

	public void excluir(ActionEvent evento) {

		try {
			visitante = (Visitante) evento.getComponent().getAttributes().get("visitanteSelecionado");

			VisitanteDAO visitanteDAO = new VisitanteDAO();
			visitanteDAO.excluir(visitante);

			listaVisitantes = visitanteDAO.listar();

			Messages.addGlobalInfo("Visitante removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Visitante.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			visitante = (Visitante) evento.getComponent().getAttributes().get("visitanteSelecionado");

			VisitanteDAO visitanteDAO = new VisitanteDAO();
			listaVisitantes = visitanteDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o visitante.");
			erro.printStackTrace();
		}
	}
	
	public void popular() {
		try {
			if (visitante.getEstado() != null) {
				CidadeDAO municipioDAO = new CidadeDAO();
				cidades = municipioDAO.buscarPorEstado(visitante.getEstado().getCodigo());
			} else {
				cidades = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades");
			erro.printStackTrace();
		}
	}
	

	public Visitante getVisitante() {
		return visitante;
	}

	public void setVisitante(Visitante visitante) {
		this.visitante = visitante;
	}

	public List<Visitante> getListaVisitantes() {
		return listaVisitantes;
	}

	public void setListaVisitantes(List<Visitante> listaVisitantes) {
		this.listaVisitantes = listaVisitantes;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	

}
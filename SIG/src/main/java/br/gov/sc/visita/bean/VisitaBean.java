
package br.gov.sc.visita.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.component.export.PDFOptions;

import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.visita.dao.CategoriaVisitaDAO;
import br.gov.sc.visita.dao.DestinoVisitaDAO;
import br.gov.sc.visita.dao.VisitaDAO;
import br.gov.sc.visita.dao.VisitanteDAO;
import br.gov.sc.visita.domain.CategoriaVisita;
import br.gov.sc.visita.domain.DestinoVisita;
import br.gov.sc.visita.domain.Visita;
import br.gov.sc.visita.domain.Visitante;
import util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VisitaBean implements Serializable {

	private Visita visita;
	private List<Visita> listaVisitas;
	
	
	private List<Visita> listaVisitaEtiquetas;

	private Visitante visitante;
	private List<Visitante> listaVisitantes;

	private List<CategoriaVisita> listaCategorias;
	private List<DestinoVisita> listaDestinos;

	private List<Cidade> cidades;
	private List<Estado> estados;

	private PDFOptions pdfOpt;
	
	private String nomeCompleto;

	@PostConstruct
	public void listar() {
		try {

			visita = new Visita();

			
			listaVisitas = new ArrayList<Visita>();

			VisitanteDAO visitanteDAO = new VisitanteDAO();
			listaVisitantes = visitanteDAO.listar();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("sigla");

			CategoriaVisitaDAO categoriaDAO = new CategoriaVisitaDAO();
			listaCategorias = categoriaDAO.listar();

			DestinoVisitaDAO destinoDAO = new DestinoVisitaDAO();
			listaDestinos = destinoDAO.listar();

			
			
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os visitantes.");
			erro.printStackTrace();
		}
	}
	
	public void listasVisitas() {
		VisitaDAO visitaDAO = new VisitaDAO();
		
		listaVisitas = visitaDAO.listarVisitas();
	}

	public void salvar() {
		try {

			VisitaDAO visitaDAO = new VisitaDAO();

			visita.setVisitante(visitante);
			visita.setDataHora(getHj());

			visitaDAO.merge(visita);

			visita = new Visita();

			listaVisitas = visitaDAO.listarVisitas();

			Messages.addGlobalInfo("Visita cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Visita.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			System.out.println("ENTROU EDITAR");
			visita = (Visita) evento.getComponent().getAttributes().get("visitaSelecionado");

			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar a visita.");
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

	public void popularDadosVisitante() throws Exception {
		try {
			if (visita.getVisitante() != null) {
				VisitaDAO visitaDAO = new VisitaDAO();
				System.out.println(visita.getVisitante().getCpf());
				visitante = (Visitante) visitaDAO.buscarVisitante(visita.getVisitante());
			} else {
				visitante = new Visitante();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar o visitante");
			erro.printStackTrace();
		}
	}

	public Date getHj() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 0);
		System.out.println(c.getTime());
		return c.getTime();
	}
	


	public void etiqueta(ActionEvent evento) throws Exception {
		VisitaDAO visitaDAO = new VisitaDAO();
		listaVisitas = visitaDAO.listarVisitas();
		

	}
	
	public void imprimirDeclaracao(ActionEvent evento) {
		visita = (Visita) evento.getComponent().getAttributes().get("visitaSelecionado");
		
		System.out.println("DECLARAÇÃO " + visita);
		try {

			JSFUtil.redirect("../ImprimirRelatorio?rlt_nome=declaracaovisita" + "&visitaId=" + visita.getCodigo());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException nulo) {
			throw new NullPointerException(
					"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
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

	public Visita getVisita() {
		return visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
	}

	public List<Visita> getListaVisitas() {
		return listaVisitas;
	}

	public void setListaVisitas(List<Visita> listaVisitas) {
		this.listaVisitas = listaVisitas;
	}

	public List<CategoriaVisita> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<CategoriaVisita> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public List<DestinoVisita> getListaDestinos() {
		return listaDestinos;
	}

	public void setListaDestinos(List<DestinoVisita> listaDestinos) {
		this.listaDestinos = listaDestinos;
	}

	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}

	public void setPdfOpt(PDFOptions pdfOpt) {
		this.pdfOpt = pdfOpt;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}




	public List<Visita> getListaVisitaEtiquetas() {
		return listaVisitaEtiquetas;
	}

	public void setListaVisitaEtiquetas(List<Visita> listaVisitaEtiquetas) {
		this.listaVisitaEtiquetas = listaVisitaEtiquetas;
	}

}
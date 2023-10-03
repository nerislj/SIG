
package br.gov.sc.pesquisa.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.pesquisa.dao.PesquisaDAO;
import br.gov.sc.pesquisa.domain.Pesquisa;
import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.visita.domain.Visita;
import util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class PesquisaBean implements Serializable {

	private Pesquisa pesquisa;
	private List<Pesquisa> listaPesquisas;

	private List<Pesquisa> listaCredenciados;
	
	private List<CredenciadoEmp> listaCredenciadoEmp;
	private List<Credenciado> listaCredenciado;
	
	private List<Cidade> municipios;

	@PostConstruct
	public void listar() {
		try {
			
			pesquisa = new Pesquisa();

			PesquisaDAO pesquisaDAO = new PesquisaDAO();
			listaPesquisas = pesquisaDAO.listar();
			
			CidadeDAO unidadeDAO = new CidadeDAO();
			municipios = unidadeDAO.buscarPorEstadoNome("SC");
			
			
				
			
			listaCredenciados = pesquisaDAO.listaCredenciados();
			
			
			
			

			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a Categoria.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			PesquisaDAO pesquisaDAO = new PesquisaDAO();

			pesquisaDAO.merge(pesquisa);

			pesquisa = new Pesquisa();

			listaPesquisas = pesquisaDAO.listar();

			Messages.addGlobalInfo("Categoria Visita cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Categoria Visita.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			pesquisa = (Pesquisa) evento.getComponent().getAttributes().get("PesquisaSelecionado");

			PesquisaDAO pesquisaDAO = new PesquisaDAO();
			pesquisaDAO.excluir(pesquisa);

			listaPesquisas = pesquisaDAO.listar();

			Messages.addGlobalInfo("Categoria removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Categoria.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			pesquisa = (Pesquisa) evento.getComponent().getAttributes().get("PesquisaSelecionado");

			PesquisaDAO pesquisaDAO = new PesquisaDAO();
			listaPesquisas = pesquisaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar a Categoria.");
			erro.printStackTrace();
		}
	}
	


	public void imprimirQR(ActionEvent evento) {
		pesquisa = (Pesquisa) evento.getComponent().getAttributes().get("nome");
		
		System.out.println("pesquisa " + pesquisa);
		try {

			JSFUtil.redirect("../ImprimirRelatorio?rlt_nome=declaracaovisita" + "&credenciado=" + pesquisa.getCodigo());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException nulo) {
			throw new NullPointerException(
					"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
		}

	}
	
	 public void runSubmit() {
	        System.out.println("Submit executed");
	    }
	
	

	public List<Pesquisa> getListaPesquisas() {
		return listaPesquisas;
	}

	public void setListaPesquisas(List<Pesquisa> listaPesquisas) {
		this.listaPesquisas = listaPesquisas;
	}

	

	

	public List<Cidade> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<Cidade> municipios) {
		this.municipios = municipios;
	}

	public Pesquisa getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}

	public List<Pesquisa> getListaCredenciados() {
		return listaCredenciados;
	}

	public void setListaCredenciados(List<Pesquisa> listaCredenciados) {
		this.listaCredenciados = listaCredenciados;
	}

	public List<CredenciadoEmp> getListaCredenciadoEmp() {
		return listaCredenciadoEmp;
	}

	public void setListaCredenciadoEmp(List<CredenciadoEmp> listaCredenciadoEmp) {
		this.listaCredenciadoEmp = listaCredenciadoEmp;
	}

	public List<Credenciado> getListaCredenciado() {
		return listaCredenciado;
	}

	public void setListaCredenciado(List<Credenciado> listaCredenciado) {
		this.listaCredenciado = listaCredenciado;
	}

	
	

}
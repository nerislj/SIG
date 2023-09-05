
package br.gov.sc.pesquisa.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.pesquisa.dao.PesquisaDAO;
import br.gov.sc.pesquisa.domain.Pesquisa;
import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PesquisaBean implements Serializable {

	private Pesquisa pesquisa;
	private List<Pesquisa> listaPesquisas;
	
	private List<Cidade> municipios;

	@PostConstruct
	public void listar() {
		try {

			PesquisaDAO pesquisaDAO = new PesquisaDAO();
			listaPesquisas = pesquisaDAO.listar();
			
			CidadeDAO unidadeDAO = new CidadeDAO();
			
			municipios = unidadeDAO.buscarPorEstadoNome("SC");

			pesquisa = new Pesquisa();
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
	
	

}
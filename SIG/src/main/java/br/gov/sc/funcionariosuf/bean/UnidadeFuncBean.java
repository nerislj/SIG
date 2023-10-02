
package br.gov.sc.funcionariosuf.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.ToggleEvent;

import com.google.gson.Gson;

import br.gov.sc.funcionariosuf.dao.EstagiariosDAO;
import br.gov.sc.funcionariosuf.dao.ServidoresDAO;
import br.gov.sc.funcionariosuf.dao.TerceirizadosDAO;
import br.gov.sc.funcionariosuf.dao.UnidadeFuncDAO;
import br.gov.sc.funcionariosuf.domain.Estagiarios;
import br.gov.sc.funcionariosuf.domain.Servidores;
import br.gov.sc.funcionariosuf.domain.Terceirizados;
import br.gov.sc.funcionariosuf.domain.UnidadeFunc;
import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UnidadeFuncBean implements Serializable {

	private UnidadeFunc unidadeFunc;
	private List<UnidadeFunc> listaUnidadeFunc;

	private Unidade unidade;
	private List<Unidade> listaUnidade;

	private List<Servidores> listaServidores;
	private List<Terceirizados> listaTerceirizados;
	private List<Estagiarios> listaEstagiarios;

	private Setor setor;
	private List<Setor> listasetores;

	private List<Estado> Estados;
	private Estado estado;
	private List<Cidade> Cidades;

	public void novo() {
		try {
			unidadeFunc = new UnidadeFunc();

			estado = new Estado();

			EstadoDAO estadoDAO = new EstadoDAO();
			Estados = estadoDAO.listar("sigla");

			Cidades = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar as Pessoas.");
			erro.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			this.Estados = estadoDAO.listar("sigla");

			UnidadeFuncDAO unidadeFuncDAO = new UnidadeFuncDAO();
			listaUnidadeFunc = unidadeFuncDAO.listar();

			SetorDAO setorDAO = new SetorDAO();
			listasetores = setorDAO.listar("setorNome");

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			listaUnidade = unidadeDAO.listar("unidadeNome");

			unidadeFunc = new UnidadeFunc();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Ciretran Citran.");
			erro.printStackTrace();
		}
	}

	/*
	 * public void viewUnidade(ActionEvent evento) {
	 * 
	 * unidadeFunc = (UnidadeFunc)
	 * evento.getComponent().getAttributes().get("unidadeSelecionado");
	 * 
	 * SetorDAO setorDAO = new SetorDAO(); CidadeDAO municipioDAO = new CidadeDAO();
	 * 
	 * ServidoresDAO servidoresDAO = new ServidoresDAO(); listaServidores =
	 * servidoresDAO.listarPorUnidadeCodigo(unidadeFunc.getUnidade(),
	 * unidadeFunc.getSetor());
	 * 
	 * TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();
	 * listaTerceirizados =
	 * terceirizadosDAO.listarPorUnidadeCodigo(unidadeFunc.getUnidadeFunc(),
	 * unidadeFunc.getSetor());
	 * 
	 * EstagiariosDAO estagiariosDAO = new EstagiariosDAO(); listaEstagiarios =
	 * estagiariosDAO.listarPorUnidadeCodigo(unidadeFunc.getCiretranCitran(),
	 * unidadeFunc.getSetor());
	 * 
	 * System.out.println(listaEstagiarios + " listaEstagiarioslistaEstagiarios");
	 * 
	 * Cidades =
	 * municipioDAO.buscarPorEstado(this.unidadeFunc.getEstadoEndereco().getCodigo()
	 * ); listasetores =
	 * setorDAO.buscarPorCiretranNome(unidadeFunc.getSetor().getSetorNome());
	 * 
	 * }
	 */

	public void salvar() {
		try {

			UnidadeFuncDAO unidadeFuncDAO = new UnidadeFuncDAO();

			unidadeFuncDAO.merge(unidadeFunc);

			unidadeFunc = new UnidadeFunc();

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			listaUnidade = unidadeDAO.listar();

			listaUnidadeFunc = unidadeFuncDAO.listar();

			Messages.addGlobalInfo("Unidade cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Unidade.");
			erro.printStackTrace();
		}
	}

	public void onRowToggle(ToggleEvent event) {
		ServidoresDAO servidoresDAO = new ServidoresDAO();
		TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();
		EstagiariosDAO estagiariosDAO = new EstagiariosDAO();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row State " + event.getVisibility(),
				"Model:" + ((UnidadeFunc) event.getData()));

		System.out.println(((UnidadeFunc) event.getData()).getUnidade().getUnidadeNome() + ""
				+ ((UnidadeFunc) event.getData()).getSetor().getSetorNome() + "LISTRA SERVIDORES");

		listaServidores = servidoresDAO.listarPorUnidade(((UnidadeFunc) event.getData()).getUnidade(),
				((UnidadeFunc) event.getData()).getSetor());
		listaTerceirizados = terceirizadosDAO.listarPorUnidade(((UnidadeFunc) event.getData()).getUnidade(),
				((UnidadeFunc) event.getData()).getSetor());
		listaEstagiarios = estagiariosDAO.listarPorUnidade(((UnidadeFunc) event.getData()).getUnidade(),
				((UnidadeFunc) event.getData()).getSetor());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void excluir(ActionEvent evento) {

		try {
			unidadeFunc = (UnidadeFunc) evento.getComponent().getAttributes().get("ciretranCitranSelecionado");

			UnidadeFuncDAO unidadeFuncDAO = new UnidadeFuncDAO();
			unidadeFuncDAO.excluir(unidadeFunc);

			listaUnidadeFunc = unidadeFuncDAO.listar();

			Messages.addGlobalInfo("Ciretran/Citran removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Ciretran/Citran.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			unidadeFunc = (UnidadeFunc) evento.getComponent().getAttributes().get("ciretranCitranSelecionado");

			EstadoDAO estadoDAO = new EstadoDAO();
			CidadeDAO municipioDAO = new CidadeDAO();
			SetorDAO setorDAO = new SetorDAO();
			listasetores = setorDAO.buscarPorCiretranNome(unidadeFunc.getSetor().getSetorNome());
			this.Estados = estadoDAO.listar("sigla");
			this.Cidades = municipioDAO.buscarPorEstado(this.unidadeFunc.getEstadoEndereco().getCodigo());

			UnidadeFuncDAO ciretranDAO = new UnidadeFuncDAO();
			listaUnidadeFunc = ciretranDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar uma Ciretran.");
			erro.printStackTrace();
		}
	}

	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {

			String newcep = unidadeFunc.getCep().replace(".", "");
			newcep = newcep.replace("-", "");

			URL url = new URL("http://viacep.com.br/ws/" + newcep + "/json");
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			StringBuilder jsonCep = new StringBuilder();

			String cep = "";

			while ((cep = br.readLine()) != null) {

				jsonCep.append(cep);

			}

			System.out.println(jsonCep);

			PessoaJuridica cepEmpresa = new Gson().fromJson(jsonCep.toString(), PessoaJuridica.class);

			System.out.println(cepEmpresa.getCep());
			System.out.println(cepEmpresa.getEndereco());

			unidadeFunc.setCep(cepEmpresa.getCep());

			unidadeFunc.setEndereco(cepEmpresa.getEndereco() + ", " + cepEmpresa.getBairro());

			CidadeDAO municipioDAO = new CidadeDAO();
			EstadoDAO estadoDAO = new EstadoDAO();

			System.out.println(estadoDAO.loadSigla(cepEmpresa.getUf())
					+ "estadoDAO.loadSigla(cepEmpresa.getUf())estadoDAO.loadSigla(cepEmpresa.getUf())estadoDAO.loadSigla(cepEmpresa.getUf())");
			unidadeFunc.setEstadoEndereco(estadoDAO.loadSigla(cepEmpresa.getUf()));

			Cidades = municipioDAO.buscarPorEstado(unidadeFunc.getEstadoEndereco().getCodigo());

			unidadeFunc.setMunicipioEndereco(municipioDAO.loadNome(cepEmpresa.getLocalidade()));

			System.out.println(municipioDAO.loadNome(cepEmpresa.getLocalidade()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void popular() {
		try {
			if (this.unidadeFunc.getEstadoEndereco() != null) {
				CidadeDAO municipioDAO = new CidadeDAO();
				this.Cidades = municipioDAO.buscarPorEstado(this.unidadeFunc.getEstadoEndereco().getCodigo());
			} else {
				this.Cidades = new ArrayList<Cidade>();
			}
		} catch (NullPointerException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades", new Object[0]);
		}

	}

	public void popularCITRAN() {
		try {
			if (unidadeFunc.getUnidade() != null) {
				UnidadeFuncDAO unidadeFuncDAO = new UnidadeFuncDAO();
				listaUnidadeFunc = unidadeFuncDAO.buscarPorCiretran(unidadeFunc.getUnidade().getCodigo());
				SetorDAO setorDAO = new SetorDAO();
				System.out.println(unidadeFunc.getUnidade().getUnidadeNome());
				listasetores = setorDAO.buscarPorCiretranNome(unidadeFunc.getUnidade().getUnidadeNome());
				System.out.println(listasetores);
			} else {
				Cidades = new ArrayList<>();

			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as CIRETRAN/CITRAN");
			erro.printStackTrace();
		}
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public List<Estado> getEstados() {
		return Estados;
	}

	public void setEstados(List<Estado> estados) {
		Estados = estados;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Cidade> getCidades() {
		return Cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		Cidades = cidades;
	}

	public List<UnidadeFunc> getListaUnidadeFunc() {
		return listaUnidadeFunc;
	}

	public void setListaUnidadeFunc(List<UnidadeFunc> listaUnidadeFunc) {
		this.listaUnidadeFunc = listaUnidadeFunc;
	}

	public List<Servidores> getListaServidores() {
		return listaServidores;
	}

	public void setListaServidores(List<Servidores> listaServidores) {
		this.listaServidores = listaServidores;
	}

	public List<Terceirizados> getListaTerceirizados() {
		return listaTerceirizados;
	}

	public void setListaTerceirizados(List<Terceirizados> listaTerceirizados) {
		this.listaTerceirizados = listaTerceirizados;
	}

	public List<Estagiarios> getListaEstagiarios() {
		return listaEstagiarios;
	}

	public void setListaEstagiarios(List<Estagiarios> listaEstagiarios) {
		this.listaEstagiarios = listaEstagiarios;
	}

	public UnidadeFunc getUnidadeFunc() {
		return unidadeFunc;
	}

	public void setUnidadeFunc(UnidadeFunc unidadeFunc) {
		this.unidadeFunc = unidadeFunc;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public List<Unidade> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidade> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public List<Setor> getListasetores() {
		return listasetores;
	}

	public void setListasetores(List<Setor> listasetores) {
		this.listasetores = listasetores;
	}

}
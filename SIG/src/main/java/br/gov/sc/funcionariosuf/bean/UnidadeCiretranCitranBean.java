
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

import br.gov.sc.codet.dao.PartesProcessoDAO;
import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.HistoricoProcessoCODET;
import br.gov.sc.codet.domain.PartesProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.funcionariosuf.dao.CiretranCitranDAO;
import br.gov.sc.funcionariosuf.dao.CiretranDAO;
import br.gov.sc.funcionariosuf.dao.EstagiariosDAO;
import br.gov.sc.funcionariosuf.dao.ServidoresDAO;
import br.gov.sc.funcionariosuf.dao.TerceirizadosDAO;
import br.gov.sc.funcionariosuf.dao.UnidadeCiretranCitranDAO;
import br.gov.sc.funcionariosuf.domain.Ciretran;
import br.gov.sc.funcionariosuf.domain.CiretranCitran;
import br.gov.sc.funcionariosuf.domain.Estagiarios;
import br.gov.sc.funcionariosuf.domain.Servidores;
import br.gov.sc.funcionariosuf.domain.Terceirizados;
import br.gov.sc.funcionariosuf.domain.UnidadeCiretranCitran;
import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Setor;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UnidadeCiretranCitranBean implements Serializable {

	private UnidadeCiretranCitran unidadeCiretranCitran;
	private List<UnidadeCiretranCitran> listaUnidadeCiretranCitran;

	private CiretranCitran ciretranCitran;
	private List<CiretranCitran> listaCiretranCitran;
	private List<Ciretran> listaCiretran;
	
	private List<Servidores> listaServidores;
	private List<Terceirizados> listaTerceirizados;
	private List<Estagiarios> listaEstagiarios;

	private Setor setor;
	private List<Setor> setores;

	private List<Estado> Estados;
	private Estado estado;
	private List<Cidade> Cidades;

	@PostConstruct
	public void listar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			this.Estados = estadoDAO.listar("sigla");

			CiretranCitranDAO ciretranCitranDAO = new CiretranCitranDAO();
			listaCiretranCitran = ciretranCitranDAO.listar();

			UnidadeCiretranCitranDAO unidadeDAO = new UnidadeCiretranCitranDAO();
			listaUnidadeCiretranCitran = unidadeDAO.listar();

			CiretranDAO ciretranDAO = new CiretranDAO();
			listaCiretran = ciretranDAO.listar("nome");

			unidadeCiretranCitran = new UnidadeCiretranCitran();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Ciretran Citran.");
			erro.printStackTrace();
		}
	}

	public void viewUnidade(ActionEvent evento) {

		unidadeCiretranCitran = (UnidadeCiretranCitran) evento.getComponent().getAttributes().get("unidadeSelecionado");

		SetorDAO setorDAO = new SetorDAO();
		CidadeDAO municipioDAO = new CidadeDAO();
		
		ServidoresDAO servidoresDAO = new ServidoresDAO();
		listaServidores = servidoresDAO.listarPorUnidadeCodigo(unidadeCiretranCitran.getCiretranCitran(), unidadeCiretranCitran.getSetor());
		
		TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();
		listaTerceirizados = terceirizadosDAO.listarPorUnidadeCodigo(unidadeCiretranCitran.getCiretranCitran(), unidadeCiretranCitran.getSetor());
		
		EstagiariosDAO estagiariosDAO = new EstagiariosDAO();
		listaEstagiarios = estagiariosDAO.listarPorUnidadeCodigo(unidadeCiretranCitran.getCiretranCitran(), unidadeCiretranCitran.getSetor());
		
		System.out.println(listaEstagiarios + " listaEstagiarioslistaEstagiarios");

		Cidades = municipioDAO.buscarPorEstado(this.unidadeCiretranCitran.getEstadoEndereco().getCodigo());
		setores = setorDAO.buscarPorCiretranNome(unidadeCiretranCitran.getCiretran().getNome());
		
		
		
	}

	public void salvar() {
		try {

			UnidadeCiretranCitranDAO ciretranCitranDAO = new UnidadeCiretranCitranDAO();

			ciretranCitranDAO.merge(unidadeCiretranCitran);

			unidadeCiretranCitran = new UnidadeCiretranCitran();

			CiretranDAO ciretranDAO = new CiretranDAO();
			listaCiretran = ciretranDAO.listar();

			listaUnidadeCiretranCitran = ciretranCitranDAO.listar();

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
				"Model:" + ((UnidadeCiretranCitran) event.getData()));

		System.out.println(((UnidadeCiretranCitran) event.getData()).getCiretranCitran().getNome() +"" + ((UnidadeCiretranCitran) event.getData()).getSetor().getSetorNome() + "LISTRA SERVIDORES");

		
		listaServidores = servidoresDAO.listarPorUnidade(((UnidadeCiretranCitran) event.getData()).getCiretranCitran(), ((UnidadeCiretranCitran) event.getData()).getSetor()); 
		listaTerceirizados = terceirizadosDAO.listarPorUnidade(((UnidadeCiretranCitran) event.getData()).getCiretranCitran(), ((UnidadeCiretranCitran) event.getData()).getSetor());
		listaEstagiarios = estagiariosDAO.listarPorUnidade(((UnidadeCiretranCitran) event.getData()).getCiretranCitran(), ((UnidadeCiretranCitran) event.getData()).getSetor());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void excluir(ActionEvent evento) {

		try {
			unidadeCiretranCitran = (UnidadeCiretranCitran) evento.getComponent().getAttributes()
					.get("ciretranCitranSelecionado");

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
			unidadeCiretranCitran = (UnidadeCiretranCitran) evento.getComponent().getAttributes()
					.get("ciretranCitranSelecionado");

			EstadoDAO estadoDAO = new EstadoDAO();
			CidadeDAO municipioDAO = new CidadeDAO();
			SetorDAO setorDAO = new SetorDAO();
			setores = setorDAO.buscarPorCiretranNome(unidadeCiretranCitran.getCiretran().getNome());
			this.Estados = estadoDAO.listar("sigla");
			this.Cidades = municipioDAO.buscarPorEstado(this.unidadeCiretranCitran.getEstadoEndereco().getCodigo());

			UnidadeCiretranCitranDAO ciretranDAO = new UnidadeCiretranCitranDAO();
			listaUnidadeCiretranCitran = ciretranDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar uma Ciretran.");
			erro.printStackTrace();
		}
	}

	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {

			String newcep = unidadeCiretranCitran.getCep().replace(".", "");
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

			unidadeCiretranCitran.setCep(cepEmpresa.getCep());

			unidadeCiretranCitran.setEndereco(cepEmpresa.getEndereco() + ", " + cepEmpresa.getBairro());

			CidadeDAO municipioDAO = new CidadeDAO();
			EstadoDAO estadoDAO = new EstadoDAO();

			System.out.println(estadoDAO.loadSigla(cepEmpresa.getUf())
					+ "estadoDAO.loadSigla(cepEmpresa.getUf())estadoDAO.loadSigla(cepEmpresa.getUf())estadoDAO.loadSigla(cepEmpresa.getUf())");
			unidadeCiretranCitran.setEstadoEndereco(estadoDAO.loadSigla(cepEmpresa.getUf()));

			Cidades = municipioDAO.buscarPorEstado(unidadeCiretranCitran.getEstadoEndereco().getCodigo());

			unidadeCiretranCitran.setMunicipioEndereco(municipioDAO.loadNome(cepEmpresa.getLocalidade()));

			System.out.println(municipioDAO.loadNome(cepEmpresa.getLocalidade()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void popular() {
		try {
			if (this.unidadeCiretranCitran.getEstadoEndereco() != null) {
				CidadeDAO municipioDAO = new CidadeDAO();
				this.Cidades = municipioDAO.buscarPorEstado(this.unidadeCiretranCitran.getEstadoEndereco().getCodigo());
			} else {
				this.Cidades = new ArrayList();
			}
		} catch (NullPointerException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades", new Object[0]);
		}

	}

	public void popularCITRAN() {
		try {
			if (unidadeCiretranCitran.getCiretran() != null) {
				CiretranCitranDAO ciretranCITRANDAO = new CiretranCitranDAO();
				listaCiretranCitran = ciretranCITRANDAO
						.buscarPorCiretran(unidadeCiretranCitran.getCiretran().getCodigo());
				SetorDAO setorDAO = new SetorDAO();
				System.out.println(unidadeCiretranCitran.getCiretran().getNome());
				setores = setorDAO.buscarPorCiretranNome(unidadeCiretranCitran.getCiretran().getNome());
				System.out.println(setores);
			} else {
				Cidades = new ArrayList<>();

			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as CIRETRAN/CITRAN");
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

	public UnidadeCiretranCitran getUnidadeCiretranCitran() {
		return unidadeCiretranCitran;
	}

	public void setUnidadeCiretranCitran(UnidadeCiretranCitran unidadeCiretranCitran) {
		this.unidadeCiretranCitran = unidadeCiretranCitran;
	}

	public List<UnidadeCiretranCitran> getListaUnidadeCiretranCitran() {
		return listaUnidadeCiretranCitran;
	}

	public void setListaUnidadeCiretranCitran(List<UnidadeCiretranCitran> listaUnidadeCiretranCitran) {
		this.listaUnidadeCiretranCitran = listaUnidadeCiretranCitran;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
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

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	
	

}
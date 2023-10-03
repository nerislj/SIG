
package br.gov.sc.funcionariosuf.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.ListUtils;
import org.omnifaces.util.Messages;
import org.primefaces.event.ToggleEvent;

import com.google.gson.Gson;

import br.gov.sc.contrato.dao.CargoTerceirizadoDAO;
import br.gov.sc.contrato.dao.FuncionarioTerceirizadoDAO;
import br.gov.sc.contrato.domain.CargoTerceirizado;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.funcionariosuf.dao.CargoServidoresDAO;
import br.gov.sc.funcionariosuf.dao.EstagiariosDAO;
import br.gov.sc.funcionariosuf.dao.ServidoresDAO;
import br.gov.sc.funcionariosuf.dao.TerceirizadosDAO;
import br.gov.sc.funcionariosuf.dao.UnidadeFuncDAO;
import br.gov.sc.funcionariosuf.domain.CargoServidores;
import br.gov.sc.funcionariosuf.domain.Estagiarios;
import br.gov.sc.funcionariosuf.domain.Servidores;
import br.gov.sc.funcionariosuf.domain.UnidadeFunc;
import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UnidadeFuncBean implements Serializable {

	private UnidadeFunc unidadeFunc;
	private List<UnidadeFunc> listaUnidadeFunc;

	private List<UnidadeFunc> listaTodosFunc;

	private Unidade unidade;
	private List<Unidade> listaUnidade;

	private List<Servidores> listaServidores;

	private Estagiarios estagiario;
	private List<Estagiarios> listaEstagiarios;

	private List<FuncionarioTerceirizado> listaTerceirizados;

	private Setor setor;
	private List<Setor> listasetores;

	private List<Estado> Estados;
	private Estado estado;
	private List<Cidade> Cidades;

	private List<CargoTerceirizado> cargos;

	private List<Unidade> listaUnidadeSelecionada;
	private List<Setor> setores;

	private Usuario usuarioLogado;
	private PessoaFisica pessoa;
	private FuncionarioTerceirizado funcionarioTerceirizado;

	private Servidores servidores;

	private List<CargoServidores> listaCargosServidores;

	private List<FuncionarioTerceirizado> combinedList;

	private List<FuncionarioTerceirizado> combinedList3;

	public List<CargoServidores> getListaCargosServidores() {
		return listaCargosServidores;
	}

	public void setListaCargosServidores(List<CargoServidores> listaCargosServidores) {
		this.listaCargosServidores = listaCargosServidores;
	}

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

			listaTodosFunc = unidadeFuncDAO.listaTodosFuncionarios();

			SetorDAO setorDAO = new SetorDAO();
			listasetores = setorDAO.listar("setorNome");

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			listaUnidade = unidadeDAO.listar("unidadeNome");

			CargoTerceirizadoDAO cargoDAO = new CargoTerceirizadoDAO();
			setCargos(cargoDAO.listar());

			CargoServidoresDAO cargoServidoresDAO = new CargoServidoresDAO();
			listaCargosServidores = cargoServidoresDAO.listar();

			unidadeFunc = new UnidadeFunc();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Ciretran Citran.");
			erro.printStackTrace();
		}
	}

	public void novoTerceirizado() {
		funcionarioTerceirizado = new FuncionarioTerceirizado();
		pessoa = new PessoaFisica();
	}

	public void novoServidor() {
		servidores = new Servidores();
		pessoa = new PessoaFisica();
	}

	public void novoEstagiario() {
		estagiario = new Estagiarios();
		pessoa = new PessoaFisica();
	}

	public void buscarCPF() {
		new FuncionarioTerceirizadoDAO();
		this.pessoa = FuncionarioTerceirizadoDAO.carregarCpf(this.pessoa.getCpf());
		CidadeDAO municipioDAO = new CidadeDAO();
		this.Cidades = municipioDAO.buscarPorEstado(this.pessoa.getEstadoEndereco().getCodigo());
	}

	@SuppressWarnings("static-access")
	public void buscarCPFServidor() {
		ServidoresDAO servidoresDAO = new ServidoresDAO();
		this.pessoa = servidoresDAO.carregarCpf(this.pessoa.getCpf());
		CidadeDAO municipioDAO = new CidadeDAO();
		this.Cidades = municipioDAO.buscarPorEstado(this.pessoa.getEstadoEndereco().getCodigo());
	}

	public void popularUnidades() {
		try {
			if (this.funcionarioTerceirizado.getUnidade() != null) {
				SetorDAO setorDAO = new SetorDAO();
				System.out.println(funcionarioTerceirizado.getUnidade().getCodigo());
				setores = setorDAO
						.buscarPorUnidadeFuncionarioTerceirizados(funcionarioTerceirizado.getUnidade().getCodigo());
				System.out.println(setores);
			} else {
				this.setores = new ArrayList();
			}
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os setores", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void viewUnidade(ActionEvent evento) {

		unidadeFunc = (UnidadeFunc) evento.getComponent().getAttributes().get("unidadeSelecionado");

		CidadeDAO municipioDAO = new CidadeDAO();

		UnidadeDAO unidadeDAO = new UnidadeDAO();
		listaUnidadeSelecionada = unidadeDAO.listarPorUnidadeUsuarioLogado(unidadeFunc.getUnidade().getUnidadeNome());

		SetorDAO setorDAO = new SetorDAO();
		setores = setorDAO.buscarPorUnidade(this.unidadeFunc.getUnidade().getCodigo());

		TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();
		listaTerceirizados = terceirizadosDAO.listarPorUnidade(unidadeFunc.getUnidade());

		ServidoresDAO servidoresDAO = new ServidoresDAO();
		listaServidores = servidoresDAO.listarPorUnidade(unidadeFunc.getUnidade());

		EstagiariosDAO estagiarioDAO = new EstagiariosDAO();
		listaEstagiarios = estagiarioDAO.listarPorUnidade(unidadeFunc.getUnidade());

		if (unidadeFunc.getEstadoEndereco() != null) {

			Cidades = municipioDAO.buscarPorEstado(this.unidadeFunc.getEstadoEndereco().getCodigo());

		}

	}

	public void salvarEstagiario() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			this.setUsuarioLogado((Usuario) sessao.getAttribute("usuario"));

			EstagiariosDAO estagiariosDAO = new EstagiariosDAO();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(this.pessoa);
			this.pessoa = PessoaDAO.carregarCpf(this.pessoa.getCpf());

			estagiario.setUsuarioCadastro(usuarioLogado);
			estagiario.setPessoa(pessoa);
			estagiario.setDataCadastro(new Date());

			estagiario.setUnidade(unidadeFunc.getUnidade());
			estagiario.setUnidadeFunc(unidadeFunc);

			estagiariosDAO.merge(estagiario);

			estagiario = new Estagiarios();

			Messages.addGlobalInfo("Estagiário cadastrado com Sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Estagiário.");
			erro.printStackTrace();
		}
	}

	public void salvarTerceirizado() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");
			FuncionarioTerceirizadoDAO funcionarioDAO = new FuncionarioTerceirizadoDAO();
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(this.pessoa);
			this.pessoa = PessoaDAO.carregarCpf(this.pessoa.getCpf());
			this.funcionarioTerceirizado.setUsuarioCadastro((Usuario) null);
			this.funcionarioTerceirizado.setDataCadastro(new Date());
			this.funcionarioTerceirizado.setPessoa(this.pessoa);
			this.funcionarioTerceirizado.setUsuarioCadastro(this.usuarioLogado);
			funcionarioTerceirizado.setUnidade(unidadeFunc.getUnidade());
			funcionarioDAO.merge(this.funcionarioTerceirizado);
			this.funcionarioTerceirizado = new FuncionarioTerceirizado();

			TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();

			listaTerceirizados = terceirizadosDAO.listarPorUnidade(unidadeFunc.getUnidade());

			Messages.addGlobalInfo("Funcionário cadastrado com Sucesso!", new Object[0]);
		} catch (RuntimeException var4) {
			Messages.addGlobalError("Funcionário já pertence a outra Unidade.", new Object[0]);
			var4.printStackTrace();
		}

	}

	public void salvarServidor() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			this.setUsuarioLogado((Usuario) sessao.getAttribute("usuario"));

			ServidoresDAO servidoresDAO = new ServidoresDAO();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(this.pessoa);
			this.pessoa = PessoaDAO.carregarCpf(this.pessoa.getCpf());

			servidores.setUsuarioCadastro(usuarioLogado);
			servidores.setPessoa(pessoa);
			servidores.setDataCadastro(new Date());
			servidores.setUnidadeFunc(unidadeFunc);
			servidores.setUnidade(unidadeFunc.getUnidade());
			System.out.println("SERVIDORES UNIDADE " + unidadeFunc);

			// servidores.setUnidadeFunc(unidadeFunc.getUnidadeFunc());
			// servidores.setUnidadeCiretranCitran(unidadeCiretranCitran);

			servidoresDAO.merge(servidores);

			servidores = new Servidores();

			Messages.addGlobalInfo("Servidores cadastrado com Sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Servidores.");
			erro.printStackTrace();
		}
	}

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

		EstagiariosDAO estagiariosDAO = new EstagiariosDAO();

		TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row State " + event.getVisibility(),
				"Model:" + ((UnidadeFunc) event.getData()));

		System.out.println(((UnidadeFunc) event.getData()).getUnidade().getUnidadeNome() + ""
				+ ((UnidadeFunc) event.getData()).getUnidade().getCodigo() + "LISTRA SERVIDORES");

		listaServidores = servidoresDAO.listarPorUnidade(((UnidadeFunc) event.getData()).getUnidade());

		listaTerceirizados = terceirizadosDAO.listarPorUnidade(((UnidadeFunc) event.getData()).getUnidade());

		listaEstagiarios = estagiariosDAO.listarPorUnidade(((UnidadeFunc) event.getData()).getUnidade());

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
			unidadeFunc = (UnidadeFunc) evento.getComponent().getAttributes().get("unidadeSelecionado");

			System.out.println("unidadeFunc EDITAR " + unidadeFunc);

			EstadoDAO estadoDAO = new EstadoDAO();
			CidadeDAO municipioDAO = new CidadeDAO();

			this.Estados = estadoDAO.listar("sigla");
			if (unidadeFunc.getEstadoEndereco() != null) {
				this.Cidades = municipioDAO.buscarPorEstado(this.unidadeFunc.getEstadoEndereco().getCodigo());
			}
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

	@SuppressWarnings("unchecked")
	public void popularTodosFuncionarios() {

		FuncionarioTerceirizadoDAO terceirizadosDAO = new FuncionarioTerceirizadoDAO();
		listaTerceirizados = terceirizadosDAO.listar();

		ServidoresDAO servidoresDAO = new ServidoresDAO();
		listaServidores = servidoresDAO.listar();

		EstagiariosDAO estagiarioDAO = new EstagiariosDAO();
		listaEstagiarios = estagiarioDAO.listar();

		combinedList = (List<FuncionarioTerceirizado>) Stream.of(listaTerceirizados, listaServidores)
				.flatMap(x -> x.stream()).collect(Collectors.toList());

		combinedList3 = (List<FuncionarioTerceirizado>) Stream.of(combinedList, listaEstagiarios)
				.flatMap(x -> x.stream()).collect(Collectors.toList());

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

	public List<FuncionarioTerceirizado> getListaTerceirizados() {
		return listaTerceirizados;
	}

	public void setListaTerceirizados(List<FuncionarioTerceirizado> listaTerceirizados) {
		this.listaTerceirizados = listaTerceirizados;
	}

	public List<CargoTerceirizado> getCargos() {
		return cargos;
	}

	public void setCargos(List<CargoTerceirizado> cargos) {
		this.cargos = cargos;
	}

	public List<Unidade> getListaUnidadeSelecionada() {
		return listaUnidadeSelecionada;
	}

	public void setListaUnidadeSelecionada(List<Unidade> listaUnidadeSelecionada) {
		this.listaUnidadeSelecionada = listaUnidadeSelecionada;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public FuncionarioTerceirizado getFuncionarioTerceirizado() {
		return funcionarioTerceirizado;
	}

	public void setFuncionarioTerceirizado(FuncionarioTerceirizado funcionarioTerceirizado) {
		this.funcionarioTerceirizado = funcionarioTerceirizado;
	}

	public Servidores getServidores() {
		return servidores;
	}

	public void setServidores(Servidores servidores) {
		this.servidores = servidores;
	}

	public Estagiarios getEstagiario() {
		return estagiario;
	}

	public void setEstagiario(Estagiarios estagiario) {
		this.estagiario = estagiario;
	}

	public List<UnidadeFunc> getListaTodosFunc() {
		return listaTodosFunc;
	}

	public void setListaTodosFunc(List<UnidadeFunc> listaTodosFunc) {
		this.listaTodosFunc = listaTodosFunc;
	}

	public List<FuncionarioTerceirizado> getCombinedList() {
		return combinedList;
	}

	public void setCombinedList(List<FuncionarioTerceirizado> combinedList) {
		this.combinedList = combinedList;
	}

	public List<FuncionarioTerceirizado> getCombinedList3() {
		return combinedList3;
	}

	public void setCombinedList3(List<FuncionarioTerceirizado> combinedList3) {
		this.combinedList3 = combinedList3;
	}

}
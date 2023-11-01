package br.gov.sc.funcionariosuf.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.FilterMeta;

import com.google.gson.Gson;

import br.gov.sc.contrato.dao.CargoTerceirizadoDAO;
import br.gov.sc.contrato.dao.ContratoRelacaoDAO;
import br.gov.sc.contrato.dao.ContratoTerceirizadoDAO;
import br.gov.sc.contrato.dao.FuncionarioTerceirizadoDAO;
import br.gov.sc.contrato.dao.NivelTerceirizadoDAO;
import br.gov.sc.contrato.dao.UserClaimsContratoDAO;
import br.gov.sc.contrato.domain.CargoTerceirizado;
import br.gov.sc.contrato.domain.ContratoHistFuncionario;
import br.gov.sc.contrato.domain.ContratoRelacao;
import br.gov.sc.contrato.domain.ContratoTerceirizado;
import br.gov.sc.contrato.domain.EventoTerceirizado;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.contrato.domain.NivelTerceirizado;
import br.gov.sc.contrato.domain.UserClaimsContrato;
import br.gov.sc.funcionariosuf.dao.CargoServidoresDAO;
import br.gov.sc.funcionariosuf.dao.EstagiariosDAO;
import br.gov.sc.funcionariosuf.dao.FuncaoServidoresDAO;
import br.gov.sc.funcionariosuf.dao.OrigemEstagiariosDAO;
import br.gov.sc.funcionariosuf.dao.ServidoresDAO;
import br.gov.sc.funcionariosuf.dao.TerceirizadosDAO;
import br.gov.sc.funcionariosuf.dao.UnidadeFuncDAO;
import br.gov.sc.funcionariosuf.domain.CargoServidores;
import br.gov.sc.funcionariosuf.domain.Estagiarios;
import br.gov.sc.funcionariosuf.domain.FuncaoServidores;
import br.gov.sc.funcionariosuf.domain.OrigemEstagiarios;
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
import util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UnidadeFuncBean implements Serializable {

	private UnidadeFunc unidadeFunc;
	private List<UnidadeFunc> listaUnidadeFunc;

	private List<UnidadeFunc> listaTodosFunc;

	private List<UnidadeFunc> filteredUnidades;
	private List<FilterMeta> filterBy;

	private Unidade unidade;
	private List<Unidade> listaUnidade;

	private List<Servidores> listaServidores;

	private Estagiarios estagiario;
	private List<Estagiarios> listaEstagiarios;

	private List<FuncionarioTerceirizado> listaTerceirizados;
	private List<ContratoRelacao> listaTerceirizadosAtivos;

	private Setor setor;
	private List<Setor> listasetores;

	private List<Estado> Estados;
	private Estado estado;
	private List<Cidade> Cidades;

	private List<CargoTerceirizado> cargos;
	private List<NivelTerceirizado> niveis;

	private List<OrigemEstagiarios> origensEstagiarios;

	private List<Unidade> listaUnidadeSelecionada;
	private List<Setor> setores;

	private List<FuncaoServidores> funcoesServidores;

	private Usuario usuarioLogado;
	private PessoaFisica pessoa;
	private FuncionarioTerceirizado funcionarioTerceirizado;
	private FuncionarioTerceirizado funcionarioSelecionado;

	private Servidores servidores;

	private List<CargoServidores> listaCargosServidores;

	private List<String> combinedList;

	private List<String> combinedList3;

	private ContratoHistFuncionario contratoHistFuncionario;
	private ContratoRelacao contratoRelacao;
	private ContratoTerceirizado contratoTerceirizado;
	private List<ContratoTerceirizado> listaContratosTerceirizados;
	private List<FuncionarioTerceirizado> listaFuncionarios;

	private List<UserClaimsContrato> listaUserClaims;
	private UserClaimsContrato userClaim;
	private boolean mostrarModuloParaGerentes;
	private boolean acaoRelatorioExcelTodasUnidades;

	public void mostrarNovoServidor() {
		servidores = new Servidores();
		pessoa = new PessoaFisica();

		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('newServidor').show();");
	}

	public void mostrarNovoTerceirizado() {
		funcionarioTerceirizado = new FuncionarioTerceirizado();
		pessoa = new PessoaFisica();
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('newTerceirizadoUnidade').show();");
	}

	public void mostrarNovoEstagiario() {
		estagiario = new Estagiarios();
		pessoa = new PessoaFisica();
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('newEstagiario').show();");
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

	public void filterListener2() {
		try {
			if (filteredUnidades != null) {
				// then filter is active, do something...
				ArrayList<String> codigosUnidades = new ArrayList<String>();
				for (int posicao = 0; posicao < filteredUnidades.size(); posicao++) {

					codigosUnidades.add(filteredUnidades.get(posicao).getUnidade().getUnidadeNome());

				}

				FuncionarioTerceirizadoDAO terceirizadosDAO = new FuncionarioTerceirizadoDAO();
				// listaTerceirizados = terceirizadosDAO.listarUnidades(codigosUnidades);

				listaTerceirizadosAtivos = terceirizadosDAO.listarContratoRelacaoFuncionariosAtivos(codigosUnidades);

				System.out.println();

				ServidoresDAO servidoresDAO = new ServidoresDAO();
				listaServidores = servidoresDAO.listarUnidades(codigosUnidades);

				EstagiariosDAO estagiarioDAO = new EstagiariosDAO();
				listaEstagiarios = estagiarioDAO.listarUnidades(codigosUnidades);

			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void listar() {
		try {

			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			filterBy = new ArrayList<>();

			EstadoDAO estadoDAO = new EstadoDAO();
			this.Estados = estadoDAO.listar("sigla");

			ContratoTerceirizadoDAO contratoDAO = new ContratoTerceirizadoDAO();
			listaContratosTerceirizados = contratoDAO.listarPorOrdemASC();

			// listaTodosFunc = unidadeFuncDAO.listaTodosFuncionarios();

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			listaUnidade = unidadeDAO.listar("unidadeNome");
			CargoServidoresDAO cargoServidoresDAO = new CargoServidoresDAO();
			listaCargosServidores = cargoServidoresDAO.listar("descricao");
			CargoTerceirizadoDAO cargoDAO = new CargoTerceirizadoDAO();
			cargos = cargoDAO.listar("descricao");

			NivelTerceirizadoDAO nivelDAO = new NivelTerceirizadoDAO();
			niveis = nivelDAO.listar("descricao");

			OrigemEstagiariosDAO origemDAO = new OrigemEstagiariosDAO();
			origensEstagiarios = origemDAO.listar("descricao");

			FuncaoServidoresDAO funDAO = new FuncaoServidoresDAO();
			funcoesServidores = funDAO.listar("descricao");

			contratoRelacao = new ContratoRelacao();
			contratoHistFuncionario = new ContratoHistFuncionario();
			pessoa = new PessoaFisica();

			mostrarModuloParaGerentes = false;

			// CLAIMS
			UnidadeFuncDAO unidadeFuncDAO = new UnidadeFuncDAO();
			UserClaimsContratoDAO userClaimDAO = new UserClaimsContratoDAO();

			FuncionarioTerceirizadoDAO funcionarioDAO = new FuncionarioTerceirizadoDAO();

			if (usuarioLogado.getNivelAcesso().getCodigo() == 1) {

				mostrarModuloParaGerentes = true;
				acaoRelatorioExcelTodasUnidades = true;

				listaUnidadeFunc = unidadeFuncDAO.listar();

				filteredUnidades = listaUnidadeFunc;

				ContratoRelacaoDAO relacaoDAO = new ContratoRelacaoDAO();
				listaTerceirizadosAtivos = relacaoDAO.listar();

				ServidoresDAO servidoresDAO = new ServidoresDAO();
				listaServidores = servidoresDAO.listar();

				EstagiariosDAO estagiarioDAO = new EstagiariosDAO();
				listaEstagiarios = estagiarioDAO.listar();

				listaFuncionarios = funcionarioDAO.listarPorTodos();

				SetorDAO setorDAO = new SetorDAO();
				listasetores = setorDAO.listar("setorNome");

			}

			if (usuarioLogado.getUnidade().getUnidadeNome().contains("CIRETRAN")
					|| usuarioLogado.getUnidade().getUnidadeNome().contains("CITRAN")
					|| usuarioLogado.getUnidade().getUnidadeNome().contains("Departamento Estadual de Trânsito")) {
				if (usuarioLogado.getNivelAcesso().getNivel().contains("GERÊNCIA")) {

					mostrarModuloParaGerentes = true;
					acaoRelatorioExcelTodasUnidades = false;

					listaUserClaims = userClaimDAO.listarPorUsuarioLogado(usuarioLogado);
					setUserClaim(new UserClaimsContrato());

					ArrayList<String> codigosUnidades = new ArrayList<String>();
					for (int posicao = 0; posicao < listaUserClaims.size(); posicao++) {

						codigosUnidades.add(listaUserClaims.get(posicao).getUnidade().getUnidadeNome());

					}

					listaUnidadeFunc = unidadeFuncDAO.listarPorClaims(codigosUnidades);

					filteredUnidades = listaUnidadeFunc;

					ContratoRelacaoDAO relacaoDAO = new ContratoRelacaoDAO();
					listaTerceirizadosAtivos = relacaoDAO.listarPorClaims(codigosUnidades);

					ServidoresDAO servidoresDAO = new ServidoresDAO();
					listaServidores = servidoresDAO.listarPorClaims(codigosUnidades);

					EstagiariosDAO estagiarioDAO = new EstagiariosDAO();
					listaEstagiarios = estagiarioDAO.listarPorClaims(codigosUnidades);

					listaFuncionarios = funcionarioDAO.listarPorClaims(codigosUnidades);

					SetorDAO setorDAO = new SetorDAO();
					listasetores = setorDAO.listar("setorNome");

					// listaContratosTerceirizadosPorContratoEmpresa =
					// contratoDAO.listarPorContratoEmpresa();

				}

			}

			if (usuarioLogado.getSetor().getSetorNome().contains("Gabinete da Presidência") || usuarioLogado.getSetor()
					.getSetorNome().contains("Coordenadoria Geral das CIRETRANs e CITRANs")) {

				mostrarModuloParaGerentes = true;
				acaoRelatorioExcelTodasUnidades = true;

				listaUnidadeFunc = unidadeFuncDAO.listar();

				filteredUnidades = listaUnidadeFunc;

				ContratoRelacaoDAO relacaoDAO = new ContratoRelacaoDAO();
				listaTerceirizadosAtivos = relacaoDAO.listar();

				ServidoresDAO servidoresDAO = new ServidoresDAO();
				listaServidores = servidoresDAO.listar();

				EstagiariosDAO estagiarioDAO = new EstagiariosDAO();
				listaEstagiarios = estagiarioDAO.listar();

				listaFuncionarios = funcionarioDAO.listarPorTodos();

				SetorDAO setorDAO = new SetorDAO();
				listasetores = setorDAO.listar("setorNome");

			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Unidades x Funcinários.");
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

		servidores = new Servidores();
		funcionarioTerceirizado = new FuncionarioTerceirizado();
		estagiario = new Estagiarios();
		pessoa = new PessoaFisica();
		funcionarioSelecionado = new FuncionarioTerceirizado();
		contratoTerceirizado = new ContratoTerceirizado();

		ContratoTerceirizadoDAO contratoDAO = new ContratoTerceirizadoDAO();

		unidadeFunc = (UnidadeFunc) evento.getComponent().getAttributes().get("unidadeSelecionado");

		listaContratosTerceirizados = contratoDAO.listarPorUnidadeView(unidadeFunc.getUnidade().getUnidadeNome());

		CidadeDAO municipioDAO = new CidadeDAO();

		UnidadeDAO unidadeDAO = new UnidadeDAO();
		listaUnidadeSelecionada = unidadeDAO.listarPorUnidadeUsuarioLogado(unidadeFunc.getUnidade().getUnidadeNome());

		SetorDAO setorDAO = new SetorDAO();
		setores = setorDAO.buscarPorUnidade(this.unidadeFunc.getUnidade().getCodigo());

		TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();
		listaTerceirizadosAtivos = terceirizadosDAO.listarContratoRelacaoFuncionariosAtivos(unidadeFunc.getUnidade());
		// listaTerceirizados =
		// terceirizadosDAO.listarPorUnidade(unidadeFunc.getUnidade());

		ServidoresDAO servidoresDAO = new ServidoresDAO();
		listaServidores = servidoresDAO.listarPorUnidade(unidadeFunc.getUnidade());

		EstagiariosDAO estagiarioDAO = new EstagiariosDAO();
		listaEstagiarios = estagiarioDAO.listarPorUnidade(unidadeFunc.getUnidade());

		if (unidadeFunc.getEstadoEndereco() != null) {

			Cidades = municipioDAO.buscarPorEstado(this.unidadeFunc.getEstadoEndereco().getCodigo());

		}

		CargoTerceirizadoDAO cargoDAO = new CargoTerceirizadoDAO();
		cargos = cargoDAO.listar();

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
			EstagiariosDAO estaDAO = new EstagiariosDAO();
			Estagiarios est = estaDAO.carregaFuncionario(pessoa.getCpf());
			Messages.addGlobalError("Estagiário já vínculado a Unidade " + est.getUnidade().getUnidadeNome() + "!");
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

			listaTerceirizadosAtivos = terceirizadosDAO
					.listarContratoRelacaoFuncionariosAtivos(unidadeFunc.getUnidade());
			// listaTerceirizados =
			// terceirizadosDAO.listarPorUnidade(unidadeFunc.getUnidade());

			Messages.addGlobalInfo("Funcionário cadastrado com Sucesso!", new Object[0]);
		} catch (RuntimeException var4) {
			FuncionarioTerceirizadoDAO estaDAO = new FuncionarioTerceirizadoDAO();
			ContratoRelacao est = estaDAO.carregaFuncionario(pessoa.getCpf());
			Messages.addGlobalError("Terceirizado já vínculado a Unidade "
					+ est.getFuncionarioTerceirizado().getUnidade().getUnidadeNome() + "!");
			var4.printStackTrace();
		}

	}

	public void salvarTerceirizadoUnidade() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			FuncionarioTerceirizadoDAO funcionarioDAO = new FuncionarioTerceirizadoDAO();

			System.out.println("valor " + funcionarioTerceirizado);

			ContratoRelacaoDAO relacaoDAO = new ContratoRelacaoDAO();
			int totalFuncionarios = relacaoDAO.listarPorContratoTerceirizadoObject(contratoTerceirizado).size();

			if (ContratoRelacaoDAO.carregaFuncionario(funcionarioSelecionado) == null) {

				if (funcionarioSelecionado.getCargoTerceirizado().equals(contratoTerceirizado.getCargoTerceirizado())) {
					if (totalFuncionarios < contratoTerceirizado.getQtdFuncionarios()) {
						contratoRelacao.setDataCadastro(new Date());
						contratoRelacao.setUsuarioCadastro(usuarioLogado);
						contratoRelacao.setContratoTerceirizado(contratoTerceirizado);
						contratoRelacao.setFuncionarioTerceirizado(funcionarioSelecionado);
						contratoRelacao.setStatus("Ativo");
						relacaoDAO.merge(contratoRelacao);

						funcionarioSelecionado.setTipoEvento((EventoTerceirizado) null);
						funcionarioDAO.merge(funcionarioSelecionado);
						contratoHistFuncionario.setStatus(contratoRelacao.getStatus());
						contratoHistFuncionario.setObservacao(null);
						relacaoDAO.salvarFuncionarioHist(contratoRelacao, contratoHistFuncionario);

						Messages.addGlobalInfo("Funcionário cadastrado com Sucesso!", new Object[0]);
					} else {
						Messages.addGlobalError("Quantidade máxima de Funcionários atingida para esse contrato.",
								new Object[0]);
					}
				} else {
					Messages.addGlobalError("Cargo do Funcionário não compatível com a Atividade do Contrato.",
							new Object[0]);
				}
			} else {

				contratoRelacao = relacaoDAO.carregaFuncionario(funcionarioSelecionado);

				Messages.addGlobalError("Funcionário já vínculado ao contrato nº "
						+ contratoRelacao.getContratoTerceirizado().getnContrato() + " - Unidade "
						+ contratoRelacao.getContratoTerceirizado().getUnidade().getUnidadeNome() + " - Município "
						+ contratoRelacao.getContratoTerceirizado().getMunicipio().getNome());

			}

			this.funcionarioTerceirizado = new FuncionarioTerceirizado();

			TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();

			listaTerceirizadosAtivos = terceirizadosDAO
					.listarContratoRelacaoFuncionariosAtivos(unidadeFunc.getUnidade());
			// listaTerceirizados =
			// terceirizadosDAO.listarPorUnidade(unidadeFunc.getUnidade());

			// Messages.addGlobalInfo("Funcionário cadastrado com Sucesso!", new Object[0]);

			// funcionarioSelecionado = new FuncionarioTerceirizado();
			// contratoTerceirizado = new ContratoTerceirizado();
		} catch (RuntimeException var4) {
			Messages.addGlobalError("Funcionário já pertence a outra Unidade.", new Object[0]);
			var4.printStackTrace();
		}

	}

	public void salvarServidor() throws IOException {
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

			// servidores = new Servidores();

			Messages.addGlobalInfo("Servidor cadastrado com Sucesso!");

		} catch (RuntimeException erro) {
			ServidoresDAO servidoresDAO = new ServidoresDAO();
			Servidores serv = servidoresDAO.carregaFuncionario(pessoa.getCpf());
			System.out.println("VENHO MAIS NÃO MOSTROU");
			Messages.addGlobalError("Servidor já vínculado a Unidade " + serv.getUnidade().getUnidadeNome() + "!");

			erro.printStackTrace();
		}
	}

	public void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	@SuppressWarnings("unlikely-arg-type")
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

		// listaTerceirizadosAtivos =
		// terceirizadosDAO.listarContratoRelacaoFuncionariosAtivos(unidadeFunc.getUnidade());
		listaTerceirizados = terceirizadosDAO.listarPorUnidade(((UnidadeFunc) event.getData()).getUnidade());
		listaTerceirizadosAtivos = terceirizadosDAO
				.listarContratoRelacaoFuncionariosAtivos(((UnidadeFunc) event.getData()).getUnidade());

		listaEstagiarios = estagiariosDAO.listarPorUnidade(((UnidadeFunc) event.getData()).getUnidade());

		unidadeFunc = (UnidadeFunc) event.getData();

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

	public void excluirEstagiario(ActionEvent evento) {

		try {
			estagiario = (Estagiarios) evento.getComponent().getAttributes().get("estagiarioSelecionado");

			EstagiariosDAO estaFuncDAO = new EstagiariosDAO();
			estaFuncDAO.excluir(estagiario);

			listaEstagiarios = estaFuncDAO.listarPorUnidade(unidadeFunc.getUnidade());

			Messages.addGlobalInfo("Estagiário excluído com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Estagiário.");
			erro.printStackTrace();
		}
	}

	public void excluirServidor(ActionEvent evento) {

		try {
			servidores = (Servidores) evento.getComponent().getAttributes().get("servidoresSelecionado");

			ServidoresDAO servDAO = new ServidoresDAO();
			servDAO.excluir(servidores);

			listaServidores = servDAO.listarPorUnidade(unidadeFunc.getUnidade());

			Messages.addGlobalInfo("Servidor excluído com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Estagiário.");
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

	@SuppressWarnings("static-access")
	public void editarServidor(ActionEvent evento) {
		try {
			this.novoServidor();

			servidores = (Servidores) evento.getComponent().getAttributes().get("servidoresSelecionado");

			System.out.println("unidadeFunc EDITAR " + servidores);

			ServidoresDAO servidoresDAO = new ServidoresDAO();

			pessoa = servidoresDAO.carregarCpf(servidores.getPessoa().getCpf());

			unidadeFunc = servidores.getUnidadeFunc();

			SetorDAO setorDAO = new SetorDAO();
			setores = setorDAO.buscarPorUnidade(servidores.getUnidade().getCodigo());

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
			// pessoa = new PessoaFisica();

			String newcep = pessoa.getCep().replace(".", "");
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

			pessoa.setCep(cepEmpresa.getCep());

			pessoa.setEndereco(cepEmpresa.getEndereco() + ", " + cepEmpresa.getBairro());

			CidadeDAO municipioDAO = new CidadeDAO();
			EstadoDAO estadoDAO = new EstadoDAO();

			System.out.println(estadoDAO.loadSigla(cepEmpresa.getUf())
					+ "estadoDAO.loadSigla(cepEmpresa.getUf())estadoDAO.loadSigla(cepEmpresa.getUf())estadoDAO.loadSigla(cepEmpresa.getUf())");
			pessoa.setEstadoEndereco(estadoDAO.loadSigla(cepEmpresa.getUf()));

			Cidades = municipioDAO.buscarPorEstado(pessoa.getEstadoEndereco().getCodigo());

			pessoa.setMunicipioEndereco(municipioDAO.loadNome(cepEmpresa.getLocalidade()));

			System.out.println(municipioDAO.loadNome(cepEmpresa.getLocalidade()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pesquisaCepUnidade(AjaxBehaviorEvent event) {
		try {
			// pessoa = new PessoaFisica();

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

			Cidades = municipioDAO.buscarPorEstado(pessoa.getEstadoEndereco().getCodigo());

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

	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public void popularTodosFuncionarios() {

		ServidoresDAO servidoresDAO = new ServidoresDAO();
		listaServidores = servidoresDAO.listar();

		EstagiariosDAO estagiarioDAO = new EstagiariosDAO();
		listaEstagiarios = estagiarioDAO.listar();

		ContratoRelacaoDAO contratoDAO = new ContratoRelacaoDAO();

		// PEGA FUNCIONARIOS TERCEIRIZADOS ATIVOS
		List<FuncionarioTerceirizado> expected = contratoDAO.listarPorTodos();

		System.out.println(expected.size());

		combinedList = (List<String>) Stream.of(expected, listaServidores).flatMap(x -> x.stream())
				.collect(Collectors.toList());

		combinedList3 = (List<String>) Stream.of(combinedList, listaEstagiarios).flatMap(x -> x.stream())
				.collect(Collectors.toList());

	}

	public void imprimirRelatorio(ActionEvent evento) {

		try {
			unidadeFunc = (UnidadeFunc) evento.getComponent().getAttributes().get("unidadeSelecionado");

			System.out.println(unidadeFunc.getUnidade() + " VALOR P RELATORIO");

			JSFUtil.redirect("../ImprimirRelatorio?rlt_nome=relatoriounidade" + "&unidadeId="
					+ unidadeFunc.getUnidade().getCodigo());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException nulo) {
			throw new NullPointerException(
					"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
		}

	}

	public void novoTerceirizadoModulo() {

		try {
			JSFUtil.redirect("/pages/contrato/controlecontrato.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void imprimirRelatorioExcel(ActionEvent evento) {

		try {
			unidadeFunc = (UnidadeFunc) evento.getComponent().getAttributes().get("unidadeSelecionado");

			System.out.println(unidadeFunc.getUnidade() + " VALOR P RELATORIO");

			JSFUtil.redirect("../ImprimirRelatorioIreportExcel?rlt_nome=relatoriounidadeexcel" + "&unidadeId="
					+ unidadeFunc.getUnidade().getCodigo());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException nulo) {
			throw new NullPointerException(
					"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
		}

	}

	public void testFunc(ActionEvent evento) {
		unidadeFunc = (UnidadeFunc) evento.getComponent().getAttributes().get("unidadeSelecionado");

		try {
			JSFUtil.redirect("../ImprimirRelatorioUnidadesXFuncionariosExcel?" + "unidadeId="
					+ unidadeFunc.getUnidade().getCodigo());
			this.refresh();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void relatorioExcelGeral(ActionEvent evento) {

		try {
			JSFUtil.redirect("../ImprimirRelatorioUnidadesXFuncionariosTodosExcel");
			this.refresh();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public List<String> getCombinedList() {
		return combinedList;
	}

	public void setCombinedList(List<String> combinedList) {
		this.combinedList = combinedList;
	}

	public List<String> getCombinedList3() {
		return combinedList3;
	}

	public void setCombinedList3(List<String> combinedList3) {
		this.combinedList3 = combinedList3;
	}

	public List<UnidadeFunc> getFilteredUnidades() {
		return filteredUnidades;
	}

	public void setFilteredUnidades(List<UnidadeFunc> filteredUnidades) {
		this.filteredUnidades = filteredUnidades;
	}

	public List<ContratoRelacao> getListaTerceirizadosAtivos() {
		return listaTerceirizadosAtivos;
	}

	public void setListaTerceirizadosAtivos(List<ContratoRelacao> listaTerceirizadosAtivos) {
		this.listaTerceirizadosAtivos = listaTerceirizadosAtivos;
	}

	public void setFilterBy(List<FilterMeta> filterBy) {
		this.filterBy = filterBy;
	}

	public UserClaimsContrato getUserClaim() {
		return userClaim;
	}

	public void setUserClaim(UserClaimsContrato userClaim) {
		this.userClaim = userClaim;
	}

	public boolean isMostrarModuloParaGerentes() {
		return mostrarModuloParaGerentes;
	}

	public void setMostrarModuloParaGerentes(boolean mostrarModuloParaGerentes) {
		this.mostrarModuloParaGerentes = mostrarModuloParaGerentes;
	}

	public boolean isAcaoRelatorioExcelTodasUnidades() {
		return acaoRelatorioExcelTodasUnidades;
	}

	public void setAcaoRelatorioExcelTodasUnidades(boolean acaoRelatorioExcelTodasUnidades) {
		this.acaoRelatorioExcelTodasUnidades = acaoRelatorioExcelTodasUnidades;
	}

	public ContratoTerceirizado getContratoTerceirizado() {
		return contratoTerceirizado;
	}

	public void setContratoTerceirizado(ContratoTerceirizado contratoTerceirizado) {
		this.contratoTerceirizado = contratoTerceirizado;
	}

	public List<ContratoTerceirizado> getListaContratosTerceirizados() {
		return listaContratosTerceirizados;
	}

	public void setListaContratosTerceirizados(List<ContratoTerceirizado> listaContratosTerceirizados) {
		this.listaContratosTerceirizados = listaContratosTerceirizados;
	}

	public ContratoRelacao getContratoRelacao() {
		return contratoRelacao;
	}

	public void setContratoRelacao(ContratoRelacao contratoRelacao) {
		this.contratoRelacao = contratoRelacao;
	}

	public ContratoHistFuncionario getContratoHistFuncionario() {
		return contratoHistFuncionario;
	}

	public void setContratoHistFuncionario(ContratoHistFuncionario contratoHistFuncionario) {
		this.contratoHistFuncionario = contratoHistFuncionario;
	}

	public List<FuncionarioTerceirizado> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<FuncionarioTerceirizado> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public FuncionarioTerceirizado getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}

	public void setFuncionarioSelecionado(FuncionarioTerceirizado funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

	public List<UserClaimsContrato> getListaUserClaims() {
		return listaUserClaims;
	}

	public void setListaUserClaims(List<UserClaimsContrato> listaUserClaims) {
		this.listaUserClaims = listaUserClaims;
	}

	public List<CargoServidores> getListaCargosServidores() {
		return listaCargosServidores;
	}

	public void setListaCargosServidores(List<CargoServidores> listaCargosServidores) {
		this.listaCargosServidores = listaCargosServidores;
	}

	public List<FilterMeta> getFilterBy() {
		return filterBy;
	}

	public List<NivelTerceirizado> getNiveis() {
		return niveis;
	}

	public void setNiveis(List<NivelTerceirizado> niveis) {
		this.niveis = niveis;
	}

	public List<OrigemEstagiarios> getOrigensEstagiarios() {
		return origensEstagiarios;
	}

	public void setOrigensEstagiarios(List<OrigemEstagiarios> origensEstagiarios) {
		this.origensEstagiarios = origensEstagiarios;
	}

	public List<FuncaoServidores> getFuncoesServidores() {
		return funcoesServidores;
	}

	public void setFuncoesServidores(List<FuncaoServidores> funcoesServidores) {
		this.funcoesServidores = funcoesServidores;
	}
}
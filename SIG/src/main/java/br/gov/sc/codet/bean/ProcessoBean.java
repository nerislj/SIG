package br.gov.sc.codet.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.exolab.castor.types.DateTime;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;

import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.cetran.domain.ProcessoCetran;
import br.gov.sc.cetran.domain.Requerente;
import br.gov.sc.codet.dao.FasesProcessoDAO;
import br.gov.sc.codet.dao.HistoricoProcessoDAO;
import br.gov.sc.codet.dao.NomenclaturaProcessoDAO;
import br.gov.sc.codet.dao.PartesProcessoDAO;
import br.gov.sc.codet.dao.PenalidadeProcessoDAO;
import br.gov.sc.codet.dao.ProcessoDAO;
import br.gov.sc.codet.dao.SetorAtualDAO;
import br.gov.sc.codet.dao.SituacaoProcessoDAO;
import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.HistoricoProcessoCODET;
import br.gov.sc.codet.domain.NomenclaturaProcesso;
import br.gov.sc.codet.domain.PartesProcesso;
import br.gov.sc.codet.domain.PenalidadeProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.codet.domain.SetorAtual;
import br.gov.sc.codet.domain.SituacaoProcesso;
import br.gov.sc.contrato.domain.EmpresaTerceirizada;
import br.gov.sc.geapo.dao.MaterialTipoDAO;
import br.gov.sc.geapo.domain.MaterialTipo;
import br.gov.sc.sgi.dao.CredenciadoDAO;
import br.gov.sc.sgi.dao.CredenciadoEmpDAO;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.domain.Usuario;
import util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProcessoBean implements Serializable {

	private Processo processo;
	private Processo processoDoBanco;
	private List<Processo> listaProcessos;

	private PartesProcesso parteProcesso;
	private FasesProcesso fasesProcesso;
	private HistoricoProcessoCODET historicoProcesso;
	private SetorAtual setorAtualCodigo;

	private List<NomenclaturaProcesso> listaNomemclaturas;
	private List<SituacaoProcesso> listaSituacoes;
	private List<SetorAtual> listaSetorAtuais;
	private List<CredenciadoEmp> listaEmpresasCredenciadas;
	private List<Credenciado> listaCredenciados;
	private List<FasesProcesso> listaFases;
	private List<PartesProcesso> listaPartesProcessos;
	private List<PenalidadeProcesso> listaPenalidadesProcessos;
	private List<HistoricoProcessoCODET> listaHistoricoProcessos;

	private String campoDaBusca;
	private CredenciadoEmp empresaPJ;
	private Credenciado credenciado;
	private Credenciado credenciadoCredencial;

	private Usuario usuarioLogado;

	private Boolean exibePainelDados;
	private Boolean arquivado;

	private int filtro;
	private Boolean isCredenciado;
	private Boolean isEmpresa;

	public int getFiltro() {
		return filtro;
	}

	public void setFiltro(int filtro) {
		this.filtro = filtro;
	}

	public List<Credenciado> getListaCredenciados() {
		return listaCredenciados;
	}

	public void setListaCredenciados(List<Credenciado> listaCredenciados) {
		this.listaCredenciados = listaCredenciados;
	}

	public HistoricoProcessoCODET getHistoricoProcesso() {
		return historicoProcesso;
	}

	public void setHistoricoProcesso(HistoricoProcessoCODET historicoProcesso) {
		this.historicoProcesso = historicoProcesso;
	}

	public List<HistoricoProcessoCODET> getListaHistoricoProcessos() {
		return listaHistoricoProcessos;
	}

	public void setListaHistoricoProcessos(List<HistoricoProcessoCODET> listaHistoricoProcessos) {
		this.listaHistoricoProcessos = listaHistoricoProcessos;
	}

	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public List<Processo> getListaProcessos() {
		return listaProcessos;
	}

	public void setListaProcessos(List<Processo> listaProcessos) {
		this.listaProcessos = listaProcessos;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("processo codet bean");
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			processo = new Processo();
			fasesProcesso = new FasesProcesso();
			parteProcesso = new PartesProcesso();
			historicoProcesso = new HistoricoProcessoCODET();

			SituacaoProcessoDAO situacaoDAO = new SituacaoProcessoDAO();
			NomenclaturaProcessoDAO nomenclaturaDAO = new NomenclaturaProcessoDAO();
			SetorAtualDAO setorAtualDAO = new SetorAtualDAO();
			PenalidadeProcessoDAO penalidadeDAO = new PenalidadeProcessoDAO();

			listaPenalidadesProcessos = penalidadeDAO.listar();

			listaSetorAtuais = setorAtualDAO.listar();
			listaSituacoes = situacaoDAO.listar();
			listaNomemclaturas = nomenclaturaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Ações.");
			erro.printStackTrace();
		}
	}

	public void habilitarParte() {
		if (filtro == 1) {
			CredenciadoDAO credenciadoDAO = new CredenciadoDAO();

			listaCredenciados = credenciadoDAO.listar();

			isEmpresa = false;
			isCredenciado = true;

		}
		if (filtro == 2) {

			CredenciadoEmpDAO empresaCredenciadaDAO = new CredenciadoEmpDAO();

			listaEmpresasCredenciadas = empresaCredenciadaDAO.listar();
			isCredenciado = false;
			isEmpresa = true;

		}

	}

	public void onRowToggle(ToggleEvent event) {
		PartesProcessoDAO partesDAO = new PartesProcessoDAO();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row State " + event.getVisibility(),
				"Model:" + ((Processo) event.getData()));

		System.out.println(event.getData());

		listaPartesProcessos = partesDAO.listarPorProcessoObject(event.getData());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void cellEditEvent(CellEditEvent event) {

		PartesProcessoDAO partesDAO = new PartesProcessoDAO();
		DataTable dataTable = (DataTable) event.getSource();

		PartesProcesso user = (PartesProcesso) dataTable.getRowData();

		try {

			// RowEditEvent --------PartesProcesso user = (PartesProcesso)
			// event.getObject();-------

			System.out.println("Edit: " + user);

			System.out.println("Penalidade Processo: " + user.getPenalidadeProcesso());
			user.setPenalidadeProcesso(user.getPenalidadeProcesso());
			user.setUsuarioCadastro(usuarioLogado);
			user.setDataCadastro(new Date());

			partesDAO.merge(user);

			System.out.println(listaPartesProcessos + "listaPartesProcessos CELLEDIEVENT");

			Messages.addGlobalInfo("Penalidade adicionada com Sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar adicionar a Penalidade.");
			erro.printStackTrace();
		}

		listaPartesProcessos = partesDAO.listarPorProcesso(processo);

		System.out.println(listaPartesProcessos + "listaPartesProcessos CELLEDIEVENT FORA");

		user = new PartesProcesso();
	}

	public void rowEditFases(RowEditEvent event) {

		FasesProcessoDAO fasesDAO = new FasesProcessoDAO();

		try {

			FasesProcesso user = (FasesProcesso) event.getObject();

			System.out.println("Edit rowEditFases: " + user);

			user.setDataCadastro(new Date());
			user.setUsuarioCadastro(usuarioLogado);

			fasesDAO.merge(user);

			Messages.addGlobalInfo("Fase alterada com Sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar alterar a Fase.");
			erro.printStackTrace();
		}

	}

	public void editarHistorico(ActionEvent event) {

		historicoProcesso = (HistoricoProcessoCODET) event.getComponent().getAttributes().get("histSelecionado");

	}
	
	public void excluirHistorico(ActionEvent evento) {

		try {
			historicoProcesso = (HistoricoProcessoCODET) evento.getComponent().getAttributes().get("histSelecionado");

			HistoricoProcessoDAO histDAO = new HistoricoProcessoDAO();
			
			
			
			histDAO.excluir(historicoProcesso);

			historicoProcesso = new HistoricoProcessoCODET();
			listaHistoricoProcessos = histDAO.listarPorProcesso(processo);

			Messages.addGlobalInfo("Historico removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Historico.");
			erro.printStackTrace();
		}
	}

	public void gravarProcesso() {
		try {
			FasesProcessoDAO fasesDAO = new FasesProcessoDAO();
			ProcessoDAO ProcessoDAO = new ProcessoDAO();
			PartesProcessoDAO partesDAO = new PartesProcessoDAO();

			processoDoBanco = ProcessoDAO.carregaProcesso(processo.getCodigo());

			System.out.println("processoDoBanco" + processoDoBanco);

			if (processoDoBanco == null && processo.getCodigo() == null) {
				System.out.println("ENTROU AQUI processoDoBanco.equals(null)");

				if (processo.getCredenciado() == null && processo.getCredenciadoPJ() == null) {
					Messages.addGlobalError("Acusado não selecionado!");
				} else {
					ProcessoDAO.merge(processo);

					Messages.addGlobalInfo("Processo cadastrado com sucesso!");
				}
				
				listaProcessos = ProcessoDAO.listarCampoDigitadoNULL();

				if (processo.getCredenciado() != null) {
					processo = ProcessoDAO.carregaProcessoUltimoCredenciado(processo.getCredenciado(),
							processo.getNomenclatura(), processo.getSituacao());

				} else {
					processo = ProcessoDAO.carregaProcessoUltimo(processo.getCredenciadoPJ(),
							processo.getNomenclatura(), processo.getSituacao());
				}

				ProcessoDAO.salvarFasesEPartes(fasesProcesso, processo, usuarioLogado, parteProcesso);

				listaFases = fasesDAO.listarPorProcesso(processo);
				listaPartesProcessos = partesDAO.listarPorProcesso(processo);
				

				// Messages.addGlobalInfo("Processo cadastrado com Sucesso!");

			} else if (processoDoBanco.getNumProcesso().equals(processo.getNumProcesso())
					&& processoDoBanco.getNumSGPE().equals(processo.getNumSGPE()) && processo.getCodigo() == null) {
				Messages.addGlobalError("Já existe processo com os dados fornecidos!");
			} else {
				System.out.println(processoDoBanco);
				System.out.println(processo);

				ProcessoDAO.merge(processo);

				System.out.println(processo);

				ProcessoDAO.salvarFases(fasesProcesso, processo, usuarioLogado);

				Messages.addGlobalInfo("Processo cadastrado com Sucesso!");
			}

			// Não precisa listar pq ele fecha
			// listaFases = fasesDAO.listarPorProcesso(processo);
			// listaPartesProcessos = partesDAO.listarPorProcesso(processo);

			processo = new Processo();

		} catch (RuntimeException erro) {

		}
	}

	public void salvarFases() {
		try {
			FasesProcessoDAO FasesProcessoDAO = new FasesProcessoDAO();
			ProcessoDAO ProcessoDAO = new ProcessoDAO();

			System.out.println(processo + " processo processo");

			ProcessoDAO.salvarAdicionarNovaFase(fasesProcesso, processo, usuarioLogado);

			listaFases = FasesProcessoDAO.listarPorProcesso(processo);

			Messages.addGlobalInfo("Ação cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Ação.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) throws Exception {

		try {
			processo = (Processo) evento.getComponent().getAttributes().get("processoSelecionado");

			ProcessoDAO ProcessoDAO = new ProcessoDAO();
			FasesProcessoDAO fasesDAO = new FasesProcessoDAO();
			PartesProcessoDAO partesDAO = new PartesProcessoDAO();
			HistoricoProcessoDAO histDAO = new HistoricoProcessoDAO();

			 System.out.println(campoDaBusca +
			 "campoDaBuscacampoDaBuscacampoDaBuscacampoDaBusca");

			listaFases = fasesDAO.listarPorProcesso(processo);
			while (listaFases.size() != 0) {

				fasesProcesso = fasesDAO.loadLast(processo);

				if (fasesProcesso != null) {

					fasesDAO.excluir(fasesProcesso);
				} else {
					CredenciadoEmp razaoSocial = CredenciadoEmpDAO.consultaporRazaoSocial(campoDaBusca);
					CredenciadoEmp nomeFantasia = CredenciadoEmpDAO.consultaporNomeFantasia(campoDaBusca);
					Credenciado nomeCompleto = CredenciadoDAO.consultaporNomeCompleto(campoDaBusca);

					listaProcessos = ProcessoDAO.listarProcessosConsulta(nomeCompleto, razaoSocial, nomeFantasia,
							campoDaBusca, credenciado, credenciadoCredencial, empresaPJ, credenciado);
					
					break;
				}

			}

			listaPartesProcessos = partesDAO.listarPorProcesso(processo);
			while (listaPartesProcessos.size() != 0) {

				parteProcesso = partesDAO.loadLast(processo);

				if (parteProcesso != null) {

					partesDAO.excluir(parteProcesso);
				} else {
					CredenciadoEmp razaoSocial = CredenciadoEmpDAO.consultaporRazaoSocial(campoDaBusca);
					CredenciadoEmp nomeFantasia = CredenciadoEmpDAO.consultaporNomeFantasia(campoDaBusca);
					Credenciado nomeCompleto = CredenciadoDAO.consultaporNomeCompleto(campoDaBusca);

					listaProcessos = ProcessoDAO.listarProcessosConsulta(nomeCompleto, razaoSocial, nomeFantasia,
							campoDaBusca, credenciado, credenciadoCredencial, empresaPJ, credenciado);
					break;
				}

			}

			
			listaHistoricoProcessos = histDAO.listarPorProcesso(processo);
			while (listaHistoricoProcessos.size() != 0) {

				historicoProcesso = histDAO.loadLast(processo);

				if (historicoProcesso != null) {

					histDAO.excluir(historicoProcesso);
				} else {
					CredenciadoEmp razaoSocial = CredenciadoEmpDAO.consultaporRazaoSocial(campoDaBusca);
					CredenciadoEmp nomeFantasia = CredenciadoEmpDAO.consultaporNomeFantasia(campoDaBusca);
					Credenciado nomeCompleto = CredenciadoDAO.consultaporNomeCompleto(campoDaBusca);

					listaProcessos = ProcessoDAO.listarProcessosConsulta(nomeCompleto, razaoSocial, nomeFantasia,
							campoDaBusca, credenciado, credenciadoCredencial, empresaPJ, credenciado);
					break;
				}

			}
			
			ProcessoDAO.excluir(processo);

			processo = new Processo();
			parteProcesso = new PartesProcesso();
			historicoProcesso = new HistoricoProcessoCODET();

			if (campoDaBusca.isEmpty()) {
				listaProcessos = ProcessoDAO.listarCampoDigitadoNULL();
			} else {
				CredenciadoEmp razaoSocial = CredenciadoEmpDAO.consultaporRazaoSocial(campoDaBusca);
				CredenciadoEmp nomeFantasia = CredenciadoEmpDAO.consultaporNomeFantasia(campoDaBusca);
				Credenciado nomeCompleto = CredenciadoDAO.consultaporNomeCompleto(campoDaBusca);

				listaProcessos = ProcessoDAO.listarProcessosConsulta(nomeCompleto, razaoSocial, nomeFantasia,
						campoDaBusca, credenciado, credenciadoCredencial, empresaPJ, credenciado);
			}
			
			Messages.addGlobalInfo("Processo excluído com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Processo.");
			erro.printStackTrace();
		}
	}

	public void excluirParte(ActionEvent evento) {

		try {
			parteProcesso = (PartesProcesso) evento.getComponent().getAttributes().get("parteSelecionado");

			PartesProcessoDAO parteDAO = new PartesProcessoDAO();
			parteDAO.excluir(parteProcesso);

			parteProcesso = new PartesProcesso();
			listaPartesProcessos = parteDAO.listarPorProcesso(processo);

			Messages.addGlobalInfo("Ação removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Ação.");
			erro.printStackTrace();
		}
	}

	public void adicionaProcesso() {

		listaProcessos = new ArrayList<>();
		listaPartesProcessos = new ArrayList<>();
		listaFases = new ArrayList<>();
		processo = new Processo();
		parteProcesso = new PartesProcesso();
		historicoProcesso = new HistoricoProcessoCODET();
		fasesProcesso = new FasesProcesso();

		System.out.println(listaProcessos);
		System.out.println(processo);
		System.out.println(listaPartesProcessos);
		System.out.println(listaFases);
	}

	public void editar(ActionEvent evento) {
		FasesProcessoDAO fasesDAO = new FasesProcessoDAO();
		PartesProcessoDAO partesDAO = new PartesProcessoDAO();
		HistoricoProcessoDAO historicoDAO = new HistoricoProcessoDAO();
		
		historicoProcesso = new HistoricoProcessoCODET();

		processo = (Processo) evento.getComponent().getAttributes().get("processoSelecionado");

		if (processo.getCredenciado() != null) {
			CredenciadoDAO credenciadoDAO = new CredenciadoDAO();

			filtro = 1;
			if (filtro == 1) {

				listaCredenciados = credenciadoDAO.consultaporProcessoCODET(processo.getCredenciado());

				isEmpresa = false;
				isCredenciado = true;

			}

		} else if (processo.getCredenciadoPJ() != null) {
			CredenciadoEmpDAO empresaCredenciadaDAO = new CredenciadoEmpDAO();

			filtro = 2;
			if (filtro == 2) {

				listaEmpresasCredenciadas = empresaCredenciadaDAO.consultaporProcessoCODET(processo.getCredenciadoPJ());

				isCredenciado = false;
				isEmpresa = true;

			}
		}

		processo.setDataInstauracao(processo.getDataInstauracao());

		arquivado = false;

		listaPartesProcessos = partesDAO.listarPorProcesso(processo);
		listaFases = fasesDAO.listarPorProcesso(processo);
		listaHistoricoProcessos = historicoDAO.listarPorProcesso(processo);
	}

	public void habilitar() {
		if (processo.getSituacao().getDescricao().equals("ARQUIVADO")) {

			SetorAtualDAO setorDAO = new SetorAtualDAO();

			setorAtualCodigo = setorDAO.carregarSetorAtualCodigo();
			System.out.println("setorAtualCodigo" + setorAtualCodigo);
			arquivado = true;
			processo.setSetorAtual(setorAtualCodigo);
			System.out.println("ARQUIVADO SIM");
		} else {
			arquivado = false;
		}

	}

	public void buscarCamposPesquisa() {

		try {

			listaProcessos = new ArrayList<>();

			empresaPJ = CredenciadoEmpDAO.consultaporCnpjString(campoDaBusca);

			credenciado = CredenciadoDAO.consultaporCpfString(campoDaBusca);

			credenciadoCredencial = CredenciadoDAO.consultaporCredencial(campoDaBusca);

			System.out.println(credenciado + " credenciado");
			System.out.println(empresaPJ + " empresaPJ");

			ProcessoDAO processoDAO = new ProcessoDAO();

			if (campoDaBusca.isEmpty()) {
				listaProcessos = processoDAO.listarCampoDigitadoNULL();
			} else {

				CredenciadoEmp razaoSocial = CredenciadoEmpDAO.consultaporRazaoSocial(campoDaBusca);
				CredenciadoEmp nomeFantasia = CredenciadoEmpDAO.consultaporNomeFantasia(campoDaBusca);
				Credenciado nomeCompleto = CredenciadoDAO.consultaporNomeCompleto(campoDaBusca);

				listaProcessos = processoDAO.listarProcessosConsulta(nomeCompleto, razaoSocial, nomeFantasia,
						campoDaBusca, credenciado, credenciadoCredencial, empresaPJ, credenciado);
			}
			System.out.println(listaProcessos + " listaProcessos");

			exibePainelDados = true;

		} catch (NullPointerException e) {
			Messages.addGlobalError("Empresa não credenciada.");

		}
	}

	public void salvarHistorico() {

		try {

			HistoricoProcessoDAO histDAO = new HistoricoProcessoDAO();

			historicoProcesso.setProcesso(processo);
			historicoProcesso.setDataCadastro(new Date());
			historicoProcesso.setUsuarioCadastro(usuarioLogado);
			historicoProcesso.setDescricao(historicoProcesso.getDescricao());

			histDAO.merge(historicoProcesso);

			listaHistoricoProcessos = histDAO.listarPorProcesso(processo);
			
			historicoProcesso = new HistoricoProcessoCODET();

			Messages.addGlobalInfo("Histórico cadastrado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Histórico.");
			erro.printStackTrace();
		}

	}

	public void novaParte() {
		parteProcesso = new PartesProcesso();
	}

	public void salvarParte() {

		try {

			PartesProcessoDAO parteDAO = new PartesProcessoDAO();

			if (parteProcesso.getCredenciadoEmpresa() != null) {
				parteProcesso.setCredenciadoEmpresa(parteProcesso.getCredenciadoEmpresa());
			} else {
				parteProcesso.setCredenciadoEmpresa(null);
			}
			parteProcesso.setCredenciado(parteProcesso.getCredenciado());
			parteProcesso.setDataCadastro(new Date());
			parteProcesso.setUsuarioCadastro(usuarioLogado);
			parteProcesso.setProcesso(processo);

			if (parteProcesso.getCredenciado() == null && parteProcesso.getCredenciadoEmpresa() == null) {
				Messages.addGlobalError("Parte Processo não seleciona!");
			} else {
				parteDAO.merge(parteProcesso);

				Messages.addGlobalInfo("Parte cadastrado com sucesso!");
			}

			listaPartesProcessos = parteDAO.listarPorProcesso(processo);

			parteProcesso = new PartesProcesso();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Parte.");
			erro.printStackTrace();
		}

	}

	public void gerarRelatorioPartes() {

		try {

			JSFUtil.redirect("../ImprimirRelatorio?rlt_nome=parteprocesso" + "&processoId=" + processo.getCodigo());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException nulo) {
			throw new NullPointerException(
					"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
		}

	}

	public void gerarRelatorioFases() {

		try {

			JSFUtil.redirect("../ImprimirRelatorio?rlt_nome=fasesprocesso" + "&processoId=" + processo.getCodigo());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException nulo) {
			throw new NullPointerException(
					"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
		}

	}

	public void gerarRelatorioHistorico() {

		try {

			JSFUtil.redirect("../ImprimirRelatorio?rlt_nome=historicoprocesso" + "&processoId=" + processo.getCodigo());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException nulo) {
			throw new NullPointerException(
					"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
		}

	}

	public List<NomenclaturaProcesso> getListaNomemclaturas() {
		return listaNomemclaturas;
	}

	public void setListaNomemclaturas(List<NomenclaturaProcesso> listaNomemclaturas) {
		this.listaNomemclaturas = listaNomemclaturas;
	}

	public PartesProcesso getParteProcesso() {
		return parteProcesso;
	}

	public void setParteProcesso(PartesProcesso parteProcesso) {
		this.parteProcesso = parteProcesso;
	}

	public String getCampoDaBusca() {
		return campoDaBusca;
	}

	public void setCampoDaBusca(String campoDaBusca) {
		this.campoDaBusca = campoDaBusca;
	}

	public CredenciadoEmp getEmpresaPJ() {
		return empresaPJ;
	}

	public void setEmpresaPJ(CredenciadoEmp empresaPJ) {
		this.empresaPJ = empresaPJ;
	}

	public List<CredenciadoEmp> getListaEmpresasCredenciadas() {
		return listaEmpresasCredenciadas;
	}

	public void setListaEmpresasCredenciadas(List<CredenciadoEmp> listaEmpresasCredenciadas) {
		this.listaEmpresasCredenciadas = listaEmpresasCredenciadas;
	}

	public FasesProcesso getFasesProcesso() {
		return fasesProcesso;
	}

	public void setFasesProcesso(FasesProcesso fasesProcesso) {
		this.fasesProcesso = fasesProcesso;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<FasesProcesso> getListaFases() {
		return listaFases;
	}

	public void setListaFases(List<FasesProcesso> listaFases) {
		this.listaFases = listaFases;
	}

	public Boolean getExibePainelDados() {
		return exibePainelDados;
	}

	public void setExibePainelDados(Boolean exibePainelDados) {
		this.exibePainelDados = exibePainelDados;
	}

	public List<SituacaoProcesso> getListaSituacoes() {
		return listaSituacoes;
	}

	public void setListaSituacoes(List<SituacaoProcesso> listaSituacoes) {
		this.listaSituacoes = listaSituacoes;
	}

	public List<SetorAtual> getListaSetorAtuais() {
		return listaSetorAtuais;
	}

	public void setListaSetorAtuais(List<SetorAtual> listaSetorAtuais) {
		this.listaSetorAtuais = listaSetorAtuais;
	}

	public List<PartesProcesso> getListaPartesProcessos() {
		return listaPartesProcessos;
	}

	public void setListaPartesProcessos(List<PartesProcesso> listaPartesProcessos) {
		this.listaPartesProcessos = listaPartesProcessos;
	}

	public List<PenalidadeProcesso> getListaPenalidadesProcessos() {
		return listaPenalidadesProcessos;
	}

	public void setListaPenalidadesProcessos(List<PenalidadeProcesso> listaPenalidadesProcessos) {
		this.listaPenalidadesProcessos = listaPenalidadesProcessos;
	}

	public Credenciado getCredenciadoCredencial() {
		return credenciadoCredencial;
	}

	public void setCredenciadoCredencial(Credenciado credenciadoCredencial) {
		this.credenciadoCredencial = credenciadoCredencial;
	}

	public Boolean getArquivado() {
		return arquivado;
	}

	public void setArquivado(Boolean arquivado) {
		this.arquivado = arquivado;
	}

	public Processo getProcessoDoBanco() {
		return processoDoBanco;
	}

	public void setProcessoDoBanco(Processo processoDoBanco) {
		this.processoDoBanco = processoDoBanco;
	}

	public SetorAtual getSetorAtualCodigo() {
		return setorAtualCodigo;
	}

	public void setSetorAtualCodigo(SetorAtual setorAtualCodigo) {
		this.setorAtualCodigo = setorAtualCodigo;
	}

	public Boolean getIsCredenciado() {
		return isCredenciado;
	}

	public void setIsCredenciado(Boolean isCredenciado) {
		this.isCredenciado = isCredenciado;
	}

	public Boolean getIsEmpresa() {
		return isEmpresa;
	}

	public void setIsEmpresa(Boolean isEmpresa) {
		this.isEmpresa = isEmpresa;
	}

}

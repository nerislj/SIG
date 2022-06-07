package br.gov.sc.codet.bean;

import java.io.Serializable;
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

import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.gov.sc.cetran.domain.HistoricoProcesso;
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
import br.gov.sc.sgi.dao.CredenciadoDAO;
import br.gov.sc.sgi.dao.CredenciadoEmpDAO;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProcessoBean implements Serializable {

	private Processo processo;
	private List<Processo> listaProcessos;

	private PartesProcesso parteProcesso;
	private FasesProcesso fasesProcesso;
	private HistoricoProcessoCODET historicoProcesso;

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

	private Usuario usuarioLogado;

	private Boolean exibePainelDados;
	
	

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
			
			CredenciadoDAO credenciadoDAO = new CredenciadoDAO();
			CredenciadoEmpDAO empresaCredenciadaDAO = new CredenciadoEmpDAO();

			listaCredenciados = credenciadoDAO.listar();
			listaEmpresasCredenciadas = empresaCredenciadaDAO.listar();

			listaPenalidadesProcessos = penalidadeDAO.listar();

			listaSetorAtuais = setorAtualDAO.listar();
			listaSituacoes = situacaoDAO.listar();
			listaNomemclaturas = nomenclaturaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Ações.");
			erro.printStackTrace();
		}
	}

	public void cellEditEvent(CellEditEvent event) {

		try {

			PartesProcessoDAO partesDAO = new PartesProcessoDAO();

			// RowEditEvent PartesProcesso user = (PartesProcesso) event.getObject

			DataTable dataTable = (DataTable) event.getSource();

			PartesProcesso user = (PartesProcesso) dataTable.getRowData();

			System.out.println("Edit: " + user);

			System.out.println("Penalidade Processo: " + user.getPenalidadeProcesso());
			user.setPenalidadeProcesso(user.getPenalidadeProcesso());

			partesDAO.merge(user);

			Messages.addGlobalInfo("Penalidade adicionada com Sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar adicionar a Penalidade.");
			erro.printStackTrace();
		}

	}

	public void editarHistorico(ActionEvent event) {

		historicoProcesso = (HistoricoProcessoCODET) event.getComponent().getAttributes().get("histSelecionado");

	}

	public void gravarProcesso() {
		try {
			FasesProcessoDAO fasesDAO = new FasesProcessoDAO();
			ProcessoDAO ProcessoDAO = new ProcessoDAO();
			PartesProcessoDAO partesDAO = new PartesProcessoDAO();

			System.out.println(processo);
			if (processo.getCodigo() == null) {

				ProcessoDAO.merge(processo);

				processo = ProcessoDAO.carregaProcesso(processo.getCredenciadoPJ());

				ProcessoDAO.salvarFasesEPartes(fasesProcesso, processo, usuarioLogado, parteProcesso);

				listaFases = fasesDAO.listarPorProcesso(processo);
				listaPartesProcessos = partesDAO.listarPorProcesso(processo);

			} else {

				parteProcesso = ProcessoDAO.carregaParteProcesso(processo);

				ProcessoDAO.salvarFasesEPartes(fasesProcesso, processo, usuarioLogado, parteProcesso);

				listaFases = fasesDAO.listarPorProcesso(processo);
				listaPartesProcessos = partesDAO.listarPorProcesso(processo);

			}

			Messages.addGlobalInfo("Ação cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Ação.");
			erro.printStackTrace();
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

	public void excluir(ActionEvent evento) {

		try {
			processo = (Processo) evento.getComponent().getAttributes().get("processoSelecionado");

			ProcessoDAO ProcessoDAO = new ProcessoDAO();
			ProcessoDAO.excluir(processo);

			processo = new Processo();
			listaProcessos = ProcessoDAO.listar();

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

		System.out.println(listaProcessos);
		System.out.println(processo);
		System.out.println(listaPartesProcessos);
		System.out.println(listaFases);
	}

	public void editar(ActionEvent evento) {
		FasesProcessoDAO fasesDAO = new FasesProcessoDAO();
		PartesProcessoDAO partesDAO = new PartesProcessoDAO();
		HistoricoProcessoDAO historicoDAO = new HistoricoProcessoDAO();

		processo = (Processo) evento.getComponent().getAttributes().get("processoSelecionado");

		listaPartesProcessos = partesDAO.listarPorProcesso(processo);
		listaFases = fasesDAO.listarPorProcesso(processo);
		listaHistoricoProcessos = historicoDAO.listarPorProcesso(processo);
	}

	public void buscarCamposPesquisa() {
		try {

			listaProcessos = new ArrayList<>();

			empresaPJ = CredenciadoEmpDAO.consultaporCnpjString(campoDaBusca);

			credenciado = CredenciadoDAO.consultaporCpfString(campoDaBusca);

			System.out.println(credenciado + " credenciado");
			System.out.println(empresaPJ + " empresaPJ");

			ProcessoDAO processoDAO = new ProcessoDAO();
			listaProcessos = processoDAO.listarProcessos(campoDaBusca, credenciado, empresaPJ);
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

			Messages.addGlobalInfo("Histórico cadastrado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Histórico.");
			erro.printStackTrace();
		}

	}
	
	public void salvarParte() {

		try {

			PartesProcessoDAO parteDAO = new PartesProcessoDAO();

			parteProcesso.setCredenciado(parteProcesso.getCredenciado());
			parteProcesso.setDataCadastro(new Date());
			parteProcesso.setUsuarioCadastro(usuarioLogado);

			parteDAO.merge(parteProcesso);

			listaPartesProcessos = parteDAO.listarPorProcesso(processo);

			Messages.addGlobalInfo("Parte cadastrado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Parte.");
			erro.printStackTrace();
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

}

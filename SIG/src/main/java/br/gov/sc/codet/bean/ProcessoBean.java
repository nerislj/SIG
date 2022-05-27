package br.gov.sc.codet.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;
import org.primefaces.event.RowEditEvent;

import br.gov.sc.codet.dao.FasesProcessoDAO;
import br.gov.sc.codet.dao.NomenclaturaProcessoDAO;
import br.gov.sc.codet.dao.PartesProcessoDAO;
import br.gov.sc.codet.dao.ProcessoDAO;
import br.gov.sc.codet.dao.SetorAtualDAO;
import br.gov.sc.codet.dao.SituacaoProcessoDAO;
import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.NomenclaturaProcesso;
import br.gov.sc.codet.domain.PartesProcesso;
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

	private List<NomenclaturaProcesso> listaNomemclaturas;
	private List<SituacaoProcesso> listaSituacoes;
	private List<SetorAtual> listaSetorAtuais;
	private List<CredenciadoEmp> listaEmpresasCredenciadas;
	private List<FasesProcesso> listaFases;
	private List<PartesProcesso> listaPartesProcessos;

	private String campoDaBusca;
	private CredenciadoEmp empresaPJ;
	private Credenciado credenciado;

	private Usuario usuarioLogado;
	
	private Boolean exibePainelDados;
	

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

			
			SituacaoProcessoDAO situacaoDAO = new SituacaoProcessoDAO();
			NomenclaturaProcessoDAO nomenclaturaDAO = new NomenclaturaProcessoDAO();
			SetorAtualDAO setorAtualDAO = new SetorAtualDAO();
			PartesProcessoDAO partesProcessoDAO = new PartesProcessoDAO();
			
			
			CredenciadoEmpDAO empresaCredenciadaDAO = new CredenciadoEmpDAO();

			listaEmpresasCredenciadas = empresaCredenciadaDAO.listar();
			
			setListaPartesProcessos(partesProcessoDAO.listar());
			listaSetorAtuais = setorAtualDAO.listar();
			listaSituacoes = situacaoDAO.listar();
			listaNomemclaturas = nomenclaturaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Ações.");
			erro.printStackTrace();
		}
	}

	public void onRowEdit(RowEditEvent event) {
		fasesProcesso = (FasesProcesso) event.getComponent().getAttributes().get("faseSelecionado");
	}

	public void salvar() {
		try {
			FasesProcessoDAO fasesDAO = new FasesProcessoDAO();
			ProcessoDAO ProcessoDAO = new ProcessoDAO();
			processo.setNumSGPE(processo.getNumSGPE());
			System.out.println(processo.getNumSGPE());
			ProcessoDAO.merge(processo);

			ProcessoDAO.salvarFases(fasesProcesso, processo, usuarioLogado);

			listaFases = fasesDAO.listarPorProcesso(processo);
			listaProcessos = ProcessoDAO.listar();

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

	public void editar(ActionEvent evento) {
		FasesProcessoDAO fasesDAO = new FasesProcessoDAO();

		processo = (Processo) evento.getComponent().getAttributes().get("processoSelecionado");

		listaFases = fasesDAO.listarPorProcesso(processo);
	}

	public void buscarCamposPesquisa() {
		try {

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



}

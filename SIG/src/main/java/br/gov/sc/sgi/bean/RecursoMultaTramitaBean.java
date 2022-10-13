package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.OficioDAO;
import br.gov.sc.sgi.dao.RecursoMultaDAO;
import br.gov.sc.sgi.dao.RecursoMultaTramitaDAO;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.RecursoMulta;
import br.gov.sc.sgi.domain.RecursoMultaTramita;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class RecursoMultaTramitaBean implements Serializable {

	private List<RecursoMulta> recursosMulta;
	private List<RecursoMulta> recursosMultaPopular;

	private List<RecursoMulta> recursosPendentes;

	private List<RecursoMulta> listaRecursoSaida;
	private RecursoMulta recursoMulta;

	private Usuario usuario;

	private List<Oficio> oficios;
	private Oficio oficio;

	private List<Unidade> unidades;
	private Unidade unidade;

	private List<Setor> setores;
	private Setor setor;

	private List<RecursoMultaTramita> listaTramitacao;
	private RecursoMultaTramita recursoTramita;

	private Boolean exibePainelDados;

	public List<RecursoMulta> getRecursosMulta() {
		return recursosMulta;
	}

	public void setRecursosMulta(List<RecursoMulta> recursosMulta) {
		this.recursosMulta = recursosMulta;
	}

	public List<RecursoMulta> getListaRecursoSaida() {
		return listaRecursoSaida;
	}

	public void setListaRecursoSaida(List<RecursoMulta> listaRecursoSaida) {
		this.listaRecursoSaida = listaRecursoSaida;
	}

	public RecursoMulta getRecursoMulta() {
		return recursoMulta;
	}

	public void setRecursoMulta(RecursoMulta recursoMulta) {
		this.recursoMulta = recursoMulta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Oficio> getOficios() {
		return oficios;
	}

	public void setOficios(List<Oficio> oficios) {
		this.oficios = oficios;
	}

	public Oficio getOficio() {
		return oficio;
	}

	public void setOficio(Oficio oficio) {
		this.oficio = oficio;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public List<RecursoMultaTramita> getListaTramitacao() {
		return listaTramitacao;
	}

	public void setListaTramitacao(List<RecursoMultaTramita> listaTramitacao) {
		this.listaTramitacao = listaTramitacao;
	}

	public RecursoMultaTramita getRecursoTramita() {
		return recursoTramita;
	}

	public void setRecursoTramita(RecursoMultaTramita recursoTramita) {
		this.recursoTramita = recursoTramita;
	}

	public List<RecursoMulta> getRecursosPendentes() {
		return recursosPendentes;
	}

	public void setRecursosPendentes(List<RecursoMulta> recursosPendentes) {
		this.recursosPendentes = recursosPendentes;
	}

	public Boolean getExibePainelDados() {
		return exibePainelDados;
	}

	public void setExibePainelDados(Boolean exibePainelDados) {
		this.exibePainelDados = exibePainelDados;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public List<RecursoMulta> getRecursosMultaPopular() {
		return recursosMultaPopular;
	}

	public void setRecursosMultaPopular(List<RecursoMulta> recursosMultaPopular) {
		this.recursosMultaPopular = recursosMultaPopular;
	}

	@PostConstruct
	public void novo() {
		try {
System.out.println("recursomultatramita bean");
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuario = (Usuario) sessao.getAttribute("usuario");

			recursoMulta = new RecursoMulta();
			recursoTramita = new RecursoMultaTramita();
			
			RecursoMultaDAO recursoMultaDAO = new RecursoMultaDAO();
			recursosMulta = recursoMultaDAO.carregarRecursoMultaEmAberto(usuario.getSetor(), usuario.getUnidade(),
					"Em Aberto");
			recursosPendentes = recursoMultaDAO.carregarRecursoMultaEmAberto(usuario.getSetor(), usuario.getUnidade(),
					"Pendente");

			listaRecursoSaida = new ArrayList<>();
			listaTramitacao = new ArrayList<>();

			exibePainelDados = false;

		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela Tramitação");
			erro.printStackTrace();
		}
	}

	public void buscar() {
		try {
			RecursoMultaDAO produtoDAO = new RecursoMultaDAO();
			recursosMultaPopular = produtoDAO.buscarFiltro(recursoMulta.getPlaca(), recursoMulta.getAutoInfracao());

			if (recursosMultaPopular == null) {
				exibePainelDados = false;
				Messages.addGlobalWarn("Não existe recurso cadastrado para o código informado");
			} else {
				exibePainelDados = true;
				recursosMultaPopular = produtoDAO.buscarFiltro(recursoMulta.getPlaca(), recursoMulta.getAutoInfracao());
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o produto");
			erro.printStackTrace();
		}
	}

	/*
	 * public void listar() { try {
	 * 
	 * HttpSession sessao = (HttpSession)
	 * FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	 * usuario = (Usuario) sessao.getAttribute("usuario");
	 * 
	 * RecursoMultaTramitaDAO recursoMultaTramitaDAO = new RecursoMultaTramitaDAO();
	 * oficiosTramita = recursoMultaTramitaDAO.listar("codigo");
	 * 
	 * SetorDAO setorDAO = new SetorDAO(); setores = setorDAO.listar();
	 * 
	 * OficioDAO oficioDAO = new OficioDAO(); oficios =
	 * oficioDAO.listarFiltro(usuario.getSetor().getCodigo());
	 * 
	 * listaTramitacao = recursoMultaTramitaDAO.listar();
	 * 
	 * } catch (RuntimeException erro) {
	 * Messages.addGlobalError("Ocorreu um erro ao tentar listar os Processos.");
	 * erro.printStackTrace(); } }
	 */

	public void adicionar(ActionEvent evento) {
		RecursoMulta recursoMulta = (RecursoMulta) evento.getComponent().getAttributes().get("recursomultaSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < listaRecursoSaida.size(); posicao++) {
			if (listaRecursoSaida.get(posicao).equals(recursoMulta)) {
				achou = posicao;
			}
		} if (achou < 0) {
			listaRecursoSaida.add(recursoMulta);
		}
	}

	public void remover(ActionEvent evento) {
		RecursoMulta recursoMulta = (RecursoMulta) evento.getComponent().getAttributes().get("listaselecionado");

		int achou = -1;
		for (int posicao = 0; posicao < listaRecursoSaida.size(); posicao++) {
			if (listaRecursoSaida.get(posicao).equals(recursoMulta)) {
				achou = posicao;
			}
		} if (achou > -1) {
			listaRecursoSaida.remove(achou);
		}
	}

	public void finalizar() {
		try {
			
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuario = (Usuario) sessao.getAttribute("usuario");

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			unidades = unidadeDAO.listar();

			OficioDAO oficioDAO = new OficioDAO();
			oficios = oficioDAO.carregarOficiosEmAberto("Em Aberto", usuario.getSetor(), usuario.getUnidade());

		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar a Tramitação");
			erro.printStackTrace();
		}
	}

	public void salvarInterno() {
		try {

			if (listaRecursoSaida.isEmpty()) {
				Messages.addGlobalError("Nenhum processo Selecionado.");
			} else {
				HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
						.getSession(false);
				usuario = (Usuario) sessao.getAttribute("usuario");

				RecursoMultaTramitaDAO recursoMultaTramitaDAO = new RecursoMultaTramitaDAO();
				recursoMultaTramitaDAO.salvarInterno(listaRecursoSaida, usuario, setor, unidade);

				RecursoMultaTramitaBean.this.novo();

				Messages.addGlobalInfo("Tramitação realizada com sucesso");
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a Tramitação");
			erro.printStackTrace();
		}
	}

	public void salvarExterno() {
		try {

			if (listaRecursoSaida.isEmpty()) {
				Messages.addGlobalError("Nenhum processo Selecionado.");
			} else {

				HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
						.getSession(false);
				usuario = (Usuario) sessao.getAttribute("usuario");

				recursoMulta.setStatus("Tramitando");
				recursoMulta.setSetorAtual(usuario.getSetor());
				System.out.println(this.recursoTramita.getDestino());
				
				RecursoMultaTramitaDAO recursoMultaTramitaDAO = new RecursoMultaTramitaDAO();
				recursoMultaTramitaDAO.salvarExterno(listaRecursoSaida, usuario, recursoTramita);

				RecursoMultaTramitaBean.this.novo();

				Messages.addGlobalInfo("Tramitação realizada com sucesso");
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a Tramitação");
			erro.printStackTrace();
		}
	}

	public void recebeProcesso() {
		try {

			if (listaRecursoSaida.isEmpty()) {
				Messages.addGlobalError("Nenhum processo Selecionado.");
			} else {

				HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
						.getSession(false);
				usuario = (Usuario) sessao.getAttribute("usuario");

				RecursoMultaTramitaDAO recursoMultaTramitaDAO = new RecursoMultaTramitaDAO();
				recursoMultaTramitaDAO.recebeProcesso(listaRecursoSaida, usuario);

				RecursoMultaTramitaBean.this.novo();

				Messages.addGlobalInfo("Processos recebidos com sucesso!");
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar o recebimento.");
			erro.printStackTrace();
		}
	}

	public void popular() {
		try {
			if (unidade.getUnidade() != null) {
				SetorDAO setorDAO = new SetorDAO();
				setores = setorDAO.buscarPorUnidade(unidade.getCodigo());
			} else {
				setores = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os setores");
			erro.printStackTrace();
		}
	}
}
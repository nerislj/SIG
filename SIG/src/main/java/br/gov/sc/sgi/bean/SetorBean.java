package br.gov.sc.sgi.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import br.gov.sc.contrato.dao.UserClaimsContratoDAO;
import br.gov.sc.contrato.domain.UserClaimsContrato;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class SetorBean implements Serializable {

	private Setor setor;
	private List<Setor> Setores;
	private List<Setor> setoresContratos;
	private List<Unidade> Unidades;

	private Usuario usuarioLogado;
	private List<UserClaimsContrato> listaUserClaims;
	

	public List<UserClaimsContrato> getListaUserClaims() {
		return listaUserClaims;
	}

	public void setListaUserClaims(List<UserClaimsContrato> listaUserClaims) {
		this.listaUserClaims = listaUserClaims;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public List<Setor> getSetores() {
		return Setores;
	}

	public void setSetores(List<Setor> setores) {
		Setores = setores;
	}

	public List<Unidade> getUnidades() {
		return Unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		Unidades = unidades;
	}

	@PostConstruct
	public void listar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");
			System.out.println("setor beAN");
			SetorDAO setorDAO = new SetorDAO();
			UserClaimsContratoDAO userClaimDAO = new UserClaimsContratoDAO();

			if (usuarioLogado.getNivelAcesso().getCodigo() == 1) {

				setoresContratos = setorDAO.listarSetoresFuncionariosTerceirizados();

			}

			if (usuarioLogado.getUnidade().getUnidadeNome().contains("CIRETRAN")
					|| usuarioLogado.getUnidade().getUnidadeNome().contains("CITRAN")) {
				if (usuarioLogado.getNivelAcesso().getNivel().contains("GERÊNCIA")) {

					listaUserClaims = userClaimDAO.listarPorUsuarioLogado(usuarioLogado);
					

					ArrayList<String> codigosUnidades = new ArrayList<String>();
					for (int posicao = 0; posicao < listaUserClaims.size(); posicao++) {

						codigosUnidades.add(listaUserClaims.get(posicao).getUnidade().getUnidadeNome());

					}

					setoresContratos = setorDAO.listarPorClaims(codigosUnidades);

					// listaContratosTerceirizadosPorContratoEmpresa =
					// contratoDAO.listarPorContratoEmpresa();

				}

				if (usuarioLogado.getSetor().getSetorNome().contains("Gerência de Apoio Operacional")
						&& usuarioLogado.getNivelAcesso().getNivel().contains("CONTRATO")) {

					setoresContratos = setorDAO.listarSetoresFuncionariosTerceirizados();

				}

			}

			Setores = setorDAO.listar();
			UnidadeDAO unidadeDAO = new UnidadeDAO();
			Unidades = unidadeDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Setores.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		setor = new Setor();
	}

	public void salvar() throws IOException {
		try {
			SetorDAO setorDAO = new SetorDAO();
			setorDAO.merge(setor);

			this.refresh();

			Messages.addGlobalInfo("Setor cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Setor.");
			erro.printStackTrace();
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

	public void excluir(ActionEvent evento) throws IOException {

		try {
			setor = (Setor) evento.getComponent().getAttributes().get("setorSelecionado");

			SetorDAO setorDAO = new SetorDAO();
			setorDAO.excluir(setor);

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());

			Messages.addGlobalInfo("Setor removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Setor.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		setor = (Setor) evento.getComponent().getAttributes().get("setorSelecionado");
	}

	public List<Setor> getSetoresContratos() {
		return setoresContratos;
	}

	public void setSetoresContratos(List<Setor> setoresContratos) {
		this.setoresContratos = setoresContratos;
	}
}

package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;
import org.primefaces.extensions.event.ClipboardErrorEvent;

import br.gov.sc.sgi.dao.OficioAnoDAO;
import br.gov.sc.sgi.dao.OficioDAO;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.OficioAno;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class OficioBean implements Serializable {

	private Oficio oficio;

	private List<Oficio> oficios;
	private List<Oficio> oficiosMenuCount;
	private List<Oficio> oficiosInativos;
	private Oficio oficioDialogo;
	private List<Oficio> filtroOficios;
	private List<Setor> setores;
	private OficioAno Ano;
	private List<OficioAno> Anos;

	private Usuario usuarioLogado;

	public Oficio getOficio() {
		return oficio;
	}

	public void setOficio(Oficio oficio) {
		this.oficio = oficio;
	}

	public List<Oficio> getOficios() {
		return oficios;
	}

	public void setOficios(List<Oficio> oficios) {
		this.oficios = oficios;
	}

	public Oficio getOficioDialogo() {
		return oficioDialogo;
	}

	public void setOficioDialogo(Oficio oficioDialogo) {
		this.oficioDialogo = oficioDialogo;
	}

	public List<Oficio> getFiltroOficios() {
		return filtroOficios;
	}

	public void setFiltroOficios(List<Oficio> filtroOficios) {
		this.filtroOficios = filtroOficios;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public OficioAno getAno() {
		return Ano;
	}

	public void setAno(OficioAno ano) {
		Ano = ano;
	}

	public List<OficioAno> getAnos() {
		return Anos;
	}

	public void setAnos(List<OficioAno> anos) {
		Anos = anos;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("oficiobean");
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			OficioDAO oficioDAO = new OficioDAO();
			
			System.out.println("e AQUI? Oficio EM ABERTO");
			
			oficios = oficioDAO.carregarOficiosEmAberto("Em Aberto", usuarioLogado.getSetor(), usuarioLogado.getUnidade());

			
			oficiosMenuCount = oficioDAO.carregarOficiosEmAberto("Em Aberto", usuarioLogado.getSetor(), usuarioLogado.getUnidade());

		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Ofícios.");
			erro.printStackTrace();
		}
	}
	
	public void listarOficios() throws Exception {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		usuarioLogado = (Usuario) sessao.getAttribute("usuario");
		
		OficioDAO oficioDAO = new OficioDAO();
		
		OficioAnoDAO oficioanoDAO = new OficioAnoDAO();
		
		
		int anoHoje = new Date().getYear() + 1900;
		oficios = oficioDAO.listarAtivos(usuarioLogado.getSetor(), usuarioLogado.getUnidade(), anoHoje);
		
		Anos = oficioanoDAO.loadAnos();

		

		oficioDialogo = oficioDAO.listarDialogo("codigo", usuarioLogado);

		
		
		System.out.println(oficios + " oficiosoficiosoficiosoficiosoficios");

		oficiosInativos = oficioDAO.listarInativos(usuarioLogado.getSetor(), usuarioLogado.getUnidade());
		
		oficiosMenuCount = oficioDAO.carregarOficiosEmAberto("Em Aberto", usuarioLogado.getSetor(), usuarioLogado.getUnidade());
		
	}

	public void consultaPorAno() {
		try {
			OficioDAO oficioDAO = new OficioDAO();
			
			System.out.println("CONSULTOU ");

			if(Ano!=null) {
			oficios = oficioDAO.listarPorAno(usuarioLogado.getSetor(), usuarioLogado.getUnidade(), Ano.getOficioAno());
			}
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Ofícios.");
			erro.printStackTrace();
		}
	}

	public void novo() {

		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			oficio = new Oficio();

			OficioDAO oficioDAO = new OficioDAO();

			oficios = oficioDAO.listarSetor(usuarioLogado.getSetor(), usuarioLogado.getUnidade());

			// oficioDAO.loadLast(oficio.getSetorAbertura());

			// oficio.setNumeroOficio(oficioDAO.loadLast(oficio.getSetorAbertura()).getNumeroOficio());

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	public void salvar() {

		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			OficioDAO oficioDAO = new OficioDAO();
			OficioAnoDAO oficioanoDAO = new OficioAnoDAO();

			oficio.setStatus("Em Aberto");
			oficio.setUsuario(usuarioLogado);
			oficio.setUnidadeAbertura(usuarioLogado.getUnidade());

			System.out.println(usuarioLogado.getUnidade());
			System.out.println(oficio.getUnidadeAbertura());

			int ano = (new Date().getYear());
			int anoOficio = (new Date().getYear() + 1900);

			if (oficioDAO.loadSetor(oficio.getSetorAbertura().getCodigo(), usuarioLogado.getUnidade()).isEmpty()) {
				oficio.setNumeroOficio(1);
			} else {
				oficio.setDataOficio(
						oficioDAO.loadLast(oficio.getSetorAbertura(), usuarioLogado.getUnidade()).getDataOficio());

				System.out.println(
						oficioDAO.loadLast(oficio.getSetorAbertura(), usuarioLogado.getUnidade()).getDataOficio());

				if (oficio.getDataOficio().getYear() != ano) {
					oficio.setNumeroOficio(1);

					System.out.println(anoOficio);
					System.out.println(oficioanoDAO.loadAno(Ano).getOficioAno());

					if (anoOficio != oficioanoDAO.loadAno(Ano).getOficioAno()) {

						Ano = new OficioAno();

						Ano.setOficioAno(new Date().getYear() + 1900);
						oficioanoDAO.merge(Ano);

					}

				} else {

					oficio.setNumeroOficio(
							oficioDAO.loadLast(oficio.getSetorAbertura(), usuarioLogado.getUnidade()).getNumeroOficio()
									+ 1);
				}
			}
			oficio.setDataOficio(new Date());
			oficio.setOficio(oficio.getNumeroOficio() + "/" + oficio.getUnidadeAbertura().getUnidade() + "/"
					+ oficio.getSetorAbertura().getSetor() + "/" + (oficio.getDataOficio().getYear() + 1900));

			oficioDAO.merge(oficio);

			
			
			
			oficiosMenuCount = oficioDAO.carregarOficiosEmAberto("Em Aberto", usuarioLogado.getSetor(), usuarioLogado.getUnidade());
			

			Messages.addGlobalInfo("Ofício salvo com sucesso.");
			
			//oficios = oficioDAO.listarSetor(usuarioLogado.getSetor(), usuarioLogado.getUnidade());
			
			this.refresh();
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Ofício");
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

	public void salvarEditar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			OficioDAO oficioDAO = new OficioDAO();

			oficioDAO.merge(oficio);

			oficio = new Oficio();
			oficios = oficioDAO.listarSetor(usuarioLogado.getSetor(), usuarioLogado.getUnidade());

			Messages.addGlobalInfo("Ofício editado com sucesso.");
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Ofício");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			oficio = (Oficio) evento.getComponent().getAttributes().get("oficioSelecionado");

			OficioDAO oficioDAO = new OficioDAO();
			oficioDAO.excluir(oficio);

			OficioBean.this.listar();

			Messages.addGlobalInfo("Recurso de Multa removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Ofício.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		oficio = (Oficio) evento.getComponent().getAttributes().get("oficioSelecionado");
	}

	public void successListener() {
		Messages.addGlobalInfo("Copiado com sucesso.");
	}

	public void errorListener(final ClipboardErrorEvent errorEvent) {
		final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
				"Component id: " + errorEvent.getComponent().getId() + " Action: " + errorEvent.getAction());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void cancelaOficio() {

		try {

			if (oficio.getStatus().equals("Cancelado")) {
				Messages.addGlobalInfo("O Ofício já está cancelado.");

			} else if (oficio.getStatus().equals("Encaminhado")) {
				Messages.addGlobalInfo("Ofício com saída não pode ser cancelado.");

			} else {

				HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
						.getSession(false);
				usuarioLogado = (Usuario) sessao.getAttribute("usuario");

				OficioDAO oficioDAO = new OficioDAO();
				oficio.setStatus("Cancelado");
				oficio.setUsuarioCancelamento(usuarioLogado);
				oficio.setDataCancelamento(new Date());
				oficioDAO.merge(oficio);

				OficioBean.this.listar();
				Messages.addGlobalInfo("Ofício cancelado com sucesso.");
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cancelar o Ofício.");
			erro.printStackTrace();
		}
	}

	public List<Oficio> getOficiosInativos() {
		return oficiosInativos;
	}

	public void setOficiosInativos(List<Oficio> oficiosInativos) {
		this.oficiosInativos = oficiosInativos;
	}

	public List<Oficio> getOficiosMenuCount() {
		return oficiosMenuCount;
	}

	public void setOficiosMenuCount(List<Oficio> oficiosMenuCount) {
		this.oficiosMenuCount = oficiosMenuCount;
	}

}

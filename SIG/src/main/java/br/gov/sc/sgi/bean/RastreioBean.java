package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import br.gov.sc.sgi.dao.RastreioDAO;
import br.gov.sc.sgi.dao.RastreioRelacaoDAO;
import br.gov.sc.sgi.dao.UsuarioDAO;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.Rastreio;
import br.gov.sc.sgi.domain.RastreioRelacao;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class RastreioBean implements Serializable {

	private Rastreio recursomultarastreio;
	private List<Rastreio> recursosmultarastreio;

	private List<RastreioRelacao> listaSaida;
	private List<RastreioRelacao> listaRelacao;

	private RastreioRelacao oficioSaida;

	private List<Usuario> usuarios;
	private Usuario usuario;

	private Oficio oficio;
	private List<Oficio> oficios;

	public Rastreio getRecursomultarastreio() {
		return recursomultarastreio;
	}

	public void setRecursomultarastreio(Rastreio recursomultarastreio) {
		this.recursomultarastreio = recursomultarastreio;
	}

	public List<Rastreio> getRecursosmultarastreio() {
		return recursosmultarastreio;
	}

	public void setRecursosmultarastreio(List<Rastreio> recursosmultarastreio) {
		this.recursosmultarastreio = recursosmultarastreio;
	}

	public List<RastreioRelacao> getListaSaida() {
		return listaSaida;
	}

	public void setListaSaida(List<RastreioRelacao> listaSaida) {
		this.listaSaida = listaSaida;
	}

	public RastreioRelacao getOficioSaida() {
		return oficioSaida;
	}

	public void setOficioSaida(RastreioRelacao oficioSaida) {
		this.oficioSaida = oficioSaida;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

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

	@PostConstruct
	public void novo() {
		try {
			System.out.println("rastreiobean");
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuario = (Usuario) sessao.getAttribute("usuario");

			recursomultarastreio = new Rastreio();

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listar();

			OficioDAO oficioDAO = new OficioDAO();
			oficios = oficioDAO.carregarOficiosEmAberto("Em Aberto", usuario.getSetor(), usuario.getUnidade());

			listaSaida = new ArrayList<>();

			RastreioRelacaoDAO rastreioRelacaoDAO = new RastreioRelacaoDAO();
			listaRelacao = rastreioRelacaoDAO.listarRastreio(usuario.getSetor(), usuario.getUnidade());

		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela Saida");
			erro.printStackTrace();
		}
	}

	public void listar() {
		try {
			RastreioDAO recursomultarastreioDAO = new RastreioDAO();
			recursosmultarastreio = recursomultarastreioDAO.listar("codigo");

			RastreioRelacaoDAO recursomultarastreioRelacaoDAO = new RastreioRelacaoDAO();
			listaSaida = recursomultarastreioRelacaoDAO.listar("codigo");
			
			
			listaSaida = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Processos.");
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {
		Oficio oficio = (Oficio) evento.getComponent().getAttributes().get("oficioSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < listaSaida.size(); posicao++) {
			if (listaSaida.get(posicao).getOficio().equals(oficio)) {
				achou = posicao;
			}
		}

		if (achou < 0) {

			RastreioRelacao oficioSaida = new RastreioRelacao();
			oficioSaida.setOficio(oficio);

			listaSaida.add(oficioSaida);

			System.out.println(listaSaida.size());

		}
	}

	public void remover(ActionEvent evento) {
		RastreioRelacao oficioSaida = (RastreioRelacao) evento.getComponent().getAttributes().get("oficioSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < listaSaida.size(); posicao++) {
			if (listaSaida.get(posicao).getOficio().equals(oficioSaida.getOficio())) {
				achou = posicao;
			}
		}
		if (achou > -1) {
			listaSaida.remove(achou);
		}

	}

	public void finalizar() {
		try {
			usuario = new Usuario();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar a Saida");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (listaSaida.isEmpty()) {
				Messages.addGlobalError("Selecione pelo menos um ofício para dar Saida.");
			} else {

				recursomultarastreio.setUsuario(usuario);
				recursomultarastreio.setDataSaida(new Date());

				if (recursomultarastreio.getRastreio() == null) {
					Messages.addGlobalError("Informe um ofício para dar Saida");
					return;
				}

				RastreioDAO recursoMultaRastreioDAO = new RastreioDAO();
				recursoMultaRastreioDAO.salvarRastreio(recursomultarastreio, listaSaida);

				RastreioBean.this.novo();

				Messages.addGlobalInfo("Saida realizada com sucesso");
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a Saida");
			erro.printStackTrace();
		}
	}

	public List<RastreioRelacao> getListaRelacao() {
		return listaRelacao;
	}

	public void setListaRelacao(List<RastreioRelacao> listaRelacao) {
		this.listaRelacao = listaRelacao;
	}
}

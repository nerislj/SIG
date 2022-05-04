package br.gov.sc.sgi.bean;

import java.io.Serializable;
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
import br.gov.sc.sgi.dao.RecursoMultaAnoDAO;
import br.gov.sc.sgi.dao.RecursoMultaDAO;
import br.gov.sc.sgi.dao.RecursoMultaTiposDAO;
import br.gov.sc.sgi.dao.RecursoMultaTramitaDAO;
import br.gov.sc.sgi.domain.OficioAno;
import br.gov.sc.sgi.domain.RecursoMulta;
import br.gov.sc.sgi.domain.RecursoMultaAno;
import br.gov.sc.sgi.domain.RecursoMultaTipos;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class RecursoMultaBean implements Serializable {

	private RecursoMulta recursomulta;
	private RecursoMulta recursoMultaUltimoCadastrado;
	private List<RecursoMulta> recursosmultas;
	private List<RecursoMulta> recursosmultasCancelados;
	private List<RecursoMultaTipos> recursosmultastipos;
	private RecursoMultaAno Ano;
	private List<RecursoMultaAno> Anos;


	private Usuario usuarioLogado;

	public RecursoMulta getRecursomulta() {
		return recursomulta;
	}

	public void setRecursomulta(RecursoMulta recursomulta) {
		this.recursomulta = recursomulta;
	}

	public List<RecursoMulta> getRecursosmultas() {
		return recursosmultas;
	}

	public void setRecursosmultas(List<RecursoMulta> recursosmultas) {
		this.recursosmultas = recursosmultas;
	}

	public List<RecursoMultaTipos> getRecursosmultastipos() {
		return recursosmultastipos;
	}

	public void setRecursosmultastipos(List<RecursoMultaTipos> recursosmultastipos) {
		this.recursosmultastipos = recursosmultastipos;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	public List<RecursoMulta> getRecursosmultasCancelados() {
		return recursosmultasCancelados;
	}

	public void setRecursosmultasCancelados(List<RecursoMulta> recursosmultasCancelados) {
		this.recursosmultasCancelados = recursosmultasCancelados;
	}
	
	public List<RecursoMultaAno> getAnos() {
		return Anos;
	}

	public void setAnos(List<RecursoMultaAno> anos) {
		Anos = anos;
	}

	public RecursoMultaAno getAno() {
		return Ano;
	}

	public void setAno(RecursoMultaAno ano) {
		Ano = ano;
	}


	@PostConstruct
	public void listar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");
			
			RecursoMultaDAO recursomultaDAO = new RecursoMultaDAO();
			RecursoMultaAnoDAO recursoMultaAnoDAO = new RecursoMultaAnoDAO();
			
			Anos = recursoMultaAnoDAO.loadAnos();

			int anoHoje = new Date().getYear() + 1900;
			
			recursosmultas = recursomultaDAO.listarAtivos(usuarioLogado.getSetor(), usuarioLogado.getUnidade(), anoHoje);
			
			recursosmultasCancelados = recursomultaDAO.listarInativos(usuarioLogado.getSetor(), usuarioLogado.getUnidade());
			
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Processo de Multa.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		recursomulta = new RecursoMulta();

		RecursoMultaTiposDAO recursomultatiposDAO = new RecursoMultaTiposDAO();
		
		recursosmultastipos = recursomultatiposDAO.listar("tipoProcesso");
	}

	public void salvar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			RecursoMultaDAO recursomultaDAO = new RecursoMultaDAO();
			recursomulta.setStatus("Em Aberto");
			recursomulta.setUsuarioCadastro(usuarioLogado);
			recursomulta.setDataCadastro(new Date());
			recursomulta.setUnidadeAbertura(usuarioLogado.getUnidade());
			recursomulta.setSetorAbertura(usuarioLogado.getSetor());
			recursomulta.setUnidadeAtual(usuarioLogado.getUnidade());
			recursomulta.setSetorAtual(usuarioLogado.getSetor());

			recursomultaDAO.merge(recursomulta);

			recursomulta = new RecursoMulta();
			RecursoMultaBean.this.listar();
			
			recursoMultaUltimoCadastrado = recursomultaDAO.loadLast(usuarioLogado.getSetor(), usuarioLogado.getUnidade());
			
			System.out.println(recursoMultaUltimoCadastrado);

			Messages.addGlobalInfo("Recurso de Multa cadastrado com Sucesso!");
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Recurso de Multa.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		recursomulta = (RecursoMulta) evento.getComponent().getAttributes().get("recursomultaSelecionado");
	}

	public void cancelaRecurso() {

		try {

			RecursoMultaDAO recursomultaDAO = new RecursoMultaDAO();
			if (recursomulta.getStatus().equals("Cancelado")) {
				Messages.addGlobalInfo("O recurso já está cancelado.");

			} else if (recursomulta.getStatus().equals("Encaminhado")) {
				Messages.addGlobalInfo("Recurso com saída não pode ser cancelado.");

			} else {
				
				HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
				usuarioLogado = (Usuario) sessao.getAttribute("usuario");
				
				recursomulta.setDataCancelamento(new Date());
				recursomulta.setUsuarioCancelamento(usuarioLogado);
				recursomulta.setStatus("Cancelado");
				recursomultaDAO.merge(recursomulta);
				
				RecursoMultaTramitaDAO recursoMultaTramitaDAO = new RecursoMultaTramitaDAO();
				recursoMultaTramitaDAO.cancelaProcesso(recursomulta, usuarioLogado);

				RecursoMultaBean.this.listar();
				Messages.addGlobalInfo("Recurso cancelado com sucesso.");
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cancelar o Recurso.");
			erro.printStackTrace();
		}
	}
	
	public void consultaPorAno() {
		try {
			RecursoMultaDAO recursoMultaDAO = new RecursoMultaDAO();

			recursosmultas = recursoMultaDAO.listarAtivos(usuarioLogado.getSetor(), usuarioLogado.getUnidade(), Ano.getRecursoMultaAno());

		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Ofícios.");
			erro.printStackTrace();
		}
	}

	public RecursoMulta getRecursoMultaUltimoCadastrado() {
		return recursoMultaUltimoCadastrado;
	}

	public void setRecursoMultaUltimoCadastrado(RecursoMulta recursoMultaUltimoCadastrado) {
		this.recursoMultaUltimoCadastrado = recursoMultaUltimoCadastrado;
	}

	
	

	
}
package br.gov.sc.cetran.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.exolab.castor.types.Date;
import org.omnifaces.util.Messages;

import br.gov.sc.cetran.dao.ProcessoCetranDAO;
import br.gov.sc.cetran.dao.RequerenteDAO;
import br.gov.sc.cetran.dao.SituacaoDAO;
import br.gov.sc.cetran.dao.TipoRequerenteDAO;
import br.gov.sc.cetran.domain.ProcessoCetran;
import br.gov.sc.cetran.domain.Requerente;
import br.gov.sc.cetran.domain.Situacao;
import br.gov.sc.cetran.domain.TipoRequerente;
import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RequerenteBean implements Serializable {

	private Requerente requerente;

	private ProcessoCetran processoCetran;

	private List<ProcessoCetran> listaProcessos;

	private List<TipoRequerente> listaTipoRequerentes;

	private List<Requerente> listaRequerentes;

	private Usuario usuarioLogado;

	private int filtro;
	private boolean mostrarCpf;
	private boolean mostrarCnpj;
	private boolean mostrarOrg;

	@PostConstruct
	public void listar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			RequerenteDAO requerenteDAO = new RequerenteDAO();
			TipoRequerenteDAO tipoRequerenteDAO = new TipoRequerenteDAO();

			listaRequerentes = requerenteDAO.listarTudo();
			listaTipoRequerentes = tipoRequerenteDAO.listarTudo();
			/*
			 * ProcessoCetranDAO processoDAO = new ProcessoCetranDAO();
			 * 
			 * listaProcessos = processoDAO.listarTudo(requerente);
			 */
			requerente = new Requerente();
			processoCetran = new ProcessoCetran();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a Requerente.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		requerente = new Requerente();
	}

	public void salvar() {
		try {

			for (TipoRequerente customer : listaTipoRequerentes) {
				if (customer.getDescricao().equals("EMPRESA") & filtro == 1) {
					System.out.println("TIPO REQUERENTE " + customer);
					requerente.setTipoRequerente(customer);
				}

				if (customer.getDescricao().equals("PESSOA") & filtro == 2) {
					System.out.println(customer);
					System.out.println("TIPO REQUERENTE " + customer);
					requerente.setTipoRequerente(customer);
				}

				if (customer.getDescricao().equals("ORGANIZAÇÃO") & filtro == 3) {
					System.out.println(customer);
					System.out.println("TIPO REQUERENTE " + customer);
					requerente.setTipoRequerente(customer);
				}
			}

			RequerenteDAO requerenteDAO = new RequerenteDAO();
			requerenteDAO.salvarProcesso(requerente, processoCetran, usuarioLogado);

			ProcessoCetranDAO processoDAO = new ProcessoCetranDAO();
			listaProcessos = processoDAO.listarTudo(requerente);
			RequerenteBean.this.listar();
			filtro = 0;

			Messages.addGlobalInfo("Salvo com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			ProcessoCetranDAO processoDAO = new ProcessoCetranDAO();

			processoCetran = (ProcessoCetran) evento.getComponent().getAttributes().get("processoSelecionado");
			
			requerente = processoCetran.getRequerente();

			processoDAO.excluir(processoCetran);

			
			listaProcessos = processoDAO.listarTudo(requerente);

			Messages.addGlobalInfo("Processo removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Processo.");
			erro.printStackTrace();
		}
	}

	public void habilitar() {
		if (filtro == 1) {

			mostrarCpf = false;
			mostrarCnpj = true;
			mostrarOrg = false;

			listaProcessos = new ArrayList<ProcessoCetran>();
			

		}
		if (filtro == 2) {
			mostrarCpf = true;
			mostrarCnpj = false;
			mostrarOrg = false;

			listaProcessos = new ArrayList<ProcessoCetran>();
			
		}
		if (filtro == 3) {
			mostrarCpf = false;
			mostrarCnpj = false;
			mostrarOrg = true;

			listaProcessos = new ArrayList<ProcessoCetran>();
			
		}

	}

	public void buscarCPFCNPJ() {

		RequerenteDAO requerenteDAO = new RequerenteDAO();
		requerente = requerenteDAO.carregarCpf(requerente.getCpf(), requerente.getCnpj());

		ProcessoCetranDAO processoDAO = new ProcessoCetranDAO();

		listaProcessos = processoDAO.listarTudo(requerente);

		System.out.println(listaProcessos);

	}

	public void editar(ActionEvent evento) {

		processoCetran = (ProcessoCetran) evento.getComponent().getAttributes().get("processoSelecionado");
		requerente = processoCetran.getRequerente();

		if (requerente.getCpf() == processoCetran.getRequerente().getCpf()) {
			filtro = 2;

			mostrarCpf = true;
			mostrarCnpj = false;
			mostrarOrg = false;

		} else if (requerente.getCnpj() == processoCetran.getRequerente().getCnpj()) {
			filtro = 1;

			mostrarCpf = false;
			mostrarCnpj = true;
			mostrarOrg = false;

		} else {
			filtro = 3;

			mostrarCpf = false;
			mostrarCnpj = false;
			mostrarOrg = true;
		}

	}

	public Requerente getRequerente() {
		return requerente;
	}

	public void setRequerente(Requerente requerente) {
		this.requerente = requerente;
	}

	public List<Requerente> getListaRequerentes() {
		return listaRequerentes;
	}

	public void setListaRequerentes(List<Requerente> listaRequerentes) {
		this.listaRequerentes = listaRequerentes;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<TipoRequerente> getListaTipoRequerentes() {
		return listaTipoRequerentes;
	}

	public void setListaTipoRequerentes(List<TipoRequerente> listaTipoRequerentes) {
		this.listaTipoRequerentes = listaTipoRequerentes;
	}

	public int getFiltro() {
		return filtro;
	}

	public void setFiltro(int filtro) {
		this.filtro = filtro;
	}

	public boolean isMostrarCpf() {
		return mostrarCpf;
	}

	public void setMostrarCpf(boolean mostrarCpf) {
		this.mostrarCpf = mostrarCpf;
	}

	public boolean isMostrarCnpj() {
		return mostrarCnpj;
	}

	public void setMostrarCnpj(boolean mostrarCnpj) {
		this.mostrarCnpj = mostrarCnpj;
	}

	public boolean isMostrarOrg() {
		return mostrarOrg;
	}

	public void setMostrarOrg(boolean mostrarOrg) {
		this.mostrarOrg = mostrarOrg;
	}

	public ProcessoCetran getProcessoCetran() {
		return processoCetran;
	}

	public void setProcessoCetran(ProcessoCetran processoCetran) {
		this.processoCetran = processoCetran;
	}

	public List<ProcessoCetran> getListaProcessos() {
		return listaProcessos;
	}

	public void setListaProcessos(List<ProcessoCetran> listaProcessos) {
		this.listaProcessos = listaProcessos;
	}

}

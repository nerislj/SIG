package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.dao.UsuarioNivelAcessoDAO;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.UsuarioNivelAcesso;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioNivelAcessoBean implements Serializable {

	private UsuarioNivelAcesso acesso;
	private List<UsuarioNivelAcesso> acessos;
	private List<Setor> setores;
	private List<Unidade> Unidades;
	private Unidade unidade;

	public UsuarioNivelAcesso getAcesso() {
		return acesso;
	}

	public void setAcesso(UsuarioNivelAcesso acesso) {
		this.acesso = acesso;
	}

	public List<UsuarioNivelAcesso> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<UsuarioNivelAcesso> acessos) {
		this.acessos = acessos;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public List<Unidade> getUnidades() {
		return Unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		Unidades = unidades;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	@PostConstruct
	public void listar() {
		try {
			UsuarioNivelAcessoDAO acessoDAO = new UsuarioNivelAcessoDAO();
			acessos = acessoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os acessos.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		acesso = new UsuarioNivelAcesso();
		
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		Unidades = unidadeDAO.listar();
		
		setores = new ArrayList<>();

	}

	public void salvar() {
		try {
			UsuarioNivelAcessoDAO acessoDAO = new UsuarioNivelAcessoDAO();
			acessoDAO.merge(acesso);

			UsuarioNivelAcessoBean.this.listar();

			Messages.addGlobalInfo("Nível de Acesso cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Nível de Acesso.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			acesso = (UsuarioNivelAcesso) evento.getComponent().getAttributes().get("acessoSelecionado");

			UsuarioNivelAcessoDAO acessoDAO = new UsuarioNivelAcessoDAO();
			acessoDAO.excluir(acesso);

			UsuarioNivelAcessoBean.this.listar();

			Messages.addGlobalInfo("Nível de Acesso removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Nível de Acesso.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			acesso = (UsuarioNivelAcesso) evento.getComponent().getAttributes().get("acessoSelecionado");

			SetorDAO setorDAO = new SetorDAO();
			setores = setorDAO.listar();
			
			UnidadeDAO unidadeDAO = new UnidadeDAO();
			Unidades = unidadeDAO.listar();						

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar um Usuário.");
			erro.printStackTrace();
		}
	}

	public void popular() {
		try {
			if (acesso.getUnidade() != null) {
				SetorDAO setorDAO = new SetorDAO();
				setores = setorDAO.buscarPorUnidade(acesso.getUnidade().getCodigo());
			} else {
				setores = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os setores");
			erro.printStackTrace();
		}
	}
}

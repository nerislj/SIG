/* Decompiler 7ms, total 561ms, lines 87 */
package br.gov.sc.contrato.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.contrato.dao.UserClaimsContratoDAO;
import br.gov.sc.contrato.domain.UserClaimsContrato;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.dao.UsuarioDAO;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UserClaimsContratoBean implements Serializable {
	
	private UserClaimsContrato userClaimsContrato;
	private List<UserClaimsContrato> listauserClaimsContratos;
	
	private Usuario usuario;
	private List<Usuario> listaUsuarios;
	
	private Unidade unidade;
	private List<Unidade> listaUnidades;
	
	
	

	public UserClaimsContrato getUserClaimsContrato() {
		return userClaimsContrato;
	}

	public void setUserClaimsContrato(UserClaimsContrato userClaimsContrato) {
		this.userClaimsContrato = userClaimsContrato;
	}

	public List<UserClaimsContrato> getListauserClaimsContratos() {
		return listauserClaimsContratos;
	}

	public void setListauserClaimsContratos(List<UserClaimsContrato> listauserClaimsContratos) {
		this.listauserClaimsContratos = listauserClaimsContratos;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("aqui userclaim contrato");
			
			
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Claims.", new Object[0]);
			var2.printStackTrace();
		}

	}
	
	public void listaruserClaimsContratos() {
		UserClaimsContratoDAO userClaimsDAO = new UserClaimsContratoDAO();
		listauserClaimsContratos = userClaimsDAO.listar();
		
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		listaUnidades = unidadeDAO.listar();
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		listaUsuarios = usuarioDAO.listar();
	}

	public void novo() {
		userClaimsContrato = new UserClaimsContrato();
	}

	public void salvar() {
		try {
			UserClaimsContratoDAO userClaimsDAO = new UserClaimsContratoDAO();
			
			userClaimsContrato.setAtivo(true);
			
			userClaimsDAO.merge(this.userClaimsContrato);
			userClaimsContrato = new UserClaimsContrato();
			this.listauserClaimsContratos = userClaimsDAO.listar();
			Messages.addGlobalInfo("Claims cadastrado com Sucesso!", new Object[0]);
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Claims.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			this.userClaimsContrato = (UserClaimsContrato) evento.getComponent().getAttributes().get("claimSelecionado");
			UserClaimsContratoDAO userClaimsDAO = new UserClaimsContratoDAO();
			userClaimsDAO.excluir(this.userClaimsContrato);
			this.userClaimsContrato = new UserClaimsContrato();
			this.listauserClaimsContratos = userClaimsDAO.listar();
			Messages.addGlobalInfo("Claim removida com sucesso.", new Object[0]);
		} catch (RuntimeException var3) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Claim.", new Object[0]);
			var3.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		this.userClaimsContrato = (UserClaimsContrato) evento.getComponent().getAttributes().get("claimSelecionado");
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public List<Unidade> getListaUnidades() {
		return listaUnidades;
	}

	public void setListaUnidades(List<Unidade> listaUnidades) {
		this.listaUnidades = listaUnidades;
	}
}
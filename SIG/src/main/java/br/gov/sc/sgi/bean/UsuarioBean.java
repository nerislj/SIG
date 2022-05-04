package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.dao.UsuarioDAO;
import br.gov.sc.sgi.dao.UsuarioNivelAcessoDAO;
import br.gov.sc.sgi.dao.UsuarioStatusDAO;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.domain.UsuarioNivelAcesso;
import br.gov.sc.sgi.domain.UsuarioStatus;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private Usuario usuario;
	private List<Usuario> Usuarios;
	private List<PessoaFisica> pessoas;
	private List<UsuarioStatus> usuarioStatus;
	private List<UsuarioNivelAcesso> usuarioNivel;
	private List<Setor> setores;
	private List<Unidade> Unidades;
	private Unidade unidade;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return Usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		Usuarios = usuarios;
	}

	public List<PessoaFisica> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaFisica> pessoas) {
		this.pessoas = pessoas;
	}

	public List<UsuarioStatus> getUsuarioStatus() {
		return usuarioStatus;
	}

	public void setUsuarioStatus(List<UsuarioStatus> usuarioStatus) {
		this.usuarioStatus = usuarioStatus;
	}

	public List<UsuarioNivelAcesso> getUsuarioNivel() {
		return usuarioNivel;
	}

	public void setUsuarioNivel(List<UsuarioNivelAcesso> usuarioNivel) {
		this.usuarioNivel = usuarioNivel;
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
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuarios = usuarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Usuários.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			usuario = new Usuario();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("cpf");

			UsuarioStatusDAO statusDAO = new UsuarioStatusDAO();
			usuarioStatus = statusDAO.listar("status");

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			Unidades = unidadeDAO.listar();
			
			setores = new ArrayList<>();

			usuarioNivel = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();

			SimpleHash hash = new SimpleHash("md5", usuario.getSenha());

			usuario.setSenha(hash.toHex());

			usuarioDAO.merge(usuario);

			UsuarioBean.this.listar();

			Messages.addGlobalInfo("Usuário cadastrado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cadastrar o Usuário.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.excluir(usuario);

			UsuarioBean.this.listar();

			Messages.addGlobalInfo("Usuário removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Usuário.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();

			UsuarioStatusDAO statusDAO = new UsuarioStatusDAO();
			usuarioStatus = statusDAO.listar();

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			Unidades = unidadeDAO.listar();

			SetorDAO setorDAO = new SetorDAO();
			setores = setorDAO.listar();

			UsuarioNivelAcessoDAO acessoDAO = new UsuarioNivelAcessoDAO();
			usuarioNivel = acessoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar um Usuário.");
			erro.printStackTrace();
		}
	}

	public void popular() {
		try {
			if (usuario.getUnidade() != null) {
				SetorDAO setorDAO = new SetorDAO();
				setores = setorDAO.buscarPorUnidade(usuario.getUnidade().getCodigo());
			} else {
				setores = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os setores");
			erro.printStackTrace();
		}
	}

	public void popularNivel() {
		try {
			if (usuario.getSetor() != null) {
				UsuarioNivelAcessoDAO acessoDAO = new UsuarioNivelAcessoDAO();
				usuarioNivel = acessoDAO.buscarPorSetor(usuario.getSetor().getCodigo());
			} else {
				usuarioNivel = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os perfis");
			erro.printStackTrace();
		}
	}
}

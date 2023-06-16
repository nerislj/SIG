package br.gov.sc.sgi.bean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.UsuarioDAO;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Usuario;

@ManagedBean
@SessionScoped
public class AutenticacaoBean {
	private Usuario usuario;
	private Usuario usuarioLogado;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@PostConstruct
	public void iniciar() {
		System.out.println("aqui atenticacaobean");
		usuario = new Usuario();
		usuario.setPessoa(new PessoaFisica());
	}

	public void autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());
			
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			sessao.setAttribute("usuario", usuarioLogado);
			
			
			if (usuarioLogado == null) {
				Messages.addGlobalError("CPF e/ou senha incorretos");
				return;
			}

			// STATUS USUÁRIO
			if (usuarioLogado.getStatus().getCodigo() == 2) {
				Messages.addGlobalError("Usuário inativo");
				return;
			}

			Faces.redirect("./pages/dashboard.xhtml");
		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}

	// Realiza o logout do usuário logado
	public String logout() {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		sessao.invalidate();

		try {
		
			Faces.redirect("./pages/dashboard.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	

	}

	public boolean temPermissoes(List<Long> permissoes) {
		for (Long permissao : permissoes) {
			
		
			if (usuarioLogado.getNivelAcesso().getCodigo() == permissao.longValue()) {
				return true;
			}
		}

		return false;
	}
	
	public boolean temPermissoesPorUnidadeCodigo(List<Long> permissoes) {
		for (Long permissao : permissoes) {
			
		
			if (usuarioLogado.getUnidade().getCodigo() == permissao.longValue()) {
				return true;
			}
		}

		return false;
	}

	public void alterarSenha() {
		try {

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuario = usuarioDAO.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());

			SimpleHash hash = new SimpleHash("md5", usuarioLogado.getSenha());

			usuario.setSenha(hash.toHex());

			usuarioDAO.merge(usuario);

			Messages.addGlobalInfo("Senha alterada com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar alterar a senha.");
			erro.printStackTrace();
		}
	}

}
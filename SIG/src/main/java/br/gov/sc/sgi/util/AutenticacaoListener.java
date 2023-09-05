package br.gov.sc.sgi.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.gov.sc.sgi.bean.AutenticacaoBean;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {
	
	private Usuario usuarioLogado;

	@Override
	public void afterPhase(PhaseEvent event) {
		String paginaAtual = Faces.getViewId();

		boolean ehPaginaDeAutenticacao = paginaAtual.contains("autenticacao.xhtml");

		if (!ehPaginaDeAutenticacao) {
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");

			//PÁGINA SEM LOGIN/AUTENTICAÇÃO
			if (Faces.getRequestURL().contains("/pesquisa/cadastro.xhtml")) {
				
				Faces.navigate("/pages/pesquisa/cadastro.xhtml");
			
			} else if (Faces.getRequestURL().contains("/pages/listatelefonicalistarExterno.xhtml")) {
				
				usuarioLogado = new Usuario();
				
				Faces.navigate("/pages/listatelefonicalistarExterno.xhtml");
			}
			
			
			//OU PÁGINA QUE EXIGE AUTENTICAÇÃO
			else {

				if (autenticacaoBean == null) {
					Faces.navigate("/pages/autenticacao.xhtml");
					return;
				}

				Usuario usuario = autenticacaoBean.getUsuarioLogado();
				if (usuario == null) {
					Faces.navigate("/pages/autenticacao.xhtml");
					return;
				}
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
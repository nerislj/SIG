package br.gov.sc.cetran.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import br.gov.sc.cetran.dao.ConselheiroDAO;
import br.gov.sc.cetran.dao.RepresentacaoDAO;
import br.gov.sc.cetran.domain.Conselheiro;
import br.gov.sc.cetran.domain.Representacao;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConselheiroBean implements Serializable {

	private Conselheiro conselheiro;
	
	
	private Representacao representcao;

	private List<Representacao> listaRepresentacoes;
	private List<Conselheiro> listaConselheiros;
	
	private Usuario usuarioLogado;
	
	
	

	@PostConstruct
	public void listar() {
		try {
			System.out.println("aqui conselheirobean");
			ConselheiroDAO conselheiroDAO = new ConselheiroDAO();
			RepresentacaoDAO representacaoDAO = new RepresentacaoDAO();
		
			listaRepresentacoes = representacaoDAO.listarTudo();
			listaConselheiros = conselheiroDAO.listarTudo();
			
			if(listaConselheiros.isEmpty()) {
				System.out.println("VAZIA");
			}
			
			conselheiro = new Conselheiro();
			
			
			
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Status.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		conselheiro = new Conselheiro();
	}

	public void salvar() {
		try {
			
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");
			
			conselheiro.setUsuarioCadastro(usuarioLogado);
			conselheiro.setDataCadastro(new Date());
			conselheiro.setNome(conselheiro.getNome().toUpperCase());
			
			
			ConselheiroDAO conselheiroDAO = new ConselheiroDAO();
			conselheiroDAO.merge(conselheiro);
			
			ConselheiroBean.this.listar();
			

			Messages.addGlobalInfo("Conselheiro cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Conselheiro.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			
			

			conselheiro = (Conselheiro) evento.getComponent().getAttributes().get("conselheiroSelecionado");	

			ConselheiroDAO conselheiroDAO = new ConselheiroDAO();
			conselheiroDAO.excluir(conselheiro);

			ConselheiroBean.this.listar();
		

			Messages.addGlobalInfo("Conselheiro removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Conselheiro vinculado em um ou mais processos.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		conselheiro = (Conselheiro) evento.getComponent().getAttributes().get("conselheiroSelecionado");		
	}

	public Conselheiro getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(Conselheiro conselheiro) {
		this.conselheiro = conselheiro;
	}

	public List<Conselheiro> getListaConselheiros() {
		return listaConselheiros;
	}

	public void setListaConselheiros(List<Conselheiro> listaConselheiros) {
		this.listaConselheiros = listaConselheiros;
	}

	public List<Representacao> getListaRepresentacoes() {
		return listaRepresentacoes;
	}

	public void setListaRepresentacoes(List<Representacao> listaRepresentacoes) {
		this.listaRepresentacoes = listaRepresentacoes;
	}

	public Representacao getRepresentcao() {
		return representcao;
	}

	public void setRepresentcao(Representacao representcao) {
		this.representcao = representcao;
	}

	


	
	

	
}



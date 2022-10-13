package br.gov.sc.cetran.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.cetran.dao.RepresentacaoDAO;
import br.gov.sc.cetran.dao.SituacaoDAO;
import br.gov.sc.cetran.domain.Representacao;
import br.gov.sc.cetran.domain.Situacao;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RepresentacaoBean implements Serializable {

	private Representacao representacao;
	
	
	
	private List<Representacao> listaRepresentacoes;
	
	
	
	

	@PostConstruct
	public void listar() {
		try {
			System.out.println("aqui representacaobean");
			RepresentacaoDAO representacaoDAO = new RepresentacaoDAO();
			
			listaRepresentacoes = representacaoDAO.listarTudo();
			
			representacao = new Representacao();
			
			
			
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a Representacao.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		representacao = new Representacao();
	}

	public void salvar() {
		try {
			
			
			representacao.setDescricao(representacao.getDescricao().toUpperCase());
			
			RepresentacaoDAO representacaoDAO = new RepresentacaoDAO();
			representacaoDAO.merge(representacao);
			
			RepresentacaoBean.this.listar();
			
			Messages.addGlobalInfo("Representacao cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Representacao.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			
			

			
			
			representacao = (Representacao) evento.getComponent().getAttributes().get("representacaoSelecionado");		

			RepresentacaoDAO representacaoDAO = new RepresentacaoDAO();
			representacaoDAO.excluir(representacao);

			RepresentacaoBean.this.listar();
		

			Messages.addGlobalInfo("Representacao removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Representacao.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		representacao = (Representacao) evento.getComponent().getAttributes().get("representacaoSelecionado");		
	}

	public Representacao getRepresentacao() {
		return representacao;
	}

	public void setRepresentacao(Representacao representacao) {
		this.representacao = representacao;
	}

	public List<Representacao> getListaRepresentacoes() {
		return listaRepresentacoes;
	}

	public void setListaRepresentacoes(List<Representacao> listaRepresentacoes) {
		this.listaRepresentacoes = listaRepresentacoes;
	}





	
	

	
}



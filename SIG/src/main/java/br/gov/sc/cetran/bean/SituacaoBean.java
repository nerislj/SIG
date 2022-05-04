package br.gov.sc.cetran.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.cetran.dao.SituacaoDAO;
import br.gov.sc.cetran.domain.Situacao;
import br.gov.sc.sgi.bean.SetorBean;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.domain.Setor;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class SituacaoBean implements Serializable {

	private Situacao situacao;
	
	
	
	private List<Situacao> listaSituacoes;
	
	
	
	

	@PostConstruct
	public void listar() {
		try {
			SituacaoDAO situacaoDAO = new SituacaoDAO();
			
			listaSituacoes = situacaoDAO.listarTudo();
			
			situacao = new Situacao();
			
			
			
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a Situacao.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		situacao = new Situacao();
	}

	public void salvar() {
		try {
			
			
			situacao.setDescricao(situacao.getDescricao().toUpperCase());
			
			SituacaoDAO situacaoDAO = new SituacaoDAO();
			situacaoDAO.merge(situacao);
			
			SituacaoBean.this.listar();
			

			Messages.addGlobalInfo("Situacao cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Situacao.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			
			situacao = (Situacao) evento.getComponent().getAttributes().get("situacaoSelecionado");	

			SituacaoDAO situacaoDAO = new SituacaoDAO();
			situacaoDAO.excluir(situacao);

			SituacaoBean.this.listar();

			
		

			Messages.addGlobalInfo("Situacao removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Situação vinculada em um ou mais processos.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		situacao = (Situacao) evento.getComponent().getAttributes().get("situacaoSelecionado");		
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public List<Situacao> getListaSituacoes() {
		return listaSituacoes;
	}

	public void setListaSituacoes(List<Situacao> listaSituacoes) {
		this.listaSituacoes = listaSituacoes;
	}

	





	
	

	
}



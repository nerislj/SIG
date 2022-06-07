package br.gov.sc.codet.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.codet.dao.SituacaoProcessoDAO;

import br.gov.sc.codet.domain.SituacaoProcesso;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class SituacaoProcessoBean implements Serializable {

	private SituacaoProcesso situacaoProcesso;
	private List<SituacaoProcesso> listaSituacoesProcessos;

	

	

	


	public SituacaoProcesso getsituacaoProcesso() {
		return situacaoProcesso;
	}

	public void setsituacaoProcesso(SituacaoProcesso situacaoProcesso) {
		this.situacaoProcesso = situacaoProcesso;
	}

	public List<SituacaoProcesso> getlistaSituacoesProcessos() {
		return listaSituacoesProcessos;
	}

	public void setlistaSituacoesProcessos(List<SituacaoProcesso> listaSituacoesProcessos) {
		this.listaSituacoesProcessos = listaSituacoesProcessos;
	}

	@PostConstruct
	public void listar() {
		try {
			SituacaoProcessoDAO situacaoProcessoDAO = new SituacaoProcessoDAO();
			listaSituacoesProcessos = situacaoProcessoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Ações.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		situacaoProcesso = new SituacaoProcesso();
	}

	public void salvar() {
		try {
			SituacaoProcessoDAO situacaoProcessoDAO = new SituacaoProcessoDAO();
			situacaoProcesso.setDescricao(situacaoProcesso.getDescricao().toUpperCase());
			
			situacaoProcessoDAO.merge(situacaoProcesso);

			situacaoProcesso = new SituacaoProcesso();
			listaSituacoesProcessos = situacaoProcessoDAO.listar();

			Messages.addGlobalInfo("Ação cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Ação.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			situacaoProcesso = (SituacaoProcesso) evento.getComponent().getAttributes().get("decisaoSelecionado");

			SituacaoProcessoDAO situacaoProcessoDAO = new SituacaoProcessoDAO();
			situacaoProcessoDAO.excluir(situacaoProcesso);

			situacaoProcesso = new SituacaoProcesso();
			listaSituacoesProcessos = situacaoProcessoDAO.listar();

			Messages.addGlobalInfo("Ação removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Ação.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		situacaoProcesso = (SituacaoProcesso) evento.getComponent().getAttributes().get("decisaoSelecionado");		
	}
}



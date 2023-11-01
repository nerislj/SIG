/* Decompiler 7ms, total 561ms, lines 87 */
package br.gov.sc.funcionariosuf.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.funcionariosuf.dao.OrigemEstagiariosDAO;
import br.gov.sc.funcionariosuf.domain.OrigemEstagiarios;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class OrigemEstagiariosBean implements Serializable {
	private OrigemEstagiarios origemEstagiarios;
	private List<OrigemEstagiarios> listaOrigemEstagiarios;

	@PostConstruct
	public void listar() {
		try {

			OrigemEstagiariosDAO cargoDAO = new OrigemEstagiariosDAO();
			listaOrigemEstagiarios = cargoDAO.listar();
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a Origem.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void novo() {
		origemEstagiarios = new OrigemEstagiarios();
	}

	public void salvar() {
		try {
			OrigemEstagiariosDAO cargoDAO = new OrigemEstagiariosDAO();
			this.origemEstagiarios.setDescricao(this.origemEstagiarios.getDescricao().toUpperCase());
			this.origemEstagiarios.setDataCadastro(new Date());
			cargoDAO.merge(this.origemEstagiarios);
			this.origemEstagiarios = new OrigemEstagiarios();
			this.listaOrigemEstagiarios = cargoDAO.listar();
			Messages.addGlobalInfo("Origem cadastrada com Sucesso!", new Object[0]);
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Origem.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			this.origemEstagiarios = (OrigemEstagiarios) evento.getComponent().getAttributes().get("origemSelecionado");
			OrigemEstagiariosDAO cargoDAO = new OrigemEstagiariosDAO();
			cargoDAO.excluir(this.origemEstagiarios);
			this.origemEstagiarios = new OrigemEstagiarios();
			this.listaOrigemEstagiarios = cargoDAO.listar();
			Messages.addGlobalInfo("Origem removida com sucesso.", new Object[0]);
		} catch (RuntimeException var3) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Origem.", new Object[0]);
			var3.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		this.origemEstagiarios = (OrigemEstagiarios) evento.getComponent().getAttributes().get("origemSelecionado");
	}

	public OrigemEstagiarios getOrigemEstagiarios() {
		return origemEstagiarios;
	}

	public void setOrigemEstagiarios(OrigemEstagiarios origemEstagiarios) {
		this.origemEstagiarios = origemEstagiarios;
	}

	public List<OrigemEstagiarios> getListaOrigemEstagiarios() {
		return listaOrigemEstagiarios;
	}

	public void setListaOrigemEstagiarios(List<OrigemEstagiarios> listaOrigemEstagiarios) {
		this.listaOrigemEstagiarios = listaOrigemEstagiarios;
	}

	
}
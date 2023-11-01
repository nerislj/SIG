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

import br.gov.sc.funcionariosuf.dao.FuncaoServidoresDAO;
import br.gov.sc.funcionariosuf.domain.CargoServidores;
import br.gov.sc.funcionariosuf.domain.FuncaoServidores;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncaoServidoresBean implements Serializable {
	private FuncaoServidores funcaoServidores;
	private List<FuncaoServidores> listaFuncaoServidores;

	@PostConstruct
	public void listar() {
		try {

			FuncaoServidoresDAO cargoDAO = new FuncaoServidoresDAO();
			listaFuncaoServidores = cargoDAO.listar();
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Funções.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void novo() {
		funcaoServidores = new FuncaoServidores();
	}

	public void salvar() {
		try {
			FuncaoServidoresDAO cargoDAO = new FuncaoServidoresDAO();
			this.funcaoServidores.setDescricao(this.funcaoServidores.getDescricao().toUpperCase());
			this.funcaoServidores.setDataCadastro(new Date());
			cargoDAO.merge(this.funcaoServidores);
			this.funcaoServidores = new FuncaoServidores();
			this.listaFuncaoServidores = cargoDAO.listar();
			Messages.addGlobalInfo("Função cadastrado com Sucesso!", new Object[0]);
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Função.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			this.funcaoServidores = (FuncaoServidores) evento.getComponent().getAttributes().get("funcaoSelecionado");
			FuncaoServidoresDAO cargoDAO = new FuncaoServidoresDAO();
			cargoDAO.excluir(this.funcaoServidores);
			this.funcaoServidores = new FuncaoServidores();
			this.listaFuncaoServidores = cargoDAO.listar();
			Messages.addGlobalInfo("Função removida com sucesso.", new Object[0]);
		} catch (RuntimeException var3) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Função.", new Object[0]);
			var3.printStackTrace();
		}

	}
	
	public void editar(ActionEvent evento) {
		this.funcaoServidores = (FuncaoServidores) evento.getComponent().getAttributes().get("funcaoSelecionado");
	}


	public List<FuncaoServidores> getListaFuncaoServidores() {
		return listaFuncaoServidores;
	}

	public void setListaFuncaoServidores(List<FuncaoServidores> listaFuncaoServidores) {
		this.listaFuncaoServidores = listaFuncaoServidores;
	}

	public FuncaoServidores getFuncaoServidores() {
		return funcaoServidores;
	}

	public void setFuncaoServidores(FuncaoServidores funcaoServidores) {
		this.funcaoServidores = funcaoServidores;
	}


	
	
}
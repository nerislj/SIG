package br.gov.sc.codet.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.codet.dao.NomenclaturaProcessoDAO;
import br.gov.sc.codet.domain.NomenclaturaProcesso;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class NomenclaturaProcessoBean implements Serializable {

	private NomenclaturaProcesso nomenclaturaProcesso;
	private List<NomenclaturaProcesso> listaNomenclaturasProcessos;

	

	

	public NomenclaturaProcesso getnomenclaturaProcesso() {
		return nomenclaturaProcesso;
	}

	public void setnomenclaturaProcesso(NomenclaturaProcesso nomenclaturaProcesso) {
		this.nomenclaturaProcesso = nomenclaturaProcesso;
	}

	public List<NomenclaturaProcesso> getlistaNomenclaturasProcessos() {
		return listaNomenclaturasProcessos;
	}

	public void setlistaNomenclaturasProcessos(List<NomenclaturaProcesso> listaNomenclaturasProcessos) {
		this.listaNomenclaturasProcessos = listaNomenclaturasProcessos;
	}

	@PostConstruct
	public void listar() {
		try {
			NomenclaturaProcessoDAO nomenclaturaProcessoDAO = new NomenclaturaProcessoDAO();
			listaNomenclaturasProcessos = nomenclaturaProcessoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Ações.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		nomenclaturaProcesso = new NomenclaturaProcesso();
	}

	public void salvar() {
		try {
			NomenclaturaProcessoDAO nomenclaturaProcessoDAO = new NomenclaturaProcessoDAO();
			nomenclaturaProcesso.setDescricao(nomenclaturaProcesso.getDescricao().toUpperCase());
			
			nomenclaturaProcessoDAO.merge(nomenclaturaProcesso);

			nomenclaturaProcesso = new NomenclaturaProcesso();
			listaNomenclaturasProcessos = nomenclaturaProcessoDAO.listar();

			Messages.addGlobalInfo("Ação cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Ação.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			nomenclaturaProcesso = (NomenclaturaProcesso) evento.getComponent().getAttributes().get("acaoSelecionado");

			NomenclaturaProcessoDAO nomenclaturaProcessoDAO = new NomenclaturaProcessoDAO();
			nomenclaturaProcessoDAO.excluir(nomenclaturaProcesso);

			nomenclaturaProcesso = new NomenclaturaProcesso();
			listaNomenclaturasProcessos = nomenclaturaProcessoDAO.listar();

			Messages.addGlobalInfo("Ação removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Ação.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		nomenclaturaProcesso = (NomenclaturaProcesso) evento.getComponent().getAttributes().get("acaoSelecionado");		
	}
}



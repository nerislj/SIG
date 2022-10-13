package br.gov.sc.codet.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.codet.dao.PenalidadeProcessoDAO;
import br.gov.sc.codet.domain.PenalidadeProcesso;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PenalidadeProcessoBean implements Serializable {

	private PenalidadeProcesso penalidadeProcesso;
	private List<PenalidadeProcesso> listaPenalidadesProcesso;
	
	



	public PenalidadeProcesso getPenalidadeProcesso() {
		return penalidadeProcesso;
	}

	public void setPenalidadeProcesso(PenalidadeProcesso penalidadeProcesso) {
		this.penalidadeProcesso = penalidadeProcesso;
	}

	public List<PenalidadeProcesso> getListaPenalidadesProcesso() {
		return listaPenalidadesProcesso;
	}

	public void setListaPenalidadesProcesso(List<PenalidadeProcesso> listaPenalidadesProcesso) {
		this.listaPenalidadesProcesso = listaPenalidadesProcesso;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("penalidadeprocesso bean");
			PenalidadeProcessoDAO penalidadeDAO = new PenalidadeProcessoDAO();
			listaPenalidadesProcesso = penalidadeDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Penalidadees.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		penalidadeProcesso = new PenalidadeProcesso();
	}

	public void salvar() {
		try {
			PenalidadeProcessoDAO penalidadeDAO = new PenalidadeProcessoDAO();
			penalidadeProcesso.setDescricao(penalidadeProcesso.getDescricao().toUpperCase());
			
			penalidadeDAO.merge(penalidadeProcesso);

			penalidadeProcesso = new PenalidadeProcesso();
			listaPenalidadesProcesso = penalidadeDAO.listar();

			Messages.addGlobalInfo("Penalidade cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Penalidade.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			penalidadeProcesso = (PenalidadeProcesso) evento.getComponent().getAttributes().get("penalidadeSelecionado");

			PenalidadeProcessoDAO penalidadeDAO = new PenalidadeProcessoDAO();
			penalidadeDAO.excluir(penalidadeProcesso);

			penalidadeProcesso = new PenalidadeProcesso();
			listaPenalidadesProcesso = penalidadeDAO.listar();

			Messages.addGlobalInfo("Penalidade removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Penalidade.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		penalidadeProcesso = (PenalidadeProcesso) evento.getComponent().getAttributes().get("penalidadeSelecionado");		
	}
}



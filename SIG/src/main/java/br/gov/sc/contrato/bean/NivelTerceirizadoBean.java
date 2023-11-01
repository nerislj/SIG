/* Decompiler 7ms, total 561ms, lines 87 */
package br.gov.sc.contrato.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.contrato.dao.CargoTerceirizadoDAO;
import br.gov.sc.contrato.dao.NivelTerceirizadoDAO;
import br.gov.sc.contrato.domain.CargoTerceirizado;
import br.gov.sc.contrato.domain.NivelTerceirizado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class NivelTerceirizadoBean implements Serializable {
	private NivelTerceirizado cargoTerceirizado;
	private List<NivelTerceirizado> listaNivelTerceirizado;

	

	@PostConstruct
	public void listar() {
		try {
			
			NivelTerceirizadoDAO cargoDAO = new NivelTerceirizadoDAO();
			this.listaNivelTerceirizado = cargoDAO.listar();
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Niveis.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void novo() {
		this.cargoTerceirizado = new NivelTerceirizado();
	}

	public void salvar() {
		try {
			NivelTerceirizadoDAO cargoDAO = new NivelTerceirizadoDAO();
			this.cargoTerceirizado.setDescricao(this.cargoTerceirizado.getDescricao().toUpperCase());
			this.cargoTerceirizado.setDataCadastro(new Date());
			cargoDAO.merge(this.cargoTerceirizado);
			this.cargoTerceirizado = new NivelTerceirizado();
			this.listaNivelTerceirizado = cargoDAO.listar();
			Messages.addGlobalInfo("Nível cadastrado com Sucesso!", new Object[0]);
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Nível.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			this.cargoTerceirizado = (NivelTerceirizado) evento.getComponent().getAttributes().get("nivelSelecionado");
			NivelTerceirizadoDAO cargoDAO = new NivelTerceirizadoDAO();
			cargoDAO.excluir(this.cargoTerceirizado);
			this.cargoTerceirizado = new NivelTerceirizado();
			this.listaNivelTerceirizado = cargoDAO.listar();
			Messages.addGlobalInfo("Nível removida com sucesso.", new Object[0]);
		} catch (RuntimeException var3) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Nível.", new Object[0]);
			var3.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		this.cargoTerceirizado = (NivelTerceirizado) evento.getComponent().getAttributes().get("nivelSelecionado");
	}

	public NivelTerceirizado getCargoTerceirizado() {
		return cargoTerceirizado;
	}

	public void setCargoTerceirizado(NivelTerceirizado cargoTerceirizado) {
		this.cargoTerceirizado = cargoTerceirizado;
	}

	public List<NivelTerceirizado> getListaNivelTerceirizado() {
		return listaNivelTerceirizado;
	}

	public void setListaNivelTerceirizado(List<NivelTerceirizado> listaNivelTerceirizado) {
		this.listaNivelTerceirizado = listaNivelTerceirizado;
	}
	
	
}
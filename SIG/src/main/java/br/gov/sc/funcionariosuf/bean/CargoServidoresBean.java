/* Decompiler 7ms, total 561ms, lines 87 */
package br.gov.sc.funcionariosuf.bean;

import br.gov.sc.contrato.dao.CargoTerceirizadoDAO;
import br.gov.sc.contrato.domain.CargoTerceirizado;
import br.gov.sc.funcionariosuf.dao.CargoServidoresDAO;
import br.gov.sc.funcionariosuf.domain.CargoServidores;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CargoServidoresBean implements Serializable {
	private CargoServidores cargoServidores;
	private List<CargoServidores> listaCargoServidores;

	@PostConstruct
	public void listar() {
		try {

			CargoServidoresDAO cargoDAO = new CargoServidoresDAO();
			listaCargoServidores = cargoDAO.listar();
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Cargos.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void novo() {
		cargoServidores = new CargoServidores();
	}

	public void salvar() {
		try {
			CargoServidoresDAO cargoDAO = new CargoServidoresDAO();
			this.cargoServidores.setDescricao(this.cargoServidores.getDescricao().toUpperCase());
			this.cargoServidores.setDataCadastro(new Date());
			cargoDAO.merge(this.cargoServidores);
			this.cargoServidores = new CargoServidores();
			this.listaCargoServidores = cargoDAO.listar();
			Messages.addGlobalInfo("Cargo cadastrado com Sucesso!", new Object[0]);
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Cargo.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			this.cargoServidores = (CargoServidores) evento.getComponent().getAttributes().get("cargoSelecionado");
			CargoServidoresDAO cargoDAO = new CargoServidoresDAO();
			cargoDAO.excluir(this.cargoServidores);
			this.cargoServidores = new CargoServidores();
			this.listaCargoServidores = cargoDAO.listar();
			Messages.addGlobalInfo("Cargo removida com sucesso.", new Object[0]);
		} catch (RuntimeException var3) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Cargo.", new Object[0]);
			var3.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		this.cargoServidores = (CargoServidores) evento.getComponent().getAttributes().get("cargoSelecionado");
	}

	public CargoServidores getCargoServidores() {
		return cargoServidores;
	}

	public void setCargoServidores(CargoServidores cargoServidores) {
		this.cargoServidores = cargoServidores;
	}

	public List<CargoServidores> getListaCargoServidores() {
		return listaCargoServidores;
	}

	public void setListaCargoServidores(List<CargoServidores> listaCargoServidores) {
		this.listaCargoServidores = listaCargoServidores;
	}
	
	
}
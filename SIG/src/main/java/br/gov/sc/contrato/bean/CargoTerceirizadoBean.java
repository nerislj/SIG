/* Decompiler 7ms, total 561ms, lines 87 */
package br.gov.sc.contrato.bean;

import br.gov.sc.contrato.dao.CargoTerceirizadoDAO;
import br.gov.sc.contrato.domain.CargoTerceirizado;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;

@ManagedBean
@ViewScoped
public class CargoTerceirizadoBean implements Serializable {
	private CargoTerceirizado cargoTerceirizado;
	private List<CargoTerceirizado> listaCargosTerceirizado;

	public CargoTerceirizado getCargoTerceirizado() {
		return this.cargoTerceirizado;
	}

	public void setCargoTerceirizado(CargoTerceirizado cargoTerceirizado) {
		this.cargoTerceirizado = cargoTerceirizado;
	}

	public List<CargoTerceirizado> getListaCargosTerceirizado() {
		return this.listaCargosTerceirizado;
	}

	public void setListaCargosTerceirizado(List<CargoTerceirizado> listaCargosTerceirizado) {
		this.listaCargosTerceirizado = listaCargosTerceirizado;
	}

	@PostConstruct
	public void listar() {
		try {
			CargoTerceirizadoDAO cargoDAO = new CargoTerceirizadoDAO();
			this.listaCargosTerceirizado = cargoDAO.listar();
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Cargos.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void novo() {
		this.cargoTerceirizado = new CargoTerceirizado();
	}

	public void salvar() {
		try {
			CargoTerceirizadoDAO cargoDAO = new CargoTerceirizadoDAO();
			this.cargoTerceirizado.setDescricao(this.cargoTerceirizado.getDescricao().toUpperCase());
			this.cargoTerceirizado.setDataCadastro(new Date());
			cargoDAO.merge(this.cargoTerceirizado);
			this.cargoTerceirizado = new CargoTerceirizado();
			this.listaCargosTerceirizado = cargoDAO.listar();
			Messages.addGlobalInfo("Cargo cadastrado com Sucesso!", new Object[0]);
		} catch (RuntimeException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Cargo.", new Object[0]);
			var2.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			this.cargoTerceirizado = (CargoTerceirizado) evento.getComponent().getAttributes().get("cargoSelecionado");
			CargoTerceirizadoDAO cargoDAO = new CargoTerceirizadoDAO();
			cargoDAO.excluir(this.cargoTerceirizado);
			this.cargoTerceirizado = new CargoTerceirizado();
			this.listaCargosTerceirizado = cargoDAO.listar();
			Messages.addGlobalInfo("Cargo removida com sucesso.", new Object[0]);
		} catch (RuntimeException var3) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Cargo.", new Object[0]);
			var3.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		this.cargoTerceirizado = (CargoTerceirizado) evento.getComponent().getAttributes().get("cargoSelecionado");
	}
}
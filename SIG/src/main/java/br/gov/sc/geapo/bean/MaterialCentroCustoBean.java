package br.gov.sc.geapo.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.geapo.dao.MaterialCentroCustoDAO;
import br.gov.sc.geapo.domain.MaterialCentroCusto;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MaterialCentroCustoBean implements Serializable {

	private MaterialCentroCusto MaterialCentroCusto;
	private List<MaterialCentroCusto> listaCusto;
	private List<Unidade> listaUnidades;

	public List<Unidade> getListaUnidades() {
		return listaUnidades;
	}

	public void setListaUnidades(List<Unidade> listaUnidades) {
		this.listaUnidades = listaUnidades;
	}

	public MaterialCentroCusto getMaterialCentroCusto() {
		return MaterialCentroCusto;
	}

	public void setMaterialCentroCusto(MaterialCentroCusto materialCentroCusto) {
		MaterialCentroCusto = materialCentroCusto;
	}

	public List<MaterialCentroCusto> getListaCusto() {
		return listaCusto;
	}

	public void setListaCusto(List<MaterialCentroCusto> listaCusto) {
		this.listaCusto = listaCusto;
	}

	@PostConstruct
	public void listar() {
		try {
			MaterialCentroCustoDAO MaterialCentroCustoDAO = new MaterialCentroCustoDAO();
			listaCusto = MaterialCentroCustoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Tipos.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {

			MaterialCentroCusto = new MaterialCentroCusto();

			UnidadeDAO unidadeDAO = new UnidadeDAO();
			listaUnidades = unidadeDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu ao gerar listagem.");
			erro.printStackTrace();
		}

	}

	public void salvar() {
		try {
			MaterialCentroCustoDAO MaterialCentroCustoDAO = new MaterialCentroCustoDAO();

			MaterialCentroCusto.setCentroCog(MaterialCentroCusto.getCentroCog().toUpperCase());

			MaterialCentroCustoDAO.merge(MaterialCentroCusto);

			MaterialCentroCusto = new MaterialCentroCusto();
			
			UnidadeDAO unidadeDAO = new UnidadeDAO();
			listaUnidades = unidadeDAO.listar();
			listaCusto = MaterialCentroCustoDAO.listar();

			Messages.addGlobalInfo("Centro de custo cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar Centro de custo.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			MaterialCentroCusto = (MaterialCentroCusto) evento.getComponent().getAttributes()
					.get("materialcentroSelecionado");

			MaterialCentroCustoDAO MaterialCentroCustoDAO = new MaterialCentroCustoDAO();
			MaterialCentroCustoDAO.excluir(MaterialCentroCusto);

			MaterialCentroCusto = new MaterialCentroCusto();
			listaCusto = MaterialCentroCustoDAO.listar();

			Messages.addGlobalInfo("Centro de custo removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Centro de custo.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		MaterialCentroCusto = (MaterialCentroCusto) evento.getComponent().getAttributes()
				.get("materialcentroSelecionado");
	}
}

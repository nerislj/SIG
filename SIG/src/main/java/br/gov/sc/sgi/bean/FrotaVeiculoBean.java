package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.FrotaUnidadeDAO;
import br.gov.sc.sgi.dao.FrotaVeiculoDAO;
import br.gov.sc.sgi.domain.FrotaUnidade;
import br.gov.sc.sgi.domain.FrotaVeiculo;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FrotaVeiculoBean implements Serializable {

	private FrotaVeiculo veiculo;
	private List<FrotaVeiculo> veiculos;
	private List<FrotaUnidade> unidades;
	

	private FrotaUnidade unidade;
	
	private Usuario usuarioLogado;
	
	
	public FrotaUnidade getUnidade() {
		return unidade;
	}

	public void setUnidade(FrotaUnidade unidade) {
		this.unidade = unidade;
	}

	public FrotaVeiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(FrotaVeiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<FrotaVeiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<FrotaVeiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public List<FrotaUnidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<FrotaUnidade> unidades) {
		this.unidades = unidades;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("frota veiculo bean");
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");
			
			
			
			FrotaVeiculoDAO veiculoDAO = new FrotaVeiculoDAO();
			veiculos = veiculoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os veiculos.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			veiculo = new FrotaVeiculo();

			FrotaUnidadeDAO unidadeDAO = new FrotaUnidadeDAO();
			
			unidades = unidadeDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar os Estados.");
			erro.printStackTrace();
		}
	}


	public void salvar() {
		try {
			FrotaVeiculoDAO veiculoDAO = new FrotaVeiculoDAO();
			
			veiculo.setUsuarioCadastro(usuarioLogado);
			veiculo.setDataInclusao(new Date());
			
			System.out.println(veiculo.getRenavam());
			
			veiculo.setPlaca(veiculo.getPlaca().toUpperCase());
			veiculo.setMarca(veiculo.getMarca().toUpperCase());
			veiculo.setModelo(veiculo.getModelo().toUpperCase());
			
			veiculoDAO.merge(veiculo);
			
			
			veiculo = veiculoDAO.carregaVeiculo(veiculo);
			
			
			
			veiculos = veiculoDAO.listar();

			Messages.addGlobalInfo("Veiculo cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Veiculo.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			veiculo = (FrotaVeiculo) evento.getComponent().getAttributes().get("veiculoSelecionado");

			FrotaVeiculoDAO veiculoDAO = new FrotaVeiculoDAO();
			veiculoDAO.excluir(veiculo);

			veiculos = veiculoDAO.listar();

			Messages.addGlobalInfo("Veiculo removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Veiculo.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			veiculo = (FrotaVeiculo) evento.getComponent().getAttributes().get("veiculoSelecionado");

			FrotaUnidadeDAO unidadeDAO = new FrotaUnidadeDAO();
			unidades = unidadeDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar um Veiculo.");
			erro.printStackTrace();
		}
	}
	


}
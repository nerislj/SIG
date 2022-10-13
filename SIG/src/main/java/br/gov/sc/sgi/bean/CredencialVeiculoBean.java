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

import br.gov.sc.sgi.dao.CredencialVeiculoDAO;
import br.gov.sc.sgi.domain.CredencialVeiculo;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CredencialVeiculoBean implements Serializable {

	private CredencialVeiculo veiculo;
	private List<CredencialVeiculo> veiculos;
	private Usuario usuarioLogado;

	public CredencialVeiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(CredencialVeiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<CredencialVeiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<CredencialVeiculo> veiculos) {
		this.veiculos = veiculos;
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
			System.out.println("AQUI CREDENCIADOVEICULO BEAN");

			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			CredencialVeiculoDAO veiculoDAO = new CredencialVeiculoDAO();
			veiculos = veiculoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Veiculos.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		veiculo = new CredencialVeiculo();
	}

	public void salvar() {
		try {
			CredencialVeiculoDAO veiculoDAO = new CredencialVeiculoDAO();

			veiculo.setUsuarioCadastro(usuarioLogado);
			veiculo.setDataInclusao(new Date());
			veiculo.setPlaca(veiculo.getPlaca().toUpperCase());
			veiculoDAO.merge(veiculo);

			CredencialVeiculoBean.this.listar();

			Messages.addGlobalInfo("Veiculo cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Veiculo.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			veiculo = (CredencialVeiculo) evento.getComponent().getAttributes().get("veiculoSelecionado");

			CredencialVeiculoDAO veiculoDAO = new CredencialVeiculoDAO();
			veiculoDAO.excluir(veiculo);

			CredencialVeiculoBean.this.listar();

			Messages.addGlobalInfo("Veiculo removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Veiculo.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		veiculo = (CredencialVeiculo) evento.getComponent().getAttributes().get("veiculoSelecionado");
	}
}

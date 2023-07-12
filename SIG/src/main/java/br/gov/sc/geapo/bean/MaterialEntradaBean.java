package br.gov.sc.geapo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import br.gov.sc.geapo.dao.MaterialDAO;
import br.gov.sc.geapo.dao.MaterialEntradaDAO;
import br.gov.sc.geapo.dao.MaterialEntradaHistDAO;
import br.gov.sc.geapo.dao.MaterialSaidaRelacaoDAO;
import br.gov.sc.geapo.dao.MaterialTipoDAO;
import br.gov.sc.geapo.domain.Material;
import br.gov.sc.geapo.domain.MaterialEntrada;
import br.gov.sc.geapo.domain.MaterialEntradaHist;
import br.gov.sc.geapo.domain.MaterialSaidaRelacao;
import br.gov.sc.geapo.domain.MaterialTipo;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MaterialEntradaBean implements Serializable {

	private MaterialEntrada materialEntrada;

	private MaterialEntrada materialEntradaFront;

	private Material material;

	private Usuario usuarioLogado;

	private MaterialEntradaHist materialEntradaHist;

	private List<MaterialEntradaHist> listamaterialEntradaHist;

	private List<MaterialEntrada> listaEntradaMateriais;

	private List<MaterialEntrada> listaMaiores;

	private List<MaterialEntrada> listarMaior;

	private List<Material> listaMateriais;
	private List<MaterialTipo> listaTipos;

	private MaterialSaidaRelacao saidaRelacao;
	private List<MaterialSaidaRelacao> listasaidaRelacao;

	public MaterialSaidaRelacao getSaidaRelacao() {
		return saidaRelacao;
	}

	public void setSaidaRelacao(MaterialSaidaRelacao saidaRelacao) {
		this.saidaRelacao = saidaRelacao;
	}

	public List<MaterialEntradaHist> getListamaterialEntradaHist() {
		return listamaterialEntradaHist;
	}

	public void setListamaterialEntradaHist(List<MaterialEntradaHist> listamaterialEntradaHist) {
		this.listamaterialEntradaHist = listamaterialEntradaHist;
	}

	public MaterialEntradaHist getMaterialEntradaHist() {
		return materialEntradaHist;
	}

	public void setMaterialEntradaHist(MaterialEntradaHist materialEntradaHist) {
		this.materialEntradaHist = materialEntradaHist;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public MaterialEntrada getMaterialEntrada() {
		return materialEntrada;
	}

	public void setMaterialEntrada(MaterialEntrada materialEntrada) {
		this.materialEntrada = materialEntrada;
	}

	public List<MaterialEntrada> getListaEntradaMateriais() {
		return listaEntradaMateriais;
	}

	public void setListaEntradaMateriais(List<MaterialEntrada> listaEntradaMateriais) {
		this.listaEntradaMateriais = listaEntradaMateriais;
	}

	public List<Material> getListaMateriais() {
		return listaMateriais;
	}

	public void setListaMateriais(List<Material> listaMateriais) {
		this.listaMateriais = listaMateriais;
	}

	public List<MaterialTipo> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<MaterialTipo> listaTipos) {
		this.listaTipos = listaTipos;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("materialentrada bean");
			materialEntradaHist = new MaterialEntradaHist();

			MaterialEntradaDAO materialEntradaDAO = new MaterialEntradaDAO();
			listaEntradaMateriais = materialEntradaDAO.listarPorOrdemASC();

			listarMaior = materialEntradaDAO.listarMaior();

			MaterialDAO materialDAO = new MaterialDAO();
			MaterialTipoDAO materialTipoDAO = new MaterialTipoDAO();
			listaMateriais = materialDAO.listar();
			listaTipos = materialTipoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Status.");
			erro.printStackTrace();
		}
	}

	public void novo() {

		materialEntrada = new MaterialEntrada();

		materialEntradaFront = new MaterialEntrada();

	}

	public void salvar() throws Exception {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			MaterialEntradaDAO materialEntradaDAO = new MaterialEntradaDAO();

			Material material = materialEntradaFront.getMaterial();

			MaterialTipo tipoMaterial = materialEntradaFront.getMaterialTipo();
			
		

			materialEntrada = materialEntradaDAO.iniciarMaterialEntrada(material.getMaterial(),
					tipoMaterial.getTipomaterial());
			
			if(materialEntrada!=null) {

			System.out.println("MATERIAL ENTRADA QUANTIDADE FRONT _ " + materialEntradaFront.getQuantidade());

			System.out.println("MATERIAL ENTRADA QUANTIDADE BANCO _ " + materialEntrada.getQuantidade());

			System.out.println("Variavel materialEntrada " + materialEntrada);

			materialEntrada.setDataCadastro(new Date());

			Integer n = materialEntradaFront.getQuantidade();

			materialEntradaDAO.loadLast(materialEntrada.getMaterial());

			n = (n + materialEntradaDAO.loadLast(materialEntrada.getMaterial()).getQuantidade());

			materialEntrada.setQuantidade(n);

			materialEntrada.setMaterialTipo(materialEntrada.getMaterialTipo());
			
			materialEntrada.setAquisicao(materialEntradaFront.getAquisicao());

			// materialEntrada = materialEntradaDAO.loadLast(materialEntrada.getMaterial());

			materialEntradaDAO.merge(materialEntrada);

			material = materialEntrada.getMaterial();
			materialEntrada = MaterialEntradaDAO.carregaMaterialEntrada(material);

			materialEntradaDAO.salvarEntradaHist(materialEntrada, material, materialEntradaHist, usuarioLogado);

			listaEntradaMateriais = materialEntradaDAO.listarPorOrdemASC();
			} else {
				materialEntrada = new MaterialEntrada();
				materialEntrada.setDataCadastro(new Date());
				materialEntrada.setMaterial(materialEntradaFront.getMaterial());
				materialEntrada.setMaterialTipo(materialEntradaFront.getMaterialTipo());
				materialEntrada.setQuantidade(materialEntradaFront.getQuantidade());
				materialEntradaDAO.merge(materialEntrada);
				
				listaEntradaMateriais = materialEntradaDAO.listarPorOrdemASC();
			}

			Messages.addGlobalInfo("Material Entrada cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Material Entrada.");
			erro.printStackTrace();
		}
	}

	public void salvarEdicao() throws Exception {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			MaterialEntradaDAO materialEntradaDAO = new MaterialEntradaDAO();

			Material material = materialEntradaFront.getMaterial();

			MaterialTipo tipoMaterial = materialEntradaFront.getMaterialTipo();

			materialEntrada = materialEntradaDAO.iniciarMaterialEntrada(material.getMaterial(),
					tipoMaterial.getTipomaterial());

			System.out.println("MATERIAL ENTRADA QUANTIDADE FRONT EDICAO _ " + materialEntradaFront.getQuantidade());

			System.out.println("MATERIAL ENTRADA QUANTIDADE BANCO EDICAO _ " + materialEntrada.getQuantidade());

			System.out.println("Variavel materialEntrada " + materialEntrada);

			materialEntrada.setDataCadastro(new Date());

			System.out.println("Fora do for" + materialEntrada.getMaterial());

			materialEntradaDAO.loadLast(materialEntrada.getMaterial());

			materialEntrada.setQuantidade(materialEntradaFront.getQuantidade());

			materialEntrada.setMaterialTipo(materialEntrada.getMaterialTipo());
			
			materialEntrada.setAquisicao(materialEntradaFront.getAquisicao());

			// materialEntrada = materialEntradaDAO.loadLast(materialEntrada.getMaterial());

			materialEntradaDAO.merge(materialEntrada);

			material = materialEntrada.getMaterial();
			materialEntrada = MaterialEntradaDAO.carregaMaterialEntrada(material);

			materialEntradaDAO.salvarEntradaHist(materialEntrada, material, materialEntradaHist, usuarioLogado);

			listaEntradaMateriais = materialEntradaDAO.listarPorOrdemASC();

			Messages.addGlobalInfo("Material Entrada cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Material Entrada.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) throws Exception {

		try {
			materialEntrada = (MaterialEntrada) evento.getComponent().getAttributes().get("materialSelecionado");

			MaterialEntradaHistDAO histDAO = new MaterialEntradaHistDAO();
			MaterialSaidaRelacaoDAO relacaoDAO = new MaterialSaidaRelacaoDAO();
			MaterialEntradaDAO materialEntradaDAO = new MaterialEntradaDAO();

			listasaidaRelacao = relacaoDAO.listarPorMaterialEntrada(materialEntrada);
			while (listasaidaRelacao.size() != 0) {

				saidaRelacao = relacaoDAO.loadLast(materialEntrada);

				if (saidaRelacao != null) {
					relacaoDAO.excluir(saidaRelacao);
				}else {
					listaEntradaMateriais = materialEntradaDAO.listarPorOrdemASC();
					break;
				}

			}

			listamaterialEntradaHist = histDAO.listarPorMaterialEntrada(materialEntrada);
			while (listamaterialEntradaHist.size() != 0) {

				materialEntradaHist = histDAO.loadLast(materialEntrada);

				if (materialEntradaHist != null) {

					histDAO.excluir(materialEntradaHist);
				}else {
					listaEntradaMateriais = materialEntradaDAO.listarPorOrdemASC();
					break;
				}

			}

			
			materialEntradaDAO.excluir(materialEntrada);

			materialEntrada = new MaterialEntrada();
			listaEntradaMateriais = materialEntradaDAO.listarPorOrdemASC();

			Messages.addGlobalInfo("Material removido com sucesso.");
			
			 ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Status.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		materialEntradaFront = (MaterialEntrada) evento.getComponent().getAttributes().get("materialSelecionado");

	}

	public void popular() {
		try {
			if (materialEntradaFront.getMaterialTipo() != null) {
				MaterialDAO materialDAO = new MaterialDAO();
				listaMateriais = materialDAO.buscarPorTipo(materialEntradaFront.getMaterialTipo().getCodigo());
			} else {
				listaMateriais = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os materiais");
			erro.printStackTrace();
		}
	}

	public List<MaterialEntrada> getListaMaiores() {
		return listaMaiores;
	}

	public void setListaMaiores(List<MaterialEntrada> listaMaiores) {
		this.listaMaiores = listaMaiores;
	}

	public List<MaterialEntrada> getListarMaior() {
		return listarMaior;
	}

	public void setListarMaior(List<MaterialEntrada> listarMaior) {
		this.listarMaior = listarMaior;
	}

	public MaterialEntrada getMaterialEntradaFront() {
		return materialEntradaFront;
	}

	public void setMaterialEntradaFront(MaterialEntrada materialEntradaFront) {
		this.materialEntradaFront = materialEntradaFront;
	}

	public List<MaterialSaidaRelacao> getListasaidaRelacao() {
		return listasaidaRelacao;
	}

	public void setListasaidaRelacao(List<MaterialSaidaRelacao> listasaidaRelacao) {
		this.listasaidaRelacao = listasaidaRelacao;
	}

}

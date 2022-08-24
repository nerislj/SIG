package br.gov.sc.geapo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import br.gov.sc.geapo.dao.MaterialDAO;
import br.gov.sc.geapo.dao.MaterialEntradaDAO;
import br.gov.sc.geapo.dao.MaterialSaidaDAO;
import br.gov.sc.geapo.dao.MaterialSaidaRelacaoDAO;
import br.gov.sc.geapo.dao.MaterialStatusDAO;
import br.gov.sc.geapo.dao.MaterialTipoDAO;
import br.gov.sc.geapo.domain.Material;
import br.gov.sc.geapo.domain.MaterialEntrada;
import br.gov.sc.geapo.domain.MaterialSaida;
import br.gov.sc.geapo.domain.MaterialSaidaHist;
import br.gov.sc.geapo.domain.MaterialSaidaRelacao;
import br.gov.sc.geapo.domain.MaterialStatus;
import br.gov.sc.geapo.domain.MaterialTipo;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MaterialSaidaBean implements Serializable {

	private MaterialSaida materialSaida;

	private MaterialSaida materialSaidaFront;

	private MaterialStatus materialStatus;

	private MaterialEntrada materialEntrada;

	private Material material;

	private Usuario usuarioLogado;

	private MaterialSaidaHist materialSaidaHist;

	private List<MaterialSaidaRelacao> listaSaida;
	
	private List<MaterialSaidaRelacao> listaRelacao;

	private List<MaterialSaida> listaSaidaMateriais;
	
	private List<MaterialSaida> listaSaidaMateriaisPendentes;
	
	private List<MaterialEntrada> listaEntradaMateriais;
	
	private List<MaterialEntrada> listaEstoqueMateriais;

	private List<MaterialSaida> listaPedidoMateriaisPorUsuario;

	private List<Material> listaMateriais;
	private List<MaterialTipo> listaTipos;
	private List<Setor> setores;
	private List<Unidade> Unidades;
	private Unidade unidade;

	@PostConstruct
	public void listar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			materialSaidaHist = new MaterialSaidaHist();

			MaterialSaidaDAO MaterialSaidaDAO = new MaterialSaidaDAO();
			listaPedidoMateriaisPorUsuario = MaterialSaidaDAO.listaPedidoMateriaisPorUsuario(usuarioLogado);
			listaSaidaMateriaisPendentes = MaterialSaidaDAO.listarMaterialPendentes();
			
			MaterialEntradaDAO MaterialEntradaDAO = new MaterialEntradaDAO();
			listaEstoqueMateriais = MaterialEntradaDAO.listar();
		

			/*
			 * listaPedidoMateriaisPorUsuario =
			 * MaterialSaidaDAO.listarPedidoMateriaisPorUsuario();
			 */

			MaterialDAO materialDAO = new MaterialDAO();
			MaterialTipoDAO materialTipoDAO = new MaterialTipoDAO();
			listaMateriais = materialDAO.listar();
			listaTipos = materialTipoDAO.listar();
			
			listaSaida = new ArrayList<>();
			
			//PESADÃO
//			MaterialSaidaRelacaoDAO materialSaidaRelacaoDAO = new MaterialSaidaRelacaoDAO();
//			listaRelacao = materialSaidaRelacaoDAO.listar("codigo");
			
			
			System.out.println("TAMANHO DA listaSaida "+ listaSaida.size());
			
			System.out.println("TAMANHO DA listaRelacao "+ listaRelacao);

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Status.");
			erro.printStackTrace();
		}
	}
	

	public void popularUnidades() {
		try {
			if (materialSaidaFront.getUnidade() != null) {
				SetorDAO setorDAO = new SetorDAO();
				setores = setorDAO.buscarPorUnidade(materialSaidaFront.getUnidade().getCodigo());
			} else {
				setores = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os setores");
			erro.printStackTrace();
		}
	}

	public void novo() {

		materialSaida = new MaterialSaida();

		materialSaidaFront = new MaterialSaida();
		
		UnidadeDAO unidadeDAO = new UnidadeDAO();
		Unidades = unidadeDAO.listar();
		
		setores = new ArrayList<>();

	}

	public void salvar() throws Exception {
		try {

			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			MaterialSaidaDAO materialSaidaDAO = new MaterialSaidaDAO();

			MaterialStatusDAO materialStatusDAO = new MaterialStatusDAO();

			Material material = materialSaidaFront.getMaterial();

			MaterialTipo tipoMaterial = materialSaidaFront.getMaterialTipo();

			materialEntrada = materialSaidaDAO.iniciarMaterialEntrada(material.getMaterial(),
					tipoMaterial.getTipomaterial());

			List<MaterialStatus> resultado = materialStatusDAO.listar();

			// APROVADO = 0
			// PENDENTE = 1
			// RESSALVA = 2

			MaterialStatus valor = resultado.get(1);

			materialSaida.setMaterialTipo(materialSaidaFront.getMaterialTipo());
			materialSaida.setMaterial(materialSaidaFront.getMaterial());
			materialSaida.setQuantidade(materialSaidaFront.getQuantidade());
			materialSaida.setDataCadastro(new Date());
			materialSaida.setMaterialStatus(valor);
			materialSaida.setSetorAbertura(usuarioLogado.getSetor());
			materialSaida.setUsuarioCadastro(usuarioLogado);
		

			materialSaidaDAO.merge(materialSaida);

			material = materialSaida.getMaterial();
			materialSaida = MaterialSaidaDAO.carregaMaterialSaida(material);

			System.out.println();

			materialSaidaDAO.salvarSaidaHist(materialSaida, material, materialSaidaHist, usuarioLogado);

			listaSaidaMateriais = materialSaidaDAO.listar();
			
			listaPedidoMateriaisPorUsuario = materialSaidaDAO.listaPedidoMateriaisPorUsuario(usuarioLogado);
			
			listaSaidaMateriaisPendentes = materialSaidaDAO.listarMaterialPendentes();

			Messages.addGlobalInfo("Material solicitado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar solicitar o Material.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			materialSaida = (MaterialSaida) evento.getComponent().getAttributes().get("materialSelecionado");

			MaterialSaidaDAO MaterialSaidaDAO = new MaterialSaidaDAO();
			MaterialSaidaDAO.excluir(materialSaida);

			materialSaida = new MaterialSaida();
			listaSaidaMateriais = MaterialSaidaDAO.listar();

			Messages.addGlobalInfo("Material removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Status.");
			erro.printStackTrace();
		}
	}

	public void ressalva(ActionEvent evento) {
		
		

		materialSaida = (MaterialSaida) evento.getComponent().getAttributes().get("materialSelecionado");
		
		this.listar();
	}

	public void popular() {
		try {
			if (materialSaidaFront.getMaterialTipo() != null) {
				MaterialDAO materialDAO = new MaterialDAO();
				listaMateriais = materialDAO.buscarPorTipo(materialSaidaFront.getMaterialTipo().getCodigo());
			} else {
				listaMateriais = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os materiais");
			erro.printStackTrace();
		}
	}

	public void salvarRessalva() {
		try {

			MaterialSaidaDAO materialSaidaDAO = new MaterialSaidaDAO();

			MaterialStatusDAO materialStatusDAO = new MaterialStatusDAO();

			List<MaterialStatus> resultado = materialStatusDAO.listar();

			// APROVADO = 0
			// PENDENTE = 1
			// RESSALVA = 2

			MaterialStatus valor = resultado.get(2);

			System.out.println("VALOOOOOOOOOOOOOOR " + valor);

			materialSaida.setMaterialStatus(valor);

			System.out.println("USUÁRIO RESSALVAAAAAAAA " + usuarioLogado);

			materialSaidaDAO.merge(materialSaida);

			material = materialSaida.getMaterial();
			materialSaida = MaterialSaidaDAO.carregaMaterialSaida(material);

			materialSaidaDAO.salvarSaidaHist(materialSaida, material, materialSaidaHist, usuarioLogado);

			listaSaidaMateriais = materialSaidaDAO.listar();
			listaSaidaMateriaisPendentes = materialSaidaDAO.listarMaterialPendentes();

			Messages.addGlobalInfo("Ressalva cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Ressalva.");
			erro.printStackTrace();
		}
	}

	// APROVAÇÃO

	public void salvarAprovado() throws Exception {
		

	
		try {

			if (listaSaida.isEmpty()) {
				Messages.addGlobalError("Selecione pelo menos um Material para dar Saida.");
			} else {
				
				
				MaterialSaidaDAO materialSaidaDAO = new MaterialSaidaDAO();
				
				
				
		       materialSaidaDAO.salvarSelecionados(listaSaida, materialEntrada);
		            
		        
				
				
				
//				materialEntrada = materialSaidaDAO.iniciarMaterialEntrada(material.getMaterial().getMaterial(),
//						material.getMaterial().getMaterialTipo().getTipomaterial());
//				
//				System.out.println("MATERIAL ENTRADA BEAN " + materialEntrada);

				

				MaterialSaidaBean.this.novo();
				
				
				listaSaidaMateriaisPendentes = materialSaidaDAO.listarMaterialPendentes();
				
				

				Messages.addGlobalInfo("Saida realizada com sucesso");
				
				MaterialEntradaDAO materialEntradaDAO = new MaterialEntradaDAO();
				listaEntradaMateriais = materialEntradaDAO.listar();
				
				

			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Status.");
			erro.printStackTrace();
		}
		

		MaterialEntradaDAO MaterialEntradaDAO = new MaterialEntradaDAO();
		listaEstoqueMateriais = MaterialEntradaDAO.listar();
		
		MaterialEntradaDAO materialEntradaDAO = new MaterialEntradaDAO();
		listaEntradaMateriais = materialEntradaDAO.listar();
	}

	public void finalizar() {
		try {
			usuarioLogado = new Usuario();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar a Saida");
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {
		MaterialSaida material = (MaterialSaida) evento.getComponent().getAttributes().get("materialSelecionado");
		
		System.out.println("material selecionado " + material.getMaterial().getMaterial());

		int achou = -1;
		for (int posicao = 0; posicao < listaSaida.size(); posicao++) {
			if (listaSaida.get(posicao).getMaterialSaida().equals(material)) {
				achou = posicao;
				
				
				//if(listaSaida.get(posicao).getMaterialSaida().getMaterial().getMaterial())
				listaSaida.get(posicao).getMaterialSaida().getMaterial().getMaterial();
			}
		}
		
		if (achou < 0) {

			MaterialSaidaRelacao materialSaida = new MaterialSaidaRelacao();
			materialSaida.setMaterialSaida(material);

			listaSaida.add(materialSaida);

			System.out.println("material selecionado " + material.getMaterial().getMaterial());
			

		}
		
		Collections.sort(listaSaida, new Comparator<MaterialSaidaRelacao>() {
	        @Override
	        public int compare(MaterialSaidaRelacao fruit2, MaterialSaidaRelacao fruit1)
	        {

	            return  fruit1.getMaterialSaida().getMaterial().getMaterial().compareTo(fruit2.getMaterialSaida().getMaterial().getMaterial());
	        }

			
	    });
		
		
	}

	public void remover(ActionEvent evento) {
		MaterialSaidaRelacao materialSaida = (MaterialSaidaRelacao) evento.getComponent().getAttributes()
				.get("materialSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < listaSaida.size(); posicao++) {
			if (listaSaida.get(posicao).getMaterialSaida().equals(materialSaida.getMaterialSaida())) {
				achou = posicao;
			}
		}
		if (achou > -1) {
			listaSaida.remove(achou);
			
			System.out.println(listaSaida.size());
			
		}

	}

	public MaterialSaida getMaterialSaida() {
		return materialSaida;
	}

	public void setMaterialSaida(MaterialSaida materialSaida) {
		this.materialSaida = materialSaida;
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

	public MaterialSaidaHist getMaterialSaidaHist() {
		return materialSaidaHist;
	}

	public void setMaterialSaidaHist(MaterialSaidaHist materialSaidaHist) {
		this.materialSaidaHist = materialSaidaHist;
	}

	public List<MaterialSaida> getListaSaidaMateriais() {
		return listaSaidaMateriais;
	}

	public void setListaSaidaMateriais(List<MaterialSaida> listaSaidaMateriais) {
		this.listaSaidaMateriais = listaSaidaMateriais;
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

	public MaterialStatus getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(MaterialStatus materialStatus) {
		this.materialStatus = materialStatus;
	}

	public List<MaterialSaidaRelacao> getListaSaida() {
		return listaSaida;
	}

	public void setListaSaida(List<MaterialSaidaRelacao> listaSaida) {
		this.listaSaida = listaSaida;
	}

	public MaterialSaida getMaterialSaidaFront() {
		return materialSaidaFront;
	}

	public void setMaterialSaidaFront(MaterialSaida materialSaidaFront) {
		this.materialSaidaFront = materialSaidaFront;
	}

	public MaterialEntrada getMaterialEntrada() {
		return materialEntrada;
	}

	public void setMaterialEntrada(MaterialEntrada materialEntrada) {
		this.materialEntrada = materialEntrada;
	}

	public List<MaterialSaida> getListaPedidoMateriaisPorUsuario() {
		return listaPedidoMateriaisPorUsuario;
	}

	public void setListaPedidoMateriaisPorUsuario(List<MaterialSaida> listaPedidoMateriaisPorUsuario) {
		this.listaPedidoMateriaisPorUsuario = listaPedidoMateriaisPorUsuario;
	}

	public List<MaterialEntrada> getListaEstoqueMateriais() {
		return listaEstoqueMateriais;
	}

	public void setListaEstoqueMateriais(List<MaterialEntrada> listaEstoqueMateriais) {
		this.listaEstoqueMateriais = listaEstoqueMateriais;
	}

	public List<MaterialSaidaRelacao> getListaRelacao() {
		return listaRelacao;
	}

	public void setListaRelacao(List<MaterialSaidaRelacao> listaRelacao) {
		this.listaRelacao = listaRelacao;
	}

	public List<MaterialSaida> getListaSaidaMateriaisPendentes() {
		return listaSaidaMateriaisPendentes;
	}

	public void setListaSaidaMateriaisPendentes(List<MaterialSaida> listaSaidaMateriaisPendentes) {
		this.listaSaidaMateriaisPendentes = listaSaidaMateriaisPendentes;
	}

	public List<MaterialEntrada> getListaEntradaMateriais() {
		return listaEntradaMateriais;
	}

	public void setListaEntradaMateriais(List<MaterialEntrada> listaEntradaMateriais) {
		this.listaEntradaMateriais = listaEntradaMateriais;
	}

	public List<Unidade> getUnidades() {
		return Unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		Unidades = unidades;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	

}

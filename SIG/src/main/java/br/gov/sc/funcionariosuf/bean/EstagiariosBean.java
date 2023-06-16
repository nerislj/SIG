
package br.gov.sc.funcionariosuf.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import com.google.gson.Gson;

import br.gov.sc.funcionariosuf.dao.EstagiariosDAO;
import br.gov.sc.funcionariosuf.dao.ServidoresDAO;
import br.gov.sc.funcionariosuf.domain.CiretranCitran;
import br.gov.sc.funcionariosuf.domain.Estagiarios;
import br.gov.sc.funcionariosuf.domain.UnidadeCiretranCitran;
import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstagiariosBean implements Serializable {

	private Estagiarios estagiarios;

	private List<Estagiarios> listaEstagiarios;
	
	private UnidadeCiretranCitran unidadeCiretranCitran;
	private CiretranCitran ciretranCitran;
	private PessoaFisica pessoa;
	private Estado estado;
	private List<Estado> Estados;
	private List<Cidade> Cidades;
	private Usuario usuarioLogado;

	@PostConstruct
	public void listar() {
		try {
			
			
			estado = new Estado();
			pessoa = new PessoaFisica();
			
			System.out.println("unidadeCiretranCitran " + unidadeCiretranCitran);

			EstagiariosDAO estagiariosDAO = new EstagiariosDAO();
			listaEstagiarios = estagiariosDAO.listar();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			this.setEstados(estadoDAO.listar("sigla"));


			estagiarios = new Estagiarios();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Estagiários.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			this.setUsuarioLogado((Usuario) sessao.getAttribute("usuario"));

			EstagiariosDAO estagiariosDAO = new EstagiariosDAO();
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(this.pessoa);
			this.pessoa = PessoaDAO.carregarCpf(this.pessoa.getCpf());
			
			estagiarios.setUsuarioCadastro(usuarioLogado);
			estagiarios.setPessoa(pessoa);
			estagiarios.setDataCadastro(new Date());
			System.out.println("SERVIDORES UNIDADE " + unidadeCiretranCitran);
			estagiarios.setSetor(unidadeCiretranCitran.getSetor());
			estagiarios.setCiretranCitran(unidadeCiretranCitran.getCiretranCitran());
			//estagiarios.setUnidadeCiretranCitran(unidadeCiretranCitran);
			
			estagiariosDAO.merge(estagiarios); 

			estagiarios = new Estagiarios();

			
			
		

			Messages.addGlobalInfo("Estagiário cadastrado com Sucesso!");
			
			refresh();
			
			this.refresh();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Estagiário.");
			erro.printStackTrace();
		}
	}

	
	public void refresh() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse();
	}
	
	public void excluir(ActionEvent evento) {

		try {
			estagiarios = (Estagiarios) evento.getComponent().getAttributes().get("estagiarioSelecionado");

			EstagiariosDAO estagiariosDAO = new EstagiariosDAO();
			estagiariosDAO.excluir(estagiarios);

			listaEstagiarios = estagiariosDAO.listar();

			Messages.addGlobalInfo("Estagiário removido com sucesso.");
			
			this.refresh();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Estagiário.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			estagiarios = (Estagiarios) evento.getComponent().getAttributes().get("estagiarioSelecionado");

			EstagiariosDAO estagiariosDAO = new EstagiariosDAO();
			listaEstagiarios = estagiariosDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o Estagiário.");
			erro.printStackTrace();
		}
	}
	
	public void buscarCPF() {
		EstagiariosDAO servidoresDAO = new EstagiariosDAO();
		this.pessoa = servidoresDAO.carregarCpf(this.pessoa.getCpf());
		CidadeDAO municipioDAO = new CidadeDAO();
		this.Cidades = municipioDAO.buscarPorEstado(this.pessoa.getEstadoEndereco().getCodigo());
	}
	
	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {

			String newcep = pessoa.getCep().replace(".", "");
			newcep = newcep.replace("-", "");

			URL url = new URL("http://viacep.com.br/ws/" + newcep + "/json");
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			StringBuilder jsonCep = new StringBuilder();

			String cep = "";

			while ((cep = br.readLine()) != null) {

				jsonCep.append(cep);

			}

			System.out.println(jsonCep);

			PessoaJuridica cepEmpresa = new Gson().fromJson(jsonCep.toString(), PessoaJuridica.class);

			System.out.println(cepEmpresa.getCep());
			System.out.println(cepEmpresa.getEndereco());

			pessoa.setCep(cepEmpresa.getCep());

			pessoa.setEndereco(cepEmpresa.getEndereco() + ", " + cepEmpresa.getBairro());

			CidadeDAO municipioDAO = new CidadeDAO();
			EstadoDAO estadoDAO = new EstadoDAO();

			pessoa.setEstadoEndereco(estadoDAO.loadSigla(cepEmpresa.getUf()));

			Cidades = municipioDAO.buscarPorEstado(pessoa.getEstadoEndereco().getCodigo());

			pessoa.setMunicipioEndereco(municipioDAO.loadNome(cepEmpresa.getLocalidade()));

			System.out.println(municipioDAO.loadNome(cepEmpresa.getLocalidade()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void popular() {
		try {
			if (this.pessoa.getEstadoEndereco() != null) {
				CidadeDAO municipioDAO = new CidadeDAO();
				this.Cidades = municipioDAO.buscarPorEstado(this.pessoa.getEstadoEndereco().getCodigo());
			} else {
				this.Cidades = new ArrayList();
			}
		} catch (NullPointerException var2) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades", new Object[0]);
		}

	}



	public UnidadeCiretranCitran getUnidadeCiretranCitran() {
		return unidadeCiretranCitran;
	}

	public void setUnidadeCiretranCitran(UnidadeCiretranCitran unidadeCiretranCitran) {
		this.unidadeCiretranCitran = unidadeCiretranCitran;
	}

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Cidade> getCidades() {
		return Cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		Cidades = cidades;
	}

	public List<Estado> getEstados() {
		return Estados;
	}

	public void setEstados(List<Estado> estados) {
		Estados = estados;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Estagiarios> getListaEstagiarios() {
		return listaEstagiarios;
	}

	public void setListaEstagiarios(List<Estagiarios> listaEstagiarios) {
		this.listaEstagiarios = listaEstagiarios;
	}

	public CiretranCitran getCiretranCitran() {
		return ciretranCitran;
	}

	public void setCiretranCitran(CiretranCitran ciretranCitran) {
		this.ciretranCitran = ciretranCitran;
	}
	
	
	
	

	
}
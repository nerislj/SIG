
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

import br.gov.sc.funcionariosuf.dao.ServidoresDAO;
import br.gov.sc.funcionariosuf.domain.Servidores;
import br.gov.sc.funcionariosuf.domain.UnidadeFunc;
import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ServidoresBean implements Serializable {

	private Servidores servidores;

	private List<Servidores> listaServidores;

	private UnidadeFunc unidadeFunc;
	private Setor setor;

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

			// System.out.println("unidadeCiretranCitran " + unidadeCiretranCitran);

			ServidoresDAO servidoresDAO = new ServidoresDAO();
			listaServidores = servidoresDAO.listar();

			EstadoDAO estadoDAO = new EstadoDAO();
			this.setEstados(estadoDAO.listar("sigla"));

			servidores = new Servidores();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os servidores.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			this.setUsuarioLogado((Usuario) sessao.getAttribute("usuario"));

			ServidoresDAO servidoresDAO = new ServidoresDAO();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(this.pessoa);
			this.pessoa = PessoaDAO.carregarCpf(this.pessoa.getCpf());

			servidores.setUsuarioCadastro(usuarioLogado);
			servidores.setPessoa(pessoa);
			servidores.setDataCadastro(new Date());
			System.out.println("SERVIDORES UNIDADE " + unidadeFunc);
			servidores.setSetor(unidadeFunc.getSetor());
			// servidores.setUnidadeFunc(unidadeFunc.getUnidadeFunc());
			// servidores.setUnidadeCiretranCitran(unidadeCiretranCitran);

			servidoresDAO.merge(servidores);

			servidores = new Servidores();

			Messages.addGlobalInfo("Servidores cadastrado com Sucesso!");

			refresh();

			this.refresh();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Servidores.");
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
			servidores = (Servidores) evento.getComponent().getAttributes().get("servidoresSelecionado");

			ServidoresDAO servidoresDAO = new ServidoresDAO();
			servidoresDAO.excluir(servidores);

			listaServidores = servidoresDAO.listar();

			Messages.addGlobalInfo("Servidores removido com sucesso.");

			this.refresh();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Servidores.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			servidores = (Servidores) evento.getComponent().getAttributes().get("servidoresSelecionado");

			ServidoresDAO servidoresDAO = new ServidoresDAO();
			listaServidores = servidoresDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o Servidores.");
			erro.printStackTrace();
		}
	}

	public void buscarCPF() {
		ServidoresDAO servidoresDAO = new ServidoresDAO();
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

	public Servidores getServidores() {
		return servidores;
	}

	public void setServidores(Servidores servidores) {
		this.servidores = servidores;
	}

	public List<Servidores> getListaServidores() {
		return listaServidores;
	}

	public void setListaServidores(List<Servidores> listaServidores) {
		this.listaServidores = listaServidores;
	}

	public UnidadeFunc getUnidadeFunc() {
		return unidadeFunc;
	}

	public void setUnidadeFunc(UnidadeFunc unidadeFunc) {
		this.unidadeFunc = unidadeFunc;
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

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

}
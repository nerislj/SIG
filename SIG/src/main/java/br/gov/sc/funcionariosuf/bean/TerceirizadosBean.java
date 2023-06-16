
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

import br.gov.sc.contrato.dao.CargoTerceirizadoDAO;
import br.gov.sc.contrato.dao.ContratoRelacaoDAO;
import br.gov.sc.contrato.dao.FuncionarioTerceirizadoDAO;
import br.gov.sc.contrato.domain.CargoTerceirizado;
import br.gov.sc.contrato.domain.ContratoRelacao;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.funcionariosuf.dao.CiretranCitranDAO;
import br.gov.sc.funcionariosuf.dao.TerceirizadosDAO;
import br.gov.sc.funcionariosuf.domain.CiretranCitran;
import br.gov.sc.funcionariosuf.domain.Terceirizados;
import br.gov.sc.funcionariosuf.domain.UnidadeCiretranCitran;
import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.dao.SetorDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TerceirizadosBean implements Serializable {

	private Terceirizados terceirizados;
	private List<Terceirizados> listaTerceirizados;
	
	private UnidadeCiretranCitran unidadeCiretranCitran;
	private Setor setor;
	private List<UnidadeCiretranCitran> listaUnidades;
	
	private Unidade unidade;
	private FuncionarioTerceirizado funcionarioTerceirizado;
	private ContratoRelacao contratoFuncionario;
	private CiretranCitran ciretranCitran;
	
	
	private PessoaFisica pessoa;
	private List<Estado> Estados;
	private Estado estado;
	private List<Cidade> Cidades;
	private List<Setor> setores;
	private List<Unidade> unidades;
	private List<CargoTerceirizado> cargos;
	private Usuario usuarioLogado;

	@PostConstruct
	public void listar() {
		try {

			TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();
			listaTerceirizados = terceirizadosDAO.listar();
			
			funcionarioTerceirizado = new FuncionarioTerceirizado();
			terceirizados = new Terceirizados();
			estado = new Estado();
			pessoa = new PessoaFisica();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			this.Estados = estadoDAO.listar("sigla");

			this.setores = new ArrayList();
			CargoTerceirizadoDAO cargoDAO = new CargoTerceirizadoDAO();
			this.cargos = cargoDAO.listar();
			
			
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os terceirizados.");
			erro.printStackTrace();
		}
	}

	
	 
	
	public void salvar() throws Exception {
		try {
			funcionarioTerceirizado = new FuncionarioTerceirizado();
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			this.usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(this.pessoa);
			this.pessoa = PessoaDAO.carregarCpf(this.pessoa.getCpf());
			
			funcionarioTerceirizado = terceirizadosDAO.loadFuncionario(pessoa.getCpf());
		
				
			
			
			System.out.println("unidadeCiretranCitran "+ unidadeCiretranCitran);
			
			//Passado por <f:setPropertyActionListener/>
			//terceirizados.setUnidadeCiretranCitran(unidadeCiretranCitran);
			terceirizados.setSetor(unidadeCiretranCitran.getSetor());
			terceirizados.setCiretranCitran(unidadeCiretranCitran.getCiretranCitran());
			terceirizados.setPessoa(pessoa);
			terceirizados.setDataCadastro(new Date());
			terceirizados.setUsuarioCadastro(usuarioLogado);
			
			unidade = terceirizadosDAO.loadUnidadePorNome(unidadeCiretranCitran.getCiretranCitran().getNome());
			
			System.out.println("unidadeunidade " + unidade);
			 
			terceirizadosDAO.salvarFuncionarioTerceirizado(unidade, funcionarioTerceirizado, terceirizados, usuarioLogado);

			
			terceirizadosDAO.merge(terceirizados);
			
			
			
			
			
			terceirizados = new Terceirizados();

			listaTerceirizados = terceirizadosDAO.listar();

			Messages.addGlobalInfo("Terceirizado cadastrado com Sucesso!");
			
			this.refresh();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Funcionário já cadastrado na Unidade " + funcionarioTerceirizado.getUnidade().getUnidadeNome());
			erro.printStackTrace();
		}
	}

	
	public void excluir(ActionEvent evento) throws Exception {

		try {
			System.out.println("ENTROU");
			terceirizados = (Terceirizados) evento.getComponent().getAttributes().get("terceirizadoSelecionado");

			TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();
			
			
			
			pessoa = terceirizados.getPessoa();
			funcionarioTerceirizado = terceirizadosDAO.loadFuncionario(pessoa.getCpf());
			
			
			
			
			contratoFuncionario = ContratoRelacaoDAO.carregaFuncionario(funcionarioTerceirizado);
			
			if(funcionarioTerceirizado!=null) {
			TerceirizadosDAO.excluirFuncionarioTerceirizadoById(funcionarioTerceirizado.getCodigo());
			}
			terceirizadosDAO.excluir(terceirizados);
			
			
			
			Messages.addGlobalInfo("Terceirizado removido com sucesso.");
			
			

			listaTerceirizados = terceirizadosDAO.listar();

			this.refresh();
			
		} catch (RuntimeException erro) {
			if(contratoFuncionario!=null) {
			Messages.addGlobalError("Funcionário vinculado ao contrato: " + contratoFuncionario.getContratoTerceirizado().getnContrato() +" "+ contratoFuncionario.getContratoTerceirizado().getUnidade().getUnidadeNome());
			erro.printStackTrace();
			}
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

	public void editar(ActionEvent evento) {
		try {
			terceirizados = (Terceirizados) evento.getComponent().getAttributes().get("terceirizadoSelecionado");

			TerceirizadosDAO terceirizadosDAO = new TerceirizadosDAO();
			listaTerceirizados = terceirizadosDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o Terceirizado.");
			erro.printStackTrace();
		}
	}
	
	public void buscarCPF() {
		new FuncionarioTerceirizadoDAO();
		this.pessoa = FuncionarioTerceirizadoDAO.carregarCpf(this.pessoa.getCpf());
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
	
	
	
	public void popularSetores() {
		try {
			if (terceirizados.getCiretranCitran() != null) {
				
				CiretranCitranDAO ciretranCITRANDAO = new CiretranCitranDAO();
				//listaCiretranCitran = ciretranCITRANDAO.buscarPorCiretran(unidadeCiretranCitran.getCiretran().getCodigo());
				SetorDAO setorDAO = new SetorDAO();
				System.out.println(terceirizados.getCiretranCitran().getNome());
				setores = setorDAO.buscarPorCiretranNome(terceirizados.getCiretranCitran().getNome());
				System.out.println(setores);
			} else {
				Cidades = new ArrayList<>();
				
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as CIRETRAN/CITRAN");
			erro.printStackTrace();
		}
	}

	public Terceirizados getTerceirizados() {
		return terceirizados;
	}

	public void setTerceirizados(Terceirizados terceirizados) {
		this.terceirizados = terceirizados;
	}

	public List<Terceirizados> getListaTerceirizados() {
		return listaTerceirizados;
	}

	public void setListaTerceirizados(List<Terceirizados> listaTerceirizados) {
		this.listaTerceirizados = listaTerceirizados;
	}

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public List<Estado> getEstados() {
		return Estados;
	}

	public void setEstados(List<Estado> estados) {
		Estados = estados;
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

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public List<CargoTerceirizado> getCargos() {
		return cargos;
	}

	public void setCargos(List<CargoTerceirizado> cargos) {
		this.cargos = cargos;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public UnidadeCiretranCitran getUnidadeCiretranCitran() {
		return unidadeCiretranCitran;
	}

	public void setUnidadeCiretranCitran(UnidadeCiretranCitran unidadeCiretranCitran) {
		this.unidadeCiretranCitran = unidadeCiretranCitran;
	}

	public List<UnidadeCiretranCitran> getListaUnidades() {
		return listaUnidades;
	}

	public void setListaUnidades(List<UnidadeCiretranCitran> listaUnidades) {
		this.listaUnidades = listaUnidades;
	}




	public Unidade getUnidade() {
		return unidade;
	}




	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}




	public FuncionarioTerceirizado getFuncionarioTerceirizado() {
		return funcionarioTerceirizado;
	}




	public void setFuncionarioTerceirizado(FuncionarioTerceirizado funcionarioTerceirizado) {
		this.funcionarioTerceirizado = funcionarioTerceirizado;
	}




	public Setor getSetor() {
		return setor;
	}




	public void setSetor(Setor setor) {
		this.setor = setor;
	}




	public CiretranCitran getCiretranCitran() {
		return ciretranCitran;
	}




	public void setCiretranCitran(CiretranCitran ciretranCitran) {
		this.ciretranCitran = ciretranCitran;
	}




	public ContratoRelacao getContratoFuncionario() {
		return contratoFuncionario;
	}




	public void setContratoFuncionario(ContratoRelacao contratoFuncionario) {
		this.contratoFuncionario = contratoFuncionario;
	}
	
	
	

}
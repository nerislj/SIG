/* Decompiler 16ms, total 169ms, lines 233 */package br.gov.sc.contrato.bean;import java.io.Serializable;import java.util.ArrayList;import java.util.Date;import java.util.HashSet;import java.util.List;import java.util.Set;import javax.annotation.PostConstruct;import javax.faces.bean.ManagedBean;import javax.faces.bean.ViewScoped;import javax.faces.context.FacesContext;import javax.faces.event.ActionEvent;import javax.servlet.http.HttpSession;import org.hibernate.criterion.Restrictions;import org.omnifaces.util.Messages;import br.gov.sc.contrato.dao.CargoTerceirizadoDAO;import br.gov.sc.contrato.dao.FuncionarioTerceirizadoDAO;import br.gov.sc.contrato.dao.UserClaimsContratoDAO;import br.gov.sc.contrato.domain.CargoTerceirizado;import br.gov.sc.contrato.domain.FuncionarioTerceirizado;import br.gov.sc.contrato.domain.UserClaimsContrato;import br.gov.sc.sgi.dao.CidadeDAO;import br.gov.sc.sgi.dao.EstadoDAO;import br.gov.sc.sgi.dao.PessoaDAO;import br.gov.sc.sgi.dao.SetorDAO;import br.gov.sc.sgi.dao.UnidadeDAO;import br.gov.sc.sgi.domain.Cidade;import br.gov.sc.sgi.domain.Estado;import br.gov.sc.sgi.domain.PessoaFisica;import br.gov.sc.sgi.domain.Setor;import br.gov.sc.sgi.domain.Unidade;import br.gov.sc.sgi.domain.Usuario;@SuppressWarnings("serial")@ManagedBean@ViewScopedpublic class FuncionarioTerceirizadoBean implements Serializable {	private FuncionarioTerceirizado funcionarioTerceirizado;	private List<FuncionarioTerceirizado> listaFuncionariosTerceirizados;	private PessoaFisica pessoa;	private List<Estado> Estados;	private Estado estado;	private List<Cidade> Cidades;	private List<Setor> setores;	private List<Unidade> unidades;	private List<CargoTerceirizado> cargos;	private Usuario usuarioLogado;	private List<UserClaimsContrato> listaUserClaims;	public List<UserClaimsContrato> getListaUserClaims() {		return listaUserClaims;	}	public void setListaUserClaims(List<UserClaimsContrato> listaUserClaims) {		this.listaUserClaims = listaUserClaims;	}	public List<Setor> getSetores() {		return this.setores;	}	public void setSetores(List<Setor> setores) {		this.setores = setores;	}	public List<Unidade> getUnidades() {		return this.unidades;	}	public void setUnidades(List<Unidade> unidades) {		this.unidades = unidades;	}	public List<Estado> getEstados() {		return this.Estados;	}	public void setEstados(List<Estado> estados) {		this.Estados = estados;	}	public Estado getEstado() {		return this.estado;	}	public void setEstado(Estado estado) {		this.estado = estado;	}	public List<Cidade> getCidades() {		return this.Cidades;	}	public void setCidades(List<Cidade> cidades) {		this.Cidades = cidades;	}	public PessoaFisica getPessoa() {		return this.pessoa;	}	public void setPessoa(PessoaFisica pessoa) {		this.pessoa = pessoa;	}	public FuncionarioTerceirizado getFuncionarioTerceirizado() {		return this.funcionarioTerceirizado;	}	public void setFuncionarioTerceirizado(FuncionarioTerceirizado funcionarioTerceirizado) {		this.funcionarioTerceirizado = funcionarioTerceirizado;	}	public List<FuncionarioTerceirizado> getListaFuncionariosTerceirizados() {		return this.listaFuncionariosTerceirizados;	}	public void setListaFuncionariosTerceirizados(List<FuncionarioTerceirizado> listaFuncionariosTerceirizados) {		this.listaFuncionariosTerceirizados = listaFuncionariosTerceirizados;	}	@PostConstruct	public void listar() {		try {			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);			usuarioLogado = (Usuario) sessao.getAttribute("usuario");			UnidadeDAO unidadeDAO = new UnidadeDAO();			FuncionarioTerceirizadoDAO funcionarioDAO = new FuncionarioTerceirizadoDAO();			UserClaimsContratoDAO userClaimDAO = new UserClaimsContratoDAO();			listaUserClaims = userClaimDAO.listarPorUsuarioLogado(usuarioLogado);			ArrayList<String> codigosUnidades = new ArrayList<String>();			for (int posicao = 0; posicao < listaUserClaims.size(); posicao++) {				codigosUnidades.add(listaUserClaims.get(posicao).getUnidade().getUnidadeNome());			}			unidades = unidadeDAO.listarPorClaims(codigosUnidades);			listaFuncionariosTerceirizados = funcionarioDAO.listarPorClaims(codigosUnidades);			if (usuarioLogado.getSetor().getSetorNome().contains("Gerência de Apoio Operacional")					&& usuarioLogado.getNivelAcesso().getNivel().contains("CONTRATO")) {				listaFuncionariosTerceirizados = funcionarioDAO.listarPorTodos();				unidades = unidadeDAO.listar();			}			if (usuarioLogado.getNivelAcesso().getCodigo() == 1) {				listaFuncionariosTerceirizados = funcionarioDAO.listarPorTodos();				unidades = unidadeDAO.listar();			}			this.estado = new Estado();			this.funcionarioTerceirizado = new FuncionarioTerceirizado();			this.pessoa = new PessoaFisica();			EstadoDAO estadoDAO = new EstadoDAO();			this.Estados = estadoDAO.listar("sigla");			this.setores = new ArrayList();			CargoTerceirizadoDAO cargoDAO = new CargoTerceirizadoDAO();			this.cargos = cargoDAO.listar();		} catch (RuntimeException var5) {			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Funcionários.", new Object[0]);			var5.printStackTrace();		}	}	public void salvar() {		try {			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);			this.usuarioLogado = (Usuario) sessao.getAttribute("usuario");			FuncionarioTerceirizadoDAO funcionarioDAO = new FuncionarioTerceirizadoDAO();			PessoaDAO pessoaDAO = new PessoaDAO();			pessoaDAO.merge(this.pessoa);			this.pessoa = PessoaDAO.carregarCpf(this.pessoa.getCpf());			this.funcionarioTerceirizado.setUsuarioCadastro((Usuario) null);			this.funcionarioTerceirizado.setDataCadastro(new Date());			this.funcionarioTerceirizado.setPessoa(this.pessoa);			this.funcionarioTerceirizado.setUsuarioCadastro(this.usuarioLogado);			funcionarioDAO.merge(this.funcionarioTerceirizado);			this.funcionarioTerceirizado = new FuncionarioTerceirizado();			this.listar();			Messages.addGlobalInfo("Funcionário cadastrado com Sucesso!", new Object[0]);		} catch (RuntimeException var4) {			Messages.addGlobalError("Funcionário já pertence a outra Unidade.", new Object[0]);			var4.printStackTrace();		}	}	public void excluir(ActionEvent evento) {		try {			this.funcionarioTerceirizado = (FuncionarioTerceirizado) evento.getComponent().getAttributes()					.get("funcionarioSelecionado");			FuncionarioTerceirizadoDAO funcionarioDAO = new FuncionarioTerceirizadoDAO();			funcionarioDAO.excluir(this.funcionarioTerceirizado);			this.funcionarioTerceirizado = new FuncionarioTerceirizado();			this.listar();			Messages.addGlobalInfo("Funcionário removido com sucesso.", new Object[0]);		} catch (RuntimeException var3) {			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Funcionário.", new Object[0]);			var3.printStackTrace();		}	}	public void editar(ActionEvent evento) {		this.funcionarioTerceirizado = (FuncionarioTerceirizado) evento.getComponent().getAttributes()				.get("funcionarioSelecionado");		this.funcionarioTerceirizado.setUnidade(this.funcionarioTerceirizado.getUnidade());		this.funcionarioTerceirizado.setSetor(this.funcionarioTerceirizado.getSetor());		this.funcionarioTerceirizado.setCargoTerceirizado(this.funcionarioTerceirizado.getCargoTerceirizado());		new FuncionarioTerceirizadoDAO();		this.pessoa = FuncionarioTerceirizadoDAO.carregarCpf(this.funcionarioTerceirizado.getPessoa().getCpf());		SetorDAO setorDAO = new SetorDAO();		this.setores = setorDAO.buscarPorUnidade(this.funcionarioTerceirizado.getUnidade().getCodigo());		CidadeDAO municipioDAO = new CidadeDAO();		this.Cidades = municipioDAO				.buscarPorEstado(this.funcionarioTerceirizado.getPessoa().getEstadoEndereco().getCodigo());	}	public void popular() {		try {			if (this.pessoa.getEstadoEndereco() != null) {				CidadeDAO municipioDAO = new CidadeDAO();				this.Cidades = municipioDAO.buscarPorEstado(this.pessoa.getEstadoEndereco().getCodigo());			} else {				this.Cidades = new ArrayList();			}		} catch (NullPointerException var2) {			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades", new Object[0]);		}	}	public void popularUnidades() {		try {			if (this.funcionarioTerceirizado.getUnidade() != null) {				SetorDAO setorDAO = new SetorDAO();				this.setores = setorDAO.buscarPorUnidade(this.funcionarioTerceirizado.getUnidade().getCodigo());			} else {				this.setores = new ArrayList();			}		} catch (RuntimeException var2) {			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os setores", new Object[0]);			var2.printStackTrace();		}	}	public void buscarCPF() {		new FuncionarioTerceirizadoDAO();		this.pessoa = FuncionarioTerceirizadoDAO.carregarCpf(this.pessoa.getCpf());		CidadeDAO municipioDAO = new CidadeDAO();		this.Cidades = municipioDAO.buscarPorEstado(this.pessoa.getEstadoEndereco().getCodigo());	}	public List<CargoTerceirizado> getCargos() {		return this.cargos;	}	public void setCargos(List<CargoTerceirizado> cargos) {		this.cargos = cargos;	}	public Usuario getUsuarioLogado() {		return this.usuarioLogado;	}	public void setUsuarioLogado(Usuario usuarioLogado) {		this.usuarioLogado = usuarioLogado;	}}
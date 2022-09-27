/* Decompiler 54ms, total 631ms, lines 578 */package br.gov.sc.contrato.bean;import br.gov.sc.contrato.dao.CargoTerceirizadoDAO;import br.gov.sc.contrato.dao.ContratoHistFuncionarioDAO;import br.gov.sc.contrato.dao.ContratoRelacaoDAO;import br.gov.sc.contrato.dao.ContratoTerceirizadoDAO;import br.gov.sc.contrato.dao.EmpresaTerceirizadaDAO;import br.gov.sc.contrato.dao.EventoTerceirizadoDAO;import br.gov.sc.contrato.dao.FuncionarioTerceirizadoDAO;import br.gov.sc.contrato.domain.CargoTerceirizado;import br.gov.sc.contrato.domain.ContratoHistFuncionario;import br.gov.sc.contrato.domain.ContratoRelacao;import br.gov.sc.contrato.domain.ContratoTerceirizado;import br.gov.sc.contrato.domain.EmpresaTerceirizada;import br.gov.sc.contrato.domain.EventoTerceirizado;import br.gov.sc.contrato.domain.FuncionarioTerceirizado;import br.gov.sc.sgi.dao.CidadeDAO;import br.gov.sc.sgi.dao.PessoaDAO;import br.gov.sc.sgi.dao.UnidadeDAO;import br.gov.sc.sgi.domain.Cidade;import br.gov.sc.sgi.domain.PessoaFisica;import br.gov.sc.sgi.domain.Unidade;import br.gov.sc.sgi.domain.Usuario;import java.io.Serializable;import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.ArrayList;import java.util.Date;import java.util.List;import java.util.Map;import javax.annotation.PostConstruct;import javax.faces.application.FacesMessage;import javax.faces.bean.ManagedBean;import javax.faces.bean.ViewScoped;import javax.faces.component.UIParameter;import javax.faces.context.FacesContext;import javax.faces.event.ActionEvent;import javax.servlet.http.HttpSession;import org.omnifaces.util.Messages;import org.primefaces.event.ToggleEvent;@SuppressWarnings("serial")@ManagedBean@ViewScopedpublic class ContratoTerceirizadoBean implements Serializable {	private ContratoRelacao contratoRelacao;	private List<ContratoRelacao> listaContratoRelacoes;	private EventoTerceirizado eventoTerceirizado;	private List<EventoTerceirizado> listaEventosTerceirizados;	private List<EventoTerceirizado> listaEventosTerceirizadosPorFuncionario;	private ContratoHistFuncionario contratoHistFuncionario;	private List<ContratoHistFuncionario> listaHistFuncionarios;	private ContratoTerceirizado contratoTerceirizado;	private List<ContratoTerceirizado> listaContratosTerceirizados;	private FuncionarioTerceirizado funcionarioSelecionado;	private List<FuncionarioTerceirizado> listaFuncionarios;	private EmpresaTerceirizada empresaTerceirizada;	private List<EmpresaTerceirizada> listaEmpresasTerceirizadas;	private PessoaFisica pessoa;	private List<Cidade> Cidades;	private List<Unidade> unidades;	private List<CargoTerceirizado> cargos;	private Usuario usuarioLogado;	private int filtro;	private boolean mostrarSubstituto;	private List<Date> dataEvento;	private List<Date> dataSubstituto;	private Date dataInicial;	private String dateIni;	private String dateFini;	private Date dataFinal;	@PostConstruct	public void listar() {		try {			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);			usuarioLogado = (Usuario) sessao.getAttribute("usuario");									EmpresaTerceirizadaDAO empresaDAO = new EmpresaTerceirizadaDAO();			listaEmpresasTerceirizadas = empresaDAO.listar();			contratoTerceirizado = new ContratoTerceirizado();			pessoa = new PessoaFisica();			UnidadeDAO unidadeDAO = new UnidadeDAO();			unidades = unidadeDAO.listar();			CidadeDAO cidadeDAO = new CidadeDAO();			Cidades = cidadeDAO.buscarPorEstado(usuarioLogado.getPessoa().getEstadoEndereco().getCodigo());			CargoTerceirizadoDAO cargoDAO = new CargoTerceirizadoDAO();			cargos = cargoDAO.listar();						FuncionarioTerceirizadoDAO funcionarioDAO = new FuncionarioTerceirizadoDAO();			ContratoTerceirizadoDAO contratoDAO = new ContratoTerceirizadoDAO();			if (usuarioLogado.getSetor().getSetorNome().contains("Gerência de Apoio Operacional")) {								listaFuncionarios = funcionarioDAO.listarPorTodos();				listaContratosTerceirizados = contratoDAO.listar();			} else {								listaFuncionarios = funcionarioDAO.listarPorUnidadeUsuarioLogado(usuarioLogado.getUnidade());				listaContratosTerceirizados = contratoDAO.listarPorUnidadeUsuarioLogado(usuarioLogado.getUnidade());			}			contratoRelacao = new ContratoRelacao();			contratoHistFuncionario = new ContratoHistFuncionario();			eventoTerceirizado = new EventoTerceirizado();			mostrarSubstituto = false;			filtro = 0;		} catch (RuntimeException var8) {			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Contratos.", new Object[0]);			var8.printStackTrace();		}	}	public void salvar() {		try {			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);			usuarioLogado = (Usuario) sessao.getAttribute("usuario");			ContratoTerceirizadoDAO contratoDAO = new ContratoTerceirizadoDAO();			contratoTerceirizado.setDataCadastro(new Date());			contratoDAO.merge(contratoTerceirizado);			contratoTerceirizado = new ContratoTerceirizado();			listaContratosTerceirizados = contratoDAO.listar();			listar();			Messages.addGlobalInfo("Contrato cadastrado com Sucesso!", new Object[0]);		} catch (RuntimeException var3) {			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Contrato.", new Object[0]);			var3.printStackTrace();		}	}	public void salvarEvento() throws ParseException {		try {			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);			usuarioLogado = (Usuario) sessao.getAttribute("usuario");			EventoTerceirizadoDAO eventoDAO = new EventoTerceirizadoDAO();			ContratoRelacaoDAO contratoRelacaoDAO = new ContratoRelacaoDAO();			FuncionarioTerceirizadoDAO funcionarioDAO = new FuncionarioTerceirizadoDAO();			for (int posicao = 1; posicao < dataEvento.size(); ++posicao) {				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");				Date date1 = sdf.parse(sdf.format((Date) dataEvento.get(0)));				Date date2 = sdf.parse(sdf.format((Date) dataEvento.get(1)));				eventoTerceirizado.setDataCadastro(new Date());				eventoTerceirizado.setColaborador(contratoRelacao.getFuncionarioTerceirizado());				eventoTerceirizado.setDataEventoInicial(date1);				eventoTerceirizado.setDataEventoFinal(date2);				eventoTerceirizado						.setEmpresaTerceirizada(contratoRelacao.getContratoTerceirizado().getEmpresaTerceirizada());				eventoTerceirizado.setUsuarioCadastro(usuarioLogado);				if (filtro == 1) {					Date date3 = sdf.parse(sdf.format((Date) dataSubstituto.get(0)));					Date date4 = sdf.parse(sdf.format((Date) dataSubstituto.get(1)));					eventoTerceirizado.setDataSubstitutoInicial(date3);					eventoTerceirizado.setDataSubstitutoFinal(date4);					contratoRelacao.setFuncionarioTerceirizado(eventoTerceirizado.getSubstituto());				}				eventoDAO.save(eventoTerceirizado);				if (eventoTerceirizado.getSubstituto() == null) {					contratoRelacao.setStatus(eventoTerceirizado.getTipoEvento());				} else {					contratoRelacao.setStatus("Substituto");					contratoHistFuncionario.setFuncionarioTerceirizado(eventoTerceirizado.getSubstituto());				}				contratoRelacaoDAO.merge(contratoRelacao);				contratoHistFuncionario.setStatus(contratoRelacao.getStatus());				contratoRelacaoDAO.salvarFuncionarioHist(contratoRelacao, contratoHistFuncionario);				funcionarioSelecionado = contratoRelacao.getFuncionarioTerceirizado();				funcionarioSelecionado.setTipoEvento(eventoTerceirizado);				funcionarioDAO.merge(funcionarioSelecionado);				Messages.addGlobalInfo("Evento cadastrado com Sucesso!", new Object[0]);			}			eventoTerceirizado = new EventoTerceirizado();			contratoHistFuncionario = new ContratoHistFuncionario();			filtro = 0;		} catch (RuntimeException var11) {			Messages.addGlobalError("Funcionário já vínculado a um contrato.", new Object[0]);			var11.printStackTrace();		}	}	public void onRowToggleFuncionarios(ToggleEvent event) {		ContratoRelacaoDAO relacaoDAO = new ContratoRelacaoDAO();		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row State " + event.getVisibility(),				"Model:" + (ContratoTerceirizado) event.getData());		System.out.println("event.getData() " + event.getData());		listaContratoRelacoes = relacaoDAO.listarPorContratoTerceirizadoObject(event.getData());		listar();		FacesContext.getCurrentInstance().addMessage((String) null, msg);	}	public void salvarNovoFuncionario() {		try {			FuncionarioTerceirizadoDAO funcDAO = new FuncionarioTerceirizadoDAO();			ContratoRelacaoDAO relacaoDAO = new ContratoRelacaoDAO();			int totalFuncionarios = relacaoDAO.listarPorContratoTerceirizadoObject(contratoTerceirizado).size();			System.out.println(contratoTerceirizado.getQtdFuncionarios());			System.out.println("TOTAL FUNCIONARIOS" + totalFuncionarios);			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);			usuarioLogado = (Usuario) sessao.getAttribute("usuario");			if (ContratoRelacaoDAO.carregaFuncionario(funcionarioSelecionado) == null) {				if (totalFuncionarios < contratoTerceirizado.getQtdFuncionarios()) {					contratoRelacao.setDataCadastro(new Date());					contratoRelacao.setUsuarioCadastro(usuarioLogado);					contratoRelacao.setContratoTerceirizado(contratoTerceirizado);					contratoRelacao.setFuncionarioTerceirizado(funcionarioSelecionado);					contratoRelacao.setStatus("Ativo");					relacaoDAO.merge(contratoRelacao);					funcionarioSelecionado.setTipoEvento((EventoTerceirizado) null);					funcDAO.merge(funcionarioSelecionado);					contratoHistFuncionario.setStatus(contratoRelacao.getStatus());					relacaoDAO.salvarFuncionarioHist(contratoRelacao, contratoHistFuncionario);					contratoRelacao = new ContratoRelacao();					contratoHistFuncionario = new ContratoHistFuncionario();					listaContratoRelacoes = relacaoDAO.listar();					Messages.addGlobalInfo("Funcionário cadastrado com Sucesso!", new Object[0]);				} else {					Messages.addGlobalError("Quantidade máxima de Funcionários atingida para esse contrato.",							new Object[0]);				}			} else {				Messages.addGlobalError("Funcionário já vínculado a um contrato.", new Object[0]);			}			listar();		} catch (RuntimeException var5) {			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Funcionário.", new Object[0]);			var5.printStackTrace();		}	}	public void excluir(ActionEvent evento) {		try {			ContratoRelacaoDAO relacaoDAO = new ContratoRelacaoDAO();			contratoRelacao = (ContratoRelacao) evento.getComponent().getAttributes().get("funcionarioSelecionado");			relacaoDAO.excluir(contratoRelacao);			contratoHistFuncionario.setStatus("Removido");			relacaoDAO.salvarFuncionarioHist(contratoRelacao, contratoHistFuncionario);			contratoRelacao = new ContratoRelacao();			listar();			Messages.addGlobalInfo("Funcionário removido com sucesso.", new Object[0]);		} catch (RuntimeException var3) {			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Funcionário.", new Object[0]);			var3.printStackTrace();		}	}	public void excluirContrato(ActionEvent evento) {		try {			ContratoTerceirizadoDAO contratoDAO = new ContratoTerceirizadoDAO();			contratoTerceirizado = (ContratoTerceirizado) evento.getComponent().getAttributes()					.get("contratoSelecionado");			contratoDAO.excluir(contratoTerceirizado);			contratoTerceirizado = new ContratoTerceirizado();			listar();			Messages.addGlobalInfo("Contrato removido com sucesso.", new Object[0]);		} catch (RuntimeException var3) {			Messages.addGlobalError("Existe funcionários vínculados a esse contrato.", new Object[0]);			var3.printStackTrace();		}	}	public void editar(ActionEvent evento) {		contratoTerceirizado = (ContratoTerceirizado) evento.getComponent().getAttributes().get("contratoSelecionado");	}	public void novoFuncionario(ActionEvent evento) {		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);		usuarioLogado = (Usuario) sessao.getAttribute("usuario");		contratoTerceirizado = (ContratoTerceirizado) evento.getComponent().getAttributes().get("contratoSelecionado");		FuncionarioTerceirizadoDAO funcDAO = new FuncionarioTerceirizadoDAO();		if (usuarioLogado.getSetor().getSetorNome().contains("Gerência de Apoio Operacional")) {			System.out.println("listarPorTodos");			listaFuncionarios = funcDAO.listarPorTodos();		} else {			System.out.println("listarPorUnidadeUsuarioLogado");			listaFuncionarios = funcDAO.listarPorUnidadeUsuarioLogado(usuarioLogado.getUnidade());		}	}	public void novoEvento(ActionEvent evento) {		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);		usuarioLogado = (Usuario) sessao.getAttribute("usuario");		contratoRelacao = (ContratoRelacao) evento.getComponent().getAttributes().get("funcionarioSelecionado");			FuncionarioTerceirizadoDAO funcDAO = new FuncionarioTerceirizadoDAO();		if (usuarioLogado.getSetor().getSetorNome().contains("Gerência de Apoio Operacional")) {						listaFuncionarios = funcDAO.listarPorTodos();		} else {						listaFuncionarios = funcDAO.listarPorUnidadeUsuarioLogado(usuarioLogado.getUnidade());		}		// EventoTerceirizadoDAO eventoDAO = new EventoTerceirizadoDAO();		// listaEventosTerceirizadosPorFuncionario =		// eventoDAO.listarFuncionarioSelecionado(contratoRelacao.getFuncionarioTerceirizado(),		// eventoTerceirizado.getTipoEvento(), dataInicial, dataFinal);				}	public void carregarListaEventosPorFuncionario() throws ParseException {		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);		usuarioLogado = (Usuario) sessao.getAttribute("usuario");									FuncionarioTerceirizadoDAO funcDAO = new FuncionarioTerceirizadoDAO();		if (usuarioLogado.getSetor().getSetorNome().contains("Gerência de Apoio Operacional")) {						listaFuncionarios = funcDAO.listarPorTodos();		} else {						listaFuncionarios = funcDAO.listarPorUnidadeUsuarioLogado(usuarioLogado.getUnidade());		}						System.out.println(contratoRelacao + " FORA contratoRelacao");				EventoTerceirizadoDAO eventoDAO = new EventoTerceirizadoDAO();			for (int posicao = 1; posicao < dataEvento.size(); ++posicao) {				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");				Date date1 = sdf.parse(sdf.format((Date) dataEvento.get(0)));				Date date2 = sdf.parse(sdf.format((Date) dataEvento.get(1)));								System.out.println(contratoRelacao + " FOR contratoRelacao");				listaEventosTerceirizadosPorFuncionario = eventoDAO.listarFuncionarioSelecionado(						contratoRelacao.getFuncionarioTerceirizado(), eventoTerceirizado.getTipoEvento(), date1, date2);					}	}	public void dadosFuncSelecionado() {		pessoa = PessoaDAO.carregarCpf(funcionarioSelecionado.getPessoa().getCpf());		System.out.println(pessoa + "pessoa");	}	public void buscarCPF() {		new FuncionarioTerceirizadoDAO();		pessoa = FuncionarioTerceirizadoDAO.carregarCpf(pessoa.getCpf());		CidadeDAO municipioDAO = new CidadeDAO();		Cidades = municipioDAO.buscarPorEstado(pessoa.getEstadoEndereco().getCodigo());	}	public void habilitar() {		if (filtro == 1) {			mostrarSubstituto = true;		}		if (filtro == 0) {			mostrarSubstituto = false;		}	}	public void blurEvento() {		new ContratoRelacaoDAO();		new ContratoTerceirizadoDAO();		EventoTerceirizadoDAO eventoDAO = new EventoTerceirizadoDAO();		new EmpresaTerceirizadaDAO();		try {			System.out.println(contratoTerceirizado.getEmpresaTerceirizada());			if (contratoTerceirizado.getEmpresaTerceirizada() != null) {				System.out.println("caiu aqui contratoTerceirizado.getEmpresaTerceirizada()");				empresaTerceirizada = EmpresaTerceirizadaDAO.carregarEmpresa(						contratoTerceirizado.getEmpresaTerceirizada().getPessoaJuridica().getNomeFantasia());				if (dataInicial != null && dataFinal == null) {					Messages.addGlobalError("Favor informar a Data Final", new Object[0]);				}				if (dataFinal != null && dataInicial == null) {					Messages.addGlobalError("Favor informar a Data Inicial", new Object[0]);				}				listaEventosTerceirizados = eventoDAO.listarPorEmpresa(empresaTerceirizada,						eventoTerceirizado.getTipoEvento(), dataInicial, dataFinal);				if (listaEventosTerceirizados.isEmpty()) {					Messages.addGlobalError("Não existe dados com os campos fornecidos!", new Object[0]);				}				System.out.println(listaEventosTerceirizados);			}		} catch (Exception var6) {			var6.printStackTrace();		}	}	public void blurContratoXFuncionario() {		new EventoTerceirizadoDAO();		new EmpresaTerceirizadaDAO();		ContratoHistFuncionarioDAO histDAO = new ContratoHistFuncionarioDAO();		try {			System.out.println(contratoTerceirizado.getEmpresaTerceirizada());			if (contratoTerceirizado.getEmpresaTerceirizada() != null) {				System.out.println("caiu aqui contratoTerceirizado.getEmpresaTerceirizada()");				empresaTerceirizada = EmpresaTerceirizadaDAO.carregarEmpresa(						contratoTerceirizado.getEmpresaTerceirizada().getPessoaJuridica().getNomeFantasia());				if (dataInicial != null && dataFinal == null) {					Messages.addGlobalError("Favor informar a Data Final", new Object[0]);				}				if (dataFinal != null && dataInicial == null) {					Messages.addGlobalError("Favor informar a Data Inicial", new Object[0]);				}				listaHistFuncionarios = histDAO.listarPorEmpresa(empresaTerceirizada,						contratoHistFuncionario.getFuncionarioTerceirizado(), dataInicial, dataFinal);				if (listaHistFuncionarios.isEmpty()) {					Messages.addGlobalError("Não existe dados com os campos fornecidos!", new Object[0]);				}				System.out.println(listaHistFuncionarios);			}		} catch (Exception var5) {			var5.printStackTrace();		}	}	public ContratoRelacao getContratoRelacao() {		return contratoRelacao;	}	public void setContratoRelacao(ContratoRelacao contratoRelacao) {		this.contratoRelacao = contratoRelacao;	}	public List<ContratoRelacao> getListaContratoRelacoes() {		return listaContratoRelacoes;	}	public void setListaContratoRelacoes(List<ContratoRelacao> listaContratoRelacoes) {		this.listaContratoRelacoes = listaContratoRelacoes;	}	public EventoTerceirizado getEventoTerceirizado() {		return eventoTerceirizado;	}	public void setEventoTerceirizado(EventoTerceirizado eventoTerceirizado) {		this.eventoTerceirizado = eventoTerceirizado;	}	public List<EventoTerceirizado> getListaEventosTerceirizados() {		return listaEventosTerceirizados;	}	public void setListaEventosTerceirizados(List<EventoTerceirizado> listaEventosTerceirizados) {		this.listaEventosTerceirizados = listaEventosTerceirizados;	}	public ContratoHistFuncionario getContratoHistFuncionario() {		return contratoHistFuncionario;	}	public void setContratoHistFuncionario(ContratoHistFuncionario contratoHistFuncionario) {		this.contratoHistFuncionario = contratoHistFuncionario;	}	public List<ContratoHistFuncionario> getListaHistFuncionarios() {		return listaHistFuncionarios;	}	public void setListaHistFuncionarios(List<ContratoHistFuncionario> listaHistFuncionarios) {		this.listaHistFuncionarios = listaHistFuncionarios;	}	public ContratoTerceirizado getContratoTerceirizado() {		return contratoTerceirizado;	}	public void setContratoTerceirizado(ContratoTerceirizado contratoTerceirizado) {		this.contratoTerceirizado = contratoTerceirizado;	}	public List<ContratoTerceirizado> getListaContratosTerceirizados() {		return listaContratosTerceirizados;	}	public void setListaContratosTerceirizados(List<ContratoTerceirizado> listaContratosTerceirizados) {		this.listaContratosTerceirizados = listaContratosTerceirizados;	}	public FuncionarioTerceirizado getFuncionarioSelecionado() {		return funcionarioSelecionado;	}	public void setFuncionarioSelecionado(FuncionarioTerceirizado funcionarioSelecionado) {		this.funcionarioSelecionado = funcionarioSelecionado;	}	public List<FuncionarioTerceirizado> getListaFuncionarios() {		return listaFuncionarios;	}	public void setListaFuncionarios(List<FuncionarioTerceirizado> listaFuncionarios) {		this.listaFuncionarios = listaFuncionarios;	}	public EmpresaTerceirizada getEmpresaTerceirizada() {		return empresaTerceirizada;	}	public void setEmpresaTerceirizada(EmpresaTerceirizada empresaTerceirizada) {		this.empresaTerceirizada = empresaTerceirizada;	}	public List<EmpresaTerceirizada> getListaEmpresasTerceirizadas() {		return listaEmpresasTerceirizadas;	}	public void setListaEmpresasTerceirizadas(List<EmpresaTerceirizada> listaEmpresasTerceirizadas) {		this.listaEmpresasTerceirizadas = listaEmpresasTerceirizadas;	}	public PessoaFisica getPessoa() {		return pessoa;	}	public void setPessoa(PessoaFisica pessoa) {		this.pessoa = pessoa;	}	public List<Cidade> getCidades() {		return Cidades;	}	public void setCidades(List<Cidade> cidades) {		Cidades = cidades;	}	public List<Unidade> getUnidades() {		return unidades;	}	public void setUnidades(List<Unidade> unidades) {		this.unidades = unidades;	}	public List<CargoTerceirizado> getCargos() {		return cargos;	}	public void setCargos(List<CargoTerceirizado> cargos) {		this.cargos = cargos;	}	public Usuario getUsuarioLogado() {		return usuarioLogado;	}	public void setUsuarioLogado(Usuario usuarioLogado) {		this.usuarioLogado = usuarioLogado;	}	public int getFiltro() {		return filtro;	}	public void setFiltro(int filtro) {		this.filtro = filtro;	}	public boolean isMostrarSubstituto() {		return mostrarSubstituto;	}	public void setMostrarSubstituto(boolean mostrarSubstituto) {		this.mostrarSubstituto = mostrarSubstituto;	}	public List<Date> getDataEvento() {		return dataEvento;	}	public void setDataEvento(List<Date> dataEvento) {		this.dataEvento = dataEvento;	}	public List<Date> getDataSubstituto() {		return dataSubstituto;	}	public void setDataSubstituto(List<Date> dataSubstituto) {		this.dataSubstituto = dataSubstituto;	}	public Date getDataInicial() {		return dataInicial;	}	public void setDataInicial(Date dataInicial) {		this.dataInicial = dataInicial;	}	public String getDateIni() {		return dateIni;	}	public void setDateIni(String dateIni) {		this.dateIni = dateIni;	}	public String getDateFini() {		return dateFini;	}	public void setDateFini(String dateFini) {		this.dateFini = dateFini;	}	public Date getDataFinal() {		return dataFinal;	}	public void setDataFinal(Date dataFinal) {		this.dataFinal = dataFinal;	}	public List<EventoTerceirizado> getListaEventosTerceirizadosPorFuncionario() {		return listaEventosTerceirizadosPorFuncionario;	}	public void setListaEventosTerceirizadosPorFuncionario(			List<EventoTerceirizado> listaEventosTerceirizadosPorFuncionario) {		this.listaEventosTerceirizadosPorFuncionario = listaEventosTerceirizadosPorFuncionario;	}}
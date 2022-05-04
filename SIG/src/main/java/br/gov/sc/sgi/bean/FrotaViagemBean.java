package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.FrotaCondutorDAO;
import br.gov.sc.sgi.dao.FrotaUnidadeDAO;
import br.gov.sc.sgi.dao.FrotaVeiculoDAO;
import br.gov.sc.sgi.dao.FrotaViagemDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.FrotaCondutor;
import br.gov.sc.sgi.domain.FrotaUnidade;
import br.gov.sc.sgi.domain.FrotaVeiculo;
import br.gov.sc.sgi.domain.FrotaViagem;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FrotaViagemBean implements Serializable {

	private PessoaFisica pessoa;

	private FrotaViagem viagem;

	private FrotaViagem viagemCarregada;
	private FrotaVeiculo veiculo;
	private FrotaUnidade unidade;
	private FrotaCondutor condutor;

	private Estado estado;
	private List<Estado> estados;

	private List<Cidade> cidades;

	private List<FrotaVeiculo> veiculos;
	private List<FrotaViagem> viagens;
	private List<FrotaViagem> viagensAprovadas;
	private List<FrotaViagem> viagensCanceladas;
	private List<FrotaViagem> viagensSolicitadas;
	private List<FrotaViagem> viagensBaixadas;
	private List<FrotaViagem> viagensReprovadas;
	private List<FrotaViagem> viagensPorSolicitante;
	private List<FrotaViagem> viagensPelaBusca;
	private List<FrotaUnidade> unidades;
	private List<FrotaCondutor> condutores;

	private Boolean exibePainelDados;

	private Usuario usuarioLogado;

	public List<FrotaViagem> getViagens() {
		return viagens;
	}

	public void setViagens(List<FrotaViagem> viagens) {
		this.viagens = viagens;
	}

	public Boolean getExibePainelDados() {
		return exibePainelDados;
	}

	public void setExibePainelDados(Boolean exibePainelDados) {
		this.exibePainelDados = exibePainelDados;
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

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public FrotaUnidade getUnidade() {
		return unidade;
	}

	public void setUnidade(FrotaUnidade unidade) {
		this.unidade = unidade;
	}
	
	public Date getHj() {
		   Calendar c = Calendar.getInstance(); 
		   c.add(Calendar.DATE, -1);
		   return c.getTime();
		}
	
	
	
	public Date getTodayPlusThree() {
		   Calendar c = Calendar.getInstance(); 
		   

		   if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)) { 
			   c.add(Calendar.DATE, 3);
			} 

		   if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)) { 
			   c.add(Calendar.DATE, 3);
			} 

		   if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)) { 
			   c.add(Calendar.DATE, 5);
			} 
		   if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)) { 
			   c.add(Calendar.DATE, 5);
			} 

		   if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) { 
			   c.add(Calendar.DATE, 5);
			} 
		   
		  
		   return c.getTime();
		}
	
	

	@PostConstruct
	public void listar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			pessoa = new PessoaFisica();

			FrotaUnidadeDAO unidadeDAO = new FrotaUnidadeDAO();
			unidades = unidadeDAO.listar();

			FrotaViagemDAO viagemDAO = new FrotaViagemDAO();
			viagensPorSolicitante = viagemDAO.listarOrder(usuarioLogado);
			viagens = viagemDAO.listar();
			
			setViagensAprovadas(viagemDAO.listarAprovadas());
			viagensCanceladas = viagemDAO.listarCanceladas();
			viagensSolicitadas = viagemDAO.listarSolicitadas();
			viagensBaixadas = viagemDAO.listarBaixadas();
			viagensReprovadas = viagemDAO.listarReprovadas();
		

			viagem = new FrotaViagem();
			veiculo = new FrotaVeiculo();

			FrotaCondutorDAO condutorDAO = new FrotaCondutorDAO();
			condutores = condutorDAO.listar();

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Cidades.");
			erro.printStackTrace();
		}
	}

	public void popular() {

		try {
			if (veiculo.getUnidade() != null) {
				FrotaVeiculoDAO veiculoDAO = new FrotaVeiculoDAO();

				System.out.println(viagens);
				veiculos = veiculoDAO.buscarPorUnidade(veiculo.getUnidade().getCodigo(), viagens);

			} else {
				veiculos = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar os veiculos");
			erro.printStackTrace();
		}

	}

	public void buscar() {
		exibePainelDados = true;

		// BUSCAR VEICULO (NULL) COM A FROTAVIAGEM RELAÇÃO
		System.out.println("Veiculo " + veiculo.getUnidade());

		FrotaViagemDAO viagemDAO = new FrotaViagemDAO();

		unidade = veiculo.getUnidade();

		System.out.println("Unidade " + unidade);

		viagensPelaBusca = viagemDAO.listarViagem(unidade, veiculo);
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

	public void EnviaEmailSalvar() throws EmailException {

		String emailAutent = "naorespondadetransc@gmail.com";
		String senhaAutent = "sistemasig2021";

		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(emailAutent, senhaAutent));
		email.setDebug(true);
		email.setSSLOnConnect(true);

		// email.setSSLCheckServerIdentity(true);
		// email.setStartTLSRequired(true);

		try {

			String horaInicio = new SimpleDateFormat("HH:mm:ss").format(viagem.getHoraInicial());

			email.setFrom(emailAutent);
			email.setSubject(
					"Nova solicitação de Viagem para " + viagem.getUsuarioCadastro().getPessoa().getNomeCompleto());
			email.setMsg(
					"<span style='font-family: Arial, Helvetica, sans-serif !important;font-size: 14px'><b>Informações sobre a viagem</b>"
							+ "\n\n" + "<b>Solicitante:</b> "
							+ viagem.getUsuarioCadastro().getPessoa().getNomeCompleto() + "\n" + "<b>Data Inicial:</b> "
							+ viagem.getDataInicial().getDate() + "/" + (viagem.getDataInicial().getMonth() + 1) + "/"
							+ (viagem.getDataInicial().getYear() + 1900) + "\n" + "<b>Hora Início:</b> " + horaInicio
							+ "\n" + "<b>Data Entrega (Prevista):</b> " + viagem.getDataPrevista().getDate() + "/"
							+ (viagem.getDataPrevista().getMonth() + 1) + "/"
							+ (viagem.getDataPrevista().getYear() + 1900) + "\n" + "<b>Unidade:</b> "
							+ viagem.getFrotaUnidade().getFrotaUnidade() + "\n" + "<b>Veículo:</b> "
							+ viagem.getFrotaVeiculo().getPlaca() + " - " + viagem.getFrotaVeiculo().getModelo() + "\n"
							+ "<b>Destino:</b> " + viagem.getDestino() + "\n" + "<b>Condutor:</b> "
							+ viagem.getFrotaCondutor().getPessoa().getNomeCompleto() + "\n" + "\n"
							+ "Verifique o sistema para mais informações" + "\n\n"
							+ "<b>SIG - Sistema Integrado de Gestão - DETRAN/SC</b> </td>" + "\n"
							+ "<img src='http://10.121.222.4:8081/SIG/resources/images/sig4.png' width='150' height='150'> </img>"

			);
			email.addTo("frota@detran.sc.gov.br"); // E-MAIL SETOR GEAPO

			email.send();

			Messages.addGlobalInfo("E-mail enviado com Sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu ao enviar o e-mail.");
			erro.printStackTrace();
		}
	}

	public void EnviaEmailAprovar() throws EmailException {

		String emailAutent = "naorespondadetransc@gmail.com";
		String senhaAutent = "sistemasig2021";

		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(emailAutent, senhaAutent));
		email.setDebug(true);
		email.setSSLOnConnect(true);

		// email.setSSLCheckServerIdentity(true);
		// email.setStartTLSRequired(true);

		try {

			String horaInicio = new SimpleDateFormat("HH:mm:ss").format(viagem.getHoraInicial());

			email.setFrom(emailAutent);
			email.setSubject("Viagem aprovada " + viagem.getUsuarioCadastro().getPessoa().getNomeCompleto() + " ("
					+ viagem.getCodigo() + ")");
			email.setMsg(
					"<span style='font-family: Arial, Helvetica, sans-serif !important;font-size: 14px'><b>Informações sobre a viagem</b>"
							+ "\n\n" + "<b>Solicitante:</b> "
							+ viagem.getUsuarioCadastro().getPessoa().getNomeCompleto() + "\n" + "<b>Data Inicial:</b> "
							+ viagem.getDataInicial().getDate() + "/" + (viagem.getDataInicial().getMonth() + 1) + "/"
							+ (viagem.getDataInicial().getYear() + 1900) + "\n" + "<b>Hora Início:</b> " + horaInicio
							+ "\n" + "<b>Data Entrega (Prevista):</b> " + viagem.getDataPrevista().getDate() + "/"
							+ (viagem.getDataPrevista().getMonth() + 1) + "/"
							+ (viagem.getDataPrevista().getYear() + 1900) + "\n" + "<b>Unidade:</b> "
							+ viagem.getFrotaUnidade().getFrotaUnidade() + "\n" + "<b>Veículo:</b> "
							+ viagem.getFrotaVeiculo().getPlaca() + " - " + viagem.getFrotaVeiculo().getModelo() + "\n"
							+ "<b>Destino:</b> " + viagem.getDestino() + "\n" + "<b>Condutor:</b> "
							+ viagem.getFrotaCondutor().getPessoa().getNomeCompleto() + "\n" + "\n"
							+ "Verifique o sistema para mais informações" + "\n\n"
							+ "<b>SIG - Sistema Integrado de Gestão - DETRAN/SC</b> </td>" + "\n"
							+ "<img src='http://10.121.222.4:8081/SIG/resources/images/sig4.png' width='150' height='150'> </img>"

			);
			email.addTo(viagem.getUsuarioCadastro().getPessoa().getEmail()); // E-MAIL SOLICITANTE

			email.send();

			Messages.addGlobalInfo("E-mail enviado com Sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu ao enviar o e-mail.");
			erro.printStackTrace();
		}
	}

	public void EnviaEmailCancelar() throws EmailException {

		String emailAutent = "naorespondadetransc@gmail.com";
		String senhaAutent = "sistemasig2021";

		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(emailAutent, senhaAutent));
		email.setDebug(true);
		email.setSSLOnConnect(true);

		// email.setSSLCheckServerIdentity(true);
		// email.setStartTLSRequired(true);

		try {

			String horaInicio = new SimpleDateFormat("HH:mm:ss").format(viagem.getHoraInicial());

			email.setFrom(emailAutent);
			email.setSubject("Viagem reprovada " + viagem.getUsuarioCadastro().getPessoa().getNomeCompleto() + " ("
					+ viagem.getCodigo() + ")");
			email.setMsg(
					"<span style='font-family: Arial, Helvetica, sans-serif !important;font-size: 14px'><b>Informações sobre a viagem</b>"
							+ "\n\n" + "<b>Motivo:</b> " + viagem.getObservacaoViagem() + "\n\n"
							+ "<b>Solicitante:</b> " + viagem.getUsuarioCadastro().getPessoa().getNomeCompleto() + "\n"
							+ "<b>Data Inicial:</b> " + viagem.getDataInicial().getDate() + "/"
							+ (viagem.getDataInicial().getMonth() + 1) + "/"
							+ (viagem.getDataInicial().getYear() + 1900) + "\n" + "<b>Hora Início:</b> " + horaInicio
							+ "\n" + "<b>Data Entrega (Prevista):</b> " + viagem.getDataPrevista().getDate() + "/"
							+ (viagem.getDataPrevista().getMonth() + 1) + "/"
							+ (viagem.getDataPrevista().getYear() + 1900) + "\n" + "<b>Unidade:</b> "
							+ viagem.getFrotaUnidade().getFrotaUnidade() + "\n" + "<b>Veículo:</b> "
							+ viagem.getFrotaVeiculo().getPlaca() + " - " + viagem.getFrotaVeiculo().getModelo() + "\n"
							+ "<b>Destino:</b> " + viagem.getDestino() + "\n" + "<b>Condutor:</b> "
							+ viagem.getFrotaCondutor().getPessoa().getNomeCompleto() + "\n" + "\n"
							+ "Verifique o sistema para mais informações" + "\n\n"
							+ "<b>SIG - Sistema Integrado de Gestão - DETRAN/SC</b> </td>" + "\n"
							+ "<img src='http://10.121.222.4:8081/SIG/resources/images/sig4.png' width='150' height='150'> </img>"

			);
			email.addTo(viagem.getUsuarioCadastro().getPessoa().getEmail()); // E-MAIL SETOR GEAPO

			email.send();

			Messages.addGlobalInfo("E-mail enviado com Sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu ao enviar o e-mail.");
			erro.printStackTrace();
		}
	}

	public void salvar() throws EmailException {
		try {

			FrotaViagemDAO viagemDAO = new FrotaViagemDAO();

			viagem.setFrotaVeiculo(veiculo);
			viagem.setFrotaUnidade(veiculo.getUnidade());
			viagem.setUsuarioCadastro(usuarioLogado);
			viagem.setDataInclusao(new Date());

			viagem.setUltimoUsuario(usuarioLogado);

			viagemDAO.merge(viagem);

			viagemDAO.salvarVeiculo(veiculo);

			// ----------------
			FrotaUnidadeDAO unidadeDAO = new FrotaUnidadeDAO();
			unidades = unidadeDAO.listar();

			viagens = viagemDAO.listar();
			
			viagensPorSolicitante = viagemDAO.listarOrder(usuarioLogado);
			
			FrotaViagemBean.this.EnviaEmailSalvar();
			
			viagem = new FrotaViagem();

			Messages.addGlobalInfo("Viagem cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Viagem.");
			erro.printStackTrace();
		}
	}

	public void salvarExcluir() throws EmailException {
		try {

			FrotaViagemDAO viagemDAO = new FrotaViagemDAO();

			viagem.setFrotaVeiculo(veiculo);
			viagem.setFrotaUnidade(veiculo.getUnidade());
			viagem.setDataInclusao(new Date());

			viagem.setUltimoUsuario(usuarioLogado);

			viagemDAO.merge(viagem);

			viagemDAO.salvarVeiculoExcluir(veiculo);

			// ----------------
			FrotaUnidadeDAO unidadeDAO = new FrotaUnidadeDAO();
			unidades = unidadeDAO.listar();

			viagens = viagemDAO.listar();
			
			viagem = new FrotaViagem();

			FrotaViagemBean.this.EnviaEmailCancelar();

			Messages.addGlobalInfo("Viagem excluída com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Viagem.");
			erro.printStackTrace();
		}
	}

	public void salvarExcluirSolicitante() throws EmailException {
		try {

			FrotaViagemDAO viagemDAO = new FrotaViagemDAO();

			viagem.setFrotaVeiculo(veiculo);
			viagem.setFrotaUnidade(veiculo.getUnidade());
			viagem.setUsuarioCadastro(usuarioLogado);
			viagem.setDataInclusao(new Date());

			viagem.setUltimoUsuario(usuarioLogado);

			viagemDAO.merge(viagem);

			viagemDAO.salvarVeiculoExcluir(veiculo);

			// ----------------
			FrotaUnidadeDAO unidadeDAO = new FrotaUnidadeDAO();
			unidades = unidadeDAO.listar();

			viagens = viagemDAO.listar();

			Messages.addGlobalInfo("Viagem excluída com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Viagem.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {

			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");
			viagem = (FrotaViagem) evento.getComponent().getAttributes().get("viagemSelecionado");

			System.out.println(viagem);
			FrotaViagemDAO viagemDAO = new FrotaViagemDAO();

			veiculo = viagem.getFrotaVeiculo();
			System.out.println(veiculo);

			viagem.setUltimoUsuario(usuarioLogado);

			viagemDAO.excluirViagem(veiculo, viagem);

			viagens = viagemDAO.listarOrder(usuarioLogado);

			Messages.addGlobalInfo("Viagem removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Viagem.");
			erro.printStackTrace();
		}
	}
	
	public void recusar(ActionEvent evento) {

		try {

			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");
			viagem = (FrotaViagem) evento.getComponent().getAttributes().get("viagemSelecionado");

			System.out.println(viagem);
			FrotaViagemDAO viagemDAO = new FrotaViagemDAO();

			veiculo = viagem.getFrotaVeiculo();
			System.out.println(veiculo);

			viagem.setUltimoUsuario(usuarioLogado);

			viagemDAO.recusarViagem(veiculo, viagem);

			viagens = viagemDAO.listarOrder(usuarioLogado);
			
			viagensSolicitadas = viagemDAO.listarSolicitadas();
			viagensAprovadas = viagemDAO.listarAprovadas();
			viagensBaixadas = viagemDAO.listarBaixadas();
			viagensReprovadas = viagemDAO.listarReprovadas();
			viagensCanceladas = viagemDAO.listarCanceladas();

			Messages.addGlobalInfo("Viagem removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Viagem.");
			erro.printStackTrace();
		}
	}

	public void visualizar(ActionEvent evento) {

		try {
			viagem = (FrotaViagem) evento.getComponent().getAttributes().get("viagemSelecionado");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar visualizar a Viagem.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			viagem = (FrotaViagem) evento.getComponent().getAttributes().get("viagemSelecionado");

			FrotaViagemDAO viagemDAO = new FrotaViagemDAO();
			viagens = viagemDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar um Viagem.");
			erro.printStackTrace();
		}
	}

	public void baixaViagem() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			FrotaViagemDAO viagemDAO = new FrotaViagemDAO();

			viagem.setStatus(4); // BAIXADA
			viagem.setUltimoUsuario(usuarioLogado);

			viagemDAO.viagemBaixa(veiculo, viagem);

			Messages.addGlobalInfo("Viagem baixada com sucesso!");

			viagens = viagemDAO.listar();
			
			viagensSolicitadas = viagemDAO.listarSolicitadas();
			viagensAprovadas = viagemDAO.listarAprovadas();
			viagensBaixadas = viagemDAO.listarBaixadas();
			viagensReprovadas = viagemDAO.listarReprovadas();
			viagensCanceladas = viagemDAO.listarCanceladas();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o SGP-e.");
			erro.printStackTrace();
		}
	}

	public void aprovarViagem() throws EmailException {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			FrotaViagemDAO viagemDAO = new FrotaViagemDAO();

			viagem.setStatus(1); // APROVADA
			viagem.setUltimoUsuario(usuarioLogado);

			viagemDAO.merge(viagem);

			FrotaViagemBean.this.EnviaEmailAprovar();

			Messages.addGlobalInfo("Viagem aprovada com sucesso!");
			
			viagensSolicitadas = viagemDAO.listarSolicitadas();
			viagensAprovadas = viagemDAO.listarAprovadas();
			viagensBaixadas = viagemDAO.listarBaixadas();
			viagensReprovadas = viagemDAO.listarReprovadas();
			viagensCanceladas = viagemDAO.listarCanceladas();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o SGP-e.");
			erro.printStackTrace();
		}
	}

	public FrotaViagem getViagem() {
		return viagem;
	}

	public void setViagem(FrotaViagem viagem) {
		this.viagem = viagem;
	}

	public FrotaCondutor getCondutor() {
		return condutor;
	}

	public void setCondutor(FrotaCondutor condutor) {
		this.condutor = condutor;
	}

	public List<FrotaCondutor> getCondutores() {
		return condutores;
	}

	public void setCondutores(List<FrotaCondutor> condutores) {
		this.condutores = condutores;
	}

	public List<FrotaViagem> getViagensPelaBusca() {
		return viagensPelaBusca;
	}

	public void setViagensPelaBusca(List<FrotaViagem> viagensPelaBusca) {
		this.viagensPelaBusca = viagensPelaBusca;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public FrotaViagem getViagemCarregada() {
		return viagemCarregada;
	}

	public void setViagemCarregada(FrotaViagem viagemCarregada) {
		this.viagemCarregada = viagemCarregada;
	}

	public List<FrotaViagem> getViagensPorSolicitante() {
		return viagensPorSolicitante;
	}

	public void setViagensPorSolicitante(List<FrotaViagem> viagensPorSolicitante) {
		this.viagensPorSolicitante = viagensPorSolicitante;
	}

	public List<FrotaViagem> getViagensCanceladas() {
		return viagensCanceladas;
	}

	public void setViagensCanceladas(List<FrotaViagem> viagensCanceladas) {
		this.viagensCanceladas = viagensCanceladas;
	}

	public List<FrotaViagem> getViagensSolicitadas() {
		return viagensSolicitadas;
	}

	public void setViagensSolicitadas(List<FrotaViagem> viagensSolicitadas) {
		this.viagensSolicitadas = viagensSolicitadas;
	}

	public List<FrotaViagem> getViagensReprovadas() {
		return viagensReprovadas;
	}

	public void setViagensReprovadas(List<FrotaViagem> viagensReprovadas) {
		this.viagensReprovadas = viagensReprovadas;
	}

	public List<FrotaViagem> getViagensBaixadas() {
		return viagensBaixadas;
	}

	public void setViagensBaixadas(List<FrotaViagem> viagensBaixadas) {
		this.viagensBaixadas = viagensBaixadas;
	}

	public List<FrotaViagem> getViagensAprovadas() {
		return viagensAprovadas;
	}

	public void setViagensAprovadas(List<FrotaViagem> viagensAprovadas) {
		this.viagensAprovadas = viagensAprovadas;
	}

}
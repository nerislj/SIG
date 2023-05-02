package br.gov.sc.ecvitl.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;
import javax.swing.text.MaskFormatter;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;

import com.google.gson.Gson;

import br.gov.sc.codet.dao.HistoricoProcessoDAO;
import br.gov.sc.codet.dao.ProcessoDAO;
import br.gov.sc.codet.domain.HistoricoProcessoCODET;
import br.gov.sc.contrato.dao.ContratoRelacaoDAO;
import br.gov.sc.contrato.dao.HistoricoEdicoesContratoDAO;
import br.gov.sc.contrato.domain.ContratoTerceirizado;
import br.gov.sc.ecvitl.dao.EcvItlDAO;
import br.gov.sc.ecvitl.dao.HistoricoDescricaoDAO;
import br.gov.sc.ecvitl.dao.PessoaEcvItlDAO;
import br.gov.sc.ecvitl.domain.EcvItl;
import br.gov.sc.ecvitl.domain.HistoricoDescricao;
import br.gov.sc.ecvitl.domain.PessoaEcvItl;
import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.CredenciadoDAO;
import br.gov.sc.sgi.dao.CredenciadoEmpDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EcvItlBean implements Serializable {

	private EcvItl ecvItl;
	private List<EcvItl> listaEcvItl;

	private HistoricoDescricao historicoEcvItl;
	private List<HistoricoDescricao> listaHistoricoEcvItl;

	private PessoaEcvItl pessoaEcvItl;
	private List<PessoaEcvItl> listaPessoasEcvItl;

	private String campoDaBusca;
	private Usuario usuarioLogado;

	public PessoaEcvItl getPessoaEcvItl() {
		return pessoaEcvItl;
	}

	public void setPessoaEcvItl(PessoaEcvItl pessoaEcvItl) {
		this.pessoaEcvItl = pessoaEcvItl;
	}

	public List<PessoaEcvItl> getListaPessoasEcvItl() {
		return listaPessoasEcvItl;
	}

	public void setListaPessoasEcvItl(List<PessoaEcvItl> listaPessoasEcvItl) {
		this.listaPessoasEcvItl = listaPessoasEcvItl;
	}

	public HistoricoDescricao getHistoricoEcvItl() {
		return historicoEcvItl;
	}

	public void setHistoricoEcvItl(HistoricoDescricao historicoEcvItl) {
		this.historicoEcvItl = historicoEcvItl;
	}

	public List<HistoricoDescricao> getListaHistoricoEcvItl() {
		return listaHistoricoEcvItl;
	}

	public void setListaHistoricoEcvItl(List<HistoricoDescricao> listaHistoricoEcvItl) {
		this.listaHistoricoEcvItl = listaHistoricoEcvItl;
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

			EcvItlDAO ecvItlDAO = new EcvItlDAO();
			listaEcvItl = ecvItlDAO.listar("credencial");

			ecvItl = new EcvItl();
			historicoEcvItl = new HistoricoDescricao();
			pessoaEcvItl = new PessoaEcvItl();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Ecv/Itl.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		ecvItl = new EcvItl();

	}

	public void salvar() {
		try {

			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			this.usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			EcvItlDAO ecvItlDAO = new EcvItlDAO();

			ecvItl.setUsuarioCadastro(usuarioLogado);
			ecvItl.setDataCadastro(new Date());

			ecvItlDAO.merge(ecvItl);

			listaEcvItl = ecvItlDAO.listar("credencial");

			Messages.addGlobalInfo("Ecv/Itl cadastrada com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Ecv/Itl.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			ecvItl = (EcvItl) evento.getComponent().getAttributes().get("ecvitlSelecionado");

			EcvItlDAO ecvItlDAO = new EcvItlDAO();
			ecvItlDAO.excluir(ecvItl);

			Messages.addGlobalInfo("Ecv/Itl removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Ecv/Itl.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		ecvItl = (EcvItl) evento.getComponent().getAttributes().get("ecvitlSelecionado");

		HistoricoDescricaoDAO histDAO = new HistoricoDescricaoDAO();
		listaHistoricoEcvItl = histDAO.listarPorECVITL(ecvItl);
	}

	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {

			String newcep = ecvItl.getCep().replace(".", "");
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

			ecvItl.setCep(cepEmpresa.getCep());

			ecvItl.setLogradouro(cepEmpresa.getEndereco());

			ecvItl.setBairro(cepEmpresa.getBairro());

			ecvItl.setEstado(cepEmpresa.getUf());

			ecvItl.setCidade(cepEmpresa.getLocalidade());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void salvarHistorico() {

		try {

			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			this.usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			HistoricoDescricaoDAO histDAO = new HistoricoDescricaoDAO();

			historicoEcvItl.setEcvItl(ecvItl);

			historicoEcvItl.setUsuarioCadastro(usuarioLogado);
			historicoEcvItl.setDataCadastro(new Date());
			historicoEcvItl.setDescricao(historicoEcvItl.getDescricao());

			histDAO.merge(historicoEcvItl);

			listaHistoricoEcvItl = histDAO.listarPorECVITL(ecvItl);

			historicoEcvItl = new HistoricoDescricao();

			Messages.addGlobalInfo("Histórico cadastrado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Histórico.");
			erro.printStackTrace();
		}

	}

	public void editarProprietario(ActionEvent evento) {

		pessoaEcvItl = (PessoaEcvItl) evento.getComponent().getAttributes().get("proprietarioSelecionado");

		ecvItl = pessoaEcvItl.getEcvItl();
	}

	public void novoProprietarioSalvar(ActionEvent evento) {

		ecvItl = (EcvItl) evento.getComponent().getAttributes().get("ecvitlSelecionado");
		System.out.println(ecvItl + " ecvItl EVENTO");
	}

	public void salvarNovoProprietario() {

		try {

			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			this.usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			PessoaEcvItlDAO pessoaDAO = new PessoaEcvItlDAO();

			pessoaEcvItl.setEcvItl(ecvItl);

			pessoaEcvItl.setTipoPessoa(pessoaEcvItl.getTipoPessoa());

			pessoaEcvItl.setUsuarioCadastro(usuarioLogado);

			pessoaDAO.merge(pessoaEcvItl);

			pessoaEcvItl = new PessoaEcvItl();

			Messages.addGlobalInfo("Proprietário cadastrado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Proprietário.");
			erro.printStackTrace();
		}

	}

	public void onRowToggleProprietarios(ToggleEvent event) {
		PessoaEcvItlDAO relacaoDAO = new PessoaEcvItlDAO();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row State " + event.getVisibility(),
				"Model:" + (EcvItl) event.getData());
		System.out.println("event.getData() " + event.getData());
		ecvItl = (EcvItl) event.getData();
		listaPessoasEcvItl = relacaoDAO.listarPorEcvItlObject(event.getData());

		System.out.println(
				listaPessoasEcvItl + " listaPessoasEcvItllistaPessoasEcvItllistaPessoasEcvItllistaPessoasEcvItl");
		FacesContext.getCurrentInstance().addMessage((String) null, msg);
	}

	public void editarHistorico(ActionEvent event) {

		historicoEcvItl = (HistoricoDescricao) event.getComponent().getAttributes().get("histSelecionado");

	}

	public void excluirHistorico(ActionEvent evento) {

		try {
			historicoEcvItl = (HistoricoDescricao) evento.getComponent().getAttributes().get("histSelecionado");

			HistoricoDescricaoDAO histDAO = new HistoricoDescricaoDAO();

			histDAO.excluir(historicoEcvItl);

			listaHistoricoEcvItl = histDAO.listarPorECVITL(ecvItl);

			historicoEcvItl = new HistoricoDescricao();

			Messages.addGlobalInfo("Historico removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Historico.");
			erro.printStackTrace();
		}
	}

	public void buscarCamposPesquisa() throws ParseException {

		try {

			listaEcvItl = new ArrayList<>();

			EcvItlDAO ecvItlDAO = new EcvItlDAO();

			System.out.println(campoDaBusca + " campoDaBusca BEAN");
			if (campoDaBusca.isEmpty()) {
				listaEcvItl = ecvItlDAO.listar("credencial");
			} else {

				//conta quantidade de números
				Integer qtdNum = 1;
				for (int i = 0; i <= campoDaBusca.length(); i++) {
					qtdNum = i;
				}
				//se for 14 = cnpj corridos
				if (qtdNum == 14) {
					
					//adiciona a mascara
					MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
					mask.setValueContainsLiteralCharacters(false);
					campoDaBusca = mask.valueToString(campoDaBusca);
				}

				if (campoDaBusca.toUpperCase().equals("ECV")) {
					campoDaBusca = "1";
				}
				if (campoDaBusca.toUpperCase().equals("ITL")) {
					campoDaBusca = "2";
				}

				listaEcvItl = ecvItlDAO.listarEmpresasConsulta(campoDaBusca);
			}
			System.out.println(listaEcvItl + " listaEcvItl");

		} catch (NullPointerException e) {
			Messages.addGlobalError("Empresa não cadastrada.");

		}
	}

	public void onRowSelectCodEcvItl(SelectEvent<EcvItl> event) {

		System.out.println(event.getObject() + "event.getObject().getCodigo()");
		ecvItl = event.getObject();

	}

	public EcvItl getEcvItl() {
		return ecvItl;
	}

	public void setEcvItl(EcvItl ecvItl) {
		this.ecvItl = ecvItl;
	}

	public List<EcvItl> getListaEcvItl() {
		return listaEcvItl;
	}

	public void setListaEcvItl(List<EcvItl> listaEcvItl) {
		this.listaEcvItl = listaEcvItl;
	}

	public String getCampoDaBusca() {
		return campoDaBusca;
	}

	public void setCampoDaBusca(String campoDaBusca) {
		this.campoDaBusca = campoDaBusca;
	}

}

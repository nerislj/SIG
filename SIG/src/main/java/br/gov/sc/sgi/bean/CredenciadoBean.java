package br.gov.sc.sgi.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

import com.google.gson.Gson;

import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.CredenciadoAlvaraDAO;
import br.gov.sc.sgi.dao.CredenciadoDAO;
import br.gov.sc.sgi.dao.CredenciadoDocAdicDAO;
import br.gov.sc.sgi.dao.CredenciadoPortariaDAO;
import br.gov.sc.sgi.dao.CredenciadoSGPEDAO;
import br.gov.sc.sgi.dao.CredencialStatusDAO;
import br.gov.sc.sgi.dao.CredencialTipoDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoAlvara;
import br.gov.sc.sgi.domain.CredenciadoDocAdic;
import br.gov.sc.sgi.domain.CredenciadoHist;
import br.gov.sc.sgi.domain.CredenciadoPortaria;
import br.gov.sc.sgi.domain.CredenciadoSGPE;
import br.gov.sc.sgi.domain.CredencialStatus;
import br.gov.sc.sgi.domain.CredencialTipo;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Usuario;
import util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CredenciadoBean implements Serializable {

	private List<Credenciado> credenciados;
	private Credenciado credenciado;
	private Credenciado credenciadoDaBusca;
	
	private CredenciadoPortaria portaria;
	private CredenciadoAlvara alvara;
	private PessoaJuridica empresa;

	private CredenciadoHist credenciadoHist;

	private List<PessoaFisica> pessoas;
	private PessoaFisica pessoa;

	private Usuario usuarioLogado;

	private List<CredenciadoSGPE> SGPEs;
	private CredenciadoSGPE SGPE;

	private List<CredenciadoDocAdic> Docs;
	private CredenciadoDocAdic Doc;

	private List<CredencialStatus> credencialStatus;
	private List<CredencialTipo> credencialTipos;

	private List<Estado> Estados;
	private Estado estado;
	private List<Cidade> Cidades;

	private Boolean exibePainelDados;

	private Boolean exibeInserirAss;

	private boolean skip;
	private boolean medicoPsicologoSim;

	private UploadedFile imagem;

	private String patchFoto;

	private String patchAss;

	private List<Credenciado> credenciadosValidadeDesc;
	private Date dataHoje;
	private Date data10Dias;

	@SuppressWarnings("unused")
	private byte[] conteudoImagem;

	public UploadedFile getImagem() {
		return imagem;
	}

	public void setImagem(UploadedFile imagem) {
		this.imagem = imagem;
	}

	public List<Credenciado> getCredenciados() {
		return credenciados;
	}

	public void setCredenciados(List<Credenciado> credenciados) {
		this.credenciados = credenciados;
	}

	public Credenciado getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}

	public List<PessoaFisica> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaFisica> pessoas) {
		this.pessoas = pessoas;
	}

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<CredenciadoSGPE> getSGPEs() {
		return SGPEs;
	}

	public void setSGPEs(List<CredenciadoSGPE> sGPEs) {
		SGPEs = sGPEs;
	}

	public CredenciadoSGPE getSGPE() {
		return SGPE;
	}

	public void setSGPE(CredenciadoSGPE sGPE) {
		SGPE = sGPE;
	}

	public List<CredenciadoDocAdic> getDocs() {
		return Docs;
	}

	public void setDocs(List<CredenciadoDocAdic> docs) {
		Docs = docs;
	}

	public CredenciadoDocAdic getDoc() {
		return Doc;
	}

	public void setDoc(CredenciadoDocAdic doc) {
		Doc = doc;
	}

	public List<CredencialStatus> getCredencialStatus() {
		return credencialStatus;
	}

	public void setCredencialStatus(List<CredencialStatus> credencialStatus) {
		this.credencialStatus = credencialStatus;
	}

	public List<CredencialTipo> getCredencialTipos() {
		return credencialTipos;
	}

	public void setCredencialTipos(List<CredencialTipo> credencialTipos) {
		this.credencialTipos = credencialTipos;
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

	public Boolean getExibePainelDados() {
		return exibePainelDados;
	}

	public void setExibePainelDados(Boolean exibePainelDados) {
		this.exibePainelDados = exibePainelDados;
	}

	public Credenciado getCredenciadoDaBusca() {
		return credenciadoDaBusca;
	}

	public void setCredenciadoDaBusca(Credenciado credenciadoDaBusca) {
		this.credenciadoDaBusca = credenciadoDaBusca;
	}

	public CredenciadoHist getCredenciadoHist() {
		return credenciadoHist;
	}

	public void setCredenciadoHist(CredenciadoHist credenciadoHist) {
		this.credenciadoHist = credenciadoHist;
	}

	public Boolean getExibeInserirAss() {
		return exibeInserirAss;
	}

	public void setExibeInserirAss(Boolean exibeInserirAss) {
		this.exibeInserirAss = exibeInserirAss;
	}

	public String getPatchFoto() {
		return patchFoto;
	}

	public void setPatchFoto(String patchFoto) {
		this.patchFoto = patchFoto;
	}

	public String getPatchAss() {
		return patchAss;
	}

	public void setPatchAss(String patchAss) {
		this.patchAss = patchAss;
	}

	public List<Credenciado> getCredenciadosValidadeDesc() {
		return credenciadosValidadeDesc;
	}

	public void setCredenciadosValidadeDesc(List<Credenciado> credenciadosValidadeDesc) {
		this.credenciadosValidadeDesc = credenciadosValidadeDesc;
	}

	public Date getDataHoje() {
		return dataHoje;
	}

	public void setDataHoje(Date dataHoje) {
		this.dataHoje = dataHoje;
	}

	public Date getData10Dias() {
		return data10Dias;
	}

	public void setData10Dias(Date data10Dias) {
		this.data10Dias = data10Dias;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	@PostConstruct
	public void listar() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");
			
			
			credenciado = new Credenciado();

			credenciadoHist = new CredenciadoHist();
			portaria = new CredenciadoPortaria();
			alvara = new CredenciadoAlvara();

			pessoa = new PessoaFisica();

			CredenciadoDAO credenciadoDAO = new CredenciadoDAO();
			credenciados = credenciadoDAO.listar();

			CredencialStatusDAO statusDAO = new CredencialStatusDAO();
			credencialStatus = statusDAO.listar("tipoStatus");

			CredencialTipoDAO tipoDAO = new CredencialTipoDAO();
			credencialTipos = tipoDAO.listar("tipocredencial");

			CredenciadoSGPEDAO sgpeDAO = new CredenciadoSGPEDAO();
			SGPEs = sgpeDAO.listar("codigo");

			SGPE = new CredenciadoSGPE();

			Doc = new CredenciadoDocAdic();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();

			estado = new Estado();

			EstadoDAO estadoDAO = new EstadoDAO();
			Estados = estadoDAO.listar("sigla");

			Cidades = new ArrayList<>();

			credenciadosValidadeDesc = credenciadoDAO.listarValidadeDesc();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Credenciado.");
			erro.printStackTrace();
		}
	}

	/*
	 * public void novo() { try { credenciado = new Credenciado();
	 * 
	 * PessoaDAO pessoaDAO = new PessoaDAO(); pessoas = pessoaDAO.listar("cpf");
	 * 
	 * CredencialStatusDAO statusDAO = new CredencialStatusDAO(); credencialStatus =
	 * statusDAO.listar("tipoStatus");
	 * 
	 * CredencialTipoDAO tipoDAO = new CredencialTipoDAO(); credencialTipos =
	 * tipoDAO.listar("tipocredencial");
	 * 
	 * 
	 * 
	 * 
	 * 
	 * } catch (RuntimeException erro) { Messages.addGlobalError(
	 * "Ocorreu um erro."); erro.printStackTrace(); } }
	 */

	public void salvar() {
		try {

			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoa.setNomeCompleto(pessoa.getNomeCompleto().toUpperCase());
			pessoaDAO.merge(pessoa);

			pessoa = PessoaDAO.carregarCpf(pessoa.getCpf());

			CredenciadoDAO credenciadoDAO = new CredenciadoDAO();

			credenciado.setPessoa(pessoa);
			credenciado.setDataCadastro(new Date());
			credenciado.setAssinatura(pessoa.getCpf() + "-ass.jpeg");
			credenciado.setFoto(pessoa.getCpf() + "-foto.jpeg");

			if (credenciado.getCredencialStatus() != null) {
				if (credenciado.getCredencialTipo() != null) {

					credenciadoDAO.merge(credenciado);

					credenciado = CredenciadoDAO.consultaporPessoa(pessoa);

					credenciadoDAO.salvarCredenciado(credenciado, pessoa, credenciadoHist, usuarioLogado);

					Messages.addGlobalInfo("Credenciado cadastrado com sucesso!");
				}

			} else {
				Messages.addGlobalInfo("Pessoa cadastrada com sucesso!");
			}

			CredenciadoBean.this.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cadastrar o Credenciado.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			credenciado = (Credenciado) evento.getComponent().getAttributes().get("credenciadoSelecionado");

			CredenciadoDAO credenciadoDAO = new CredenciadoDAO();
			credenciadoDAO.excluir(credenciado);

			CredenciadoBean.this.listar();

			Messages.addGlobalInfo("Credenciado removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Credenciado.");
			erro.printStackTrace();
		}
	}

	public void carregarImagemFoto(FileUploadEvent event) {

		try {

			CredenciadoBean.this.buscarCredenciado();

			if (event.getFile() != null) {
				this.imagem = event.getFile();
				this.conteudoImagem = imagem.getContents();

				String caminho = "C:/detran-resource/Credenciados";

				File file1 = new File(caminho, credenciado.getPessoa().getCpf() + "-foto.jpeg");

				FileOutputStream fos = new FileOutputStream(file1);
				fos.write(event.getFile().getContents());
				fos.close();

			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Credenciado.");
			erro.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregarImagemAss(FileUploadEvent event2) {

		try {
			CredenciadoBean.this.buscarCredenciado();

			if (event2.getFile() != null) {
				this.imagem = event2.getFile();
				this.conteudoImagem = imagem.getContents();

				String caminho = "C:/detran-resource/Credenciados";

				File file1 = new File(caminho, credenciado.getPessoa().getCpf() + "-ass.jpeg");

				FileOutputStream fos = new FileOutputStream(file1);
				fos.write(event2.getFile().getContents());
				CredenciadoBean.this.buscarCredenciado();
				fos.close();

			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Credenciado.");
			erro.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gerarCarteira() {

		try {

			CredenciadoDAO credenciadoDAO = new CredenciadoDAO();
			credenciadoDAO.merge(credenciado);

			CredenciadoBean.this.buscarCredenciado();

			// FOTO
			String fullPathFoto = "C:/detran-resource/Credenciados/" + credenciado.getPessoa().getCpf() + "-foto.jpeg";

			File fileFoto = new File(fullPathFoto);

			boolean existsFoto = fileFoto.exists();

			// ASSINATURA
			String fullPathAss = "C:/detran-resource/Credenciados/" + credenciado.getPessoa().getCpf() + "-ass.jpeg";

			File fileAss = new File(fullPathAss);

			boolean existsAss = fileAss.exists();

			if (existsFoto == false) {
				Messages.addGlobalError("Credenciado sem Foto.");
			}

			if (existsAss == false) {
				Messages.addGlobalError("Credenciado sem Assinatura.");
			}

			if (existsFoto == true && existsAss == true) {

				JSFUtil.redirect("./ImprimirRelatorio?rlt_nome=Carteira" + "&credenciadoId=" + credenciado.getCodigo());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException nulo) {
			throw new NullPointerException(
					"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
		}
	}
	
	public void adicionaPortaria() {
		try {

			portaria.setUsuarioCadastro(usuarioLogado);
			portaria.setDataInclusao(new Date());
			portaria.setEmpresaPF(credenciado);

			CredenciadoPortariaDAO portariaDAO = new CredenciadoPortariaDAO();
			portariaDAO.merge(portaria);

			Messages.addGlobalInfo("Portaria incluída com sucesso!");

			portaria = new CredenciadoPortaria();

			CredenciadoBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir a Portaria.");
			erro.printStackTrace();
			CredenciadoBean.this.buscar();
		}
	}
	
	public void adicionaAlvara() {
		try {

			alvara.setUsuarioCadastro(usuarioLogado);
			alvara.setDataInclusao(new Date());
			alvara.setEmpresaPF(credenciado);

			CredenciadoAlvaraDAO alvaraDAO = new CredenciadoAlvaraDAO();
			alvaraDAO.merge(alvara);

			Messages.addGlobalInfo("Alvará incluído com sucesso!");

			alvara = new CredenciadoAlvara();

			CredenciadoBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o Alvará.");
			erro.printStackTrace();
			CredenciadoBean.this.buscar();
		}
	}

	public void buscar() {

		CidadeDAO municipioDAO = new CidadeDAO();
		exibePainelDados = true;

		try {
			pessoa = PessoaDAO.carregarCpf(pessoa.getCpf());
			credenciado = CredenciadoDAO.consultaporPessoa(pessoa);
			if (pessoa.getEstadoEndereco() != null) {
				Cidades = municipioDAO.buscarPorEstado(pessoa.getEstadoEndereco().getCodigo());
			}
		} catch (NullPointerException error) {
			Messages.addGlobalWarn("Pessoa não credenciada");
			if (pessoa == null) {
				estado = new Estado();
				EstadoDAO estadoDAO = new EstadoDAO();
				Estados = estadoDAO.listar("sigla");
				Cidades = new ArrayList<>();
			} else if (pessoa.getEstadoEndereco() != null) {
				Cidades = municipioDAO.buscarPorEstado(pessoa.getEstadoEndereco().getCodigo());
			}
		}
	}

	public void buscarCredenciado() throws MalformedURLException {
		try {

			pessoa = PessoaDAO.carregarCpf(pessoa.getCpf());

			credenciadoDaBusca = CredenciadoDAO.consultaporPessoa(pessoa);
			System.out.println(credenciadoDaBusca.getCredencialTipo());
			if(credenciadoDaBusca.getCredencialTipo().getTipocredencial().contains("Médico") || credenciadoDaBusca.getCredencialTipo().getTipocredencial().contains("Psicólogo")) {
				medicoPsicologoSim = true;
				System.out.println("PASSOU" + credenciadoDaBusca.getCredencialTipo());
			}

			if (pessoa == null) {
				CredenciadoBean.this.listar();
				exibePainelDados = false;

			} else if (pessoa != null) {
				credenciado = CredenciadoDAO.consultaporPessoa(pessoa);

				String fullPathFoto = "C:/detran-resource/Credenciados/" + credenciado.getPessoa().getCpf()
						+ "-foto.jpeg";
				String PathFoto = "/images/" + credenciado.getPessoa().getCpf() + "-foto.jpeg";

				File fileFoto = new File(fullPathFoto);

				boolean existsFoto = fileFoto.exists();

				if (existsFoto == true) {
					patchFoto = PathFoto;
				} else {
					patchFoto = "/images/semfoto.jpg";
				}

				// Diretório original sem contexto servlet, usado no boolean
				// existsAss para ver se existe
				String fullPathAss = "C:/detran-resource/Credenciados/" + credenciado.getPessoa().getCpf()
						+ "-ass.jpeg";
				// Diretório com servlet /images/*
				String PathAss = "/images/" + credenciado.getPessoa().getCpf() + "-ass.jpeg";

				File fileAss = new File(fullPathAss);

				boolean existsAss = fileAss.exists();

				if (existsAss == true) {
					patchAss = PathAss;
				} else {
					patchAss = "/images/semass.jpg";
				}

				if (credenciado == null) {
					CredenciadoBean.this.listar();
					exibePainelDados = false;

				} else if (credenciado != null) {
					CidadeDAO municipioDAO = new CidadeDAO();
					Cidades = municipioDAO.buscarPorEstado(pessoa.getEstadoEndereco().getCodigo());
					exibePainelDados = true;
				}
			}
		} catch (NullPointerException e) {
			Messages.addGlobalError("Pessoa não credenciada.");

			CredenciadoBean.this.listar();
		}

	}

	public void popular() {
		try {
			if (pessoa.getEstadoEndereco() != null) {
				CidadeDAO municipioDAO = new CidadeDAO();
				Cidades = municipioDAO.buscarPorEstado(pessoa.getEstadoEndereco().getCodigo());
			} else {
				Cidades = new ArrayList<>();
			}
		} catch (NullPointerException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades");
		}
	}

	public void adicionaSGPE() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			SGPE.setUsuarioCadastro(usuarioLogado);
			SGPE.setDataInclusao(new Date());
			SGPE.setPessoa(credenciadoDaBusca);

			CredenciadoSGPEDAO sgpeDAO = new CredenciadoSGPEDAO();
			sgpeDAO.merge(SGPE);

			Messages.addGlobalInfo("SGP-e incluído com sucesso!");

			SGPE = new CredenciadoSGPE();

			CredenciadoBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o SGP-e.");
			erro.printStackTrace();
		}
	}

	public void adicionaDoc() {
		try {
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			Doc.setUsuarioCadastro(usuarioLogado);
			Doc.setDataInclusao(new Date());
			Doc.setPessoa(credenciado);

			CredenciadoDocAdicDAO docDAO = new CredenciadoDocAdicDAO();
			docDAO.merge(Doc);

			Messages.addGlobalInfo("Documento adicional incluído com sucesso!");

			Doc = new CredenciadoDocAdic();

			CredenciadoBean.this.buscar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o Documento adicional.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			credenciado = (Credenciado) evento.getComponent().getAttributes().get("credenciadoSelecionado");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();

			CredencialStatusDAO statusDAO = new CredencialStatusDAO();
			credencialStatus = statusDAO.listar("tipoStatus");

			CredencialTipoDAO tipoDAO = new CredencialTipoDAO();
			credencialTipos = tipoDAO.listar("tipocredencial");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar um Usuário.");
			erro.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public String convertTime(Date time) {
		dataHoje = new Date();

		dataHoje.setDate(dataHoje.getDate() - 1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(dataHoje);
	}

	@SuppressWarnings("deprecation")
	public String convert10Dias(Date time) {
		data10Dias = new Date();
		data10Dias.setDate(data10Dias.getDate() + 10);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(data10Dias);
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "pessoal";
		} else {
			return event.getNewStep();
		}
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

			PessoaFisica cepEmpresa = new Gson().fromJson(jsonCep.toString(), PessoaFisica.class);

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

	public CredenciadoPortaria getPortaria() {
		return portaria;
	}

	public void setPortaria(CredenciadoPortaria portaria) {
		this.portaria = portaria;
	}

	public CredenciadoAlvara getAlvara() {
		return alvara;
	}

	public void setAlvara(CredenciadoAlvara alvara) {
		this.alvara = alvara;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public boolean isMedicoPsicologoSim() {
		return medicoPsicologoSim;
	}

	public void setMedicoPsicologoSim(boolean medicoPsicologoSim) {
		this.medicoPsicologoSim = medicoPsicologoSim;
	}

	
	
	
	
}
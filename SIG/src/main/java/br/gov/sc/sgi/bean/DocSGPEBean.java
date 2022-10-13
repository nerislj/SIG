package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.DocSGPEDAO;
import br.gov.sc.sgi.domain.DocSGPE;
import br.gov.sc.sgi.domain.DocSGPEHist;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DocSGPEBean implements Serializable {

	private List<DocSGPE> sgpes;
	private DocSGPE sgpe;

	private List<DocSGPEHist> sgpehists;
	private DocSGPEHist sgpehist;

	private Usuario usuarioLogado;
	private Boolean exibePainelDados;

	public List<DocSGPE> getSgpes() {
		return sgpes;
	}

	public void setSgpes(List<DocSGPE> sgpes) {
		this.sgpes = sgpes;
	}

	public DocSGPE getSgpe() {
		return sgpe;
	}

	public void setSgpe(DocSGPE sgpe) {
		this.sgpe = sgpe;
	}

	public List<DocSGPEHist> getSgpehists() {
		return sgpehists;
	}

	public void setSgpehists(List<DocSGPEHist> sgpehists) {
		this.sgpehists = sgpehists;
	}

	public DocSGPEHist getSgpehist() {
		return sgpehist;
	}

	public void setSgpehist(DocSGPEHist sgpehist) {
		this.sgpehist = sgpehist;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Boolean getExibePainelDados() {
		return exibePainelDados;
	}

	public void setExibePainelDados(Boolean exibePainelDados) {
		this.exibePainelDados = exibePainelDados;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("docsgpe bean");
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");

			sgpe = new DocSGPE();
			sgpe.setSgpe("DETRAN");
			
			Date dataHoje = new Date();
			sgpe.setSgpeAno(dataHoje.getYear()+1900);
			
			sgpehist = new DocSGPEHist();

			exibePainelDados = false;

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar o Processo.");
			erro.printStackTrace();
		}
	}
	

	public void buscar() {
		try {

			sgpe = DocSGPEDAO.carregaSgpe(usuarioLogado, sgpe);

			exibePainelDados = true;
		} catch (NullPointerException e) {

			sgpe.setDataRecebido(new Date());
			Messages.addGlobalError("Processo Não Cadastrado.");
			exibePainelDados = true;
		}

	}
	
	public void buscarLista() {
		try {

			DocSGPEDAO DocsgpeDAO = new DocSGPEDAO();
			sgpes = DocsgpeDAO.carregaSgpeLista(sgpe, usuarioLogado);

			exibePainelDados = true;
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Processos.");
			erro.printStackTrace();
		}
	}
	

	public void salvar() {
		try {
			sgpe.setSetorAbertura(usuarioLogado.getSetor());
			sgpe.setUnidadeAbertura(usuarioLogado.getUnidade());
			sgpe.setStatus(1);
			sgpe.setUsuarioCadastro(usuarioLogado);
			sgpe.setSgpe(sgpe.getSgpe().toUpperCase());

			DocSGPEDAO DocSGPEDAO = new DocSGPEDAO();
			DocSGPEDAO.merge(sgpe);

			DocSGPEBean.this.buscar();

			sgpehist.setAcao(1);
			sgpehist.setDestino(sgpe.getUnidadeAbertura().getUnidade() + "/" + sgpe.getSetorAbertura().getSetor());
			DocSGPEDAO.salvarSgpeHist(sgpe, sgpehist, usuarioLogado);

			Messages.addGlobalInfo("SGP-e incluido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o SGP-e.");
			erro.printStackTrace();
		}
		DocSGPEBean.this.buscar();
	}

	public void arquivar() {
		try {

			sgpe.setStatus(3);

			DocSGPEDAO DocSGPEDAO = new DocSGPEDAO();
			DocSGPEDAO.merge(sgpe);

			DocSGPEBean.this.buscar();

			sgpehist.setAcao(3);
			sgpehist.setDestino("ARQUIVO");
			sgpehist.setObsArquivo(sgpehist.getObsArquivo().toUpperCase());
			DocSGPEDAO.salvarSgpeHist(sgpe, sgpehist, usuarioLogado);

			Messages.addGlobalInfo("SGP-e arquivado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar arquivar o SGP-e.");
			erro.printStackTrace();
		}
		DocSGPEBean.this.buscar();
	}

	public void reabrir() {
		try {

			sgpe.setStatus(1);

			DocSGPEDAO DocSGPEDAO = new DocSGPEDAO();
			DocSGPEDAO.merge(sgpe);

			DocSGPEBean.this.buscar();

			sgpehist.setAcao(4);
			sgpehist.setDestino(sgpe.getUnidadeAbertura().getUnidade() + "/" + sgpe.getSetorAbertura().getSetor());
			DocSGPEDAO.salvarSgpeHist(sgpe, sgpehist, usuarioLogado);

			Messages.addGlobalInfo("SGP-e reaberto com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar reabrir o SGP-e.");
			erro.printStackTrace();
		}
		DocSGPEBean.this.buscar();
	}

	public void receber() {
		try {

			sgpe.setStatus(1);

			DocSGPEDAO DocSGPEDAO = new DocSGPEDAO();
			DocSGPEDAO.merge(sgpe);

			DocSGPEBean.this.buscar();

			sgpehist.setAcao(1);
			sgpehist.setDestino(sgpe.getUnidadeAbertura().getUnidade() + "/" + sgpe.getSetorAbertura().getSetor());
			DocSGPEDAO.salvarSgpeHist(sgpe, sgpehist, usuarioLogado);

			Messages.addGlobalInfo("SGP-e recebido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar receber o SGP-e.");
			erro.printStackTrace();
		}
		DocSGPEBean.this.buscar();
	}

	public void encaminhar() {
		try {

			sgpe.setStatus(2);

			DocSGPEDAO DocSGPEDAO = new DocSGPEDAO();
			DocSGPEDAO.merge(sgpe);

			DocSGPEBean.this.buscar();

			sgpehist.setAcao(2);
			sgpehist.setDestino(sgpehist.getDestino().toUpperCase());
			DocSGPEDAO.salvarSgpeHist(sgpe, sgpehist, usuarioLogado);

			Messages.addGlobalInfo("SGP-e encaminhado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar encaminhar o SGP-e.");
			erro.printStackTrace();
		}
		DocSGPEBean.this.buscar();
	}

}

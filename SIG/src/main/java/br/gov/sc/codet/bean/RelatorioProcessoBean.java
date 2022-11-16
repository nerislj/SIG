package br.gov.sc.codet.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

import br.gov.sc.cetran.dao.ConselheiroDAO;
import br.gov.sc.cetran.dao.HistoricoProcessoDAO;
import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.codet.dao.ProcessoDAO;
import br.gov.sc.codet.dao.SetorAtualDAO;
import br.gov.sc.codet.dao.SituacaoProcessoDAO;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.codet.domain.SetorAtual;
import br.gov.sc.codet.domain.SituacaoProcesso;
import util.DateUtil;
import util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RelatorioProcessoBean implements Serializable {

	private Processo processo;
	private SetorAtual setorAtual;
	private SituacaoProcesso situacaoProcesso;

	private String setorAtualString;
	private String situacaoProcessoString;

	private List<Processo> listaProcessos;
	private List<SetorAtual> listaSetores;
	private List<SituacaoProcesso> situacoesProcessos;

	private Date dataInicial;
	private String dateIni;
	private String dateFini;
	private Date dataFinal;

	public String getSetorAtualString() {
		return setorAtualString;
	}

	public void setSetorAtualString(String setorAtualString) {
		this.setorAtualString = setorAtualString;
	}

	public String getSituacaoProcessoString() {
		return situacaoProcessoString;
	}

	public void setSituacaoProcessoString(String situacaoProcessoString) {
		this.situacaoProcessoString = situacaoProcessoString;
	}

	public SituacaoProcesso getSituacaoProcesso() {
		return situacaoProcesso;
	}

	public void setSituacaoProcesso(SituacaoProcesso situacaoProcesso) {
		this.situacaoProcesso = situacaoProcesso;
	}

	public List<SituacaoProcesso> getSituacoesProcessos() {
		return situacoesProcessos;
	}

	public void setSituacoesProcessos(List<SituacaoProcesso> situacoesProcessos) {
		this.situacoesProcessos = situacoesProcessos;
	}

	public SetorAtual getSetorAtual() {
		return setorAtual;
	}

	public void setSetorAtual(SetorAtual setorAtual) {
		this.setorAtual = setorAtual;
	}

	public List<SetorAtual> getListaSetores() {
		return listaSetores;
	}

	public void setListaSetores(List<SetorAtual> listaSetores) {
		this.listaSetores = listaSetores;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public List<Processo> getListaProcessos() {
		return listaProcessos;
	}

	public void setListaProcessos(List<Processo> listaProcessos) {
		this.listaProcessos = listaProcessos;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("relatorico processo codet");

			SetorAtualDAO setorDAO = new SetorAtualDAO();
			SituacaoProcessoDAO situacaoDAO = new SituacaoProcessoDAO();

			listaSetores = setorDAO.listar();
			situacoesProcessos = situacaoDAO.listar();

			processo = new Processo();
			// EDIÇÃO HISTORICO BEAN

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar o Processo.");
			erro.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void novo() {
		processo = new Processo();
	}

	@SuppressWarnings("static-access")
	public void blurProcessos() {

		ProcessoDAO processoDAO = new ProcessoDAO();
		SetorAtualDAO setorDAO = new SetorAtualDAO();
		SituacaoProcessoDAO situacaoDAO = new SituacaoProcessoDAO();
		
		
		

		try {
			
				
				
				
				
		
			
			
			
			
			
			System.out.println(processo.getSetorAtual());
			System.out.println(processo.getSituacao());

			if (processo.getSetorAtual() != null && processo.getSituacao() == null) {
				System.out.println("SETOR ATUAL");

				setorAtual = setorDAO.carregarSetorAtual(processo.getSetorAtual().getDescricao());

				situacaoProcesso = null;
				if(dataInicial != null && dataFinal == null) {
					Messages.addGlobalError("Favor informar a Data Final");
					
				}
				
				if(dataFinal != null && dataInicial == null) {
					Messages.addGlobalError("Favor informar a Data Inicial");
					
				}
				listaProcessos = processoDAO.listarTudo(setorAtual, situacaoProcesso,  dataInicial, dataFinal);
				if(listaProcessos.equals(null)) {
					Messages.addGlobalError("Não existe dados com os campos fornecidos!");
				}
				System.out.println(listaProcessos);
			}

			if (processo.getSetorAtual() == null && processo.getSituacao() != null) {
				System.out.println("AQUI SITUAÇÃO");

				situacaoProcesso = situacaoDAO.carregarSituacao(processo.getSituacao().getDescricao());

				setorAtual = null;
				if(dataInicial != null && dataFinal == null) {
					Messages.addGlobalError("Favor informar a Data Final");
					
				}
				
				if(dataFinal != null && dataInicial == null) {
					Messages.addGlobalError("Favor informar a Data Inicial");
					
				}
				listaProcessos = processoDAO.listarTudo(setorAtual, situacaoProcesso,  dataInicial, dataFinal);
				if(listaProcessos.equals(null)) {
					Messages.addGlobalError("Não existe dados com os campos fornecidos!");
				}
				System.out.println(listaProcessos);
			}

			if (processo.getSetorAtual() != null && processo.getSituacao() != null) {
				System.out.println("OS DOIS");
				setorAtual = setorDAO.carregarSetorAtual(processo.getSetorAtual().getDescricao());
				situacaoProcesso = situacaoDAO.carregarSituacao(processo.getSituacao().getDescricao());
				if(dataInicial != null && dataFinal == null) {
					Messages.addGlobalError("Favor informar a Data Final");
					
				}
				
				if(dataFinal != null && dataInicial == null) {
					Messages.addGlobalError("Favor informar a Data Inicial");
					
				}
				listaProcessos = processoDAO.listarTudo(setorAtual, situacaoProcesso,  dataInicial, dataFinal);
				if(listaProcessos.equals(null)) {
					Messages.addGlobalError("Não existe dados com os campos fornecidos!");
				}
				System.out.println(listaProcessos);
			}
			
			if (processo.getSetorAtual() == null && processo.getSituacao() == null) {
				System.out.println("OS DOIS NULL");
				setorAtual = null;
				situacaoProcesso = null;
				if(dataInicial != null && dataFinal == null) {
					Messages.addGlobalError("Favor informar a Data Final");
					
				}
				
				if(dataFinal != null && dataInicial == null) {
					Messages.addGlobalError("Favor informar a Data Inicial");
					
				}
				listaProcessos = processoDAO.listarTudo(setorAtual, situacaoProcesso,  dataInicial, dataFinal);
				if(listaProcessos.equals(null)) {
					Messages.addGlobalError("Não existe dados com os campos fornecidos!");
				}
				System.out.println(listaProcessos);
			}

			
				
				
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(pdf, baos);

		pdf.open();
		pdf.setPageSize(PageSize.A4.rotate());

		String logo = "http://localhost/Sistema_Integrado_de_Gest%C3%A3o/resources/images/detran-logo.png";

		System.out.println(logo);

		pdf.add(Image.getInstance(logo));
	}

	public void gerarRelatorioSetorSituacao() {

		try {

			if (processo.getSetorAtual() != null && processo.getSituacao() == null) {
				System.out.println("SETOR ATUAL" + processo.getSetorAtual().getCodigo().toString());

				setorAtualString = processo.getSetorAtual().getCodigo().toString();

				situacaoProcessoString = null;
			}
			if (processo.getSetorAtual() == null && processo.getSituacao() != null) {
				System.out.println("AQUI SITUAÇÃO" + processo.getSituacao().getCodigo().toString());

				situacaoProcessoString = processo.getSituacao().getCodigo().toString();

				setorAtualString = null;
			}

			if (processo.getSetorAtual() != null && processo.getSituacao() != null) {
				System.out.println("OS DOIS" + processo.getSetorAtual().getCodigo().toString()
						+ processo.getSituacao().getCodigo().toString());
				setorAtualString = processo.getSetorAtual().getCodigo().toString();
				situacaoProcessoString = processo.getSituacao().getCodigo().toString();

			}

			if (processo.getSetorAtual() == null && processo.getSituacao() == null) {

				setorAtualString = null;
				situacaoProcessoString = null;
			}

			System.out.println("-- RELATORIO inicio --");
			System.out.println(setorAtualString);
			System.out.println(situacaoProcessoString);
			System.out.println("-- RELATORIO fim --");

			JSFUtil.redirect("../ImprimirRelatorio?rlt_nome=setorsituacao" + "&setorId=" + setorAtualString
					+ "&situacaoId=" + situacaoProcessoString);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException nulo) {
			throw new NullPointerException(
					"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
		}

	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getDateIni() {
		return dateIni;
	}

	public void setDateIni(String dateIni) {
		this.dateIni = dateIni;
	}

	public String getDateFini() {
		return dateFini;
	}

	public void setDateFini(String dateFini) {
		this.dateFini = dateFini;
	}

}

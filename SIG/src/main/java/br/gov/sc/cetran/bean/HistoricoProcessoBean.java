package br.gov.sc.cetran.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.cetran.dao.ConselheiroDAO;
import br.gov.sc.cetran.dao.HistoricoProcessoDAO;
import br.gov.sc.cetran.dao.RepresentacaoDAO;
import br.gov.sc.cetran.dao.RequerenteDAO;
import br.gov.sc.cetran.domain.Conselheiro;
import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.cetran.domain.Representacao;
import util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HistoricoProcessoBean implements Serializable {

	private HistoricoProcesso historicoProcesso;
	private Conselheiro conselheiro;
	
	private List<HistoricoProcesso> listaHistoricoProcessos;
	private List<Conselheiro> listaConselheiros;
	

	@PostConstruct
	public void listar() {
		try {
			
			ConselheiroDAO conselheiroDAO = new ConselheiroDAO();
			listaConselheiros = conselheiroDAO.listarTudo();
			
			historicoProcesso = new HistoricoProcesso();
			
			//EDIÇÃO HISTORICO BEAN
			
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a HistoricoProcesso.");
			erro.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void novo() {
		historicoProcesso = new HistoricoProcesso();
	}
	
	@SuppressWarnings("static-access")
	public void blurConselheiro() {
		
		ConselheiroDAO conselheiroDAO = new ConselheiroDAO();
		conselheiro = conselheiroDAO.carregarConselheiro(historicoProcesso.getConselheiro().getNome());
		
		
		System.out.println("VALOR conselheiro " +conselheiro);
		
		HistoricoProcessoDAO HistoricoProcessoDAO = new HistoricoProcessoDAO();
		listaHistoricoProcessos = HistoricoProcessoDAO.listarTudo(conselheiro);
		
		System.out.println(listaHistoricoProcessos);
		System.out.println(conselheiro);
		System.out.println(historicoProcesso.getConselheiro().getNome());
	}

	public void salvar() {
		try {
			
			
			HistoricoProcessoDAO HistoricoProcessoDAO = new HistoricoProcessoDAO();
			HistoricoProcessoDAO.merge(historicoProcesso);
			

			Messages.addGlobalInfo("HistoricoProcesso cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o HistoricoProcesso.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			
			

			historicoProcesso = (HistoricoProcesso) evento.getComponent().getAttributes().get("histSelecionado");		

			HistoricoProcessoDAO histDAO = new HistoricoProcessoDAO();
			histDAO.excluir(historicoProcesso);

			
			
			listaHistoricoProcessos = new ArrayList<>();
			HistoricoProcessoBean.this.listar();
		

			Messages.addGlobalInfo("HistoricoProcesso removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o HistoricoProcesso.");
			erro.printStackTrace();
		}
	}
	
public void gerarRelatorio() {
		
		try {
			
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			 String dataFormat = df.format(historicoProcesso.getDataDistribuicao());
			
		
		JSFUtil.redirect("../ImprimirRelatorio?rlt_nome=RelatorioDistribuicao" + "&data=" + dataFormat + "&id=" + historicoProcesso.getConselheiro().getCodigo());
		System.out.println("IMPRIMIR RELATÓRIO DADOS" + historicoProcesso);
		
	} catch (IOException e) {
		e.printStackTrace();
	} catch (NullPointerException nulo) {
		throw new NullPointerException(
				"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
	}
		
	}

	public void editar(ActionEvent evento) {
		historicoProcesso = (HistoricoProcesso) evento.getComponent().getAttributes().get("historicoProcessoSelecionado");		
	}

	public HistoricoProcesso getHistoricoProcesso() {
		return historicoProcesso;
	}

	public void setHistoricoProcesso(HistoricoProcesso historicoProcesso) {
		this.historicoProcesso = historicoProcesso;
	}

	public List<HistoricoProcesso> getListaHistoricoProcessos() {
		return listaHistoricoProcessos;
	}

	public void setListaHistoricoProcessos(List<HistoricoProcesso> listaHistoricoProcessos) {
		this.listaHistoricoProcessos = listaHistoricoProcessos;
	}

	public Conselheiro getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(Conselheiro conselheiro) {
		this.conselheiro = conselheiro;
	}

	public List<Conselheiro> getListaConselheiros() {
		return listaConselheiros;
	}

	public void setListaConselheiros(List<Conselheiro> listaConselheiros) {
		this.listaConselheiros = listaConselheiros;
	}


	





	
	

	
}



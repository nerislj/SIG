package br.gov.sc.cetran.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.cetran.dao.ConselheiroDAO;
import br.gov.sc.cetran.dao.HistoricoProcessoDAO;
import br.gov.sc.cetran.domain.Conselheiro;
import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.sgi.domain.OficioAno;
import util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HistoricoProcessoBean implements Serializable {

	private HistoricoProcesso historicoProcesso;
	private Conselheiro conselheiro;
	
	private OficioAno Ano;

	private List<HistoricoProcesso> listaHistoricoProcessos;
	private List<HistoricoProcesso> listaHistoricoProcessosAVencer;
	private List<Conselheiro> listaConselheiros;

	@PostConstruct
	public void listar() {
		try {

			ConselheiroDAO conselheiroDAO = new ConselheiroDAO();
			listaConselheiros = conselheiroDAO.listarTudo();

			HistoricoProcessoDAO HistoricoProcessoDAO = new HistoricoProcessoDAO();
			listaHistoricoProcessos = HistoricoProcessoDAO.listar();
			
			int anoHoje = new Date().getYear() + 1900;
			listaHistoricoProcessosAVencer = HistoricoProcessoDAO.listarPorData(anoHoje);

			historicoProcesso = new HistoricoProcesso();

			// EDIÇÃO HISTORICO BEAN

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a HistoricoProcesso.");
			erro.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void consultaPorAno() {
		try {
			HistoricoProcessoDAO HistoricoProcessoDAO = new HistoricoProcessoDAO();

			listaHistoricoProcessosAVencer = HistoricoProcessoDAO.listarPorData(Ano.getOficioAno());

		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Ofícios.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		historicoProcesso = new HistoricoProcesso();
	}

	@SuppressWarnings("static-access")
	public void blurConselheiro() {

		ConselheiroDAO conselheiroDAO = new ConselheiroDAO();
		conselheiro = conselheiroDAO.carregarConselheiro(historicoProcesso.getConselheiro().getNome());

		System.out.println("VALOR conselheiro " + conselheiro);

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

			JSFUtil.redirect("../ImprimirRelatorio?rlt_nome=RelatorioDistribuicao" + "&data=" + dataFormat + "&id="
					+ historicoProcesso.getConselheiro().getCodigo());
			System.out.println("IMPRIMIR RELATÓRIO DADOS" + historicoProcesso);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException nulo) {
			throw new NullPointerException(
					"Erro ao imprimir o relatório. Valores das variáveis inválidos " + nulo.getMessage());
		}

	}

	public void editar(ActionEvent evento) {
		historicoProcesso = (HistoricoProcesso) evento.getComponent().getAttributes()
				.get("historicoProcessoSelecionado");
	}

	private Date dataHoje;
	

	
	public Date getDataHoje() {
		return dataHoje;
	}

	public void setDataHoje(Date dataHoje) {
		this.dataHoje = dataHoje;
	}


	

	@SuppressWarnings("deprecation")
	public Long convertTime()  {
		
		
		
		System.out.println("System.currentTimeMillis();" + System.currentTimeMillis());
		return System.currentTimeMillis();
	}

	
	
	public OficioAno getAno() {
		return Ano;
	}

	public void setAno(OficioAno ano) {
		Ano = ano;
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

	public List<HistoricoProcesso> getListaHistoricoProcessosAVencer() {
		return listaHistoricoProcessosAVencer;
	}

	public void setListaHistoricoProcessosAVencer(List<HistoricoProcesso> listaHistoricoProcessosAVencer) {
		this.listaHistoricoProcessosAVencer = listaHistoricoProcessosAVencer;
	}

}

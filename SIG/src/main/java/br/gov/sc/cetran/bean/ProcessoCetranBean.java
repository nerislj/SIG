package br.gov.sc.cetran.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import br.gov.sc.cetran.dao.ConselheiroDAO;
import br.gov.sc.cetran.dao.ProcessoAnoDAO;
import br.gov.sc.cetran.dao.ProcessoCetranDAO;
import br.gov.sc.cetran.dao.SituacaoDAO;
import br.gov.sc.cetran.domain.Conselheiro;
import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.cetran.domain.ProcessoAno;
import br.gov.sc.cetran.domain.ProcessoCetran;
import br.gov.sc.cetran.domain.Situacao;
import br.gov.sc.geapo.domain.MaterialSaidaRelacao;
import br.gov.sc.sgi.dao.OficioAnoDAO;
import br.gov.sc.sgi.dao.OficioDAO;
import br.gov.sc.sgi.domain.OficioAno;
import br.gov.sc.sgi.domain.Usuario;
import util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProcessoCetranBean implements Serializable {

	private ProcessoCetran processoCetran;
	
	private HistoricoProcesso historicoProcesso;
	private List<HistoricoProcesso> listaHistoricoProcesso;
	
	private List<ProcessoCetran> listaProcessosCetran;
	
	private List<Conselheiro> listaConselheiros;
	private List<Situacao> listaSituacoes;
	
	private ProcessoAno ano;
	private List<ProcessoAno> anos;
	
	
	private Usuario usuarioLogado;
	
	private Integer progresso;
	long totalTime;
	private String mensagem;
	
   

	@PostConstruct
	public void listar() {
		try {
			ProcessoAnoDAO processoanoDAO = new ProcessoAnoDAO();

			anos = processoanoDAO.loadAnos();
			
			int anoHoje = new Date().getYear() + 1900;
			
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			usuarioLogado = (Usuario) sessao.getAttribute("usuario");
			
			
			ConselheiroDAO conselheiroDAO = new ConselheiroDAO();
			listaConselheiros = conselheiroDAO.listarTudo();
			
			SituacaoDAO situacaoDAO = new SituacaoDAO();
			listaSituacoes = situacaoDAO.listarTudo();
			
			ProcessoCetranDAO ProcessoCetranDAO = new ProcessoCetranDAO();
		

			listaProcessosCetran = ProcessoCetranDAO.listar();
		
			
			
			//listaProcessosCetran = ProcessoCetranDAO.listarParaVincular(anoHoje);
			
			processoCetran = new ProcessoCetran();
			historicoProcesso = new HistoricoProcesso();
			
			listaHistoricoProcesso = new ArrayList<>();
			
		
			
			
			System.out.println("TAMANHO DA listaSaida "+ listaProcessosCetran.size() + usuarioLogado);
			
		
		
			
				
				
				
				
			
			
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a ProcessoCetran.");
			erro.printStackTrace();
		}
	}
	
	

	



	public void novo() {
		processoCetran = new ProcessoCetran();
		listaHistoricoProcesso = new ArrayList<HistoricoProcesso>();
		
	}

	public void salvar() {
		try {
			
			
			ProcessoCetranDAO ProcessoCetranDAO = new ProcessoCetranDAO();
			ProcessoCetranDAO.merge(processoCetran);
			

			Messages.addGlobalInfo("ProcessoCetran cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o ProcessoCetran.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			
			

			processoCetran = new ProcessoCetran();
		

			Messages.addGlobalInfo("ProcessoCetran removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o ProcessoCetran.");
			erro.printStackTrace();
		}
	}
	
	public void adicionar(ActionEvent evento) {
		ProcessoCetran processo = (ProcessoCetran) evento.getComponent().getAttributes().get("processoSelecionado");
		
		System.out.println("Processo selecionado " + processo.getRecorrido());

		int achou = -1;
		for (int posicao = 0; posicao < listaHistoricoProcesso.size(); posicao++) {
			if (listaHistoricoProcesso.get(posicao).getProcessoCetran().equals(processo)) {
				achou = posicao;
				
				
				//if(listaSaida.get(posicao).getMaterialSaida().getMaterial().getMaterial())
				listaHistoricoProcesso.get(posicao).getProcessoCetran().getNumero();
			}
		}
		
		if (achou < 0) {

			HistoricoProcesso historicoSaida = new HistoricoProcesso();
			historicoSaida.setProcessoCetran(processo);

			listaHistoricoProcesso.add(historicoSaida);

			System.out.println("processo selecionado " + processo.getNumero());
			

		}
		
		Collections.sort(listaHistoricoProcesso, new Comparator<HistoricoProcesso>() {
	        @Override
	        public int compare(HistoricoProcesso fruit2, HistoricoProcesso fruit1)
	        {

	            return  fruit1.getProcessoCetran().getNumero().compareTo(fruit2.getProcessoCetran().getNumero());
	        }

			
	    });
		
		
	}
	
	public void remover(ActionEvent evento) {
		HistoricoProcesso historicoSaida = (HistoricoProcesso) evento.getComponent().getAttributes()
				.get("processoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < listaHistoricoProcesso.size(); posicao++) {
			if (listaHistoricoProcesso.get(posicao).getProcessoCetran().equals(historicoSaida.getProcessoCetran())) {
				achou = posicao;
			}
		}
		if (achou > -1) {
			listaHistoricoProcesso.remove(achou);
			
			System.out.println(listaHistoricoProcesso.size());
		}
	}
	
	public void finalizar() {
		try {
			
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar a Saida");
			erro.printStackTrace();
		}
	}
	
	public void vincularConselheiro() {
		
		
		try {
			if (listaHistoricoProcesso.isEmpty()) {
				Messages.addGlobalError("Selecione pelo menos um processo para vincular ao conselheiro.");
			} else {
				

				ProcessoCetranDAO processoCetranDAO = new ProcessoCetranDAO();
				
				
				processoCetranDAO.salvarHistorico(historicoProcesso, listaHistoricoProcesso, usuarioLogado);

				ProcessoCetranBean.this.listar();

				Messages.addGlobalInfo("Saida realizada com sucesso");
			}

			
			this.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a Saida");
			erro.printStackTrace();
		}
	}
	
	



	public void editar(ActionEvent evento) {
		processoCetran = (ProcessoCetran) evento.getComponent().getAttributes().get("processoCetranSelecionado");		
	}

	public ProcessoCetran getProcessoCetran() {
		return processoCetran;
	}

	public void setProcessoCetran(ProcessoCetran processoCetran) {
		this.processoCetran = processoCetran;
	}

	public List<ProcessoCetran> getListaProcessosCetran() {
		return listaProcessosCetran;
	}

	public void setListaProcessosCetran(List<ProcessoCetran> listaProcessosCetran) {
		this.listaProcessosCetran = listaProcessosCetran;
	}

	public List<HistoricoProcesso> getListaHistoricoProcesso() {
		return listaHistoricoProcesso;
	}

	public void setListaHistoricoProcesso(List<HistoricoProcesso> listaHistoricoProcesso) {
		this.listaHistoricoProcesso = listaHistoricoProcesso;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public HistoricoProcesso getHistoricoProcesso() {
		return historicoProcesso;
	}

	public void setHistoricoProcesso(HistoricoProcesso historicoProcesso) {
		this.historicoProcesso = historicoProcesso;
	}

	public List<Conselheiro> getListaConselheiros() {
		return listaConselheiros;
	}

	public void setListaConselheiros(List<Conselheiro> listaConselheiros) {
		this.listaConselheiros = listaConselheiros;
	}

	

	public List<Situacao> getListaSituacoes() {
		return listaSituacoes;
	}

	public void setListaSituacoes(List<Situacao> listaSituacoes) {
		this.listaSituacoes = listaSituacoes;
	}

	public ProcessoAno getAno() {
		return ano;
	}

	public void setAno(ProcessoAno ano) {
		this.ano = ano;
	}

	public List<ProcessoAno> getAnos() {
		return anos;
	}

	public void setAnos(List<ProcessoAno> anos) {
		this.anos = anos;
	}

	public Integer getProgresso() {
		return progresso;
	}

	public void setProgresso(Integer progresso) {
		this.progresso = progresso;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	
	





	
	

	
}



package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaFisica;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable {

	private PessoaFisica pessoa;
	private List<PessoaFisica> pessoas;
	private List<PessoaFisica> pessoasPopular;
	
	private Boolean exibePainelDados;

	private Estado estado;
	private List<Estado> Estados;

	private List<Cidade> Cidades;

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public List<PessoaFisica> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaFisica> pessoas) {
		this.pessoas = pessoas;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return Estados;
	}

	public void setEstados(List<Estado> uFs) {
		Estados = uFs;
	}

	public List<Cidade> getCidades() {
		return Cidades;
	}

	public void setCidades(List<Cidade> municipios) {
		Cidades = municipios;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("pessoabean");
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Pessoas.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			pessoa = new PessoaFisica();

			estado = new Estado();

			EstadoDAO estadoDAO = new EstadoDAO();
			Estados = estadoDAO.listar("sigla");

			Cidades = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar as Pessoas.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoa.setNomeCompleto(pessoa.getNomeCompleto().toUpperCase());
			pessoaDAO.merge(pessoa);
			
			pessoas = pessoaDAO.listar("nomeCompleto");

			pessoa = new PessoaFisica();
			
			estado = new Estado();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			Estados = estadoDAO.listar("nome");
			
			Cidades = new ArrayList<>();

			Messages.addGlobalInfo("Pessoa cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Pessoa.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			pessoa = (PessoaFisica) evento.getComponent().getAttributes().get("pessoaSelecionada");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.excluir(pessoa);

			pessoa = new PessoaFisica();
			pessoas = pessoaDAO.listar();

			Messages.addGlobalInfo("Pessoa removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Pessoa.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			pessoa = (PessoaFisica) evento.getComponent().getAttributes().get("pessoaSelecionada");

			EstadoDAO estadoDAO = new EstadoDAO();
			Estados = estadoDAO.listar();

			CidadeDAO municipioDAO = new CidadeDAO();
			Cidades = municipioDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar Pessoas");
			erro.printStackTrace();
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
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades");
			erro.printStackTrace();
		}
	}
	
	//Filtra por CPF e popula a Cidade caso Estado != null
	public void buscar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoa = pessoaDAO.carregarCpf(pessoa.getCpf());
			
			if (pessoa.getEstadoEndereco() != null) {
				CidadeDAO municipioDAO = new CidadeDAO();
				Cidades = municipioDAO.buscarPorEstado(pessoa.getEstadoEndereco().getCodigo());
			} else {
				Cidades = new ArrayList<>();
			}

			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o produto");
			erro.printStackTrace();
		}
	}

	public List<PessoaFisica> getPessoasPopular() {
		return pessoasPopular;
	}

	public void setPessoasPopular(List<PessoaFisica> pessoasPopular) {
		this.pessoasPopular = pessoasPopular;
	}

	public Boolean getExibePainelDados() {
		return exibePainelDados;
	}

	public void setExibePainelDados(Boolean exibePainelDados) {
		this.exibePainelDados = exibePainelDados;
	}

}



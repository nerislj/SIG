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
import br.gov.sc.sgi.dao.PessoaJuridicaDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.domain.PessoaJuridica;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaJuridicaBean implements Serializable {

	private PessoaJuridica empresa;
	private List<PessoaJuridica> empresas;

	private Estado estado;
	private List<Estado> Estados;

	private List<Cidade> Cidades;

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public List<PessoaJuridica> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<PessoaJuridica> empresas) {
		this.empresas = empresas;
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

	public void setEstados(List<Estado> estados) {
		Estados = estados;
	}

	public List<Cidade> getCidades() {
		return Cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		Cidades = cidades;
	}

	@PostConstruct
	public void listar() {
		try {
			System.out.println("pessoajuridica bean");
			PessoaJuridicaDAO empresaDAO = new PessoaJuridicaDAO();
			empresas = empresaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Pessoas.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			
			PessoaJuridicaBean.this.listar();
			
			empresa = new PessoaJuridica();

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
			PessoaJuridicaDAO empresaDAO = new PessoaJuridicaDAO();
			empresaDAO.merge(empresa);

			empresas = empresaDAO.listar();

			empresa = new PessoaJuridica();

			estado = new Estado();

			EstadoDAO estadoDAO = new EstadoDAO();
			Estados = estadoDAO.listar("nome");

			Cidades = new ArrayList<>();

			Messages.addGlobalInfo("Pessoa cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Empresa.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			empresa = (PessoaJuridica) evento.getComponent().getAttributes().get("empresaSelecionada");

			PessoaJuridicaDAO empresaDAO = new PessoaJuridicaDAO();
			empresaDAO.excluir(empresa);

			empresa = new PessoaJuridica();
			empresas = empresaDAO.listar();

			Messages.addGlobalInfo("Pessoa removida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Empresa.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			empresa = (PessoaJuridica) evento.getComponent().getAttributes().get("empresaSelecionada");

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
			if (empresa.getEstadoEndereco() != null) {
				CidadeDAO municipioDAO = new CidadeDAO();
				Cidades = municipioDAO.buscarPorEstado(empresa.getEstadoEndereco().getCodigo());
			} else {
				Cidades = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades");
			erro.printStackTrace();
		}
	}

}



package br.gov.sc.sgi.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.FrotaCondutorDAO;
import br.gov.sc.sgi.dao.PessoaDAO;
import br.gov.sc.sgi.domain.FrotaCondutor;
import br.gov.sc.sgi.domain.FrotaUnidade;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FrotaCondutorBean implements Serializable {

	private Usuario usuario;
	private List<PessoaFisica> pessoas;
	private List<FrotaUnidade> FrotaUnidade;
	private FrotaUnidade frotaunidade;
	private List<FrotaCondutor> FrotaCondutores;
	private FrotaCondutor frotacondutor;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<PessoaFisica> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaFisica> pessoas) {
		this.pessoas = pessoas;
	}

	public List<FrotaUnidade> getFrotaUnidade() {
		return FrotaUnidade;
	}

	public void setFrotaUnidade(List<FrotaUnidade> frotaUnidade) {
		FrotaUnidade = frotaUnidade;
	}

	public FrotaUnidade getFrotaunidade() {
		return frotaunidade;
	}

	public void setFrotaunidade(FrotaUnidade frotaunidade) {
		this.frotaunidade = frotaunidade;
	}

	public List<FrotaCondutor> getFrotaCondutores() {
		return FrotaCondutores;
	}

	public void setFrotaCondutores(List<FrotaCondutor> frotaCondutores) {
		FrotaCondutores = frotaCondutores;
	}

	public FrotaCondutor getFrotacondutor() {
		return frotacondutor;
	}

	public void setFrotacondutor(FrotaCondutor frotacondutor) {
		this.frotacondutor = frotacondutor;
	}

	@PostConstruct
	public void listar() {
		try {
			FrotaCondutorDAO frotaCondutorDAO = new FrotaCondutorDAO();
			FrotaCondutores = frotaCondutorDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Usuários.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			frotacondutor = new FrotaCondutor();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("cpf");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			FrotaCondutorDAO frotaCondutorDAO = new FrotaCondutorDAO();

			frotaCondutorDAO.merge(frotacondutor);

			FrotaCondutorBean.this.listar();

			Messages.addGlobalInfo("Condutor cadastrado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cadastrar o Condutor");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			frotacondutor = (FrotaCondutor) evento.getComponent().getAttributes().get("CondutorSelecionado");

			FrotaCondutorDAO frotaCondutorDAO = new FrotaCondutorDAO();
			frotaCondutorDAO.excluir(frotacondutor);

			FrotaCondutorBean.this.listar();

			Messages.addGlobalInfo("Usuário removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Usuário.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			frotacondutor = (FrotaCondutor) evento.getComponent().getAttributes().get("CondutorSelecionado");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar um Usuário.");
			erro.printStackTrace();
		}
	}

}

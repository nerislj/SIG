package br.gov.sc.funcionariosuf.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionariosuf_servidores")
public class Servidores extends GenericDomain {

	@Column(length = 50, nullable = false, unique = true)
	private String matricula;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Unidade unidade;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private CargoServidores cargo;

	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private PessoaFisica pessoa;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Setor setor;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;

	@OneToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private FuncaoServidores funcao;
	
	private String tipo = "Servidor";


	  @OneToOne
	   @JoinColumn(
	      nullable = false
	   )
	  private UnidadeFunc unidadeFunc;
	

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public UnidadeFunc getUnidadeFunc() {
		return unidadeFunc;
	}

	public void setUnidadeFunc(UnidadeFunc unidadeFunc) {
		this.unidadeFunc = unidadeFunc;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public CargoServidores getCargo() {
		return cargo;
	}

	public void setCargo(CargoServidores cargo) {
		this.cargo = cargo;
	}

	public String getTipo() {
		return "Servidor";
	}

	public FuncaoServidores getFuncao() {
		return funcao;
	}

	public void setFuncao(FuncaoServidores funcao) {
		this.funcao = funcao;
	}



	

	

}
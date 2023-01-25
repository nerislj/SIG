package br.gov.sc.ecvitl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.sc.sgi.domain.Usuario;




@SuppressWarnings("serial")
@Entity
@Table(name = "ecvitl_pessoaecvitl")
public class PessoaEcvItl extends GenericDomainEcvItl {
	
	
	
	
	@Column(length = 10)
	private String tipoPessoa;
	
	@Column(length = 255)
	private String nome;
	
	@Column(length = 100)
	private String cpf;
	
	@Column(length = 100)
	private String email;
	
	@Column(length = 100)
	private String telefone;

	@Column(length = 100)
	private String telefone2;
	
	@Column(length = 10)
	private String ativo;
	
	@ManyToOne
	@JoinColumn
	private Usuario usuarioCadastro;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private EcvItl ecvItl;

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public EcvItl getEcvItl() {
		return ecvItl;
	}

	public void setEcvItl(EcvItl ecvItl) {
		this.ecvItl = ecvItl;
	}




	


	

}

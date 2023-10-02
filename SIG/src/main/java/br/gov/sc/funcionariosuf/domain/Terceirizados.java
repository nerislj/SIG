
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

import br.gov.sc.contrato.domain.CargoTerceirizado;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionariosuf_terceirizados")
public class Terceirizados extends GenericDomain {

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

	@OneToOne
	@JoinColumn
	private CargoTerceirizado cargoTerceirizado;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "unidade_codigo") private UnidadeFunc unidadefunc;
	 */

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

	

	public CargoTerceirizado getCargoTerceirizado() {
		return cargoTerceirizado;
	}

	public void setCargoTerceirizado(CargoTerceirizado cargoTerceirizado) {
		this.cargoTerceirizado = cargoTerceirizado;
	}

}
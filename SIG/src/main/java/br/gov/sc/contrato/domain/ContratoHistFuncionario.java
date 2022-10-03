/* Decompiler 8ms, total 157ms, lines 84 */
package br.gov.sc.contrato.domain;

import br.gov.sc.sgi.domain.Usuario;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "controle_contratohistfuncionario")
public class ContratoHistFuncionario extends GenericDomainContrato {
	@OneToOne
	@JoinColumn(nullable = false)
	private FuncionarioTerceirizado funcionarioTerceirizado;
	@OneToOne
	@JoinColumn(nullable = false)
	private ContratoTerceirizado contratoTerceirizado;
	@Column(length = 255, nullable = false)
	private String status;
	@Column(length = 255, nullable = false)
	private String observacao;
	@OneToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;
	
	

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public FuncionarioTerceirizado getFuncionarioTerceirizado() {
		return this.funcionarioTerceirizado;
	}

	public void setFuncionarioTerceirizado(FuncionarioTerceirizado funcionarioTerceirizado) {
		this.funcionarioTerceirizado = funcionarioTerceirizado;
	}

	public ContratoTerceirizado getContratoTerceirizado() {
		return this.contratoTerceirizado;
	}

	public void setContratoTerceirizado(ContratoTerceirizado contratoTerceirizado) {
		this.contratoTerceirizado = contratoTerceirizado;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Usuario getUsuarioCadastro() {
		return this.usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}
}
/* Decompiler 4ms, total 150ms, lines 124 */
package br.gov.sc.contrato.domain;

import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@SuppressWarnings("serial")
@Entity
@Table(name = "controle_funcionarioterceirizado")
public class FuncionarioTerceirizado extends GenericDomainContrato {
	@OneToOne
	@JoinColumn(unique = true)
	private EventoTerceirizado tipoEvento;
	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private PessoaFisica pessoa;
	@ManyToOne
	@JoinColumn
	private Unidade unidade;
	@ManyToOne
	@JoinColumn(nullable = true)
	private Setor setor;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataCadastro;
	@OneToOne
	@JoinColumn(nullable = false)
	private CargoTerceirizado cargoTerceirizado;
	@OneToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionarioTerceirizado")
	@Fetch(FetchMode.SUBSELECT)
	private List<ContratoRelacao> contratoRelacao;
	@OneToOne
	@JoinColumn
	private NivelTerceirizado nivelTerceirizado;
	
	private String tipo = "Terceirizado";

	public EventoTerceirizado getTipoEvento() {
		return this.tipoEvento;
	}

	public void setTipoEvento(EventoTerceirizado tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public PessoaFisica getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public Unidade getUnidade() {
		return this.unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public CargoTerceirizado getCargoTerceirizado() {
		return this.cargoTerceirizado;
	}

	public void setCargoTerceirizado(CargoTerceirizado cargoTerceirizado) {
		this.cargoTerceirizado = cargoTerceirizado;
	}

	public Usuario getUsuarioCadastro() {
		return this.usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	

	public List<ContratoRelacao> getContratoRelacao() {
		return contratoRelacao;
	}

	public void setContratoRelacao(List<ContratoRelacao> contratoRelacao) {
		this.contratoRelacao = contratoRelacao;
	}

	public NivelTerceirizado getNivelTerceirizado() {
		return nivelTerceirizado;
	}

	public void setNivelTerceirizado(NivelTerceirizado nivelTerceirizado) {
		this.nivelTerceirizado = nivelTerceirizado;
	}
	
	public String getTipo() {
		return "Terceirizado";
	}
	
	
}
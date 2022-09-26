/* Decompiler 17ms, total 168ms, lines 41 */
package br.gov.sc.contrato.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "controle_cargoterceirizado")
public class CargoTerceirizado extends GenericDomainContrato {
	
	@Column(length = 255)
	private String descricao;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataCadastro;

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
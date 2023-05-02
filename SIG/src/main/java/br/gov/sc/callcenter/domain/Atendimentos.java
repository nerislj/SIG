package br.gov.sc.callcenter.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity
@Table(name = "callcenter_atendimentos")
public class Atendimentos extends GenericDomain {

	@Column(length = 255)
	private String nomeCompleto;

	@Column(length = 255)
	private String cpf;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataInicial;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataFinal;

	@OneToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioCadastro;
	

	

}
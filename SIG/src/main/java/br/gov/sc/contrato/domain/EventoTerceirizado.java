/* Decompiler 8ms, total 152ms, lines 136 */
package br.gov.sc.contrato.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.sc.sgi.domain.Usuario;

@SuppressWarnings("serial")
@Entity
@Table(
   name = "controle_eventoterceirizado"
)
public class EventoTerceirizado extends GenericDomainContrato {
   @Column(
      length = 20
   )
   private String tipoEvento;
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private FuncionarioTerceirizado colaborador;
   @Temporal(TemporalType.DATE)
   @Column
   private Date dataEventoInicial;
   @Temporal(TemporalType.DATE)
   @Column
   private Date dataEventoFinal;
   @OneToOne
   @JoinColumn
   private FuncionarioTerceirizado substituto;
   @Temporal(TemporalType.DATE)
   @Column
   private Date dataSubstitutoInicial;
   @Temporal(TemporalType.DATE)
   @Column
   private Date dataSubstitutoFinal;
   @Column(
      length = 30
   )
   private Integer dias;
   @Temporal(TemporalType.DATE)
   @Column(
      nullable = false
   )
   private Date dataCadastro;
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private EmpresaTerceirizada empresaTerceirizada;
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private Usuario usuarioCadastro;
   
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private ContratoTerceirizado contratoTerceirizado;
   
   

   public ContratoTerceirizado getContratoTerceirizado() {
	return contratoTerceirizado;
}

public void setContratoTerceirizado(ContratoTerceirizado contratoTerceirizado) {
	this.contratoTerceirizado = contratoTerceirizado;
}

public Usuario getUsuarioCadastro() {
	return usuarioCadastro;
}

public void setUsuarioCadastro(Usuario usuarioCadastro) {
	this.usuarioCadastro = usuarioCadastro;
}

public EmpresaTerceirizada getEmpresaTerceirizada() {
      return this.empresaTerceirizada;
   }

   public void setEmpresaTerceirizada(EmpresaTerceirizada empresaTerceirizada) {
      this.empresaTerceirizada = empresaTerceirizada;
   }

   public String getTipoEvento() {
      return this.tipoEvento;
   }

   public FuncionarioTerceirizado getColaborador() {
      return this.colaborador;
   }

   public void setColaborador(FuncionarioTerceirizado colaborador) {
      this.colaborador = colaborador;
   }

   public FuncionarioTerceirizado getSubstituto() {
      return this.substituto;
   }

   public void setSubstituto(FuncionarioTerceirizado substituto) {
      this.substituto = substituto;
   }

   public Integer getDias() {
      return this.dias;
   }

   public void setDias(Integer dias) {
      this.dias = dias;
   }

   public Date getDataCadastro() {
      return this.dataCadastro;
   }

   public void setDataCadastro(Date dataCadastro) {
      this.dataCadastro = dataCadastro;
   }

   public void setTipoEvento(String tipoEvento) {
      this.tipoEvento = tipoEvento;
   }

   public Date getDataEventoInicial() {
      return this.dataEventoInicial;
   }

   public void setDataEventoInicial(Date dataEventoInicial) {
      this.dataEventoInicial = dataEventoInicial;
   }

   public Date getDataEventoFinal() {
      return this.dataEventoFinal;
   }

   public void setDataEventoFinal(Date dataEventoFinal) {
      this.dataEventoFinal = dataEventoFinal;
   }

   public Date getDataSubstitutoInicial() {
      return this.dataSubstitutoInicial;
   }

   public void setDataSubstitutoInicial(Date dataSubstitutoInicial) {
      this.dataSubstitutoInicial = dataSubstitutoInicial;
   }

   public Date getDataSubstitutoFinal() {
      return this.dataSubstitutoFinal;
   }

   public void setDataSubstitutoFinal(Date dataSubstitutoFinal) {
      this.dataSubstitutoFinal = dataSubstitutoFinal;
   }
}
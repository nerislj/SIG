/* Decompiler 7ms, total 152ms, lines 170 */
package br.gov.sc.contrato.domain;

import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Unidade;
import java.math.BigDecimal;
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

@Entity
@Table(
   name = "controle_contratoterceirizado"
)
public class ContratoTerceirizado extends GenericDomainContrato {
   @Column(
      length = 255,
      nullable = false
   )
   private String nContrato;
   @ManyToOne
   @JoinColumn
   private Unidade unidade;
   @ManyToOne
   @JoinColumn(
      nullable = false
   )
   private Cidade municipio;
   @Column(
      length = 255,
      nullable = false
   )
   private Integer qtdFuncionarios;
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private CargoTerceirizado cargoTerceirizado;
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private EmpresaTerceirizada empresaTerceirizada;
   @Column(
      length = 10,
      nullable = false
   )
   private Integer hrContrato;
   @Column(
      length = 255,
      nullable = false,
      precision = 10,
      scale = 2
   )
   private BigDecimal valorNF;
   @Column(
      length = 255
   )
   private String nSGPE;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      nullable = false
   )
   private Date dataCadastro;
   @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "contratoTerceirizado"
   )
   @Fetch(FetchMode.SUBSELECT)
   private List<ContratoRelacao> contratoRelacao;

   public EmpresaTerceirizada getEmpresaTerceirizada() {
      return this.empresaTerceirizada;
   }

   public void setEmpresaTerceirizada(EmpresaTerceirizada empresaTerceirizada) {
      this.empresaTerceirizada = empresaTerceirizada;
   }

   public String getnContrato() {
      return this.nContrato;
   }

   public void setnContrato(String nContrato) {
      this.nContrato = nContrato;
   }

   public Unidade getUnidade() {
      return this.unidade;
   }

   public void setUnidade(Unidade unidade) {
      this.unidade = unidade;
   }

   public Cidade getMunicipio() {
      return this.municipio;
   }

   public void setMunicipio(Cidade municipio) {
      this.municipio = municipio;
   }

   public Integer getQtdFuncionarios() {
      return this.qtdFuncionarios;
   }

   public void setQtdFuncionarios(Integer qtdFuncionarios) {
      this.qtdFuncionarios = qtdFuncionarios;
   }

   public CargoTerceirizado getCargoTerceirizado() {
      return this.cargoTerceirizado;
   }

   public void setCargoTerceirizado(CargoTerceirizado cargoTerceirizado) {
      this.cargoTerceirizado = cargoTerceirizado;
   }

   public Integer getHrContrato() {
      return this.hrContrato;
   }

   public void setHrContrato(Integer hrContrato) {
      this.hrContrato = hrContrato;
   }

   public String getnSGPE() {
      return this.nSGPE;
   }

   public void setnSGPE(String nSGPE) {
      this.nSGPE = nSGPE;
   }

   public Date getDataCadastro() {
      return this.dataCadastro;
   }

   public void setDataCadastro(Date dataCadastro) {
      this.dataCadastro = dataCadastro;
   }

   public List<ContratoRelacao> getContratoRelacao() {
      return this.contratoRelacao;
   }

   public void setContratoRelacao(List<ContratoRelacao> contratoRelacao) {
      this.contratoRelacao = contratoRelacao;
   }

   public BigDecimal getValorNF() {
      return this.valorNF;
   }

   public void setValorNF(BigDecimal valorNF) {
      this.valorNF = valorNF;
   }
}
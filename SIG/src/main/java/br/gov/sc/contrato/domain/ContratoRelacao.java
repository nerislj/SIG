/* Decompiler 24ms, total 168ms, lines 85 */
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

@Entity
@Table(
   name = "controle_relacaocontratoterceirizado"
)
public class ContratoRelacao extends GenericDomainContrato {
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private FuncionarioTerceirizado funcionarioTerceirizado;
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private ContratoTerceirizado contratoTerceirizado;
   @Column(
      length = 255,
      nullable = false
   )
   private String status;
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private Usuario usuarioCadastro;
   @Temporal(TemporalType.DATE)
   @Column(
      nullable = false
   )
   private Date dataCadastro;

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
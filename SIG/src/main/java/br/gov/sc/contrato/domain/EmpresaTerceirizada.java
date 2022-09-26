/* Decompiler 2ms, total 147ms, lines 59 */
package br.gov.sc.contrato.domain;

import br.gov.sc.sgi.domain.PessoaJuridica;
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
   name = "controle_empresaterceirizada"
)
public class EmpresaTerceirizada extends GenericDomainContrato {
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private PessoaJuridica pessoaJuridica;
   @Temporal(TemporalType.DATE)
   @Column(
      nullable = false
   )
   private Date dataCadastro;
   @OneToOne
   @JoinColumn(
      nullable = false
   )
   private Usuario usuarioCadastro;

   public PessoaJuridica getPessoaJuridica() {
      return this.pessoaJuridica;
   }

   public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
      this.pessoaJuridica = pessoaJuridica;
   }

   public Date getDataCadastro() {
      return this.dataCadastro;
   }

   public void setDataCadastro(Date dataCadastro) {
      this.dataCadastro = dataCadastro;
   }

   public Usuario getUsuarioCadastro() {
      return this.usuarioCadastro;
   }

   public void setUsuarioCadastro(Usuario usuarioCadastro) {
      this.usuarioCadastro = usuarioCadastro;
   }
}
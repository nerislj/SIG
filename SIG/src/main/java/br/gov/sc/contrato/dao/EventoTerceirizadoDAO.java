/* Decompiler 4ms, total 154ms, lines 45 */
package br.gov.sc.contrato.dao;

import br.gov.sc.contrato.domain.EmpresaTerceirizada;
import br.gov.sc.contrato.domain.EventoTerceirizado;
import br.gov.sc.sgi.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class EventoTerceirizadoDAO extends GenericDAO<EventoTerceirizado> {
   public List<EventoTerceirizado> listarPorEmpresa(EmpresaTerceirizada empresa, String tipoEvento, Date dateIni, Date dateFini) {
      Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

      List var9;
      try {
         Criteria consulta = sessao.createCriteria(EventoTerceirizado.class);
         if (empresa != null) {
            consulta.add(Restrictions.eq("empresaTerceirizada", empresa));
            consulta.add(Restrictions.eq("tipoEvento", tipoEvento));
            if (dateIni != null || dateFini != null) {
               if (dateIni.equals(dateFini)) {
                  consulta.add(Restrictions.eq("dataEventoInicial", dateIni));
               } else {
                  System.out.println(dateIni);
                  System.out.println(dateFini);
                  consulta.add(Restrictions.ge("dataEventoInicial", dateIni));
                  consulta.add(Restrictions.le("dataEventoFinal", dateFini));
               }
            }
         }

         List<EventoTerceirizado> resultado = consulta.list();
         var9 = resultado;
      } catch (RuntimeException var12) {
         throw var12;
      } finally {
         sessao.close();
      }

      return var9;
   }
}
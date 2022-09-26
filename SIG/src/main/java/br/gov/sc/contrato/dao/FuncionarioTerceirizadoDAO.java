/* Decompiler 4ms, total 154ms, lines 61 */
package br.gov.sc.contrato.dao;

import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class FuncionarioTerceirizadoDAO extends GenericDAO<FuncionarioTerceirizado> {
   public static PessoaFisica carregarCpf(String cpf) {
      Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
      Criteria consulta = sessao.createCriteria(PessoaFisica.class);
      SimpleExpression condicao1 = Restrictions.eq("cpf", cpf);
      consulta.add(Restrictions.or(new Criterion[]{condicao1})).setMaxResults(1).uniqueResult();
      return consulta.uniqueResult().equals((Object)null) ? null : (PessoaFisica)consulta.setMaxResults(1).uniqueResult();
   }

   public List<FuncionarioTerceirizado> listarPorTodos() {
      Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

      List var5;
      try {
         Criteria consulta = sessao.createCriteria(FuncionarioTerceirizado.class);
         consulta.addOrder(Order.asc("codigo"));
         List<FuncionarioTerceirizado> resultado = consulta.list();
         var5 = resultado;
      } catch (RuntimeException var8) {
         throw var8;
      } finally {
         sessao.close();
      }

      return var5;
   }

   public List<FuncionarioTerceirizado> listarPorUnidadeUsuarioLogado(Unidade unidade) {
      Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

      List var6;
      try {
         Criteria consulta = sessao.createCriteria(FuncionarioTerceirizado.class);
         consulta.add(Restrictions.eq("unidade", unidade));
         consulta.addOrder(Order.asc("codigo"));
         List<FuncionarioTerceirizado> resultado = consulta.list();
         var6 = resultado;
      } catch (RuntimeException var9) {
         throw var9;
      } finally {
         sessao.close();
      }

      return var6;
   }
}
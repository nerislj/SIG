/* Decompiler 4ms, total 154ms, lines 61 */
package br.gov.sc.contrato.dao;

import br.gov.sc.codet.domain.HistoricoProcessoCODET;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.contrato.domain.UserClaimsContrato;
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

  
  
   
 
   
   @SuppressWarnings("unchecked")
	public List<FuncionarioTerceirizado> listarPorClaims(List<String> unidades) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(FuncionarioTerceirizado.class);
			consulta.createAlias("unidade", "u");
			consulta.add(Restrictions.in("u.unidadeNome", unidades));

			List<FuncionarioTerceirizado> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	   
  
   
 
}
/* Decompiler 2ms, total 151ms, lines 29 */
package br.gov.sc.contrato.dao;

import br.gov.sc.contrato.domain.EmpresaTerceirizada;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class EmpresaTerceirizadaDAO extends GenericDAO<EmpresaTerceirizada> {
   public static PessoaJuridica carregarCnpj(String cnpj) {
      Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
      Criteria consulta = sessao.createCriteria(PessoaJuridica.class);
      SimpleExpression condicao1 = Restrictions.eq("cnpj", cnpj);
      consulta.add(Restrictions.or(new Criterion[]{condicao1})).setMaxResults(1).uniqueResult();
      return consulta.uniqueResult().equals((Object)null) ? null : (PessoaJuridica)consulta.setMaxResults(1).uniqueResult();
   }

   public static EmpresaTerceirizada carregarEmpresa(String nomeFantasia) {
      Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
      Criteria criteria = sessao.createCriteria(EmpresaTerceirizada.class);
      criteria.createAlias("pessoaJuridica", "p");
      criteria.add(Restrictions.eq("p.nomeFantasia", nomeFantasia));
      return (EmpresaTerceirizada)criteria.setMaxResults(1).uniqueResult();
   }
}
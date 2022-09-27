/* Decompiler 1ms, total 148ms, lines 7 */
package br.gov.sc.contrato.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.contrato.domain.ContratoTerceirizado;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.util.HibernateUtil;

public class ContratoTerceirizadoDAO extends GenericDAO<ContratoTerceirizado> {
	
	
	public List<ContratoTerceirizado> listarPorUnidadeUsuarioLogado(Unidade unidade) {
	      Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

	      List var6;
	      try {
	         Criteria consulta = sessao.createCriteria(ContratoTerceirizado.class);
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
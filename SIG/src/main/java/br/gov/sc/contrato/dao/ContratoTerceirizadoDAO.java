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
	
	

	
	  @SuppressWarnings("unchecked")
		public List<ContratoTerceirizado> listarPorClaims(List<String> unidades) {

			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {

				Criteria consulta = sessao.createCriteria(ContratoTerceirizado.class);
				consulta.createAlias("unidade", "u");
				consulta.add(Restrictions.in("u.unidadeNome", unidades));

				List<ContratoTerceirizado> resultado = consulta.list();

				return resultado;

			} catch (RuntimeException erro) {
				throw erro;
			} finally {
				sessao.close();
			}
		}
}
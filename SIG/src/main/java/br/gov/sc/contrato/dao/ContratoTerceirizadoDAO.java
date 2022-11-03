/* Decompiler 1ms, total 148ms, lines 7 */
package br.gov.sc.contrato.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import br.gov.sc.contrato.bean.ContratoTerceirizadoBean;
import br.gov.sc.contrato.domain.ContratoTerceirizado;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.geapo.domain.Material;
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
	  
	  
	  @SuppressWarnings("unchecked")
		public List<ContratoTerceirizado> listarPorOrdemASC() {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {
				Criteria consulta = sessao.createCriteria(ContratoTerceirizado.class);
				consulta.addOrder(Order.asc("codigo"));
				List<ContratoTerceirizado> resultado = consulta.list();
				return resultado;
			} catch (RuntimeException erro) {
				throw erro;
			} finally {
				sessao.close();
			}
		}
	  
	  @SuppressWarnings("unchecked")
		public List<ContratoTerceirizado> listarPorContratoEmpresa() {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {
				Criteria c = sessao.createCriteria(ContratoTerceirizado.class);
				
			
				 ProjectionList projList = Projections.projectionList();
				    projList.add(Projections.distinct(Projections.property("nContrato")));
				 
				    c.setProjection(projList);
				    
				   
				    List<ContratoTerceirizado> resultado = c. list(); 
				return resultado;
			} catch (RuntimeException erro) {
				throw erro;
			} finally {
				sessao.close();
			}
		}
}
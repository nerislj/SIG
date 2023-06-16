package br.gov.sc.funcionariosuf.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.dao.GenericDAO;
import br.gov.sc.funcionariosuf.domain.CiretranCitran;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.util.HibernateUtil;

public class CiretranCitranDAO extends GenericDAO<CiretranCitran>{

		@SuppressWarnings("unchecked")
		public List<CiretranCitran> buscarPorCiretran(Long estadoCodigo) {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {
				Criteria consulta = sessao.createCriteria(CiretranCitran.class);
				consulta.add(Restrictions.eq("ciretran.codigo", estadoCodigo));	
				consulta.addOrder(Order.asc("nome"));
				List<CiretranCitran> resultado = consulta.list();
				return resultado;
			} catch (RuntimeException erro) {
				throw erro;
			} finally {
				sessao.close();
			}
		}
		
		
		@SuppressWarnings("unchecked")
		public List<CiretranCitran> buscarPorEstadoNome(String nome) {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {
				Criteria consulta = sessao.createCriteria(CiretranCitran.class);
				
				consulta.createAlias("ciretran", "e");
				consulta.add(Restrictions.eq("e.nome", nome));	
				consulta.addOrder(Order.asc("nome"));
				List<CiretranCitran> resultado = consulta.list();
				return resultado;
			} catch (RuntimeException erro) {
				throw erro;
			} finally {
				sessao.close();
			}
		}
		
		
		public CiretranCitran loadNome(String nome) throws Exception {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			Criteria criteria = sessao.createCriteria(CiretranCitran.class);
			criteria.addOrder(Order.desc("codigo"));
			criteria.add(Restrictions.eq("nome", nome));
			

			return (CiretranCitran) criteria.setMaxResults(1).uniqueResult();
		}
		
		public CiretranCitran loadNomeString(String nome) throws Exception {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			Criteria criteria = sessao.createCriteria(CiretranCitran.class);
			criteria.addOrder(Order.desc("codigo"));
			criteria.add(Restrictions.eq("nome", nome));
		

			return (CiretranCitran) criteria.setMaxResults(1).uniqueResult();
		}
}

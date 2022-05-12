package br.gov.sc.sgi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.util.HibernateUtil;

public class CidadeDAO extends GenericDAO<Cidade>{

		@SuppressWarnings("unchecked")
		public List<Cidade> buscarPorEstado(Long estadoCodigo) {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {
				Criteria consulta = sessao.createCriteria(Cidade.class);
				consulta.add(Restrictions.eq("estado.codigo", estadoCodigo));	
				consulta.addOrder(Order.asc("nome"));
				List<Cidade> resultado = consulta.list();
				return resultado;
			} catch (RuntimeException erro) {
				throw erro;
			} finally {
				sessao.close();
			}
		}
		
		
		public Cidade loadNome(String nome) throws Exception {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			Criteria criteria = sessao.createCriteria(Cidade.class);
			criteria.addOrder(Order.desc("codigo"));
			criteria.add(Restrictions.eq("nome", nome));
			

			return (Cidade) criteria.setMaxResults(1).uniqueResult();
		}
}

package br.gov.sc.sgi.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.util.HibernateUtil;

public class EstadoDAO extends GenericDAO<Estado>{
	
	
	public Estado loadSigla(String estado) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(Estado.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("sigla", estado));
		

		return (Estado) criteria.setMaxResults(1).uniqueResult();
	}
	
}

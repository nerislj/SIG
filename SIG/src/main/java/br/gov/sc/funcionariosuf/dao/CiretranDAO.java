package br.gov.sc.funcionariosuf.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.dao.GenericDAO;
import br.gov.sc.funcionariosuf.domain.Ciretran;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.sgi.util.HibernateUtil;

public class CiretranDAO extends GenericDAO<Ciretran>{
	
	
	public Ciretran loadSigla(String estado) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(Ciretran.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("nome", estado));
		

		return (Ciretran) criteria.setMaxResults(1).uniqueResult();
	}
	
	
	
}

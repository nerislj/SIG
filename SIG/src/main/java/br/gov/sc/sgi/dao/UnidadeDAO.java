package br.gov.sc.sgi.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.util.HibernateUtil;

public class UnidadeDAO extends GenericDAO<Unidade>{
	
	public static Unidade carregarUnidadeAtual(String setor) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		
		Criteria criteria = sessao.createCriteria(Unidade.class);
		
		criteria.add(Restrictions.eq("unidadeNome", setor));
		

		return (Unidade) criteria.setMaxResults(1).uniqueResult();
	}
	
}

package br.gov.sc.geapo.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.geapo.domain.MaterialStatus;
import br.gov.sc.sgi.util.HibernateUtil;

public class MaterialStatusDAO extends GenericDAO<MaterialStatus>{
	
	public static MaterialStatus carregarStatus(String situacao) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		
		Criteria criteria = sessao.createCriteria(MaterialStatus.class);
		
		criteria.add(Restrictions.eq("materialStatus", situacao));
		

		return (MaterialStatus) criteria.setMaxResults(1).uniqueResult();
	}
	
}

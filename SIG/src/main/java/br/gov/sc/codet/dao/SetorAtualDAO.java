package br.gov.sc.codet.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.domain.NomenclaturaProcesso;
import br.gov.sc.codet.domain.SetorAtual;
import br.gov.sc.sgi.util.HibernateUtil;


public class SetorAtualDAO extends GenericDAO<SetorAtual>{
	
	public static SetorAtual carregarSetorAtual(String setor) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		
		Criteria criteria = sessao.createCriteria(SetorAtual.class);
		
		criteria.add(Restrictions.eq("descricao", setor));
		

		return (SetorAtual) criteria.setMaxResults(1).uniqueResult();
	}
	
}

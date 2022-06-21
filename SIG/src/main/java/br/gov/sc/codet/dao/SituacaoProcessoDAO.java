package br.gov.sc.codet.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.domain.SituacaoProcesso;
import br.gov.sc.sgi.util.HibernateUtil;

public class SituacaoProcessoDAO extends GenericDAO<SituacaoProcesso>{

	
	public static SituacaoProcesso carregarSituacao(String situacao) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		
		Criteria criteria = sessao.createCriteria(SituacaoProcesso.class);
		
		criteria.add(Restrictions.eq("descricao", situacao));
		

		return (SituacaoProcesso) criteria.setMaxResults(1).uniqueResult();
	}
	
}

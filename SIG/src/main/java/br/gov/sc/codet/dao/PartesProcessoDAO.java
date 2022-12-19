package br.gov.sc.codet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.PartesProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.sgi.util.HibernateUtil;

public class PartesProcessoDAO extends GenericDAO<PartesProcesso>{
	
	
	@SuppressWarnings("unchecked")
	public List<PartesProcesso> listarPorProcesso(Processo processo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(PartesProcesso.class);
			consulta.add(Restrictions.eq("processo", processo));	
			consulta.addOrder(Order.asc("codigo"));
			List<PartesProcesso> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PartesProcesso> listarPorProcessoObject(Object processo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(PartesProcesso.class);
			consulta.add(Restrictions.eq("processo", processo));	
			consulta.addOrder(Order.asc("codigo"));
			List<PartesProcesso> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public PartesProcesso loadLast(Processo processo) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(PartesProcesso.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("processo", processo));

		return (PartesProcesso) criteria.setMaxResults(1).uniqueResult();
	}
	
	
}

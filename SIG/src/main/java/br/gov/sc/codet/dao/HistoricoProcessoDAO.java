package br.gov.sc.codet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.HistoricoProcessoCODET;
import br.gov.sc.codet.domain.PartesProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.sgi.util.HibernateUtil;

public class HistoricoProcessoDAO extends GenericDAO<HistoricoProcessoCODET>{
	
	
	@SuppressWarnings("unchecked")
	public List<HistoricoProcessoCODET> listarPorProcesso(Processo processo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(HistoricoProcessoCODET.class);
			consulta.add(Restrictions.eq("processo", processo));	
			consulta.addOrder(Order.desc("dataCadastro"));
			List<HistoricoProcessoCODET> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public HistoricoProcessoCODET loadLast(Processo processo) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(HistoricoProcessoCODET.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("processo", processo));

		return (HistoricoProcessoCODET) criteria.setMaxResults(1).uniqueResult();
	}
	
	
}

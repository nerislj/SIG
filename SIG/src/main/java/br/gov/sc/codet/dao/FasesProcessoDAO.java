package br.gov.sc.codet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.sgi.util.HibernateUtil;

public class FasesProcessoDAO extends GenericDAO<FasesProcesso>{
	
	
	@SuppressWarnings("unchecked")
	public List<FasesProcesso> listarPorProcesso(Processo processo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(FasesProcesso.class);
			consulta.add(Restrictions.eq("processo", processo));	
			consulta.addOrder(Order.desc("dataCadastro"));
			List<FasesProcesso> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
}

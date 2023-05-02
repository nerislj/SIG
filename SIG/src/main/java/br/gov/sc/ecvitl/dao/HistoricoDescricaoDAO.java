package br.gov.sc.ecvitl.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.domain.HistoricoProcessoCODET;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.ecvitl.domain.EcvItl;
import br.gov.sc.ecvitl.domain.HistoricoDescricao;
import br.gov.sc.sgi.util.HibernateUtil;


public class HistoricoDescricaoDAO extends GenericDAO<HistoricoDescricao>{
	
	
	@SuppressWarnings("unchecked")
	public List<HistoricoDescricao> listarPorECVITL(EcvItl processo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(HistoricoDescricao.class);
			consulta.add(Restrictions.eq("ecvItl", processo));	
			consulta.addOrder(Order.desc("dataCadastro"));
			List<HistoricoDescricao> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	
}

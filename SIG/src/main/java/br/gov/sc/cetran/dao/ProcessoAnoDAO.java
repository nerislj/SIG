package br.gov.sc.cetran.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.gov.sc.sgi.dao.GenericDAO;
import br.gov.sc.cetran.domain.ProcessoAno;
import br.gov.sc.sgi.util.HibernateUtil;

public class ProcessoAnoDAO extends GenericDAO<ProcessoAno> {

	public ProcessoAno loadAno(ProcessoAno ano) throws Exception {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(ProcessoAno.class);
		criteria.addOrder(Order.desc("codigo"));

		return (ProcessoAno) criteria.setMaxResults(1).uniqueResult();

	}

	@SuppressWarnings("unchecked")
	public List<ProcessoAno> loadAnos() throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(ProcessoAno.class);
		consulta.addOrder(Order.desc("processoAno"));
		List<ProcessoAno> resultado = (List<ProcessoAno>) consulta.list();

		return resultado;
	}
}
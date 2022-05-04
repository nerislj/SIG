package br.gov.sc.sgi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.gov.sc.sgi.domain.RecursoMultaAno;
import br.gov.sc.sgi.util.HibernateUtil;

public class RecursoMultaAnoDAO extends GenericDAO<RecursoMultaAno> {

	public RecursoMultaAno loadAno(RecursoMultaAno ano) throws Exception {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(RecursoMultaAno.class);
		criteria.addOrder(Order.desc("codigo"));

		return (RecursoMultaAno) criteria.setMaxResults(1).uniqueResult();

	}

	@SuppressWarnings("unchecked")
	public List<RecursoMultaAno> loadAnos() throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(RecursoMultaAno.class);
		consulta.addOrder(Order.desc("recursoMultaAno"));
		List<RecursoMultaAno> resultado = (List<RecursoMultaAno>) consulta.list();

		return resultado;
	}
}
package br.gov.sc.sgi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.gov.sc.sgi.domain.OficioAno;
import br.gov.sc.sgi.util.HibernateUtil;

public class OficioAnoDAO extends GenericDAO<OficioAno> {

	public OficioAno loadAno(OficioAno ano) throws Exception {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(OficioAno.class);
		criteria.addOrder(Order.desc("codigo"));

		return (OficioAno) criteria.setMaxResults(1).uniqueResult();

	}

	@SuppressWarnings("unchecked")
	public List<OficioAno> loadAnos() throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(OficioAno.class);
		consulta.addOrder(Order.desc("oficioAno"));
		List<OficioAno> resultado = (List<OficioAno>) consulta.list();

		return resultado;
	}
}
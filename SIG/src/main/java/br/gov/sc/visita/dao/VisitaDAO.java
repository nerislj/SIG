
package br.gov.sc.visita.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.dao.GenericDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.util.HibernateUtil;
import br.gov.sc.visita.domain.Visita;
import br.gov.sc.visita.domain.Visitante;

public class VisitaDAO extends GenericDAO<Visita>{
	
	
	
	@SuppressWarnings("unchecked")
	public List<Visita> listarVisitas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Visita.class);
			consulta.addOrder(Order.desc("codigo"));
			

			List<Visita> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public Visitante buscarVisitante(Visitante visitante) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(Visitante.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("cpf", visitante.getCpf()));
		

		return (Visitante) criteria.setMaxResults(1).uniqueResult();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Visita> carregarVisitante(Visitante visitante) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(Visita.class);
		consulta.addOrder(Order.desc("codigo"));
		consulta.createAlias("visitante", "v");
		consulta.add(Restrictions.eq("v.cpf", visitante.getCpf()));
		List<Visita> resultado = (List<Visita>) consulta.list();

		return resultado;
	}
	
	

	
}
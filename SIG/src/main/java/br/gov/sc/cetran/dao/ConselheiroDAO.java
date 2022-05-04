package br.gov.sc.cetran.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import br.gov.sc.cetran.domain.Conselheiro;
import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.geapo.dao.GenericDAO;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.util.HibernateUtil;

public class ConselheiroDAO extends GenericDAO<Conselheiro>{
	
	@SuppressWarnings("unchecked")
	public List<Conselheiro> buscarPorTipo(Long tipoCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Conselheiro.class);
			consulta.add(Restrictions.eq("conselheiro.codigo", tipoCodigo));	
			consulta.addOrder(Order.asc("conselheiro"));
			List<Conselheiro> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	
	public static Conselheiro carregarConselheiro(String nome) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		
		Criteria criteria = sessao.createCriteria(Conselheiro.class);
		
		criteria.add(Restrictions.eq("nome", nome));
		

		return (Conselheiro) criteria.setMaxResults(1).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Conselheiro> listarTudo() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query query = sessao.createQuery("from Conselheiro"); //You will get Weayher object
			List<Conselheiro> list = query.list(); //You are accessing  as list<WeatherModel>

			return list;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}

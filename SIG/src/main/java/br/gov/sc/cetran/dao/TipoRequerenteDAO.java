package br.gov.sc.cetran.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.gov.sc.cetran.domain.TipoRequerente;
import br.gov.sc.geapo.dao.GenericDAO;
import br.gov.sc.sgi.util.HibernateUtil;

public class TipoRequerenteDAO extends GenericDAO<TipoRequerente>{
	

	
	
	
	
	@SuppressWarnings("unchecked")
	public List<TipoRequerente> listarTudo() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query query = sessao.createQuery("from TipoRequerente"); //You will get Weayher object
			List<TipoRequerente> list = query.list(); //You are accessing  as list<WeatherModel>

			return list;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}

package br.gov.sc.cetran.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.gov.sc.cetran.domain.Situacao;
import br.gov.sc.codet.domain.SituacaoProcesso;
import br.gov.sc.geapo.dao.GenericDAO;
import br.gov.sc.sgi.util.HibernateUtil;

public class SituacaoDAO extends GenericDAO<Situacao>{
	

	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Situacao> listarTudo() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query query = sessao.createQuery("from Situacao"); //You will get Weayher object
			List<Situacao> list = query.list(); //You are accessing  as list<WeatherModel>

			return list;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}

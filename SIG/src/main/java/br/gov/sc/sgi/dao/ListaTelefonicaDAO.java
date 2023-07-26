package br.gov.sc.sgi.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.ListaTelefonica;
import br.gov.sc.sgi.util.HibernateUtil;

public class ListaTelefonicaDAO extends GenericDAO<ListaTelefonica> {

	
	public ListaTelefonica autenticarPorCpf(String cpf) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(ListaTelefonica.class);

			
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.cpf", cpf));
			
			
			ListaTelefonica resultado = (ListaTelefonica) consulta.uniqueResult(); 
			
			return resultado;
		
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}

	}

}

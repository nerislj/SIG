package br.gov.sc.ecvitl.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.contrato.domain.ContratoRelacao;
import br.gov.sc.ecvitl.domain.PessoaEcvItl;
import br.gov.sc.sgi.util.HibernateUtil;


public class PessoaEcvItlDAO extends GenericDAO<PessoaEcvItl>{
	
	
	public List<PessoaEcvItl> listarPorEcvItlObject(Object ecvItl) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		List var6;
		try {
			Criteria consulta = sessao.createCriteria(PessoaEcvItl.class);
			consulta.add(Restrictions.eq("ecvItl", ecvItl));
			consulta.addOrder(Order.asc("codigo"));
			List<PessoaEcvItl> resultado = consulta.list();
			var6 = resultado;
		} catch (RuntimeException var9) {
			throw var9;
		} finally {
			sessao.close();
		}

		return var6;
	}

	
}

package br.gov.sc.sgi.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.FrotaCondutor;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.util.HibernateUtil;

public class FrotaCondutorDAO extends GenericDAO<FrotaCondutor> {

	public static FrotaCondutor consultaporPessoa(PessoaFisica pessoa) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(FrotaCondutor.class);

		consulta.add(Restrictions.eq("pessoa", pessoa));

		if (consulta.uniqueResult().equals(null)) {
			return null;
		} else {
			return (FrotaCondutor) consulta.uniqueResult();
		}
	}
	
}

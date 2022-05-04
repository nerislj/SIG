package br.gov.sc.sgi.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.util.HibernateUtil;

public class PessoaJuridicaDAO extends GenericDAO<PessoaJuridica> {

	public static PessoaJuridica carregarCnpj(String cnpj) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(PessoaJuridica.class);

		consulta.add(Restrictions.eq("cnpj", cnpj));

		if (consulta.uniqueResult().equals(null)) {
			return null;
		} else {
			return (PessoaJuridica) consulta.uniqueResult();
		} 
	}

}

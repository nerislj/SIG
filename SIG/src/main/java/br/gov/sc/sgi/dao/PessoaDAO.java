package br.gov.sc.sgi.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.util.HibernateUtil;

public class PessoaDAO extends GenericDAO<PessoaFisica> {

	public static PessoaFisica carregarCpf(String cpf) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(PessoaFisica.class);

		consulta.add(Restrictions.eq("cpf", cpf));

		if (consulta.uniqueResult().equals(null)) {
			return null;
		} else {
			return (PessoaFisica) consulta.uniqueResult();
		}
	}
}
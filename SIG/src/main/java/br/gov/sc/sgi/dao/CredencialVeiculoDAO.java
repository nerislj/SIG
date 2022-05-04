package br.gov.sc.sgi.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.CredencialVeiculo;
import br.gov.sc.sgi.util.HibernateUtil;

public class CredencialVeiculoDAO extends GenericDAO<CredencialVeiculo>{
	
	public CredencialVeiculo carregarVeiculo(String placa) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			
		Criteria consulta = sessao.createCriteria(CredencialVeiculo.class);

		consulta.add(Restrictions.eq("placa", placa));
		 
		return (CredencialVeiculo) consulta.uniqueResult();
			
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}

	}

}




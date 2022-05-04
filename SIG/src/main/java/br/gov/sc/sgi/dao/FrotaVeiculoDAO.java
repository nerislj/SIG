package br.gov.sc.sgi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.FrotaVeiculo;
import br.gov.sc.sgi.domain.FrotaViagem;
import br.gov.sc.sgi.util.HibernateUtil;

public class FrotaVeiculoDAO extends GenericDAO<FrotaVeiculo> {

	@SuppressWarnings("unchecked")
	public List<FrotaVeiculo> buscarPorUnidade(Long veiculoCodigo, List<FrotaViagem> viagens) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			
			
			Criteria consulta = sessao.createCriteria(FrotaVeiculo.class);
			
			
			
				System.out.println(viagens);
				consulta.add(Restrictions.eq("unidade.codigo", veiculoCodigo));	
				consulta.add(Restrictions.like("status", "Disp%"));
				
				
				consulta.addOrder(Order.asc("placa"));
			
			
				
			
			
			
			
			List<FrotaVeiculo> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	
	public FrotaVeiculo carregaVeiculo(FrotaVeiculo veiculo) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(FrotaVeiculo.class);
		consulta.addOrder(Order.desc("codigo"));

		if (consulta.setMaxResults(1).uniqueResult().equals(null)) {
			return null;
		} else {
			return (FrotaVeiculo) consulta.setMaxResults(1).uniqueResult();
		}
	}
	
	
	
	
}

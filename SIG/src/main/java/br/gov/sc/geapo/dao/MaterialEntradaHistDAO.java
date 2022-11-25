package br.gov.sc.geapo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.geapo.domain.MaterialEntrada;
import br.gov.sc.geapo.domain.MaterialEntradaHist;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.util.HibernateUtil;

public class MaterialEntradaHistDAO extends GenericDAO<MaterialEntradaHist>{
	
	@SuppressWarnings("unchecked")
	public List<MaterialEntradaHist> listarPorMaterialEntrada(MaterialEntrada material) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(MaterialEntradaHist.class);
			
			consulta.add(Restrictions.eq("materialEntrada", material));
			

			List<MaterialEntradaHist> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public MaterialEntradaHist loadLast(MaterialEntrada material) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(MaterialEntradaHist.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("materialEntrada", material));

		return (MaterialEntradaHist) criteria.setMaxResults(1).uniqueResult();
	}
	
}

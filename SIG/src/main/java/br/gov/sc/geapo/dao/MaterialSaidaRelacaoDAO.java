package br.gov.sc.geapo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.geapo.domain.MaterialEntrada;
import br.gov.sc.geapo.domain.MaterialEntradaHist;
import br.gov.sc.geapo.domain.MaterialSaidaRelacao;
import br.gov.sc.sgi.util.HibernateUtil;
	
public class MaterialSaidaRelacaoDAO extends GenericDAO<MaterialSaidaRelacao>{
	
	
	public MaterialSaidaRelacao loadLast(MaterialEntrada material) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(MaterialSaidaRelacao.class);
		criteria.addOrder(Order.desc("codigo"));	
		criteria.add(Restrictions.eq("materialEntrada", material));

		return (MaterialSaidaRelacao) criteria.setMaxResults(1).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<MaterialSaidaRelacao> listarPorMaterialEntrada(MaterialEntrada material) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(MaterialSaidaRelacao.class);
			
			consulta.add(Restrictions.eq("materialEntrada", material));
			

			List<MaterialSaidaRelacao> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
}

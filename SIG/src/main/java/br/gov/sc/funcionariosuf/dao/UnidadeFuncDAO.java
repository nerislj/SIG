
package br.gov.sc.funcionariosuf.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.dao.GenericDAO;
import br.gov.sc.funcionariosuf.domain.UnidadeFunc;
import br.gov.sc.sgi.util.HibernateUtil;

public class UnidadeFuncDAO extends GenericDAO<UnidadeFunc> {

	@SuppressWarnings("unchecked")
	public List<UnidadeFunc> buscarPorCiretran(Long estadoCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(UnidadeFunc.class);
			consulta.add(Restrictions.eq("unidade.codigo", estadoCodigo));
			consulta.addOrder(Order.asc("unidadeNome"));
			List<UnidadeFunc> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<UnidadeFunc> buscarPorEstadoNome(String nome) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(UnidadeFunc.class);

			consulta.createAlias("unidade", "e");
			consulta.add(Restrictions.eq("e.unidadeNome", nome));
			consulta.addOrder(Order.asc("unidadeNome"));
			List<UnidadeFunc> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public UnidadeFunc loadNome(String nome) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(UnidadeFunc.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("unidadeNome", nome));

		return (UnidadeFunc) criteria.setMaxResults(1).uniqueResult();
	}

	public UnidadeFunc loadNomeString(String nome) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(UnidadeFunc.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("unidadeNome", nome));

		return (UnidadeFunc) criteria.setMaxResults(1).uniqueResult();
	}
}
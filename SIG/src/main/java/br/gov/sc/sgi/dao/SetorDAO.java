package br.gov.sc.sgi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.domain.SetorAtual;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.util.HibernateUtil;

public class SetorDAO extends GenericDAO<Setor>{
	
	@SuppressWarnings("unchecked")
	public List<Setor> buscarPorUnidade(Long unidadeCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Setor.class);
			consulta.add(Restrictions.eq("unidade.codigo", unidadeCodigo));	
			List<Setor> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public static Setor carregarSetorAtual(String setor) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		
		Criteria criteria = sessao.createCriteria(Setor.class);
		
		criteria.add(Restrictions.eq("setor", setor));
		

		return (Setor) criteria.setMaxResults(1).uniqueResult();
	}
	
}

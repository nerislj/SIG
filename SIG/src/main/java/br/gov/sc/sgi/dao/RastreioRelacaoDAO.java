package br.gov.sc.sgi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.RastreioRelacao;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.util.HibernateUtil;

public class RastreioRelacaoDAO extends GenericDAO<RastreioRelacao> {

	@SuppressWarnings("unchecked")
	public List<RastreioRelacao> listarRastreio(Setor setor, Unidade unidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(RastreioRelacao.class);

			consulta.createAlias("oficio", "o");
			consulta.add(Restrictions.eq("o.setorAbertura", setor));
			consulta.add(Restrictions.eq("o.unidadeAbertura", unidade));
			consulta.addOrder(Order.desc("codigo"));

			List<RastreioRelacao> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<RastreioRelacao> listarfiltro(Setor setor, Unidade unidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(RastreioRelacao.class);

			consulta.createAlias("oficio", "o");
			consulta.add(Restrictions.eq("o.unidadeAbertura", unidade));
			consulta.add(Restrictions.eq("o.setorAbertura", setor));

			List<RastreioRelacao> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}

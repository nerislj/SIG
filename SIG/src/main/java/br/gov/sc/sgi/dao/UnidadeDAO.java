package br.gov.sc.sgi.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Array;

import br.gov.sc.cetran.domain.Requerente;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.contrato.domain.UserClaimsContrato;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.util.HibernateUtil;

public class UnidadeDAO extends GenericDAO<Unidade> {

	public static Unidade carregarUnidadeAtual(String setor) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria criteria = sessao.createCriteria(Unidade.class);

		criteria.add(Restrictions.eq("unidadeNome", setor));

		return (Unidade) criteria.setMaxResults(1).uniqueResult();
	}

	public List<Unidade> listarPorUnidadeUsuarioLogado(String unidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		List var6;
		try {
			Criteria consulta = sessao.createCriteria(Unidade.class);
			consulta.add(Restrictions.eq("unidadeNome", unidade));

			List<Unidade> resultado = consulta.list();
			var6 = resultado;
		} catch (RuntimeException var9) {
			throw var9;
		} finally {
			sessao.close();
		}

		return var6;
	}

	@SuppressWarnings("unchecked")
	public List<Unidade> listarPorClaims(List<String> unidades) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Unidade.class);
			consulta.add(Restrictions.in("unidadeNome", unidades));

			List<Unidade> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}

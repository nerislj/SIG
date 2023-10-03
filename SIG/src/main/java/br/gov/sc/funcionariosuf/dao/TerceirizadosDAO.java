
package br.gov.sc.funcionariosuf.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import br.gov.sc.codet.dao.GenericDAO;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.funcionariosuf.domain.Estagiarios;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.util.HibernateUtil;

public class TerceirizadosDAO extends GenericDAO<Estagiarios> {

	public static PessoaFisica carregarCpf(String cpf) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(PessoaFisica.class);
		SimpleExpression condicao1 = Restrictions.eq("cpf", cpf);
		consulta.add(Restrictions.or(new Criterion[] { condicao1 })).setMaxResults(1).uniqueResult();
		return consulta.uniqueResult().equals((Object) null) ? null
				: (PessoaFisica) consulta.setMaxResults(1).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<FuncionarioTerceirizado> listarPorUnidade(Object processo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(FuncionarioTerceirizado.class);
			consulta.add(Restrictions.eq("unidade", processo));
			
			consulta.addOrder(Order.desc("codigo"));
			List<FuncionarioTerceirizado> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}


}
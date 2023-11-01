
package br.gov.sc.funcionariosuf.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import br.gov.sc.codet.dao.GenericDAO;
import br.gov.sc.contrato.domain.ContratoRelacao;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.funcionariosuf.domain.Servidores;
import br.gov.sc.funcionariosuf.domain.UnidadeFunc;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.util.HibernateUtil;

public class ServidoresDAO extends GenericDAO<Servidores> {

	public static PessoaFisica carregarCpf(String cpf) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(PessoaFisica.class);
		SimpleExpression condicao1 = Restrictions.eq("cpf", cpf);
		consulta.add(Restrictions.or(new Criterion[] { condicao1 })).setMaxResults(1).uniqueResult();
		return consulta.uniqueResult().equals((Object) null) ? null
				: (PessoaFisica) consulta.setMaxResults(1).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Servidores> listarPorUnidade(Object processo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Servidores.class);
			consulta.add(Restrictions.eq("unidade", processo));
		
			consulta.addOrder(Order.desc("codigo"));
			List<Servidores> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Servidores> listarPorUnidadeCodigo(UnidadeFunc processo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Servidores.class);
			consulta.add(Restrictions.eq("unidade", processo));
		
			consulta.addOrder(Order.asc("codigo"));
			List<Servidores> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
  	public List<Servidores> listarUnidades(List<String> unidades) {

  		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
  		try {

  			Criteria consulta = sessao.createCriteria(Servidores.class);
  			consulta.createAlias("unidade", "u");
  			consulta.add(Restrictions.in("u.unidadeNome", unidades));
  			

  			List<Servidores> resultado = consulta.list();

  			return resultado;

  		} catch (RuntimeException erro) {
  			throw erro;
  		} finally {
  			sessao.close();
  		}
  	}
	
	 @SuppressWarnings("unchecked")
		public List<Servidores> listarPorClaims(List<String> unidades) {

			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {

				Criteria consulta = sessao.createCriteria(Servidores.class);
				consulta.createAlias("unidade", "u");
				consulta.add(Restrictions.in("u.unidadeNome", unidades));
				
				

				List<Servidores> resultado = consulta.list();

				return resultado;

			} catch (RuntimeException erro) {
				throw erro;
			} finally {
				sessao.close();
			}
		}
	 
	 public static Servidores carregaFuncionario(String id) {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

			Servidores var4;
			try {
				Criteria consulta = sessao.createCriteria(Servidores.class);
				consulta.createAlias("pessoa", "p");
				consulta.add(Restrictions.eq("p.cpf", id));
				var4 = (Servidores) consulta.setMaxResults(1).uniqueResult();
			} catch (RuntimeException var7) {
				throw var7;
			} finally {
				sessao.close();
			}

			return var4;
		}

}
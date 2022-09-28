package br.gov.sc.sgi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.util.HibernateUtil;

public class UnidadeDAO extends GenericDAO<Unidade>{
	
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
}

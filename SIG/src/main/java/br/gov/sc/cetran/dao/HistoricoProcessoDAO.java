package br.gov.sc.cetran.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import br.gov.sc.cetran.domain.Conselheiro;
import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.cetran.domain.ProcessoCetran;
import br.gov.sc.cetran.domain.Requerente;
import br.gov.sc.geapo.dao.GenericDAO;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.OficioAno;
import br.gov.sc.sgi.util.HibernateUtil;

public class HistoricoProcessoDAO extends GenericDAO<HistoricoProcesso>{
	

	
	@SuppressWarnings("unchecked")
	public List<HistoricoProcesso> listarPorData() throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(HistoricoProcesso.class);
		consulta.addOrder(Order.desc("dataDistribuicao"));
		List<HistoricoProcesso> resultado = (List<HistoricoProcesso>) consulta.list();

		return resultado;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<HistoricoProcesso> listarTudo(Conselheiro conselheiro) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			
			System.out.println(conselheiro.getCodigo());
			Criteria consulta = sessao.createCriteria(HistoricoProcesso.class);
			
			consulta.add(Restrictions.eq("conselheiro", conselheiro));
		

			List<HistoricoProcesso> resultado = consulta.list();

			return resultado;
			
			
		
			
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}

package br.gov.sc.sgi.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.RecursoMulta;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.util.HibernateUtil;

public class RecursoMultaDAO extends GenericDAO<RecursoMulta> {
	
	
	@SuppressWarnings("unchecked")
	public List<RecursoMulta> carregarRecursoMultaEmAberto(Setor setor, Unidade unidade, String status) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(RecursoMulta.class);
		
		consulta.add(Restrictions.eq("unidadeAtual", unidade));
		consulta.add(Restrictions.eq("setorAtual", setor));
		consulta.add(Restrictions.eq("status", status));
		

		List<RecursoMulta> resultado = (List<RecursoMulta>) consulta.list();

		return resultado;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RecursoMulta> listarAtivos(Setor setor, Unidade unidade, int anoHoje) throws ParseException {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(RecursoMulta.class);
			
			String sDateS = anoHoje + "-01-01";
			Date sDateF=	new SimpleDateFormat("yyyy-MM-dd").parse(sDateS);
			
			String eDateS = anoHoje + "-12-31";
			Date eDateF=new SimpleDateFormat("yyyy-MM-dd").parse(eDateS); 
			
			consulta.add(Restrictions.eq("unidadeAtual", unidade));
			consulta.add(Restrictions.eq("setorAtual", setor));
			consulta.add(Restrictions.eq("status", "Em Aberto"));
			
			consulta.add(Restrictions.ge("dataCadastro", sDateF)); 
			consulta.add(Restrictions.lt("dataCadastro", eDateF));
			
			consulta.addOrder(Order.desc("codigo"));
			
			

			List<RecursoMulta> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RecursoMulta> listarInativos(Setor setor, Unidade unidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(RecursoMulta.class);
			
			consulta.add(Restrictions.eq("unidadeAtual", unidade));
			consulta.add(Restrictions.eq("setorAtual", setor));
			consulta.add(Restrictions.eq("status", "Cancelado"));
			consulta.addOrder(Order.desc("codigo"));

			List<RecursoMulta> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public  List<RecursoMulta> buscarFiltro(String placa, String auto) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(RecursoMulta.class);
			
			 Criterion placa2 = Restrictions.eq("placa", placa);
			 
			 Criterion auto2 = Restrictions.eq("autoInfracao", auto);
			
			//consulta.add(Restrictions.eq("placa", placa));
			//consulta.add(Restrictions.eq("autoInfracao", auto));
			
			LogicalExpression orExp = Restrictions.or(placa2,auto2);
			
			consulta.add(orExp);
			
			
			List<RecursoMulta> resultado = consulta.list();
			
			//RecursoMulta resultado = (RecursoMulta) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close(); 
		}
		
	}
	
	public RecursoMulta loadLast(Setor setor, Unidade unidade) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(RecursoMulta.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("unidadeAtual", unidade));
		criteria.add(Restrictions.eq("setorAtual", setor));

		return (RecursoMulta) criteria.setMaxResults(1).uniqueResult();
	}


	
	
}

package br.gov.sc.sgi.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class OficioDAO extends GenericDAO<Oficio> {
	
	/* NÃO EDITAR O LOAD LAST!!!!!!!!!!!!!!! */
	
	public Oficio loadLast(Setor setorAbertura, Unidade unidadeAbertura) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(Oficio.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("unidadeAbertura", unidadeAbertura));
		criteria.add(Restrictions.eq("setorAbertura", setorAbertura));

		return (Oficio) criteria.setMaxResults(1).uniqueResult();
	}
	
	/* NÃO EDITAR O LOAD LAST!!!!!!!!!!!!!!! */

	@SuppressWarnings("unchecked")
	public List<Oficio> listarSetor(Setor setor, Unidade unidadeAbertura) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Oficio.class);
			
			consulta.add(Restrictions.eq("unidadeAbertura", unidadeAbertura));
			consulta.add(Restrictions.eq("setorAbertura", setor));

			List<Oficio> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Oficio> listarAtivos(Setor setor, Unidade unidadeAbertura, int anoHoje) throws ParseException {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Oficio.class);
			
			String sDateS = anoHoje + "-01-01";
			Date sDateF=new SimpleDateFormat("yyyy-MM-dd").parse(sDateS);
			
			String eDateS = anoHoje + "-12-31";
			Date eDateF=new SimpleDateFormat("yyyy-MM-dd").parse(eDateS); 
			
			
			consulta.add(Restrictions.eq("unidadeAbertura", unidadeAbertura));
			consulta.add(Restrictions.eq("setorAbertura", setor));
			consulta.add(Restrictions.like("status", "E%"));
			consulta.add(Restrictions.ge("dataOficio", sDateF)); 
			consulta.add(Restrictions.lt("dataOficio", eDateF));
			consulta.addOrder(Order.desc("numeroOficio"));
			
			

			List<Oficio> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Oficio> listarInativos(Setor setor, Unidade unidadeAbertura) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Oficio.class);
			
			consulta.add(Restrictions.eq("unidadeAbertura", unidadeAbertura));			
			consulta.add(Restrictions.eq("setorAbertura", setor));
			consulta.add(Restrictions.eq("status", "Cancelado"));
			consulta.addOrder(Order.desc("numeroOficio"));

			List<Oficio> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Oficio> listarFiltro(Long setorAbertura, Unidade unidadeAbertura) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Oficio.class);
			consulta.addOrder(Order.desc("numeroOficio"));
			consulta.add(Restrictions.eq("unidadeAbertura", unidadeAbertura));
			consulta.createAlias("setor", "p");
			consulta.add(Restrictions.eq("p.codigo", setorAbertura));

			List<Oficio> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Oficio> loadSetor(Long setorAbertura, Unidade unidadeAbertura) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(Oficio.class);
		consulta.add(Restrictions.eq("unidadeAbertura", unidadeAbertura));
		consulta.createAlias("setorAbertura", "s");
		consulta.add(Restrictions.eq("s.codigo", setorAbertura));

		List<Oficio> resultado = (List<Oficio>) consulta.list();

		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Oficio> carregarSetorPorNome(String setorAbertura, Unidade unidadeAbertura) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(Oficio.class);
		consulta.add(Restrictions.eq("unidadeAbertura", unidadeAbertura));
		consulta.createAlias("setorAbertura", "s");
		consulta.add(Restrictions.eq("s.setor", setorAbertura));

		List<Oficio> resultado = (List<Oficio>) consulta.list();

		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Oficio> carregarOficiosEmAberto(String status, Setor setor, Unidade unidadeAbertura) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(Oficio.class);
		consulta.add(Restrictions.eq("status", status));
		consulta.add(Restrictions.eq("unidadeAbertura", unidadeAbertura));
		consulta.add(Restrictions.eq("setorAbertura", setor));
		List<Oficio> resultado = (List<Oficio>) consulta.list();

		return resultado;
	}

	public Oficio listarDialogo(String campoOrdenacao, Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Oficio.class);
			
			consulta.add(Restrictions.eq("usuario", usuario));
			consulta.addOrder(Order.desc(campoOrdenacao));

			return (Oficio) consulta.setMaxResults(1).uniqueResult();

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public List<Oficio> listarPorAno(Setor setor, Unidade unidadeAbertura, int ano) throws ParseException {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Oficio.class);
			
			String sDateS = ano + "-01-01";
			Date sDateF=new SimpleDateFormat("yyyy-MM-dd").parse(sDateS);
			
			String eDateS = ano + "-12-31";
			Date eDateF=new SimpleDateFormat("yyyy-MM-dd").parse(eDateS); 
			
			
			consulta.add(Restrictions.eq("unidadeAbertura", unidadeAbertura));
			consulta.add(Restrictions.eq("setorAbertura", setor));
			consulta.add(Restrictions.like("status", "E%"));
			consulta.add(Restrictions.ge("dataOficio", sDateF)); 
			consulta.add(Restrictions.lt("dataOficio", eDateF));
			consulta.addOrder(Order.desc("numeroOficio"));
			
			

			@SuppressWarnings("unchecked")
			List<Oficio> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
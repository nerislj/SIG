package br.gov.sc.geapo.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.geapo.domain.Material;
import br.gov.sc.geapo.domain.MaterialEntrada;
import br.gov.sc.geapo.domain.MaterialEntradaHist;
import br.gov.sc.geapo.domain.MaterialTipo;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class MaterialEntradaDAO extends GenericDAO<MaterialEntrada>{
	
	public MaterialEntrada loadLast(Material material) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		
		Criteria criteria = sessao.createCriteria(MaterialEntrada.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("material", material));
		

		return (MaterialEntrada) criteria.setMaxResults(1).uniqueResult();
		
	}
	
	public MaterialEntrada iniciarMaterialEntrada(String material, String tipoMaterial) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		
		Criteria criteria = sessao.createCriteria(MaterialEntrada.class);
		criteria.addOrder(Order.desc("codigo"));
		
		criteria.createAlias("materialTipo", "t");
		criteria.add(Restrictions.eq("t.tipomaterial", tipoMaterial));
		
		criteria.createAlias("material", "m");
		criteria.add(Restrictions.eq("m.material", material));
		

		return (MaterialEntrada) criteria.setMaxResults(1).uniqueResult();
		
	}
	

	@SuppressWarnings("unchecked")
	public List<MaterialEntrada> listarMaterial(Material material) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(MaterialEntrada.class);
			
			consulta.add(Restrictions.eq("material", material));

			List<MaterialEntrada> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MaterialEntrada> listarMaior() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(MaterialEntrada.class);
			
			consulta.addOrder(Order.desc("quantidade"));
			
			List<MaterialEntrada> resultado = consulta.list();
		
			
			
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close(); 
		}
	}
	
	/*
	 * @SuppressWarnings("unchecked") public List<MaterialEntrada> listarMaiores() {
	 * Session sessao = HibernateUtil.getFabricaDeSessoes().openSession(); try {
	 * Criteria consulta = sessao.createCriteria(MaterialEntrada.class);
	 * 
	 * ProjectionList projList = Projections.projectionList();
	 * 
	 * projList.add(Projections.max("quantidade"));
	 * 
	 * consulta.setProjection(projList);
	 * 
	 * 
	 * List<MaterialEntrada> resultado = consulta.list();
	 * 
	 * return resultado;
	 * 
	 * } catch (RuntimeException erro) { throw erro; } finally { sessao.close(); } }
	 */
	
	
	public static MaterialEntrada carregaMaterialEntrada(Material material) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(MaterialEntrada.class);

		consulta.add(Restrictions.eq("material", material));

		if (consulta.setMaxResults(1).uniqueResult().equals(null)) {
			return null;
		} else {
			return (MaterialEntrada) consulta.setMaxResults(1).uniqueResult();
		}
	}
	
	
	
	
	public void salvarEntradaHist(MaterialEntrada materialEntrada, Material material, MaterialEntradaHist materialEntradaHist, Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			System.out.println(usuarioLogado);
			materialEntradaHist.setUsuarioCadastro(usuarioLogado);
			materialEntradaHist.setMaterial(material);
			materialEntradaHist.setMaterialEntrada(materialEntrada);
			materialEntradaHist.setQuantidadeHist(materialEntrada.getQuantidade());
			materialEntradaHist.setDataAlteracao(new Date());
			
			
			
			
			//sessao.update(materialEntrada);
						
			sessao.save(materialEntradaHist);
			
			//sessao.update(materialEntrada);

			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
}

package br.gov.sc.geapo.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.geapo.domain.Material;
import br.gov.sc.geapo.domain.MaterialEntrada;
import br.gov.sc.geapo.domain.MaterialTipo;
import br.gov.sc.sgi.util.HibernateUtil;

public class MaterialDAO extends GenericDAO<Material>{
	
	@SuppressWarnings("unchecked")
	public List<Material> buscarPorTipo(Long tipoCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Material.class);
			consulta.add(Restrictions.eq("materialTipo.codigo", tipoCodigo));	
			consulta.addOrder(Order.asc("material"));
			List<Material> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	
	public void salvarEntradaEstoque(MaterialEntrada materialEntrada, Material material, MaterialTipo materialTipo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			System.out.println(material + " material");
			
			materialEntrada.setMaterial(material);
			materialEntrada.setMaterialTipo(materialTipo);
			materialEntrada.setQuantidade(0);
			materialEntrada.setDataCadastro(new Date());
			
			
			
	
						
			sessao.save(materialEntrada);
			
		
			
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
	
	public static Material carregarMaterial(String centroCog) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		
		Criteria criteria = sessao.createCriteria(Material.class);
		
		criteria.add(Restrictions.eq("material", centroCog));
		

		return (Material) criteria.setMaxResults(1).uniqueResult();
	}
	
}

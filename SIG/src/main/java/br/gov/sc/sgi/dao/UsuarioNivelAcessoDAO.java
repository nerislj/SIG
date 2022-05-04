package br.gov.sc.sgi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import br.gov.sc.sgi.domain.UsuarioNivelAcesso;
import br.gov.sc.sgi.util.HibernateUtil;

public class UsuarioNivelAcessoDAO extends GenericDAO<UsuarioNivelAcesso>{
	
	@SuppressWarnings("unchecked")
	public List<UsuarioNivelAcesso> buscarPorSetor(Long setorCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(UsuarioNivelAcesso.class);
			consulta.add(Restrictions.eq("setor.codigo", setorCodigo));	
			consulta.addOrder(Order.asc("setor"));
			List<UsuarioNivelAcesso> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}


